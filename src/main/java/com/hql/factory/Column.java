package com.hql.factory;

import java.io.Serializable;

public class Column implements Serializable {

	private static final long serialVersionUID = 8190275158774197232L;

	private String colName;
	
	private String proName;
	
	private String colType;
	
	private String comment;

	public String getJavaType() {
		String javaType = TableFactory.typeMap.get(colType);
		if(javaType == null){
			if(colType.indexOf("int")>-1){
				return "Integer";
			}
			return "String";
		}
		return javaType;
	}

	public String getColName() {
		return colName;
	}
	
	public Boolean isDateColumn(){
		return getJavaType().contains("Date")?true:false;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getProName() {
		return proName;
	}

	public String getUpFirstProName() {
		return proName.substring(0, 1).toUpperCase()+proName.substring(1);
	}

	public String getLowerCaseProName(){
		return proName.toLowerCase();
	}
	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
