package com.weixin.sell.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.weixin.sell.dataobject.OrderMaster;
import com.weixin.sell.dto.OrderDto;

public class OrderMaster2OrderDto {
	
	public static OrderDto convert(OrderMaster orderMaster){
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderMaster, orderDto);
		return orderDto;
	}
	
	public static List<OrderDto> convert(List<OrderMaster> orderMasterList){
		
		List<OrderDto> list = orderMasterList.stream().map(e -> 
				convert(e)
				).collect(Collectors.toList());
		return list;
		
	}

}
