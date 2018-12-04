package com.weixin.sell.utils;

import com.weixin.sell.enums.CodeEnum;

public class EnumUtil {
	
	public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enaumClass){
		for (T each : enaumClass.getEnumConstants()) {
			if(code.equals(each.getCode())){
				return each;
			}
		}
		return null;
	}

}
