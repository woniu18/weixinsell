package com.weixin.sell.repository;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.OrderMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
	
	@Autowired
	private OrderMasterRepository repository;
	
	private final String OPENID = "110110";
	
	@Test
	public void testSave(){
		OrderMaster orderMaster = new OrderMaster();
		orderMaster.setOrderId("1234567");
		orderMaster.setBuyerName("八戒");
		orderMaster.setBuyerPhone("12345678912");
		orderMaster.setBuyerAddress("淘宝网");
		orderMaster.setBuyerOpenid(OPENID);
		orderMaster.setOrderAmount(new BigDecimal(2.5));
		
		OrderMaster master = repository.save(orderMaster);
		Assert.assertNotNull(master);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindByBuyerOpenid() {
		PageRequest request = new PageRequest(0, 2);
		Page<OrderMaster> page = repository.findByBuyerOpenid(OPENID, request);
		System.out.println(page.getNumberOfElements());page.getNumber();
		Assert.assertNotEquals(0, page.getNumberOfElements());
	}

}
