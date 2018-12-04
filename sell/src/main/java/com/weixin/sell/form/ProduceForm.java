package com.weixin.sell.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProduceForm {
	
	private String productId;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private Integer productStock;//库存
	
	private String productDescription;
	
	private String productIcon;//小图'
	
	private Integer categoryType;

}
