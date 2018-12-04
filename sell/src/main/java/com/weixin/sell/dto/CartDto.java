package com.weixin.sell.dto;

import lombok.Data;

@Data
public class CartDto {
	
	private String productId;
	
	private Integer productQuantity;

	public CartDto(String productId, Integer productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	
	

}
