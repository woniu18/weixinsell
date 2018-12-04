package com.weixin.sell.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.weixin.sell.dataobject.OrderMaster;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

	Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);
	
}
