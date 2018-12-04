package com.weixin.sell.form;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 订单表单
 * @author Administrator
 *
 */
@Data
public class OrderForm {
	
	/**
	 * 买家姓名
	 */
	@NotEmpty(message="姓名必填")
	private String name;
	
	@NotEmpty(message="手机号必填")
	private String phone;
	
	@NotEmpty(message="地址必填")
	private String address;
	
	@NotEmpty(message="openId必填")
	private String openid;
	
	@NotEmpty(message="购物车不能为空")
	private String items;
	

}
