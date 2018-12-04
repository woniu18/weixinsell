package com.weixin.sell.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weixin.sell.dataobject.ProductCategory;
import com.weixin.sell.repository.ProductCategoryRepository;
import com.weixin.sell.service.CategoryService;

/**
 * 类目
 * @author Administrator
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	
	private ProductCategoryRepository pcRepository;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		Optional<ProductCategory> optional = pcRepository.findById(categoryId);
		ProductCategory productCategory = optional.get();
		return productCategory;
	}

	@Override
	public List<ProductCategory> findAll() {
		return pcRepository.findAll();
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> types) {
		return pcRepository.findByCategoryTypeIn(types);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return pcRepository.save(productCategory);
	}

}
