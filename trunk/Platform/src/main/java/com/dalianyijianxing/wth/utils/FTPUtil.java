package com.dalianyijianxing.wth.utils;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPHTTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FTPUtil {
	private static Logger	logger	= Logger.getLogger(FTPUtil.class);
	
	/**
	 * 获取FTPClient对象
	 * 
	 * @param ftpHost
	 *            FTP主机服务器
	 * @param ftpPassword
	 *            FTP 登录密码
	 * @param ftpUserName
	 *            FTP登录用户名
	 * @param ftpPort
	 *            FTP端口 默认为21
	 * @return
	 */
	public static FTPClient getFTPClient(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			if ( !FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTP，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTP连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTP的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTP的端口错误,请正确配置。");
		}
		return ftpClient;
	}
	
	/**
	 * 链接关闭
	 * @param ftpClient 链接关闭
	 * @return 
	 */
	public static void close(FTPClient ftpClient) {
		try {
			ftpClient.logout();
			ftpClient.disconnect();
			logger.debug("ftp logout ....");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.toString());
		}
	}
	
	/**
	 * 
	*  方法名： getWebrequest  
	*  功    能： http连接方式
	*  参    数： @param ftpHost
	*  参    数： @param ftpPassword
	*  参    数： @param ftpUserName
	*  参    数： @param ftpPort
	*  参    数： @return 
	*  返    回： FTPHTTPClient  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static FTPHTTPClient getWebrequest(String ftpHost, String ftpPassword,
			String ftpUserName, int ftpPort) {
		FTPHTTPClient ftpClient = null;
		try {
			ftpClient = new FTPHTTPClient(ftpHost, ftpPort, ftpUserName, ftpPassword);
			ftpClient.connect(ftpHost, ftpPort);
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			if ( !FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				logger.info("未连接到FTPweb，用户名或密码错误。");
				ftpClient.disconnect();
			} else {
				logger.info("FTPweb连接成功。");
			}
		} catch (SocketException e) {
			e.printStackTrace();
			logger.info("FTPwev的IP地址可能错误，请正确配置。");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("FTPweb的端口错误,请正确配置。");
		}
		return ftpClient;
	}
	
}
