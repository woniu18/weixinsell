package com.weixin.sell.dataobject;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * 订单详情
 * @author Administrator
 *
 */
@Entity
@Data
public class OrderDetail {
	
	@Id
	private String detailId;
	
	private String orderId;
	
	private String productId;
	
	private String productName;
	
	private BigDecimal productPrice;
	
	//商品数量
	private Integer productQuantity;
	
	private String productIcon;

}
