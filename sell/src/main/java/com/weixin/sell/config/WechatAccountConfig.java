package com.weixin.sell.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatAccountConfig {
	
	//公众平台Id
	private String mpAppId;
	
	//公众平台密钥
	private String mpAppSecret;
	
	//开放平台ID
	private String openAppId;
	
	//开放平台密钥
	private String openAppSecret;
	
	//微信模板id
	private Map<String, String> templateId;
	
}
