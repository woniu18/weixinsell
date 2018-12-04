package com.weixin.sell.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weixin.sell.dataobject.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer>{
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> types);

}
