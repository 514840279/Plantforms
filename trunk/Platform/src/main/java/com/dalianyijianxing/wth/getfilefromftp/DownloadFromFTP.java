package com.dalianyijianxing.wth.getfilefromftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import com.dalianyijianxing.wth.bean.FTPBean;
import com.dalianyijianxing.wth.bean.FileDirectory;
import com.dalianyijianxing.wth.utils.ReadFTPFile;

/**    
*  文件名 ： DownloadFromFTP.java  
*  包    名 ： com.dalianyijianxing.wth.getfilefromftp  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月16日 下午4:20:14  
*  版    本 ： V1.0    
*/
public class DownloadFromFTP {
	private FTPBean bean = new FTPBean("");
	private FileDirectory dir = new FileDirectory();

	public void downLoadFromFTP(FTPClient ftpClient, String path,String fileName) throws IOException {
		ReadFTPFile file = new ReadFTPFile();
		file.download(ftpClient, path, dir.getDownLoadDirectory()+ fileName);
		//StringFileUtils.string2File(string, dir.getDownLoadDirectory() + fileName);
	}

	/**  
	 *  方法名 ： getBean 
	 *  功    能 ： 取得 bean 的值  
	 *  @return: FTPBean  bean
	 */
	public synchronized FTPBean getBean() {
		return bean;
	}

	/**  
	 *  方法名 ： setBean 
	 *  功    能 ： 设置 bean 的值
	 */
	public synchronized void setBean(FTPBean bean) {
		this.bean = bean;
	}

	/**  
	 *  方法名 ： getDir 
	 *  功    能 ： 取得 dir 的值  
	 *  @return: FileDirectory  dir
	 */
	public synchronized FileDirectory getDir() {
		return dir;
	}

	/**  
	 *  方法名 ： setDir 
	 *  功    能 ： 设置 dir 的值
	 */
	public synchronized void setDir(FileDirectory dir) {
		this.dir = dir;
	}

}
