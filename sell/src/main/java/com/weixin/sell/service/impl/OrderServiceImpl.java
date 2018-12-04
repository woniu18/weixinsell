package com.weixin.sell.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.weixin.sell.converter.OrderMaster2OrderDto;
import com.weixin.sell.dataobject.OrderDetail;
import com.weixin.sell.dataobject.OrderMaster;
import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.dto.CartDto;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.ResponseBankException;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.repository.OrderDetailRepository;
import com.weixin.sell.repository.OrderMasterRepository;
import com.weixin.sell.service.OrderService;
import com.weixin.sell.service.ProductInfoService;
import com.weixin.sell.service.PushMessageService;
import com.weixin.sell.service.WebSocket;
import com.weixin.sell.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private OrderDetailRepository OrderDetailRepository;
	
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private WebSocket webSocket;

	@Override
	@Transactional
	public OrderDto create(OrderDto orderDto) {
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		//1.查询商品(数量，价格)
		List<OrderDetail> details = orderDto.getDetails();
		for (OrderDetail orderDetail : details) {
			ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
			if(productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
				//throw new ResponseBankException();
			}
			//2.算总价
			orderAmount = productInfo.getProductPrice()
					.multiply(new BigDecimal(orderDetail.getProductQuantity()))
					.add(orderAmount);
			//订单详情入库
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetail.setOrderId(orderId);
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			OrderDetailRepository.save(orderDetail);
		}
		
		//3.写入订单数据库（orderMaster和detail）
		OrderMaster orderMaster = new OrderMaster();
		orderDto.setOrderId(orderId);
		BeanUtils.copyProperties(orderDto, orderMaster);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMaster.setOrderAmount(orderAmount);
		orderMasterRepository.save(orderMaster);
		//4.扣除库存
		List<CartDto> cartDtoList = orderDto.getDetails().stream().map(e -> 
			new CartDto(e.getProductId(), e.getProductQuantity()))
				.collect(Collectors.toList());
		productInfoService.decreaseStock(cartDtoList);
		//发送websocket消息
		webSocket.sendMessage("有新的订单");
		
		return orderDto;
	}

	@Override
	public OrderDto findOne(String orderId) {
		Optional<OrderMaster> optional = orderMasterRepository.findById(orderId);
		if(!optional.isPresent()){
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		OrderMaster orderMaster = optional.get();
		List<OrderDetail> details = OrderDetailRepository.findByOrderId(orderId);
		if(CollectionUtils.isEmpty(details)){
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderMaster, orderDto);
		orderDto.setDetails(details);
		
		return orderDto;
	}

	@Override
	public Page<OrderDto> findList(String buyerOpenid, Pageable page) {
		Page<OrderMaster> OrderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, page);
		List<OrderDto> orderDtoList = OrderMaster2OrderDto.convert(OrderMasterPage.getContent());
		Page<OrderDto> pageOrderDto = new PageImpl<>(orderDtoList, page, OrderMasterPage.getTotalElements());

		return pageOrderDto;
	}

	@Override
	@Transactional
	public OrderDto cancel(OrderDto orderDto) {
		OrderMaster orderMaster = new OrderMaster();
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【取消订单】订单状态不正确，orderId={}，orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		
		//修改订单状态
		orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster save = orderMasterRepository.save(orderMaster);
		if(null == save){
			log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		//返回库存
		if(CollectionUtils.isEmpty(orderDto.getDetails())){
			log.error("【取消订单】订单中无商品详情，orderDto={}",orderDto);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDto> cartDtoList = orderDto.getDetails().stream().map(e ->
		new CartDto(e.getProductId(), e.getProductQuantity()))
		.collect(Collectors.toList());
		productInfoService.increaseStock(cartDtoList);
		
		//如果已支付，需要退款
		if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS)){
			//TODO
		}
		
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto finish(OrderDto orderDto) {
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【完结订单】订单状态不正确，orderId={}，orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster save = orderMasterRepository.save(orderMaster);
		if(null == save){
			log.error("【完结订单】更新订单状态失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		//推送微信模板消息
		pushMessageService.orderStatus(orderDto);
		
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto paid(OrderDto orderDto) {
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
			log.error("【订单支付完成】订单状态不正确，orderId={}，orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//判断支付状态
		if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
			log.error("【订单支付完成】订单支付状态不正确，orderDto={}",orderDto);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		//修改支付状态
		orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());		
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster save = orderMasterRepository.save(orderMaster);
		if(null == save){
			log.error("【订单支付完成】更新支付状态失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDto;
	}

	@Override
	public Page<OrderDto> findList(Pageable page) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(page);
		List<OrderDto> orderDtoList = OrderMaster2OrderDto.convert(orderMasterPage.getContent());
		Page<OrderDto> pageOrderDto = new PageImpl<>(orderDtoList, page, orderMasterPage.getTotalElements());
		return pageOrderDto;
	}

}
