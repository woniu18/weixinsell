package com.weixin.sell.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.weixin.sell.dataobject.ProductCategory;
import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.dto.OrderDto;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.form.ProduceForm;
import com.weixin.sell.service.CategoryService;
import com.weixin.sell.service.ProductInfoService;
import com.weixin.sell.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1")Integer page,
			@RequestParam(value="size",defaultValue="10")Integer size,
			Map<String, Object> map){
		
		PageRequest request = PageRequest.of(page-1, size);
		Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
		map.put("productInfoPage", productInfoPage);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("product/list", map);
	}
	
	@GetMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId")String productId,
			Map<String, Object> map){
		try {
			productInfoService.onSale(productId);
		} catch (SellException e) {
			log.error("【商品上架】错误{}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			new ModelAndView("common/error", map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", map);
	}
	
	@GetMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId")String productId,
			Map<String, Object> map){
		try {
			productInfoService.offSale(productId);
		} catch (SellException e) {
			log.error("【商品下架】错误{}", e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			new ModelAndView("common/error", map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success", map);
	}
	
	@GetMapping("/index")
	public ModelAndView index(@RequestParam(value="productId",required=false)String productId,
			Map<String, Object> map){
		if(!StringUtils.isEmpty(productId)){
			ProductInfo productInfo = productInfoService.findOne(productId);
			map.put("productInfo", productInfo);
		}
		
		//查询所属类目
		List<ProductCategory> categoryList = categoryService.findAll();
		map.put("categoryList", categoryList);
		return new ModelAndView("product/index", map);
	}
	
	@PostMapping("/save")
	public ModelAndView save(@Valid ProduceForm form,
			BindingResult bindingResult, Map<String, Object> map){
		if(bindingResult.hasErrors()){
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}
		
		ProductInfo productInfo = new ProductInfo();
		try {
			//如果productId为空，则为新增
			if(StringUtils.isNotEmpty(form.getProductId())){
				productInfo = productInfoService.findOne(form.getProductId());
			}else{
				form.setProductId(KeyUtil.genUniqueKey());
			}
			BeanUtils.copyProperties(form, productInfo);
			productInfoService.save(productInfo);
			
		} catch (SellException e) {
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/index");
			return new ModelAndView("common/error", map);
		}
		
		map.put("url", "/sell/seller/product/list");
		return new  ModelAndView("common/success", map);
	}

}
