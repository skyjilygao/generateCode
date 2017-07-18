package com.hql.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static List<String> getName(File file) {
		List<String> fl = new ArrayList<String>();
		if (file.isDirectory()) {
			File[] dirFile = file.listFiles();
			for (File f : dirFile) {
				if (f.isDirectory())
					fl.addAll(getName(f));
				else {
					fl.add(f.getAbsolutePath());
				}
			}
		}
		return fl;
	}
	
	public static void writefile(String ctt,String targetFile){
		try {
			valdateDir(targetFile);
			OutputStream out = new FileOutputStream(new File(targetFile));
			out.write(ctt.getBytes("UTF-8"));
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()+"：输出"+targetFile+"文件失败!");
		}
	}

	public static void valdateDir(String saveName) {
		String dirStr=saveName.substring(0, saveName.lastIndexOf("/"));
		File dir=new File(dirStr);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}
	
	public static Boolean isNull(String str){
		return str==null||str.trim().length()==0;
	}

	/**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(String dir) {
    	File dirF = new File(dir);
        if (dirF.exists()) {
			if (dirF.isDirectory()) {
				File[] children = dirF.listFiles();
				//递归删除目录中的子目录下
				for (int i = 0; i < children.length; i++) {
					boolean success = deleteDir(children[i].getAbsolutePath());
					if (!success) {
						return false;
					}
				}
			}
			// 目录此时为空，可以删除
			return dirF.delete();
		}
        return true;
    }
}
