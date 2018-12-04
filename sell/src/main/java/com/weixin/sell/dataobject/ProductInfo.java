package com.weixin.sell.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weixin.sell.enums.ProductInfoEnum;
import com.weixin.sell.utils.EnumUtil;

import lombok.Data;

/**
 * 商品信息
 * @author Administrator
 *
 */
@Data
@Entity
@DynamicUpdate
public class ProductInfo {
	
	@Id
	private String productId;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	private Integer productStock;//库存
	
	private String productDescription;
	
	private String productIcon;//小图'
	
	private Integer productStatus = ProductInfoEnum.UP.getCode();//状态：0正常1下架
	
	private Integer categoryType;
	
	private Date createTime;
	
	private Date updateTime;
	
	@JsonIgnore
	public ProductInfoEnum getProductInfoEnum(){
		return EnumUtil.getByCode(productStatus, ProductInfoEnum.class);
	}

}
