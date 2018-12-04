package com.weixin.sell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WechatOpenConfig {
	
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Bean
	public WxMpService wxOpenService(){
		WxMpService wxOpenService = new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
		return wxOpenService;
	}
	
	@Bean
	public WxMpConfigStorage wxOpenConfigStorage(){
		WxMpInMemoryConfigStorage storage = new WxMpInMemoryConfigStorage();
		storage.setAppId(accountConfig.getOpenAppId());
		storage.setSecret(accountConfig.getOpenAppSecret());
		return storage;
	}

}
