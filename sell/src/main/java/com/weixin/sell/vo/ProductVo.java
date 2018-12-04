package com.weixin.sell.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 商品信息（包含类目）
 * @author Administrator
 *
 */
@Data
public class ProductVo {
	
	@JsonProperty("name")
	private String categoryName;
	
	@JsonProperty("type")
	private Integer categoryType;
	
	@JsonProperty("foods")
	private List<ProductInfoVo> productInfoVoList;

}
