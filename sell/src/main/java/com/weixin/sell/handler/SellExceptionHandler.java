package com.weixin.sell.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.sell.config.ProjectUrlConfig;
import com.weixin.sell.exception.ResponseBankException;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.exception.SellerAuthorizeException;
import com.weixin.sell.utils.ResultVoUtil;
import com.weixin.sell.vo.ResultVo;

@ControllerAdvice
public class SellExceptionHandler {
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerAuthorizeException(){
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "未登陆，请进行登陆");
		map.put("url", "/sell/seller/user/login");
		return new ModelAndView("common/error",map);
	}
	
	@ExceptionHandler(value = SellException.class)
	@ResponseBody
	public ResultVo handlerSellException(SellException e){
		return ResultVoUtil.error(e.getCode(), e.getMessage());
	}
	
	@ExceptionHandler(value = ResponseBankException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ResultVo handlerResponseBankException(ResponseBankException e){
		return ResultVoUtil.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
	}
	
}
