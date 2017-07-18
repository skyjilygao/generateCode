package com.hql.frame;

import com.hql.factory.SqlBean;
import com.hql.factory.TableFactory;
import com.hql.freemarker.FreemarkerFactory;
import com.hql.util.Utils;

public class CreateCode {

	private String getTp(){
		return System.getProperty("user.dir").replace("\\", "/");
	}

	public void create(String tables,String outDir){
		try {
			System.out.println("生成代码开始");
			SqlBean sql = SqlBean.getInstance();
			System.out.println("设置参数");
			sql.setDatabaseDriver("com.mysql.jdbc.Driver");
			sql.setConUrl("jdbc:mysql://127.0.0.1:3306/userMgt?useUnicode=true&amp;characterEncoding=UTF-8");
			sql.setUserName("root");
			sql.setPassword("HelloWorld1!");
			sql.setDb("userMgt");
			if(Utils.isNull(tables)){
				throw new Exception("表名不能空!");
			}
			Utils.deleteDir(outDir);
			TableFactory.getDefault().getCurrentAllTalbesStatus(sql,tables);
			if (!Utils.isNull(outDir)) {
				FreemarkerFactory ff = FreemarkerFactory.getDefault();
				ff.setModule("user");//模块
				ff.setBao("com.gao");//包
				ff.setTemplateDir(getTp()+"/template");//模板目录
				ff.createJavaCode(outDir);
			}
			System.out.println("生成代码完毕!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		CreateCode cc = new CreateCode();
		cc.create("u_user","tempCode");//表,输出目录：当前项目下tempCode目录下
	}
}