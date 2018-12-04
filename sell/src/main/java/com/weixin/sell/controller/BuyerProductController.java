package com.weixin.sell.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weixin.sell.dataobject.ProductCategory;
import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.service.CategoryService;
import com.weixin.sell.service.ProductInfoService;
import com.weixin.sell.utils.ResultVoUtil;
import com.weixin.sell.vo.ProductInfoVo;
import com.weixin.sell.vo.ProductVo;
import com.weixin.sell.vo.ResultVo;

/**
 * 买家商品
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list")
	public ResultVo list(){
		//1.查询所有上架的商品
		List<ProductInfo> productInfoList = productInfoService.findUpAll();
		//2.查询类目（一次性查询）
			//java8,lambda
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
		//3.数据拼装
		List<ProductVo> productVoList = new ArrayList<>();
		for (ProductCategory productCategory : categoryList) {
			ProductVo productVo = new ProductVo();
			productVo.setCategoryType(productCategory.getCategoryType());
			productVo.setCategoryName(productCategory.getCategoryName());
			
			List<ProductInfoVo> productInfoVoList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
					ProductInfoVo productInfoVo = new ProductInfoVo();
					BeanUtils.copyProperties(productInfo, productInfoVo);
					productInfoVoList.add(productInfoVo);
				}
			}
			productVo.setProductInfoVoList(productInfoVoList);
			productVoList.add(productVo);
		}
		
		return ResultVoUtil.success(productVoList);
	}

}
