package com.dalianyijianxing.wth.runsp;

import java.sql.CallableStatement;
import java.sql.Connection;

import com.dalianyijianxing.wth.utils.DBUtils;

/**    
*  文件名 ： RunSP.java  
*  包    名 ： com.dalianyijianxing.wth.runsp  
*  描    述 ： 调用存储过程写入数据文件
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月17日 下午2:58:07  
*  版    本 ： V1.0    
*/
public class RunSP {
	/**
	 * 
	*  方法名： runSp  
	*  功    能： 执行存储过程
	*  参    数： @param path
	*  参    数： @return
	*  参    数： @throws Exception 
	*  返    回： boolean  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public boolean runSp(String path, String filename) throws Exception
	{
		
		// 获得连接
		Connection conn = DBUtils.getConnection();
		
		String sql = "{call SP_LOAD_FILE('" + path + "','" + filename + "','`')}";
		// 创建存储过程的对象
		CallableStatement c = conn.prepareCall(sql);
		
		// // 给存储过程的第一个参数设置值
		// c.setString(1, path);
		//
		// // 注册存储过程的第二个参数
		// c.setString(2, filename);
		
		// 执行存储过程
		c.execute();
		
		// 得到存储过程的输出参数值
		// System.out.println(c.getInt(2));
		conn.close();
		return true;
	}
	
//	public static void main(String[] args) throws Exception {
//		RunSP sp = new RunSP();
//		sp.runSp("D:\\zhcx_datafile_path", "上市公司名单.txt");
//		
//	}
	
}
