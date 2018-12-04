package com.weixin.sell.dataobject.mapper;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {
	
	@Autowired
	private ProductCategoryMapper mapper;

	@Test
	public void testInsertByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("categoryName", "最后欢迎");
		map.put("categoryType", 102);
		int insertByMap = mapper.insertByMap(map);
		System.out.println(insertByMap);
		
	}

}
