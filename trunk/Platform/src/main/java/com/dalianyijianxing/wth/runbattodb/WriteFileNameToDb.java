package com.dalianyijianxing.wth.runbattodb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.dalianyijianxing.wth.bean.FileListBean;
import com.dalianyijianxing.wth.bean.RarOldBean;
import com.dalianyijianxing.wth.changerartofile.OldRarFile;
import com.dalianyijianxing.wth.utils.DBUtils;
import com.dalianyijianxing.wth.utils.MySQLDBUtils;

/**
 * 文件名 ： WriteFileNameToDb.java
 * 包 名 ： com.dalianyijianxing.wth.runbattodb
 * 描 述 ： 将文件信息写入数据库中
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Tenghui.Wang
 * 时 间 ： 2015年9月17日 上午11:35:45
 * 版 本 ： V1.0
 */
public class WriteFileNameToDb {
	private static Logger	logger	= Logger.getLogger(OldRarFile.class);

	/**
	 * 
	 * 方法名： insert
	 * 功 能：插入新文件
	 * 参 数： @param map
	 * 返 回： void
	 * 作 者 ： Tenghui.Wang
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public void insert(Map<String, String> map) {
		// String cteatesql = "create table SYS_LRD_FILELIST(FILEPATH VARCHAR2(1000),FILENAME VARCHAR2(200),STATEFLAG CHAR(2),DATE1 DATE default sysdate)";
		Connection conn = DBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			// stat.executeUpdate(cteatesql);
			Set<Entry<String, String>> set = map.entrySet();
			// Get an iterator
			Iterator<Entry<String, String>> i = set.iterator();
			// Display elements
			while (i.hasNext()) {
				Map.Entry<String, String> me = (Entry<String, String>) i.next();
				String uuid = UUID.randomUUID().toString();
				// 判断数据库中有没有，没有就要插入
				if (select(me.getKey().trim(), me.getValue().trim())) {
					String insertsql = "insert into SYS_LRD_FILELIST(filepath,filename, stateflag, date1, reason ,REMOVEERRORCODE,uuid) values('" + me.getKey().trim() + "','" + me.getValue().trim() + "','0',sysdate,'','0','" + uuid + "')";
					stat.executeUpdate(insertsql);
					logger.info(insertsql);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}

	}

	/**
	 * 
	 * 方法名： update
	 * 功 能： 更新
	 * 参 数： @param type
	 * 返 回： void
	 * 作 者 ： Tenghui.Wang
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public void update(String type, String path, String name) {
		String update = "update  SYS_LRD_FILELIST set stateflag='" + type + "',update_Time = sysdate,STORAGE_START_DATE_TIME = sysdate where filepath = '" + path + "' and filename = '" + name + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}

	}

	/**
	 * 
	 * 方法名： select
	 * 功 能： 查询方法所有没有执行和废弃的信息
	 * 参 数： @param path
	 * 参 数： @param name
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Tenghui.Wang
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public List<FileListBean> select() {
		// sql
		String sql = "select * from SYS_LRD_FILELIST where stateflag not in('3','10') order by filename";
		// get connection
		Connection conn = DBUtils.getConnection();
		// new list
		List<FileListBean> list = new ArrayList<FileListBean>();
		Statement stat;
		try {
			// create statement
			stat = conn.createStatement();
			// execute query
			ResultSet rs = stat.executeQuery(sql);
			// 循环取值
			while (rs.next()) {
				FileListBean bean = new FileListBean(rs.getString("FILEPATH"), rs.getString("FILENAME"), rs.getString("STATEFLAG"));
				list.add(bean);
				logger.info(sql);
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
	 * 方法名： select
	 * 功 能： 判断已经录入过
	 * 参 数： @param path
	 * 参 数： @param name
	 * 参 数： @return
	 * 返 回： boolean
	 * 作 者 ： Tenghui.Wang
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	private boolean select(String path, String name) {
		boolean b = true;
		String sql = "select count(*) from SYS_LRD_FILELIST where filepath = '" + path + "' and filename = '" + name + "'";
		Connection conn = DBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				int i = rs.getInt(1);
				if (i > 0) {
					b = false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return b;

	}

	/**
	 * 
	 * 方法名： getTableNamesFiles
	 * 功 能： 查询出所有文件没有陪字段的信息
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： Tenghui.Wang
	 * 时 间 ： 2015年10月23日 上午9:18:51
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public List<String> getTableNamesFiles() {
		String sql = "select distinct t.file_desc from sys_lrd_cols_map t where t.tab_name is not null ";
		Connection conn = DBUtils.getConnection();
		Statement stat;
		List<String> list = new ArrayList<String>();
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(1).replace("%", ""));
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
	 * 方法名： selectAllErrorFileByStateFlag
	 * 功 能： 查询出来所有的出错的文件 集合
	 * 参 数： @return
	 * 返 回： List<String>
	 * 作 者 ： wang
	 * 时 间 ： 2015年11月12日 下午4:14:24
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static List<String> selectAllErrorFileByStateFlag() {
		// sql
		String sql = "select filepath from SYS_LRD_FILELIST where stateflag not in('3','10') and REMOVEERRORCODE ='0'";
		// get connection
		Connection conn = DBUtils.getConnection();
		// new list
		List<String> list = new ArrayList<String>();
		Statement stat;
		try {
			// create statement
			stat = conn.createStatement();
			// execute query
			ResultSet rs = stat.executeQuery(sql);
			// 循环取值
			while (rs.next()) {
				list.add(rs.getString(1));
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
	 * 方法名： updateErrorByFile
	 * 功 能： 更新删除错误的行
	 * 参 数： @param path
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年11月19日 下午3:58:31
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void updateErrorByFile(String path) {
		String update = "update  SYS_LRD_FILELIST set REMOVEERRORCODE='1', update_Time = sysdate where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 方法名： queryLengthb
	 * 功 能： 获取汉子的字符长度
	 * 参 数： @param path
	 * 参 数： @return
	 * 返 回： int
	 * 作 者 ： wang
	 * 时 间 ： 2015年11月19日 下午4:01:57
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static int queryLengthb(String path) {
		String sql = "select lengthb('" + path + "') from dual";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		int i = 0;
		try {
			stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			// 循环取值
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return i;
	}

	/**
	 * 方法名： addErrorByFile
	 * 功 能： 插入error表
	 * 参 数： @param string
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年11月24日 上午10:35:13
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void addErrorByFile(String name, String date1, String ori_file_path, int ori_date_size, String error_file_path, int error_date_size, String errorormore) {
		String sql = "insert into sys_lrd_errordatefile(id,name,date1,ori_file_path,ori_date_size,error_file_path,error_date_size,error_or_more)values(lrd_error_id.nextval,?,?,?,?,?,?,?)";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat;
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, date1);
			stat.setString(3, ori_file_path);
			stat.setInt(4, ori_date_size);
			stat.setString(5, error_file_path);
			stat.setInt(6, error_date_size);
			stat.setString(7, errorormore);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}

	}

	/**
	 * 方法名： selcetLengthb
	 * 功 能： 查询字符长度
	 * 参 数：
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年11月24日 下午3:33:52
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void selcetLengthb(String... str) throws Exception {
		StringBuilder sql = new StringBuilder(" select 1");
		for (int i = 0; i < str.length; i++) {
			sql.append(" ,lengthb('" + str[i] + "') ");
		}
		sql.append(" from dual");
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.execute(sql.toString());
		} finally {
			DBUtils.close(conn);
		}

	}

	/**
	 * 
	 * 方法名： update
	 * 功 能： 更新
	 * 参 数： @param type
	 * 返 回： void
	 * 作 者 ： Tenghui.Wang
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void delete(String path) {
		String delete = "delete  SYS_LRD_FILELIST  where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(delete);
			logger.info(delete);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}

	}

	/**
	 * 
	 * 方法名： updateOriCount
	 * 功 能： 插入数据总量
	 * 参 数： @param path
	 * 参 数： @param num
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月2日 上午10:31:01
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void updateOriCount(String path, int num) {
		String update = "update  SYS_LRD_FILELIST set count_number=" + num + ",update_time = sysdate where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 
	 * 方法名： updateOriCount
	 * 功 能： 插入数据总量
	 * 参 数： @param path
	 * 参 数： @param num
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月2日 上午10:31:01
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void updateErrorCount(String path, int num) {
		String update = "update  SYS_LRD_FILELIST set error_number=" + num + ",update_time = sysdate where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	/**
	 * 
	 * 方法名： updateMoreCount
	 * 功 能： 插入超过长度的数据
	 * 参 数： @param path
	 * 参 数： @param num
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月2日 上午10:33:26
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void updateMoreCount(String path, int num) {
		String update = "update  SYS_LRD_FILELIST set more_number=" + num + ",update_time = sysdate where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	public static void updateEndtime(String path) {
		String update = "update  SYS_LRD_FILELIST set storage_end_date_time=sysdate,update_time = sysdate where filepath = '" + path + "'";
		Connection conn = DBUtils.getConnection();
		// Connection conn = SQLiteDBUtils.getConnection();
		Statement stat;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(update);
			logger.info(update);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
	}

	// public static void updateStarttime(String path) {
	// String update = "update  SYS_LRD_FILELIST set storage_start_date_time=sysdate,update_time = sysdate where filepath = '" + path + "'";
	// Connection conn = DBUtils.getConnection();
	// // Connection conn = SQLiteDBUtils.getConnection();
	// Statement stat;
	// try {
	// stat = conn.createStatement();
	// stat.executeUpdate(update);
	// logger.info(update);
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// DBUtils.close(conn);
	// }
	// }

	/**
	 * 方法名： queryFileListBean
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param dir
	 * 参 数： @return
	 * 返 回： FileListBean
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月3日 下午5:21:23
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static FileListBean queryFileListBean(String dir) {
		// sql
		String sql = "select * from SYS_LRD_FILELIST T where T.FILEPATH = '" + dir + "' ";
		// get connection
		Connection conn = DBUtils.getConnection();
		// new list
		FileListBean bean = new FileListBean();
		Statement stat;
		try {
			// create statement
			stat = conn.createStatement();
			// execute query
			ResultSet rs = stat.executeQuery(sql);
			// 循环取值
			while (rs.next()) {
				bean.setFilepath(rs.getString("FILEPATH"));
				bean.setFilename(rs.getString("FILENAME"));
				bean.setStateflag(rs.getString("STATEFLAG"));
				bean.setDate1(rs.getDate("DATE1"));
				bean.setReason(rs.getString("REASON"));
				bean.setRemoveerrorcode(rs.getString("REMOVEERRORCODE"));
				bean.setUuid(rs.getString("UUID"));
				bean.setCountNumber(rs.getInt("COUNT_NUMBER"));
				bean.setErrorNumber(rs.getInt("ERROR_NUMBER"));
				bean.setMoreNumber(rs.getInt("MORE_NUMBER"));
				bean.setStorageEndDateTime(rs.getDate("STORAGE_END_DATE_TIME"));
				bean.setStorageStartDateTime(rs.getDate("STORAGE_START_DATE_TIME"));
				bean.setStorageTableName(rs.getString("STORAGE_TABLE_NAME"));
				bean.setStorageNumber(rs.getInt("STORAGE_NUMBER"));
				bean.setLrdTaskId(rs.getString("LRD_TASK_ID"));
				bean.setUpdateTime(rs.getDate("UPDATE_TIME"));
				logger.info(sql.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(conn);
		}
		return bean;
	}

	/**
	 * 方法名： updateToMySqlDB
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param dir
	 * 参 数： @param bean
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月3日 下午5:21:26
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public static void updateToMySqlDB(FileListBean bean) {
		String filename = bean.getFilename();
		String databasename = filename.substring(0, filename.indexOf("_"));
		String tablename = filename.substring(filename.indexOf("_") + 1, filename.length() - 12);
		String date = filename.substring(filename.indexOf("_") + 1, filename.length() - 4).replace(tablename, "");

		StringBuffer sb = new StringBuffer();
		sb.append("update 增量表 ");
		sb.append("set  uuid = '" + bean.getUuid() + "'");
		sb.append(", 文件路径 = '" + bean.getFilepath() + "'");
		sb.append(", 文件名 = '" + bean.getFilename() + "'");
		sb.append(", 删除原因 = '" + bean.getReason() + "'");
		sb.append(", 文件数据总量 = " + bean.getCountNumber());
		sb.append(", 错误的数据量 = " + bean.getErrorNumber());
		sb.append(", 超出4000长度的量 = " + bean.getMoreNumber());
		sb.append(", 入库开始时间 = '" + bean.getStorageStartDateTime() + "'");
		sb.append(", 入库结束时间 = '" + bean.getStorageEndDateTime() + "'");
		sb.append(", 入库的最终表名 = '" + bean.getStorageTableName() + "'");
		sb.append(", 入库的数据量 = " + bean.getStorageNumber());
		sb.append(", 入库的单号 = '" + bean.getLrdTaskId() + "'");
		sb.append(", utime = '" + bean.getUpdateTime() + "'");
		sb.append(" where  库名='" + databasename + "'");
		sb.append(" and  表名='" + tablename + "'");
		sb.append(" and  date='" + date + "'");
		System.out.println(sb.toString());
		Connection conn = MySQLDBUtils.getConnection();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			stat.executeUpdate(sb.toString());
			logger.info(sb.toString());
		} catch (SQLException e) {
			System.out.println("在updateToMySqlDB错误信息" + e.toString());
		} finally {
			MySQLDBUtils.close(stat);
			MySQLDBUtils.close(conn);
		}

	}

	/**
	 * 方法名： updateOldrar
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数：
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月4日 下午2:11:38
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	public List<RarOldBean> updateOldrar() {
		// sql
		StringBuffer sb = new StringBuffer();
		sb.append("select replace(substr(t.oldfile,instr(t.oldfile,'\\',1,5)+1,3),'\\','') as name,");
		sb.append("	substr(t.oldfile,instr(t.oldfile,'\\',1,6)+1,8) as datet,");
		sb.append(" t.*,");
		sb.append(" t.rowid");
		sb.append("  from sys_lrd_oldrar t");
		sb.append(" where 1 = 1");
		// sb.append("   and t.flag = '2'");
		sb.append("   and t.oldfile not like '%html%'");
		sb.append("   and t.date1> sysdate-1");
		sb.append(" order by date1 desc");
		// get connection
		Connection conn = DBUtils.getConnection();
		// new list
		List<RarOldBean> list = new ArrayList<RarOldBean>();
		Statement stat = null;
		ResultSet rs = null;
		try {
			// create statement
			stat = conn.createStatement();
			// execute query
			rs = stat.executeQuery(sb.toString());
			// 循环取值
			while (rs.next()) {
				RarOldBean bean = new RarOldBean();
				bean.setDatet(rs.getString("DATET"));
				bean.setName(rs.getString("NAME"));
				bean.setOldfile(rs.getString("OLDFILE"));
				bean.setFlag(rs.getString("FLAG"));
				bean.setDate1(rs.getString("DATE1"));
				list.add(bean);
				System.out.println(list.size() + bean.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(rs);
			DBUtils.close(stat);
			DBUtils.close(conn);
		}
		return list;
	}

	/**
	 * 方法名： updateOldrarToMySql
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param list
	 * 返 回： void
	 * 作 者 ： wang
	 * 时 间 ： 2015年12月4日 下午2:30:29
	 * 版 本 ： V1.0
	 * 
	 * @throws
	 */
	@SuppressWarnings("resource")
	public void updateOldrarToMySql(List<RarOldBean> list) {
		// sql
		String sql = "insert into 上传文件表(姓名,日期,文件,解压,录入时间) values(?,?,?,?,?)";
		String upsql = "update 上传文件表  set 姓名 = ?,日期 =?, 解压 = ?,录入时间=? where 文件 = ?";
		String num = "select count(1) from 上传文件表   where 文件 =?";

		// get connection
		Connection conn = MySQLDBUtils.getConnection();
		// new list
		PreparedStatement pstat = null;
		// ResultSet 
		ResultSet rs  = null;
		for (RarOldBean bean : list) {
			try {
				pstat = conn.prepareStatement(num);
				pstat.setString(1, bean.getOldfile());
				int i = 0;
				rs = pstat.executeQuery();
				while (rs.next()) {
					i = rs.getInt(1);
				}

				if (i > 0) {
					pstat = conn.prepareStatement(upsql);
					pstat.setString(1, bean.getName());
					pstat.setString(2, bean.getDatet());
					pstat.setString(3, bean.getFlag());
					pstat.setString(4, bean.getDate1());
					pstat.setString(5, bean.getOldfile());
					// 执行语句
					pstat.executeUpdate();
				} else {
					// create statement
					pstat = conn.prepareStatement(sql);

					// 设置参数
					pstat.setString(1, bean.getName());
					pstat.setString(2, bean.getDatet());
					pstat.setString(3, bean.getOldfile());
					pstat.setString(4, bean.getFlag());
					pstat.setString(5, bean.getDate1());
					// 执行语句
					pstat.executeUpdate();
				}
			} catch (SQLException e) {
			} finally {
				MySQLDBUtils.close(rs);
				MySQLDBUtils.close(pstat);
				MySQLDBUtils.close(conn);
			}
		}
	}
}
