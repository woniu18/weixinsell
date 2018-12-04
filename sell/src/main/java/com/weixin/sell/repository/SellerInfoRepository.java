package com.weixin.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weixin.sell.dataobject.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
	
	SellerInfo findByOpenid(String openid);

}
