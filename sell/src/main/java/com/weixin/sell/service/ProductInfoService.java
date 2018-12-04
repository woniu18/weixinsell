package com.weixin.sell.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.dto.CartDto;

/**
 * 商品
 * @author Administrator
 *
 */
public interface ProductInfoService {
	
	ProductInfo findOne(String productId);
	
	//在架商品
	List<ProductInfo> findUpAll();
	
	Page<ProductInfo> findAll(Pageable page);
	
	ProductInfo save(ProductInfo productInfo);
	
	//加库存
	void increaseStock(List<CartDto> cartList);
	
	//减库存
	void decreaseStock(List<CartDto> cartList);
	
	//上架
	ProductInfo onSale(String productId);
	
	//下架
	ProductInfo offSale(String productId);
	
}
