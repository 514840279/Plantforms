package com.dalianyijianxing.wth.changerartofile;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dalianyijianxing.wth.utils.DBUtils;

/**    
*  文件名 ： OldRarFile.java  
*  包    名 ： com.dalianyijianxing.wth.changerartofile  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月18日 下午12:58:24  
*  版    本 ： V1.0    
*/
public class OldRarFile {
	private static Logger logger = Logger.getLogger(OldRarFile.class);
	
	/**
	 * 
	*  方法名： getOldfile  
	*  功    能： TODO(这里用一句话描述这个方法的作用)  
	*  参    数： @return 
	*  返    回： List<String>  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static List<String> getOldfile(){
		// String sql = "create table sys_load_oldrar(oldfile varchar2(200),flag char(2),DATE1 DATE default sysdate)";
		Connection conn = DBUtils.getConnection();
		List<String> list = new ArrayList<String>();
		String sqls = "select oldfile from sys_lrd_oldrar t where flag <> '2'and t.oldfile not like '%html%'";
		logger.info(sqls);
		try {
			Statement stat = conn.createStatement();
			// stat.executeUpdate(sql);
			ResultSet rs = stat.executeQuery(sqls);
			while (rs.next()) {
				list.add(rs.getString("OLDFILE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return list;
	}
	
	/**
	 * 
	*  方法名： getOldfile  
	*  功    能： TODO(这里用一句话描述这个方法的作用)  
	*  参    数： @return 
	*  返    回： List<String>  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static List<String> getfullOldfile(){
		// String sql = "create table sys_load_oldrar(oldfile varchar2(200),flag char(2),DATE1 DATE default sysdate)";
		Connection conn = DBUtils.getConnection();
		List<String> list = new ArrayList<String>();
		String sqls = "select distinct oldfile from sys_lrd_oldrar";
		logger.info(sqls);
		try {
			Statement stat = conn.createStatement();
			// stat.executeUpdate(sql);
			ResultSet rs = stat.executeQuery(sqls);
			while (rs.next()) {
				list.add(rs.getString("OLDFILE"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return list;
	}

	/**  
	*  方法名： update  
	*  功    能： 修改状态
	*  参    数： @param file 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
    public static void update(String file,String flag) {
		Connection conn = DBUtils.getConnection();
		String sqls = "update sys_lrd_oldrar set flag = '"+flag+"' where oldfile = '"+file+"'";
		logger.info(sqls);
		try {
			Statement stat = conn.createStatement();
			 stat.executeUpdate(sqls);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	    
    }
    
	/**
	 * 
	*  方法名： insert  
	*  功    能： 插入新数据
	*  参    数： @param file 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
    public static void insert(String file){
    	Connection conn = DBUtils.getConnection();
		String sqls = "insert into sys_lrd_oldrar values('"+file.replace("/", "\\").replace("d:", "D:")+"','0',sysdate) ";
		logger.info(sqls);
		try {
			Statement stat = conn.createStatement();
			 stat.executeUpdate(sqls);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	    
    }
	
}
