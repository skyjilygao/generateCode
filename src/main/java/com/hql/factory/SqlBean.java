package com.hql.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hql.util.Utils;

public class SqlBean {
	
	private static SqlBean instance;
	
	private static Connection conn = null;
	public ResultSet rs = null;

	private String DatabaseDriver;
	private String conUrl;
	private String userName;
	private String password;
	private String db;

	public String getDatabaseDriver() {
		return DatabaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		DatabaseDriver = databaseDriver;
	}

	public String getConUrl() {
		return conUrl;
	}

	public void setConUrl(String conUrl) {
		this.conUrl = conUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public static SqlBean getInstance() {
		if(instance==null){
			instance=new SqlBean();
		}
		return instance;
	}
	
	public Connection getConnection(){
		try {
			if(conn==null||conn.isClosed()){
				if(Utils.isNull(db)){
					throw new Exception("数据库名不能为空!");
				}
				Class.forName(DatabaseDriver);
				conn=DriverManager.getConnection(conUrl,userName,password);
			}
			return conn;
		} catch (Exception e) {
			System.out.println("加载驱动器出现问题： " + e.getMessage());
		}
		CloseDatabase();
		return null;
	}

	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			System.out.println(sql);
			Statement stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("执行查询出现问题： " + ex.getMessage());
		}
		return rs;
	}

	public static void CloseDatabase() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception end) {
			System.out.println("执行关闭数据库出现问题： " + end.getMessage());
		}
	}

}