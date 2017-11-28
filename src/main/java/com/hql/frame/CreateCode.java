package com.hql.frame;

import com.hql.factory.SqlBean;
import com.hql.factory.TableFactory;
import com.hql.freemarker.FreemarkerFactory;
import com.hql.util.Utils;

public class CreateCode {

//*********************配置以下关键信息，以生成java Code！*******开始**************************************//
	// 只需写表名，数据库连接信息
    final static String tempTable ="ads_conversion_specs";//表

	final static String DB_HOST="192.168.88.89";
	final static String DB_PORT="3306";
	final static String DB_NAME ="fbads";//数据库名
	final static String user="user-admin";//数据库用户名
	final static String password="ceshi";//数据库密码
	final static String packageBao="com.adorado.fbads";//生成java文件包名
	final static String outTempDir="tempCode";//输出目录：当前项目下tempCode目录下
	final static String templateDir="/template";//模版路径
	static String module="adsConversionSpecs";//模块.无需指定，main中根据表名自动替换下划线，大写处理

//配置完成后，还需要设置模版中目录名称，即各个包名称。如：项目dao的java文件属于com.gao.dao下，请将设置一个包名称为dao
// *********************配置以下关键信息，以生成java Code！*******开始**************************************//
	//在构造方法中拼接
	static String conURL="";
	public static void main(String[] args) {
		CreateCode cc = new CreateCode();
        cc.generateModule();
		cc.create(tempTable,outTempDir);//表,输出目录：当前项目下tempCode目录下
	}
    public void generateModule(){
        module = tempTable;
        while (module.indexOf("_") > -1) {
            String s1 = module.substring(module.indexOf("_")+1, module.indexOf("_")+2);
            module = module.replaceFirst(module.substring(module.indexOf("_"), module.indexOf("_")+2), s1.toUpperCase());
        }
        System.out.println("表名："+tempTable +", 实体名："+module);
    }
	public CreateCode() {
		conURL="jdbc:mysql://"+DB_HOST+":"+DB_PORT+"/"+ DB_NAME +"?useUnicode=true&amp;characterEncoding=UTF-8";
	}

	public void create(String tables, String outDir){

		try {
			System.out.println("生成代码开始");
			SqlBean sql = SqlBean.getInstance();
			System.out.println("设置参数");
			sql.setDatabaseDriver("com.mysql.jdbc.Driver");
			sql.setConUrl(conURL);
			sql.setUserName(user);
			sql.setPassword(password);
			sql.setDb(DB_NAME);
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