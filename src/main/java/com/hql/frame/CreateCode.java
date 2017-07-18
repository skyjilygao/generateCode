package com.hql.frame;

import com.hql.factory.SqlBean;
import com.hql.factory.TableFactory;
import com.hql.freemarker.FreemarkerFactory;
import com.hql.util.Utils;

public class CreateCode {


	final static String tempTable ="u_user";//表
	final static String outTempDir="tempCode";//输出目录：当前项目下tempCode目录下


	final static String templateDir="/template";//模版路径
	final static String module="user";//模块
	final static String DBname="userMgt";//数据库名
	final static String user="root";//数据库用户名
	final static String password="HelloWorld1!";//数据库密码
	final static String packageBao="com.gao";//生成java文件包名

	public static void main(String[] args) {
		CreateCode cc = new CreateCode();
		cc.create(tempTable,outTempDir);//表,输出目录：当前项目下tempCode目录下
	}

	public void create(String tables,String outDir){

		try {
			System.out.println("生成代码开始");
			SqlBean sql = SqlBean.getInstance();
			System.out.println("设置参数");
			sql.setDatabaseDriver("com.mysql.jdbc.Driver");
			sql.setConUrl("jdbc:mysql://127.0.0.1:3306/"+DBname+"?useUnicode=true&amp;characterEncoding=UTF-8");
			sql.setUserName(user);
			sql.setPassword(password);
			sql.setDb(DBname);
			if(Utils.isNull(tables)){
				throw new Exception("表名不能空!");
			}
			Utils.deleteDir(outDir);
			TableFactory.getDefault().getCurrentAllTalbesStatus(sql,tables);
			if (!Utils.isNull(outDir)) {
				FreemarkerFactory ff = FreemarkerFactory.getDefault();
				ff.setModule(module);//模块
				ff.setBao(packageBao);//包
				ff.setTemplateDir(getTp()+templateDir);//模板目录
				ff.createJavaCode(outDir);
			}
			System.out.println("生成代码完毕!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String getTp(){
		return System.getProperty("user.dir").replace("\\", "/");
	}
}