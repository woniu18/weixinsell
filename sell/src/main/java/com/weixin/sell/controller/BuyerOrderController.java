package com.weixin.sell.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.sell.converter.OrderForm2OrderDto;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.OrderForm;
import com.weixin.sell.service.BuyerService;
import com.weixin.sell.service.OrderService;
import com.weixin.sell.utils.ResultVoUtil;
import com.weixin.sell.vo.ResultVo;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BuyerService BuyerService;
	
	//创建订单
	@PostMapping("/create")
	public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
												BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			log.error("【创建订单】参数不正确，orderForm={}",orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		OrderDto orderDto = OrderForm2OrderDto.convert(orderForm);
		if(CollectionUtils.isEmpty(orderDto.getDetails())){
			log.error("【创建订单】购物车不能为空");
			throw new SellException(ResultEnum.CAR_EMPTY);
		}
		OrderDto create = orderService.create(orderDto);
		Map<String, String> map = new HashMap<>();
		map.put("orderId", create.getOrderId());
		
		return ResultVoUtil.success(map);
	}
	
	//订单列表
	@GetMapping("/list")
	public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid,
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="size",defaultValue="10") Integer size){
		if(StringUtils.isEmpty(openid)){
			log.error("【查询订单列表】openid为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		PageRequest request = PageRequest.of(page, size);
		Page<OrderDto> list = orderService.findList(openid, request);
		return ResultVoUtil.success(list.getContent());
		
	}
	
	//订单详情
	@GetMapping("/detail")
	public ResultVo<OrderDto> detail(@RequestParam("openid") String openid,
			@RequestParam("orderId")String orderId){
		OrderDto orderDto = BuyerService.findOrderOne(openid, orderId);
		return ResultVoUtil.success(orderDto);
	}
	
	//取消订单
	@PostMapping("/cancel")
	public ResultVo cancel(@RequestParam("openid") String openid,
			@RequestParam("orderId")String orderId){
		BuyerService.cancelOrder(openid, orderId);
		return ResultVoUtil.success();
	}

}
