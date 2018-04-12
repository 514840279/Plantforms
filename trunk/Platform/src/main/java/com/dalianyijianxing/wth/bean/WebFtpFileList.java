package com.dalianyijianxing.wth.bean;


/**    
*  文件名 ： WebFtpFileList.java  
*  包    名 ： com.dalianyijianxing.wth.bean  
*  描    述 ： webFtp文件列表实体类
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月26日 下午1:50:59  
*  版    本 ： V1.0    
*/
public class WebFtpFileList {
	private String fileList ;
	private String fileName ;
	private String filesize;
	private String session;
	/**  
	 *  方法名 ： getFileList 
	 *  功    能 ： 返回变量 fileList 的值  
	 *  @return: String 
	 */
	public String getFileList() {
		return fileList;
	}
	/**  
	 *  方法名 ： setFileList 
	 *  功    能 ： 设置变量 fileList 的值
	 */
	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	/**  
	 *  方法名 ： getFileName 
	 *  功    能 ： 返回变量 fileName 的值  
	 *  @return: String 
	 */
	public String getFileName() {
		return fileName;
	}
	/**  
	 *  方法名 ： setFileName 
	 *  功    能 ： 设置变量 fileName 的值
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**  
	 *  方法名 ： getFilesize 
	 *  功    能 ： 返回变量 filesize 的值  
	 *  @return: String 
	 */
	public String getFilesize() {
		return filesize;
	}
	/**  
	 *  方法名 ： setFilesize 
	 *  功    能 ： 设置变量 filesize 的值
	 */
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	/**  
	 *  方法名 ： getSession 
	 *  功    能 ： 返回变量 session 的值  
	 *  @return: String 
	 */
	public String getSession() {
		return session;
	}
	/**  
	 *  方法名 ： setSession 
	 *  功    能 ： 设置变量 session 的值
	 */
	public void setSession(String session) {
		this.session = session;
	}
	
	
}
