package com.weixin.sell.service;

import com.weixin.sell.dataobject.SellerInfo;

public interface SellerInfoService {
	
	SellerInfo findByOpenid(String openid);

}
