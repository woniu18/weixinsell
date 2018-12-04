package com.weixin.sell.utils;

import java.util.Random;

/**
 * 主键生成
 * @author Administrator
 *
 */
public class KeyUtil {
	
	/**
	 * 生成唯一主键：时间+6位随机数
	 * 
	 * 多线程时防止重复,加synchronized
	 * @return
	 */
	public static synchronized String genUniqueKey(){
		Random random = new Random();
		Integer number = random.nextInt(900000) + 100000;
		return System.currentTimeMillis() + String.valueOf(number);
	}

}
