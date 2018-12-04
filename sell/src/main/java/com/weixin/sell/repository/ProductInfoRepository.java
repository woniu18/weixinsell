package com.weixin.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weixin.sell.dataobject.ProductInfo;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>{
	
	List<ProductInfo> findByProductStatus(Integer status);

}
