package com.weixin.sell.utils;

import com.weixin.sell.vo.ResultVo;

/**
 * 返回结果工具类
 * @author Administrator
 *
 */
public class ResultVoUtil {
	
	public static ResultVo success(Object object){
		ResultVo<Object> resultVo = new ResultVo<>();
		resultVo.setCode(0);
		resultVo.setMsg("成功");
		resultVo.setData(object);
		return resultVo;
	}
	
	public static ResultVo success(){
		return success(null);
	}
	
	public static ResultVo error(Integer code,String msg){
		ResultVo<Object> resultVo = new ResultVo<>();
		resultVo.setCode(code);
		resultVo.setMsg(msg);
		return resultVo;
	}
}
