package com.weixin.sell.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.sell.config.WechatAccountConfig;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.service.PushMessageService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

	@Autowired
	private WxMpService wxMpService;
	
	@Autowired
	private WechatAccountConfig wechatAccountConfig;
	
	@Override
	public void orderStatus(OrderDto orderDto) {
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("orderSattus"));
		templateMessage.setToUser(orderDto.getBuyerOpenid());
		List<WxMpTemplateData> data = Arrays.asList(
				new WxMpTemplateData("first", "亲，请记得收货。"),
				new WxMpTemplateData("keyword1", "微信点餐"),
				new WxMpTemplateData("keyword2", "18868812345"),
				new WxMpTemplateData("keyword3", orderDto.getOrderId()),
				new WxMpTemplateData("keyword4", orderDto.getOrderStatusEnum().getMessage()),
				new WxMpTemplateData("keyword5", "￥"+orderDto.getOrderAmount()),
				new WxMpTemplateData("remark", "欢迎再次光临")
				);
		templateMessage.setData(data);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
			log.error("【微信模板消息】发送失败，{}",e);
		}

	}

}
