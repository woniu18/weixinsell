package com.weixin.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.sell.dataobject.SellerInfo;
import com.weixin.sell.repository.SellerInfoRepository;
import com.weixin.sell.service.SellerInfoService;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {
	
	@Autowired
	private SellerInfoRepository sellerInfoRepository;

	@Override
	public SellerInfo findByOpenid(String openid) {
		SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);
		return sellerInfo;
	}

}
