package com.dalianyijianxing.wth.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**    
*  文件名 ： ListFiles.java  
*  包    名 ： net.gicp.wth.file  
*  描    述 ： 獲取文件類  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月9日 上午10:20:21  
*  版    本 ： V1.0    
*/
public class ListFiles {
	/**
	 * 
	*  方法名： getFileList  
	*  功    能： 获取文件列表
	*  参    数： @param directory
	*  参    数： @return 
	*  返    回： Map<String,String>  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public Map<String, String> getFileList(String directory) {
		Map<String, String> noteList = new HashMap<String, String>();
		File f = new File(directory);
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// System.out.println("文件：" + files[i]);
				String name= files[i].getName();
				System.out.println(name);
				if(!name.contains(".txt")){
					continue;
				}
				noteList.put(files[i].toString().trim(), files[i].getName().trim());
			} else {
				
				// list.put(directory, noteList);
				// System.out.println("目录：" + files[i]);
				System.out.println("目录绝对地址：" + files[i].getAbsolutePath());
				noteList.putAll(getFileList(files[i].getAbsolutePath()));
			}
		}
		return noteList;
	}
}
