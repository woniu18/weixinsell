package com.weixin.sell.dataobject.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;

public interface ProductCategoryMapper {
	
	@Insert("insert into product_categroy(category_name,category_type) values(#{categoryName,jdbcType=VARCHAR, #{categoryType,jdbcType=INTEGER})")
	int insertByMap(Map<String, Object> map);

}
