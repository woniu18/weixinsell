package com.weixin.sell.vo;

import lombok.Data;

/**
 * http请求返回对象
 * @author Administrator
 *
 */
@Data
public class ResultVo<T> {
	
	private Integer code;
	
	private String msg;
	
	private T data;

}
