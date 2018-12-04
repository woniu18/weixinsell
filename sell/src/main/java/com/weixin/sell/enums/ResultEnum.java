package com.weixin.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
	SUCCESS(0,"成功"),
	PARAM_ERROR(1,"参数不正确"),
	PRODUCT_NOT_EXIST(10,"商品不存在"),
	PRODUCT_STOCK_ERROR(11,"商品库存错误"),
	ORDER_NOT_EXIST(12,"订单不存在"),
	ORDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
	ORDER_STATUS_ERROR(14,"订单状态不正确"),
	ORDER_UPDATE_FAIL(15,"订单更新失败"),
	ORDER_DETAIL_EMPTY(16,"订单无商品详情"),
	ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
	CAR_EMPTY(18,"购物车为空"),
	order_owner_error(19,"该订单不属于当前用户"),
	ORDER_CANCEL_SUCCESS(20,"取消订单成功"),
	ORDER_FINISH_SUCCESS(21,"完结订单成功"),
	PRODUCT_STATUS_ERROR(22,"商品状态不正确"),
	WECHAT_MP_ERROR(23,"微信公众账号方面错误"),
	LOGIN_FAIL(24,"登陆失败"),
	LOGOUT_SUCCESS(25,"登出成功"),
	;
	
	private Integer code;
	
	private String message;

	private ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
	

}
