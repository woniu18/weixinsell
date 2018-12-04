package com.weixin.sell.service;

import com.weixin.sell.dto.OrderDto;

public interface BuyerService {
	
	OrderDto findOrderOne(String openid, String orderId);
	
	OrderDto cancelOrder(String openid, String orderId);

}
