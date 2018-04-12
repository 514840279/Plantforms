package com.dalianyijianxing.wth.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**    
*  文件名 ： FTPBean.java  
*  包    名 ： com.dalianyijianxing.wth.bean  
*  描    述 ：ftp实体类提供链接
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月16日 下午5:12:22  
*  版    本 ： V1.0    
*/
public class FTPBean {
	private String url;
	private String port;
	private String username;
	private String password;
	private String path;
	private static Logger logger = Logger.getLogger(FTPBean.class);

	public FTPBean(String str) {
//		String path = System.getProperty("user.dir");
		Properties prop = new Properties();
//		String fullpath = this.getClass().getClassLoader().getResource(".").getPath(); 
		InputStream in = null;
        try {
        	in=Object.class.getClass().getResourceAsStream("/ftpAddress.properties");
//	        in = new BufferedInputStream(new FileInputStream("ftpAddress.properties"));
	        prop.load(in);
			url = prop.getProperty("ftp.url").trim();
			port = prop.getProperty("ftp.port").trim();
			username = prop.getProperty("ftp.username").trim();
			password = prop.getProperty("ftp.password").trim();
			path = prop.getProperty("ftp.path").trim();
			logger.info(url+port+username+password+path);
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	public FTPBean(int str) {
//		String path = System.getProperty("user.dir");
		Properties prop = new Properties();
//		String fullpath = this.getClass().getClassLoader().getResource(".").getPath(); 
		 InputStream in=Object.class.getClass().getResourceAsStream("/ftpAddress.properties");
//		InputStream in;
        try {
//	        in = new BufferedInputStream(new FileInputStream("ftpAddress.properties"));
	        prop.load(in);
			url = prop.getProperty("ftpweb.url").trim();
			port = prop.getProperty("ftpweb.port").trim();
			username = prop.getProperty("ftpweb.username").trim();
			password = prop.getProperty("ftpweb.password").trim();
			path = prop.getProperty("ftpweb.path").trim();
			logger.info(url+port+username+password+path);
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	/**  
	 *  方法名 ： getUrl 
	 *  功    能 ： 返回变量 url 的值  
	 *  @return: String 
	 */
	public String getUrl() {
		return url;
	}
	
	/**  
	 *  方法名 ： setUrl 
	 *  功    能 ： 设置变量 url 的值
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**  
	 *  方法名 ： getPort 
	 *  功    能 ： 返回变量 port 的值  
	 *  @return: String 
	 */
	public String getPort() {
		return port;
	}
	
	/**  
	 *  方法名 ： setPort 
	 *  功    能 ： 设置变量 port 的值
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**  
	 *  方法名 ： getUsername 
	 *  功    能 ： 返回变量 username 的值  
	 *  @return: String 
	 */
	public String getUsername() {
		return username;
	}
	
	/**  
	 *  方法名 ： setUsername 
	 *  功    能 ： 设置变量 username 的值
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**  
	 *  方法名 ： getPassword 
	 *  功    能 ： 返回变量 password 的值  
	 *  @return: String 
	 */
	public String getPassword() {
		return password;
	}
	
	/**  
	 *  方法名 ： setPassword 
	 *  功    能 ： 设置变量 password 的值
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**  
	 *  方法名 ： getPath 
	 *  功    能 ： 返回变量 path 的值  
	 *  @return: String 
	 */
	public String getPath() {
		return path;
	}
	
	/**  
	 *  方法名 ： setPath 
	 *  功    能 ： 设置变量 path 的值
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
}
