package com.weixin.sell.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.weixin.sell.dataobject.OrderDetail;
import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;
import com.weixin.sell.utils.Date2LongSerializer;
import com.weixin.sell.utils.EnumUtil;

import lombok.Data;

@Data
//@JsonSerialize(include=Inclusion.NON_NULL)	//已经被下面注解替换
//@JsonInclude(Include.NON_NULL) //空值字段不显示；如果不想在每个类上加该注解，可以在配置文件中统一配置
public class OrderDto {
	
	@Id
	private String orderId;
	
	private String buyerName;
	
	private String buyerAddress;
	
	private String buyerPhone;
	
	private String buyerOpenid;//买家微信openid
	
	private BigDecimal orderAmount;//订单总额
	//默认0新下单
	private Integer orderStatus ;
	
	//默认0未支付
	private Integer payStatus;

	@JsonSerialize(using=Date2LongSerializer.class)
	private Date createTime;
	
	@JsonSerialize(using=Date2LongSerializer.class)
	private Date updateTime;
	
	@JsonProperty("orderDetailList")
	private List<OrderDetail> details;
	
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum(){
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}
	
	@JsonIgnore
	public PayStatusEnum getPayStatusEnum(){
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}

}
