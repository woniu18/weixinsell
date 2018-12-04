package com.weixin.sell.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.sell.config.ProjectUrlConfig;
import com.weixin.sell.constant.CookieConstant;
import com.weixin.sell.constant.RedisConstant;
import com.weixin.sell.dataobject.SellerInfo;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.service.SellerInfoService;
import com.weixin.sell.utils.CookieUtil;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/seller")
public class SellerUserController {
	
	@Autowired
	private SellerInfoService sellerInfoService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	@GetMapping("/user/login")
	public ModelAndView login(){
		return new ModelAndView("login/login");
	}
	
	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openid")String openid,
			HttpServletResponse response,
			Map<String, Object> map){
		//1. openid去和数据库里的数据匹配
		SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
		if(sellerInfo == null){
			map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error", map);
		}
		//设置token至Redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
		//设置token至cookie中
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
	}
	
	@SuppressWarnings("resource")
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response,
			Map<String, Object> map){
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if(cookie != null){
			//清除redis
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
			//清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		}
		
		map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success", map);
	}

}
