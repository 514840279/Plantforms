package com.dalianyijianxing.wth.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.dalianyijianxing.wth.changerartofile.OldRarFile;

public class ReadFTPFile {
	private Logger	logger	= Logger.getLogger(ReadFTPFile.class);
	private String pathtemp = ""; 
	
	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed,   // 断点下载文件失败
		Download_New_Success,    // 全新下载文件成功
		Download_New_Failed;    // 全新下载文件失败
	}
	
	/**
	 * 去 服务器的FTP路径下上读取文件(小文件)
	 * 
	 * @param ftpUserName
	 * @param ftpPassword
	 * @param ftpPath
	 * @param FTPServer
	 * @return
	 */
	public String readConfigFileForFTP(FTPClient ftpClient, String ftpPath, String fileName) {
		StringBuffer resultBuffer = new StringBuffer();
		InputStream in = null;
		logger.info("开始读取绝对路径" + ftpPath + "文件!");
		try {
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpPath);
			in = ftpClient.retrieveFileStream(ftpPath + fileName);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			e.printStackTrace();
			return "下载配置文件失败，请联系管理员.";
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("文件读取错误。");
			e.printStackTrace();
			return "配置文件读取失败，请联系管理员.";
		}
		if (in != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data = null;
			try {
				while ((data = br.readLine()) != null) {
					resultBuffer.append(data + "\n");
				}
			} catch (IOException e) {
				logger.error("文件读取错误。");
				e.printStackTrace();
				return "配置文件读取失败，请联系管理员.";
			} finally {
				// try {
				// ftpClient.disconnect();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
			}
		} else {
			logger.error("in为空，不能读取。");
			return "配置文件读取失败，请联系管理员.";
		}
		return resultBuffer.toString();
	}
	
	
	/**
	 * 
	 *  方法名： readFullPath  
	 *  功    能： 获取ftp文件夹下所有的文件
	 *  参    数： @param ftpClient
	 *  参    数： @param path
	 *  参    数： @return
	 *  参    数： @throws IOException 
	 *  返    回： List<String>  
	 *  作    者 ： Tenghui.Wang  
	 *  @throws
	 */
	public List<String> readFullPath(FTPClient ftpClient, String path) throws IOException {
		List<String> list = new ArrayList<String>();
//		ftpClient.changeWorkingDirectory(path);
		ftpClient.changeWorkingDirectory(new String(path.getBytes("UTF-8"),"ISO-8859-1"));
//		ftpClient.changeWorkingDirectory(new String(path.getBytes(),"utf-8"));
//		 转码
		logger.info(path);
		// 开通一个端口来传输数据 ,防止假死
		ftpClient.enterLocalPassiveMode();
		// 取文件列表
		FTPFile[] files = ftpClient.listFiles();
		// 取目录列表名
		// FTPFile[] Directories = ftpClient.listDirectories(path);
		// DataInputStream dis = new DataInputStream();
		
		for (int i=0; i<files.length;i++) {
			FTPFile ftpFile = files[i];
			if(".".equals(ftpFile.getName())||"..".equals(ftpFile.getName())){
				continue;
			}
			if (ftpFile.isFile()) {
				// if (ftpFile.getName().toLowerCase().contains("rar")
				// || ftpFile.getName().toLowerCase().contains("zip")) {
				// 添加文件名
				list.add(path + ftpFile.getName());
				// System.out.println(ftpFile.isFile() + "====" + ftpFile.getName());
				// }
			}
			else {
				// 改变目录很重要
//				ftpClient.changeWorkingDirectory(path+ftpFile.getName() + "/");
//				ftpClient.changeWorkingDirectory(new String(path.getBytes(),"ISO-8859-1"));
				// 递归
				list.addAll(readFullPath(ftpClient, path+ftpFile.getName() + "/"));
			}
			
		}
		return list;
	}
	
	/**  
	* 从FTP服务器上下载文件,支持断点续传，上传百分比汇报  
	* @param remote 远程文件路径  
	* @param local 本地文件路径  
	* @return 上传的状态  
	* @throws IOException  
	*/
	public DownloadStatus download(FTPClient ftpClient, String remote, String local) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;
		// 开通一个端口来传输数据 ,防止假死
		ftpClient.enterLocalPassiveMode();
		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length != 1) {
			System.out.println("远程文件不存在");
			return DownloadStatus.Remote_File_Noexist;
		}
		
		long lRemoteSize = files[0].getSize();
		// 判断一个文件夹是否存在，并创建目录
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				System.out.println("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}
			
			// 进行断点续传，并记录状态
			
			FileOutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("UTF-8"), "iso-8859-1"));
			byte[] bytes = new byte[1024*1024*10];
			long step = lRemoteSize / 100;
			if (step == 0){
				step = 1;
			}
			long process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						// 更新文件下载进度,值存放在process变量中
						System.out.println("下载进度：" + remote + " 下载了 " + process + "%");
					
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				result = DownloadStatus.Download_From_Break_Failed;
			}
			OldRarFile.insert(local);
		} else {
			String[] temp = local.split("/");
			String name = temp[temp.length-1];
			// 开通一个端口来传输数据 ,防止假死
			ftpClient.enterLocalPassiveMode();
			File ftemp = new File(local.replace(name, ""));
			ftemp.mkdirs();
			OutputStream out = new FileOutputStream(f);
			InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("UTF-8"), "iso-8859-1"));
			byte[] bytes = new byte[1024*1024*10];
			long step = lRemoteSize / 100;
			if (step == 0){
				step = 1;
			}
			long process = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					if (process % 10 == 0)
						System.out.println("下载进度：" + remote + " 下载了 " + process + "%");
					// 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			OldRarFile.insert(local);
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				result = DownloadStatus.Download_New_Success;
			} else {
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/**  
	 *  方法名 ： getPathtemp 
	 *  功    能 ： 返回变量 pathtemp 的值  
	 *  @return: String 
	 */
	public String getPathtemp() {
		return pathtemp;
	}

	/**  
	 *  方法名 ： setPathtemp 
	 *  功    能 ： 设置变量 pathtemp 的值
	 */
	public void setPathtemp(String pathtemp) {
		this.pathtemp = pathtemp;
	}
	
	
}
