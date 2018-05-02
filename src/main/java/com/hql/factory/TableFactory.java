package com.hql.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hql.util.Utils;

public class TableFactory {

	public static Map<String, TableInfo> tableMap = new HashMap<String, TableInfo>();

	public static Map<String, String> typeMap = new HashMap<String, String>();
	
	private static TableFactory instance;

	public static TableFactory getDefault(){
		if (instance == null) {
			instance = new TableFactory();
			typeMap.put("timestamp", "Date");
			typeMap.put("date", "Date");
			typeMap.put("datetime", "Date");
			typeMap.put("bit", "Short");
			typeMap.put("int", "Integer");
			typeMap.put("decimal", "Double");
			typeMap.put("double", "Double");
			typeMap.put("bigint", "Long");
		}
		return instance;
	}

	public void setDBTypeToJavaType(String DBType,String javaType){
		if(!Utils.isNull(DBType)&&!Utils.isNull(javaType)){
			if(typeMap.get(DBType)!=null){
				typeMap.remove(DBType);
			}
			typeMap.put(DBType, javaType);
		}
	}

	public void getCurrentAllTalbesStatus(SqlBean sqlbean,String tables) throws Exception {
		System.out.println("查询表信息!");
		String sql = "show table status;";
		if (!tables.contains(",")) {
			if (!tables.contains("%")) {
				sql = "show table status where name = '" + tables + "';";
			}else{
				sql = "show table status where name like '" + tables + "';";
			}
		}else if (tables != null && tables.trim().length() > 0) {
			sql="show table status where name in ('"+tables.replace(",", "','")+"');";
		}
		ResultSet rs = sqlbean.executeQuery(sql);
		TableInfo info = null;
		String com;
		try {
			while (rs.next()) {
				info = new TableInfo();
				com=rs.getString("Comment");
				info.setTable_comment(com.indexOf(";")>0?com.substring(0, com.indexOf(";")):com);
				info.setTable_name(rs.getString("Name"));
				tableMap.put(info.getTable_name(), info);
			}
			getCurrentTablesStatus(sqlbean, tableMap);
			System.out.println("查询表信息完毕!");
		} catch (SQLException e) {
			System.out.println("查询表信息错误："+e.getMessage());
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void getCurrentTablesStatus(SqlBean sqlbean,
			Map<String, TableInfo> tables) {
		System.out.println("查询表列信息!");
		String sql = "";
		List<Column> tableFilds = null;
		List<Column> noidtableFilds = null;
		Column field = null;
		Set<String> sets = tables.keySet();
		for (String tn : sets) {
			TableInfo ti = tables.get(tn);
			//查询出表的字段名,注释,字段类型
			sql = "select COLUMN_NAME,COLUMN_COMMENT,DATA_TYPE,COLUMN_KEY from information_schema.columns "
					+ "where table_name = '" + tn + "' AND table_schema='"+sqlbean.getDb()+"'";
			ResultSet rs = sqlbean.executeQuery(sql);
			try {
				tableFilds = new ArrayList<Column>();
				noidtableFilds = new ArrayList<Column>();
				while (rs.next()) {
					field = new Column();
					field.setColName(rs.getString("COLUMN_NAME"));
					String name = field.getColName();
					if(name.indexOf("_") < 0){
						int i = 0;
						Map<Boolean, Integer> upOrlowCase = new HashMap<Boolean, Integer>();
						boolean isAllUpperCase=true;
						int u=0;
						int l=0;
						upOrlowCase.put(true,u);
						upOrlowCase.put(false,l);
						while(i < name.length()){
							char chr = name.charAt(i);
							if(Character.isUpperCase(chr)){
								upOrlowCase.put(true,++u);
							}else if(Character.isLowerCase(chr)){
								upOrlowCase.put(false,++l);
								break;
							}
							i++;
						}
						if(upOrlowCase.get(true) == name.length()){
							name = name.toLowerCase();
						}else {
							name = name.substring(0,1).toLowerCase() + name.substring(1);
						}
					}else {
						name = name.toLowerCase(); // 如果字段中有大写，一律改成小写
					}
					while (name.indexOf("_") > 0) {
						int index = name.indexOf("_");
						name = name.subSequence(0, index)
								+ name.substring(index + 1, index + 2)
										.toUpperCase()
								+ name.substring(index + 2);
					}
					field.setProName(name);
					field.setComment(rs.getString("COLUMN_COMMENT"));
					field.setColType(rs.getString("DATA_TYPE"));
					tableFilds.add(field);
					String colkey = rs.getString("COLUMN_KEY");
					if("PRI".equals(colkey)){
						ti.setIdCol(field);
					}else{
						noidtableFilds.add(field);
					}
					if(field.getJavaType().equals("Date")){
						if (!ti.getInclude().contains("Date")) {
							ti.setInclude("import java.util.Date;\r\nimport com.golive.common.util.DateUtils;\r\n");
						}
					}
				}
				ti.setCols(tableFilds);
				ti.setNoIdCols(noidtableFilds);
			} catch (SQLException e) {
				System.out.println("查询表列信息错误："+e.getMessage());
			} finally {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				SqlBean.CloseDatabase();
			}
		}
	}
}