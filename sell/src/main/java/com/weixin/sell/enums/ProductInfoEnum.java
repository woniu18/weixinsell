package com.weixin.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author Administrator
 *
 */
@Getter
public enum ProductInfoEnum implements CodeEnum{
	
	UP(0,"在架"),
	DOWN(1,"下架")
	;
	
	private Integer code;
	
	private String message;

	private ProductInfoEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	

}
