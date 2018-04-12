package com.dalianyijianxing.wth.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dalianyijianxing.wth.bean.FTPBean;
import com.dalianyijianxing.wth.bean.FileFilter;
import com.dalianyijianxing.wth.bean.WebFtpFileList;
import com.dalianyijianxing.wth.changerartofile.OldRarFile;


public class Down_by_http {
	
	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed,   // 断点下载文件失败
		Download_New_Success,    // 全新下载文件成功
		Download_New_Failed;    // 全新下载文件失败
	}
	
	//
	private static FTPBean	bean	= new FTPBean(0);
	
	public List<WebFtpFileList> login() {
		WebFtpFileList fileList = null;
		HttpClientBuilder httpclientbuilder = HttpClientBuilder.create();
		CloseableHttpClient closeablehttpclient = httpclientbuilder.build();
		HttpPost httppost = new HttpPost("http://121.41.47.76:60819/Web%20Client/Login.xml?Command=Login&Sync=1441682752171");
		httppost.addHeader("Host", "121.41.47.76:60819");
		httppost.addHeader("X-User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
		httppost.addHeader("Origin", "http://121.41.47.76:60819");
		httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
		httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httppost.addHeader("Accept", "*/*");
		httppost.addHeader("Referer", "http://121.41.47.76:60819/");
		httppost.addHeader("Accept-Encoding", "gzip");
		httppost.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		List<WebFtpFileList> resultList = new ArrayList<WebFtpFileList>();
		try {
			// 设置超时
//			HttpClient client = new DefaultHttpClient();
//			HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);
//			HttpResponse hps = client.execute(httppost);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user", "zmn40414"));
			params.add(new BasicNameValuePair("pword", "asinidea0414"));
			params.add(new BasicNameValuePair("language", "zh%2CCN"));
		
			System.out.println("登录成功");
		
			fileList = new WebFtpFileList();
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
			httppost.setEntity(entity);
			
			HttpResponse httpresponse = closeablehttpclient.execute(httppost);
			int status = httpresponse.getStatusLine().getStatusCode();
			System.out.println("status : " + status);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(httpresponse.getEntity().getContent(), "utf-8"));
			String line = null;
			StringBuilder stringbuilder = new StringBuilder();
			while ((line = bufferedreader.readLine()) != null) {
				stringbuilder.append(line);
				if (line.indexOf("Operation was successful.") != -1)
					System.out.println("Operation was successful.");
			}
			fileList.setSession(stringbuilder.toString());
			fileList.setSession(fileList.getSession().substring(fileList.getSession().indexOf("<sessionid>") + 11, fileList.getSession().indexOf("</sessionid>")));
			// System.out.println("sessionid=" + fileList.getSession());
			// System.out.println(stringbuilder.toString());
			resultList.addAll(getlist(fileList.getSession(), bean.getPath()));
			bufferedreader.close();
			closeablehttpclient.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return resultList;
	}
	
	private List<WebFtpFileList> getlist(String session, String dir) {
		System.out.println("获取下载路径"+dir);
		HttpClientBuilder httpclientbuilder = HttpClientBuilder.create();
		CloseableHttpClient closeablehttpclient = httpclientbuilder.build();
		
		HttpGet httpget = new HttpGet("http://121.41.47.76:60819/Web%20Client/ListError.xml?Command=List&Dir=" + dir + "/&sync=1441768201352");
		httpget.addHeader("Host", "121.41.47.76:60819");
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpget.addHeader("Accept-Encoding", "gzip");
		httpget.addHeader("Content-Type", "application/x-www-form-urlencoded");
		httpget.addHeader("Referer", "http://121.41.47.76:60819/Web%20Client/ListDir.htm");
		httpget.addHeader("Cookie", "killmenothing; SULang=zh%2CCN; themename=vista; domainname=ZMN_03; homelinktip=false; multitrans=0; multitransbubbletip=false; Session=" + session);
		List<WebFtpFileList> resultList = new ArrayList<WebFtpFileList>();
		WebFtpFileList fileList = null;
		try {
			
			HttpResponse httpresponse = closeablehttpclient.execute(httpget);
			// int status = httpresponse.getStatusLine().getStatusCode();
			// System.out.println("status : " + status + "---dir : " + dir + "---路径：" + URLDecoder.decode(dir));
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(httpresponse.getEntity().getContent());
			NodeList files = document.getElementsByTagName("file");
			if (files.getLength() < 1) {
				System.out.println("无文件");
				return new ArrayList<WebFtpFileList>();
			}
			// String yesterday = GetDate.getYesterday();
			for (int i = 0; i < files.getLength(); i++) {
				fileList = new WebFtpFileList();
				Element file = (Element) files.item(i);
				// System.out.println("----"+file.getTextContent()+"----");
				String filename = file.getElementsByTagName("FileName").item(0).getFirstChild().getNodeValue();
				// System.out.println("FileName:" + URLDecoder.decode(filename, "utf-8"));
				String filesize = file.getElementsByTagName("FileSize").item(0).getFirstChild().getNodeValue();
				// System.out.println("FileSize:" + filesize);
				// String filedate = file.getElementsByTagName("FileDate").item(0).getFirstChild().getNodeValue();
				// System.out.println("FileDate:" + filedate);
				String filepath = file.getElementsByTagName("FilePath").item(0).getFirstChild().getNodeValue();
				// System.out.println("FilePath:" + URLDecoder.decode(filepath, "utf-8"));
				String fileisdir = file.getElementsByTagName("FileIsDir").item(0).getFirstChild().getNodeValue();
				// System.out.println("FileIsDir:" + fileisdir);
				if (fileisdir.equals("1"))
					resultList.addAll(getlist(session, filepath));
				// dirlist.add(filepath);
				if (fileisdir.equals("0") && !filesize.equals("0")) {// &&filename.equals(yesterday+".rar")&& filename.indexOf(yesterday) != -1&& Integer.parseInt(filesize)<1024*1024*50
					System.out.println(filename + " 添加到下载目录...");
					fileList.setFileName(filename);
					fileList.setFileList(filepath);
					fileList.setFilesize(filesize);
					fileList.setSession(session);
					
					resultList.add(fileList);
				}
				System.out.println("--------------------------------------");
			}
			
			// bufferedreader.close();
			closeablehttpclient.close();
			System.out.println("路径取得完成");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	private void downfile(String filename, String filepath, String session, String size, String dir) {
		
		HttpClientBuilder httpclientbuilder = HttpClientBuilder.create();
		CloseableHttpClient closeablehttpclient = httpclientbuilder.build();
		
		HttpGet httpget = new HttpGet("http://121.41.47.76:60819/?Command=Download&File=" + filepath);
		httpget.addHeader("Host", "121.41.47.76:60819");
		httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
		httpget.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpget.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpget.addHeader("Accept-Encoding", "gzip");
		httpget.addHeader("Referer", "http://121.41.47.76:60819/Web%20Client/ListDir.htm");
		httpget.addHeader("Cookie", "killmenothing; SULang=zh%2CCN; themename=vista; domainname=ZMN_03; homelinktip=false; multitrans=0; multitransbubbletip=false; Session=" + session);
		httpget.addHeader("Connection", "keep-alive");
		// F:\data_recovery_by_ftp\data\数据采集\杨春雨
		InputStream inputstream = null;
		OutputStream fileout = null;
		
		try {
			String path = dir + URLDecoder.decode(filepath, "utf-8");
			System.out.println("开始下载文件"+path);
			
			HttpResponse httpresponse = closeablehttpclient.execute(httpget);
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("文件不存在 " + file.getName());
				
				file.getParentFile().mkdirs();
				System.out.println(file.getName() + "创建完毕");
				inputstream = httpresponse.getEntity().getContent();
				fileout = new FileOutputStream(file);
				
				byte[] buffer = new byte[1024 * 1024];
				int ch = 0;
				while ((ch = inputstream.read(buffer)) != -1) {
					fileout.write(buffer, 0, ch);
				}
				inputstream.close();
				fileout.close();
				closeablehttpclient.close();
				System.out.println("下载"+path+"结束");
			}else {
				
				long localSize = file.length();
				long lRemoteSize = Long.parseLong(size);
				if (localSize >= lRemoteSize) {
					System.out.println("本地文件大于远程文件，下载中止");
					return ;
				}
				// 进行断点续传，????(不好用)
//				httpget.addHeader("RANGE", "bytes="+localSize+1+"-");
				inputstream = httpresponse.getEntity().getContent();
				// 跳过模块localsize大小
//				inputstream.skip(localSize);
				
				fileout = new FileOutputStream(file);
				
				byte[] buffer = new byte[1024 * 1024];
				int ch = 0;
				while ((ch = inputstream.read(buffer)) != -1) {
					fileout.write(buffer, 0, ch);
				}
				inputstream.close();
				fileout.close();
				closeablehttpclient.close();
				System.out.println("下载"+path+"结束");
			}
			OldRarFile.insert(path);
		} catch (IOException e) {
			return;
		}
		
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	*  方法名： doDownload  
	*  功    能： 下载  
	*  参    数： @param dir 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public void doDownload(String dir) throws UnsupportedEncodingException {
		// 文件过滤
		FileFilter fileFilter = new FileFilter();
		// 
		Down_by_http down = new Down_by_http();
		// 
		List<String> listrar = OldRarFile.getfullOldfile();
		
		List<WebFtpFileList> fileList = down.login();
		for (WebFtpFileList webFtpFileList : fileList) {
			// 已经录入的文件
			
			
			String path = dir + URLDecoder.decode(webFtpFileList.getFileList(), "utf-8");
			path=path.replace("/", "\\").replace("d:", "D:");
			if (listrar.contains(path)) {
				continue;
			}
			boolean flag = false;
			for (String str : fileFilter.getStrlist()) {
				if (path.contains(str)) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			down.downfile(webFtpFileList.getFileName(), webFtpFileList.getFileList(), webFtpFileList.getSession(), webFtpFileList.getFilesize(), dir);
			
			listrar.add(path);
		}
	}
	
	/**
	 * 
	*  文件名 ： Down_by_http.java  
	*  包    名 ： com.dalianyijianxing.wth.utils  
	*  描    述 ： 内部类  StopMsgException
	*  作    者 ： Tenghui.Wang  
	*  时    间 ： 2015年9月27日 上午9:31:16  
	*  版    本 ： V1.0
	 */
	static class StopMsgException extends RuntimeException {
		
		// @Fields serialVersionUID : 标识串码
		private static final long	serialVersionUID	= -2115036428032152170L;
	}
	
	
}
