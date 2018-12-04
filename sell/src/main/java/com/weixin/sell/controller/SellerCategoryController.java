package com.weixin.sell.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.sell.dataobject.ProductCategory;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.CategoryForm;
import com.weixin.sell.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list")
	public ModelAndView list(Map<String, Object> map){
		List<ProductCategory> list = categoryService.findAll();
		map.put("categoryList", list);
		return new ModelAndView("category/list", map);
	}
	
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value="categoryId",required=false)Integer categoryId,
			Map<String, Object> map){
		if(null != categoryId && categoryId > 0){
			ProductCategory category = categoryService.findOne(categoryId);
			map.put("category", category);
		}
		return new ModelAndView("category/index", map);
	}
	
	/**
	 * 保存
	 * @param form
	 * @param bindingResult
	 * @param map
	 * @return
	 */
	@PostMapping("/save")
	public ModelAndView save(@Valid CategoryForm form,
			BindingResult bindingResult, Map<String, Object> map){
		if(bindingResult.hasErrors()){
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/category/list");
			return new ModelAndView("common/error", map);
		}
		
		ProductCategory category = new ProductCategory();
		try {
			if(form.getCategoryId() != null){
				category = categoryService.findOne(form.getCategoryId());
			}
			BeanUtils.copyProperties(form, category);
			categoryService.save(category);
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/category/list");
			return new ModelAndView("common/error", map);
		}
		map.put("url", "/sell/seller/category/list");
		return new ModelAndView("common/success", map);
		
	}
	

}
