package com.dalianyijianxing.wth.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Administrator
 *
 */
public class MySQLDBUtils {

	// jdbc:oracle:thin
	private static final String	URL			= "jdbc:mysql://218.60.65.15:3306/data_recovery&autoReconnect=true&failOverReadOnly=false";
	private static final String	USER		= "root";
	private static final String	PASSWORD	= "P$6w*5D#9";

	// 1 
	static {
		try {
			// OracleDriver
			Class.forName("org.gjt.mm.mysql.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement state) {
		try {
			if (state != null) {
				state.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
