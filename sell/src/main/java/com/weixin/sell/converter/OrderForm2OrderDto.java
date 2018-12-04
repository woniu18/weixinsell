package com.weixin.sell.converter;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.internal.bytebuddy.description.type.TypeVariableToken;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weixin.sell.dataobject.OrderDetail;
import com.weixin.sell.dto.CartDto;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.OrderForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderForm2OrderDto {
	
	public static OrderDto convert(OrderForm orderForm){
		Gson gson = new Gson();
		OrderDto orderDto = new OrderDto();
		orderDto.setBuyerName(orderForm.getName());
		orderDto.setBuyerPhone(orderForm.getPhone());
		orderDto.setBuyerAddress(orderForm.getAddress());
		orderDto.setBuyerOpenid(orderForm.getOpenid());
		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(), 
					new TypeToken<List<OrderDetail>>(){}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误，String={}",orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		
		orderDto.setDetails(orderDetailList);
		return orderDto;
		
	}

}
