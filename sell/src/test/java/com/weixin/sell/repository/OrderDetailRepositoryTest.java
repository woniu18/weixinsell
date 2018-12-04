package com.weixin.sell.repository;


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.OrderDetail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
	
	@Autowired
	private OrderDetailRepository repository;
	
	@Test
	public void testSave(){
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setDetailId("123456789");
		orderDetail.setOrderId("111111112");
		orderDetail.setProductIcon("http://xxx.jpg");
		orderDetail.setProductId("1111113");
		orderDetail.setProductName("皮蛋瘦肉粥");
		orderDetail.setProductPrice(new BigDecimal(2.2));
		orderDetail.setProductQuantity(3);
		OrderDetail detail = repository.save(orderDetail);
		Assert.assertNotNull(detail);
	}

	@Test
	public void testFindByOrderId() {
		
	}

}
