package com.weixin.sell.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="projecturl")
public class ProjectUrlConfig {
	
	private String wechatMpAuthorize;
	
	private String wechatOpenAuthorize;
	
	private String sell;

}
