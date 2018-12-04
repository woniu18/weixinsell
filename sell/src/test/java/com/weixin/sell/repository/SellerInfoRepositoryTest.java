package com.weixin.sell.repository;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.SellerInfo;
import com.weixin.sell.service.SellerInfoService;
import com.weixin.sell.utils.KeyUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
	
	@Autowired
	private SellerInfoRepository repository;
	
	@Autowired
	private SellerInfoService service;

	@Test
	public void test() {
		SellerInfo sellerInfo = new SellerInfo();
		sellerInfo.setSellerId(KeyUtil.genUniqueKey());
		sellerInfo.setUsername("admin");
		sellerInfo.setPassword("admin");
		sellerInfo.setOpenid("abc");
		SellerInfo save = repository.save(sellerInfo);
		Assert.assertNotNull(save);
	}
	
	@Test
	public void serviceTest() {
		SellerInfo sellerInfo = service.findByOpenid("abc");
		Assert.assertEquals("abc", sellerInfo.getOpenid());
	}

}
