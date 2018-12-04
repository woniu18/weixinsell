package com.weixin.sell.dataobject;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.weixin.sell.enums.OrderStatusEnum;
import com.weixin.sell.enums.PayStatusEnum;

import lombok.Data;

/**
 * 订单
 * @author Administrator
 *
 */
@Entity
@Data
public class OrderMaster {
	
	@Id
	private String orderId;
	
	private String buyerName;
	
	private String buyerAddress;
	
	private String buyerPhone;
	
	private String buyerOpenid;//买家微信openid
	
	private BigDecimal orderAmount;//订单总额
	//默认0新下单
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();
	
	//默认0未支付
	private Integer payStatus = PayStatusEnum.WAIT.getCode();

	private Date createTime;
	
	private Date updateTime;
}
