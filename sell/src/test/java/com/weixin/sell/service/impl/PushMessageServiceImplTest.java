package com.weixin.sell.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.service.OrderService;
import com.weixin.sell.service.PushMessageService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PushMessageServiceImplTest {
	
	@Autowired
	private PushMessageService service;
	
	@Autowired
	private OrderService orderService;

	@Test
	public void testOrderStatus() {

		OrderDto orderDto = orderService.findOne("1542793702431530078");
		
		service.orderStatus(orderDto);
	}
	
	@Test
	public void test(){
		Jedis jedis = new Jedis("192.168.80.129",6379);
		Client client = jedis.getClient();
		jedis.connect();
		String set = jedis.set("aaa", "123", "NX", "PX", 3000);
		String string = jedis.get("aaa");
		System.out.println("value:"+string);
		System.out.println(set);
	}

}
