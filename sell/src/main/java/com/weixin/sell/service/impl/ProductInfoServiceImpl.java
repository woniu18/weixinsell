package com.weixin.sell.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.sell.dataobject.ProductInfo;
import com.weixin.sell.dto.CartDto;
import com.weixin.sell.enums.ProductInfoEnum;
import com.weixin.sell.enums.ResultEnum;
import com.weixin.sell.exception.SellException;
import com.weixin.sell.repository.ProductInfoRepository;
import com.weixin.sell.service.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoRepository piRepostory;
	
	@Override
	public ProductInfo findOne(String productId) {
		Optional<ProductInfo> optional = piRepostory.findById(productId);
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

	@Override
	public List<ProductInfo> findUpAll() {
		List<ProductInfo> list = piRepostory.findByProductStatus(ProductInfoEnum.UP.getCode());
		return list;
	}

	@Override
	public Page<ProductInfo> findAll(Pageable page) {
		Page<ProductInfo> all = piRepostory.findAll(page);
		return all;
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		ProductInfo save = piRepostory.save(productInfo);
		return save;
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDto> cartList) {
		for (CartDto cartDto : cartList) {
			Optional<ProductInfo> optional = piRepostory.findById(cartDto.getProductId());
			ProductInfo productInfo = optional.get();
			if(productInfo == null){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() + cartDto.getProductQuantity();
			productInfo.setProductStock(result);
			piRepostory.save(productInfo);
			
		}
		
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDto> cartList) {
		for (CartDto cartDto : cartList) {
			Optional<ProductInfo> optional = piRepostory.findById(cartDto.getProductId());
			ProductInfo productInfo = optional.get();
			if(null == productInfo){
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
			if(result < 0){
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}
			productInfo.setProductStock(result);
			piRepostory.save(productInfo);
		}
		
	}

	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = this.findOne(productId);
		if(null == productInfo){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		if(productInfo.getProductInfoEnum() == ProductInfoEnum.UP){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductInfoEnum.UP.getCode());
		ProductInfo save = piRepostory.save(productInfo);
		return save;
	}

	@Override
	public ProductInfo offSale(String productId) {
		ProductInfo productInfo = this.findOne(productId);
		if(null == productInfo){
			throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		}
		if(productInfo.getProductInfoEnum() == ProductInfoEnum.DOWN){
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductInfoEnum.DOWN.getCode());
		ProductInfo save = piRepostory.save(productInfo);
		return save;
	}

}
