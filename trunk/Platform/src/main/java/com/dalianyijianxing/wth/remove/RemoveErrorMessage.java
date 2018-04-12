package com.dalianyijianxing.wth.remove;

import java.io.IOException;
import java.util.List;

import com.dalianyijianxing.wth.bean.FileDirectory;
import com.dalianyijianxing.wth.runbattodb.WriteFileNameToDb;

/**    
*  文件名 ： RemoveErrorMessage.java  
*  包    名 ： com.dalianyijianxing.wth.remove  
*  描    述 ： 文件操作，刪除錯誤信息  
*  作    者 ： wang  
*  时    间 ： 2015年11月12日 下午2:34:09  
*  版    本 ： V1.0    
*/
public class RemoveErrorMessage {
	
	public RemoveErrorMessage(FileDirectory	dir) throws IOException{
		// 开始获取文件列表
		List<String> fileNameList = start();
		for (String string : fileNameList) {
			// 调用文件操作类
			FileIOStream.start(string,dir.getError_file());
			
		}
		
		
	}

	/**  
	*  方法名： start  
	*  功    能： 入口  
	*  参    数： @return 
	*  返    回： List<String>  
	*  作    者 ： wang  
	*  时    间 ： 2015年11月12日 下午4:04:23 
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private List<String> start() {
		System.out.println("开始调试文件");
		// List<String> list
		List<String> list = WriteFileNameToDb.selectAllErrorFileByStateFlag();
		return list;
	}
	
}
