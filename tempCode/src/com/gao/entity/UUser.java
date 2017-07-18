package com.gao.entity;

import java.util.Date;
import com.golive.common.util.DateUtils;

public class UUser implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Integer id;
	private String name;
	private String loginName;
	private String roleName;
	private Date createTime;
	private Date lastLoginTime;
	private Integer dataFlag;
	private String password;

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return this.id;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setLoginName(String value) {
		this.loginName = value;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setRoleName(String value) {
		this.roleName = value;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeStr() {
		return DateUtils.dTS(this.createTime,DateUtils.dt);
	}
	
	public void setLastLoginTime(Date value) {
		this.lastLoginTime = value;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public String getLastLoginTimeStr() {
		return DateUtils.dTS(this.lastLoginTime,DateUtils.dt);
	}
	
	public void setDataFlag(Integer value) {
		this.dataFlag = value;
	}

	public Integer getDataFlag() {
		return this.dataFlag;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

}