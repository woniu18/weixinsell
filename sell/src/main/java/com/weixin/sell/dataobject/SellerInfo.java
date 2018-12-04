package com.weixin.sell.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
public class SellerInfo {
	
	@Id
	private String sellerId;
	
	private String username;
	
	private String  password;
	
	private String  openid;
	
	private Date createTime;
	
	private Date updateTime;

}
