package com.weixin.sell;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.PageAdapter;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.service.impl.ProductInfoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
	
	@Autowired
	private ProductInfoServiceImpl service;

	@Test
	public void testFindOne() {
		//fail("Not yet implemented");
	}

	@Test
	public void testFindUpAll() {
		//fail("Not yet implemented");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testFindAll() {
		PageRequest request = new PageRequest(0, 2);
		Page<ProductInfo> page = service.findAll(request);
		
		List<ProductInfo> content = page.getContent();
		int size = page.getSize();
		System.out.println(size);
	}

	@Test
	public void testSave() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123457");
		productInfo.setProductName("蔬菜粥");
		productInfo.setProductPrice(new BigDecimal(3.2));
		productInfo.setProductStock(100);
		productInfo.setProductDescription("很好喝的蔬菜粥");
		productInfo.setProductIcon("http://xxx.jpg");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(2);
		service.save(productInfo);
	}

}
