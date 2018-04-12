package com.dalianyijianxing.wth.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dalianyijianxing.wth.bean.FileDirectory;

/**    
*  文件名 ： ReadTxtFile.java  
*  包    名 ： com.dalianyijianxing.wth.utils  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月23日 上午11:47:39  
*  版    本 ： V1.0    
*/
public class ReadTxtFile {
	private static Logger logger = Logger.getLogger(FileDirectory.class);
	/** 
	* 功能：Java读取txt文件的内容 
	* 步骤：1：先获得文件句柄 
	* 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取 
	* 3：读取到输入流后，需要读取生成字节流 
	* 4：一行一行的输出。readline()。 
	* 备注：需要考虑的是异常情况 
	* @param filePath 
	*/
	public static List<String> readTxtFile(String filePath) {
		List<String> strList = new ArrayList<String>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				strList = new ArrayList<String>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
					strList.add(lineTxt);
					logger.info(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return strList;
		
	}
	
}
