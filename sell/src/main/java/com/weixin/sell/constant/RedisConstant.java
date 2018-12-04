package com.weixin.sell.constant;

import lombok.Data;

public interface RedisConstant {
	
	String TOKEN_PREFIX = "token_%s";
	
	Integer EXPIRE = 7200;//2小时

}
