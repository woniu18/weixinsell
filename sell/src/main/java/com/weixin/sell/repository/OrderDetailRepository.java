package com.weixin.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weixin.sell.dataobject.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
	
	List<OrderDetail> findByOrderId(String orderId);

}
