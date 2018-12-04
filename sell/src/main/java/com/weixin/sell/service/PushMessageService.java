package com.weixin.sell.service;

import com.weixin.sell.dto.OrderDto;

public interface PushMessageService {
	
	void orderStatus(OrderDto orderDto);

}
