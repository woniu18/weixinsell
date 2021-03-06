package com.weixin.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.service.BuyerService;
import com.weixin.sell.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
	
	@Autowired
	private OrderService orderService;

	@Override
	public OrderDto findOrderOne(String openid, String orderId) {
		OrderDto orderDto = checkOrderOwner(openid, orderId);
		return orderDto;
	}

	@Override
	public OrderDto cancelOrder(String openid, String orderId) {
		OrderDto orderDto = checkOrderOwner(openid, orderId);
		if(orderDto == null){
			log.error("【取消订单】查不到该订单，orderId={}",orderId);
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancel(orderDto);
	}
	
	private OrderDto checkOrderOwner(String openid, String orderId){
		OrderDto orderDto = orderService.findOne(orderId);
		if(orderDto == null){
			return null;
		}
		//判断是否是自己的 订单
		if(!orderDto.getBuyerOpenid().equals(openid)){
			log.error("【查询订单】订单的openid不一致, openid={}, orderDto={}", openid, orderDto);
			throw new SellException(ResultEnum.order_owner_error);
		}
		return orderDto;
	}

}
