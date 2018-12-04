package com.weixin.sell.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weixin.sell.dataobject.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
	
	@Autowired
	private ProductCategoryRepository pcRepository;

	@Test
	public void test() {
		Optional<ProductCategory> findById = pcRepository.findById(1);
		ProductCategory productCategory = findById.get();
		System.out.println("信息："+productCategory.toString());
		
	}
	
	@Test
	public void saveTest() {
		ProductCategory productCategory = new ProductCategory("男生最爱",10);
		ProductCategory save = pcRepository.save(productCategory);
		System.out.println("添加："+save.toString());
	}
	
	@Test
	public void updateTest() {
		Optional<ProductCategory> findById = pcRepository.findById(2);
		ProductCategory productCategory = findById.get();
		productCategory.setCategoryType(3);
		ProductCategory save = pcRepository.save(productCategory);
		System.out.println("添加："+save.toString());
	}
	
	@Test
	public void typesTest() {
		List<Integer> list  = new ArrayList<>();
		list.add(1);
		list.add(3);
		List<ProductCategory> types = pcRepository.findByCategoryTypeIn(list);
		System.out.println("types："+types.size());
	}

}
