package com.weixin.sell;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogTest {
	
	@Test
	public void test1(){
		log.info("info...");
		log.debug("name:{},password:{}","zhangsan","123");
		log.info("name:{},password:{}","zhangsan","123");
		log.error("name:{},password:{}","zhangsan","123");
	}

}
