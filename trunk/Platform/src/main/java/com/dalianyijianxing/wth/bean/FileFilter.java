package com.dalianyijianxing.wth.bean;

import java.util.List;

import com.dalianyijianxing.wth.utils.ReadTxtFile;

/**    
*  文件名 ： FileFilter.java  
*  包    名 ： com.dalianyijianxing.wth.bean  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月23日 上午11:37:52  
*  版    本 ： V1.0    
*/
public class FileFilter {
	
	List<String> strlist;
	
	public FileFilter(){
		String path = System.getProperty("user.dir")+"/filter.txt";
		strlist = ReadTxtFile.readTxtFile(path);
//		strlist = ReadTxtFile.readTxtFile("src/main/resources/filter.txt");
		
	}

	/**  
	 *  方法名 ： getStrlist 
	 *  功    能 ： 返回变量 strlist 的值  
	 *  @return: List<String> 
	 */
	public List<String> getStrlist() {
		return strlist;
	}

	/**  
	 *  方法名 ： setStrlist 
	 *  功    能 ： 设置变量 strlist 的值
	 */
	public void setStrlist(List<String> strlist) {
		this.strlist = strlist;
	}
	
	
 	
}
