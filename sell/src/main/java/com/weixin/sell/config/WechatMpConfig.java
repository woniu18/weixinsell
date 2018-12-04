package com.weixin.sell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WechatMpConfig {
	
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Bean
	public WxMpService wxMpService(){
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}
	
	@Bean
	public WxMpConfigStorage wxMpConfigStorage(){
		WxMpInMemoryConfigStorage storage = new WxMpInMemoryConfigStorage();
		storage.setAppId(accountConfig.getMpAppId());
		storage.setSecret(accountConfig.getMpAppSecret());
		return storage;
	}

}
