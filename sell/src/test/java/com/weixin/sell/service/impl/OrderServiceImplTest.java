package com.weixin.sell.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PageRanges;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.OrderDetail;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
	
	@Autowired
	private OrderService orderService;
	
	private final String BUYER_OPENID = "110110";

	@Test
	public void testCreate() {
		OrderDto orderDto = new OrderDto();
		orderDto.setBuyerName("大师兄");
		orderDto.setBuyerAddress("花果山");
		orderDto.setBuyerPhone("12345678910");
		orderDto.setBuyerOpenid(BUYER_OPENID);
		//购物车
		List<OrderDetail> orderDetailList = new ArrayList<>();
		OrderDetail detail1 = new OrderDetail();
		detail1.setProductId("123456");
		detail1.setProductQuantity(1);
		orderDetailList.add(detail1);
		OrderDetail detail2 = new OrderDetail();
		detail2.setProductId("123457");
		detail2.setProductQuantity(1);
		orderDetailList.add(detail2);
		
		orderDto.setDetails(orderDetailList);
		OrderDto result = orderService.create(orderDto);
		log.info("【创建订单】result={}",result);
		
	}

	@Test
	public void testFindOne() {
		OrderDto orderDto = orderService.findOne("1542270027980303780");
		log.info("【查询单个订单信息】result={}",orderDto);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindList() {
		PageRequest request = new PageRequest(0, 2);
		//PageRequest request2 = PageRequest.of(0, 2);
		Page<OrderDto> page = orderService.findList(BUYER_OPENID, request);
		log.info("【查询用户的订单信息】result={}",page);
	}

	@Test
	public void testCancel() {
		OrderDto orderDto = orderService.findOne("1542270027980303780");
		OrderDto result = orderService.cancel(orderDto);
		log.info("【取消订单】resul={}",result);
	}

	@Test
	public void testFinish() {
		OrderDto orderDto = orderService.findOne("1542269939638722626");
		OrderDto result = orderService.finish(orderDto);
		Assert.assertEquals(null,OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	
	}

	@Test
	public void testPaid() {
		OrderDto orderDto = orderService.findOne("1542269885726645303");
		OrderDto result = orderService.paid(orderDto);
		//Assert.assertEquals(null,OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
	
	}

}
