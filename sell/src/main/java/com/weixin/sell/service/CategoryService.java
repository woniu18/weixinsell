package com.weixin.sell.service;

import java.util.List;

import com.weixin.sell.dataobject.ProductCategory;

public interface CategoryService {
	
	ProductCategory findOne(Integer categoryId);
	
	List<ProductCategory> findAll();
	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> types);
	
	ProductCategory save(ProductCategory productCategory);

}
