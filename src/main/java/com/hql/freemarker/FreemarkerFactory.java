package com.hql.freemarker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hql.factory.TableFactory;
import com.hql.factory.TableInfo;
import com.hql.util.Utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FreemarkerFactory {

	private Configuration cfg = new Configuration();
	
	private static FreemarkerFactory instance;
	
	public static FreemarkerFactory getDefault(){
		if (instance==null) {
			instance = new FreemarkerFactory();
		}
		return instance;
	}
	
	private String bao;
	
	private String templateDir;
	
	private String module;
	
	public String getModule() {
		if(module == null){
			String[] dir = bao.split("\\.");
			module = dir[dir.length-1];
		}
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getBao() {
		return bao;
	}

	public String getBaoDir() {
		return bao.replace(".", "/");
	}
	public void setBao(String bao) {
		this.bao = bao;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void createJavaCode(String outDir) {
		System.out.println("生成java代码!");
		try {
			if(getTemplateDir()==null){
				throw new Exception("模板目录不能为空!");
			}
			File templateDir = new File(getTemplateDir());
			List<String> files=Utils.getName(templateDir);
			cfg.setDirectoryForTemplateLoading(templateDir);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template template;
			Map<String, Object> table_info = new HashMap<String, Object>();
			table_info.put("basepackage", bao);
			table_info.put("module", getModule());
			Set<String> tableNames = TableFactory.tableMap.keySet();
			String saveName;
			for (String tableN : tableNames) {
				TableInfo table = TableFactory.tableMap.get(tableN);
				table_info.put("table", table);
				for (String fn : files) {
					String abutemp = fn.replace(templateDir.getAbsolutePath(), "");
					if(abutemp.endsWith(".java")||abutemp.endsWith(".html")||abutemp.endsWith(".xml")){
						template = cfg.getTemplate(abutemp, "UTF-8");
						saveName = outDir + fn.replace(templateDir.getAbsolutePath(), "")
											.replace("${basepackage_dir}",getBaoDir())
											.replace("${lowerClassName}",table.getLowerClassName())
											.replace("${className}",table.getClassName())
											.replace("\\", "/");
						outFile(template, table_info, saveName);
					}
				}
			}
			System.out.println("生成java代码完毕!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage()+":获取模板失败!");
		}
	}

	private void outFile(Template template, Map<String, Object> dataMap,
			String saveName){
		Utils.valdateDir(saveName);
		try {
			FileOutputStream fos = new FileOutputStream(saveName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(dataMap, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()+"：输出模板文件失败!");
		}
	}
	
	public void delzfc(String file){
		StringBuffer ctt=new StringBuffer();
		Boolean flag=false;
		try {
			FileReader in=new FileReader(new File(file));
			BufferedReader reader=new BufferedReader(in);
			String line;
			while((line=reader.readLine())!=null){
				if(line.indexOf("$!{")>-1){
					flag=true;
					line=line.replace("$!", "$");
				}
				ctt.append(line+"\r\n");
			}
			in.close();
			if (flag) {
				OutputStream out = new FileOutputStream(new File(file));
				out.write(ctt.toString().getBytes("UTF-8"));
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()+"：修改文件失败!");
		}
	}
}