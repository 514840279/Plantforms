package com.dalianyijianxing.wth.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**    
 *  文件名 ： FileDirectory.java  
 *  包    名 ： com.dalianyijianxing.wth.bean  
 *  描    述 ： TODO(用一句话描述该文件做什么)  
 *  机能名称：
 *  技能ID ：
 *  作    者 ： Tenghui.Wang  
 *  时    间 ： 2015年9月17日 上午1:40:49  
 *  版    本 ： V1.0    
 */
public class FileDirectory {
	// 下载文件路径
	private String			downLoadDirectory;
	// web下载文件路径
	private String			webdownLoadDirectory;
	// 解压文件路径
	private String			pressFileDirectory;
	// web解压文件路径
	private String			webpressFileDirectory;
	// error_file 错误文件
	private String			error_file;
	//
	private int				threadCurrent;
	// log日志
	private static Logger	logger	= Logger.getLogger(FileDirectory.class);
	
	/**
	 * 
	*  构造方法： 
	*  描    述：初始化
	*  参    数： 
	*  时    间 ： 2015年9月27日 上午10:15:44 
	*  作    者 ： Tenghui.Wang  
	*  @throws
	 */
	public FileDirectory() {
		Properties prop = new Properties();
		// String fullpath = this.getClass().getClassLoader().getResource("").getPath();
		InputStream in;
		try {
			// String path = System.getProperty("user.dir");
			// in = new BufferedInputStream(new FileInputStream(fullpath+"directory.properties"));
			in = Object.class.getClass().getResourceAsStream("/directory.properties");
			//
			prop.load(in);
			downLoadDirectory = prop.getProperty("directory.downLoadDirectory").trim().replace("/", "\\");
			pressFileDirectory = prop.getProperty("directory.compressDirectory").trim().replace("/", "\\");
			webpressFileDirectory = prop.getProperty("directory.webcompressDirectory").trim().replace("/", "\\");
			webdownLoadDirectory = prop.getProperty("directory.webdownLoadDirectory").trim().replace("/", "\\");
			error_file = prop.getProperty("directory.error_file").trim().replace("/", "\\");
			
			logger.info("downLoadDirectory= " + downLoadDirectory);
			logger.info("pressFileDirectory= " + pressFileDirectory);
			logger.info("webpressFileDirectory= " + webpressFileDirectory);
			logger.info("webdownLoadDirectory= " + webdownLoadDirectory);
			logger.info("error_file= " + error_file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 *  方法名 ： getDownLoadDirectory 
	 *  功    能 ： 取得 downLoadDirectory 的值  
	 *  @return: String  downLoadDirectory
	 */
	public synchronized String getDownLoadDirectory() {
		return downLoadDirectory;
	}
	
	/**  
	 *  方法名 ： setDownLoadDirectory 
	 *  功    能 ： 设置 downLoadDirectory 的值
	 */
	public synchronized void setDownLoadDirectory(String downLoadDirectory) {
		this.downLoadDirectory = downLoadDirectory;
	}
	
	/**  
	 *  方法名 ： getPressFileDirectory 
	 *  功    能 ： 取得 pressFileDirectory 的值  
	 *  @return: String  pressFileDirectory
	 */
	public synchronized String getPressFileDirectory() {
		return pressFileDirectory;
	}
	
	/**  
	 *  方法名 ： setPressFileDirectory 
	 *  功    能 ： 设置 pressFileDirectory 的值
	 */
	public synchronized void setPressFileDirectory(String pressFileDirectory) {
		this.pressFileDirectory = pressFileDirectory;
	}
	
	/**  
	 *  方法名 ： getThreadCurrent 
	 *  功    能 ： 返回变量 threadCurrent 的值  
	 *  @return: int 
	 */
	public int getThreadCurrent() {
		return threadCurrent;
	}
	
	/**  
	 *  方法名 ： setThreadCurrent 
	 *  功    能 ： 设置变量 threadCurrent 的值
	 */
	public void setThreadCurrent(int threadCurrent) {
		this.threadCurrent = threadCurrent;
	}
	
	/**  
	 *  方法名 ： getWebdownLoadDirectory 
	 *  功    能 ： 返回变量 webdownLoadDirectory 的值  
	 *  @return: String 
	 */
	public String getWebdownLoadDirectory() {
		return webdownLoadDirectory;
	}
	
	/**  
	 *  方法名 ： setWebdownLoadDirectory 
	 *  功    能 ： 设置变量 webdownLoadDirectory 的值
	 */
	public void setWebdownLoadDirectory(String webdownLoadDirectory) {
		this.webdownLoadDirectory = webdownLoadDirectory;
	}
	
	/**  
	 *  方法名 ： getWebpressFileDirectory 
	 *  功    能 ： 返回变量 webpressFileDirectory 的值  
	 *  @return: String 
	 */
	public String getWebpressFileDirectory() {
		return webpressFileDirectory;
	}
	
	/**  
	 *  方法名 ： setWebpressFileDirectory 
	 *  功    能 ： 设置变量 webpressFileDirectory 的值
	 */
	public void setWebpressFileDirectory(String webpressFileDirectory) {
		this.webpressFileDirectory = webpressFileDirectory;
	}

	/**  
	 *  方法名 ： getError_file 
	 *  功    能 ： 返回变量 error_file 的值  
	 *  @return: String 
	 */
	public String getError_file() {
		return error_file;
	}

	/**  
	 *  方法名 ： setError_file 
	 *  功    能 ： 设置变量 error_file 的值
	 */
	public void setError_file(String error_file) {
		this.error_file = error_file;
	}
	
	
}
