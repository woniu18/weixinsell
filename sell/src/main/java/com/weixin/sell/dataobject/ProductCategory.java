package com.weixin.sell.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate	//动态更新updateTime
@Data
public class ProductCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	private String categoryName;
	
	private Integer categoryType;
	
	private Date createTime;
	
	private Date updateTime;

	public ProductCategory(String categoryName, Integer categoryType) {
		super();
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}

	public ProductCategory() {
		
	}
	
	
	
}
