package com.dalianyijianxing.wth.remove;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.dalianyijianxing.wth.runbattodb.WriteFileNameToDb;

/**    
*  文件名 ： FileIOStream.java  
*  包    名 ： com.dalianyijianxing.wth.remove  
*  描    述 ： 文件操作類
*  作    者 ： wang  
*  时    间 ： 2015年11月12日 下午2:43:39  
*  版    本 ： V1.0    
*/
public class FileIOStream {
	/**
	 * 
	*  方法名： readTxtFile  
	*  功    能： 讀取文件
	*  参    数： @param filePath
	*  参    数： @return 
	*  返    回： List<String>  
	*  作    者 ： wang  
	*  时    间 ： 2015年11月12日 下午2:46:14 
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static List<String> readTxtFile(String filePath) {
		List<String> strList = new ArrayList<String>();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				strList = new ArrayList<String>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					strList.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return strList;
	}
	
	/**
	 * 
	*  方法名： writeTxtFile  
	*  功    能： 寫入文件  
	*  参    数： @param filePath
	*  参    数： @param list
	*  参    数： @throws IOException 
	*  返    回： void  
	*  作    者 ： wang  
	*  时    间 ： 2015年11月12日 下午4:32:59 
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static void writeTxtFile(String filePath, List<String> list) throws IOException {
		
		// 创建目录
		File dir = new File(filePath.substring(0, filePath.lastIndexOf("\\")));
		System.out.println(filePath.substring(0, filePath.lastIndexOf("\\")));
		if ( !dir.exists() && (filePath.contains("more") || filePath.contains("error"))) {
			File file = new File(filePath);
			dir.mkdirs();
			file.createNewFile();
		}
		// 输出信息
		FileOutputStream fileOutput = new FileOutputStream(filePath);
		PrintWriter fileWrite = new PrintWriter(new OutputStreamWriter(fileOutput, "UTF-8"));
		BufferedWriter buffereWriter = new BufferedWriter(fileWrite);
		for (String string : list) {
			
			buffereWriter.write(string);
			buffereWriter.write("\r\n");
		}
		buffereWriter.close();
		fileWrite.close();
		fileOutput.close();
	}
	
	/**
	 * 
	*  方法名： start  
	*  功    能： 开始处理
	*  参    数： @param filepath
	*  参    数： @throws IOException 
	*  返    回： void  
	*  作    者 ： wang  
	*  时    间 ： 2015年11月12日 下午4:32:34 
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static void start(String filepath, String error_filePath) throws IOException {
		File file = new File(filepath);
		// 判断是否纯在文件
		if (file.exists()) {
			System.out.println("文件：" + filepath);
			List<String> list = readTxtFile(filepath);
			// 插入数据库数据
			WriteFileNameToDb.updateOriCount(filepath, list.size()-1);
			System.out.println("数据量" + list.size());
			int num = count(list.get(0), "`");
			System.out.println("原始烈数" + (num + 1));
			List<String> error_list = new ArrayList<String>();
			List<String> more_list = new ArrayList<String>();
			error_list.add(list.get(0));
			more_list.add(list.get(0));
			for (int i = 1; i < list.size(); i++) {
				
				// 删除多字段的行
				if (count(list.get(i), "`") != num) {
					error_list.add(list.get(i));
					list.remove(list.get(i));
					
					i--;
					continue;
				} else if (list.get(i).length() > 2000) {
					// 删除单字段长度大于4000的
					String[] s = list.get(i).split("`");
					try {
						WriteFileNameToDb.selcetLengthb(s);
					} catch (Exception e) {
						more_list.add(list.get(i));
						list.remove(list.get(i));
						i--;
					}
					
				}
				
			}
			System.out.println("修改后数据量" + list.size());
			// 重新写入文件
			writeTxtFile(filepath, list);
			WriteFileNameToDb.updateErrorByFile(filepath);
			if (error_list.size() > 1 || more_list.size() > 1) {
				// 文件短路径
				String temp_file_path = filepath.replace("D:\\zhcx_datafile_path\\datafiles\\webdownload\\数据采集\\", "").replace("D:\\zhcx_datafile_path\\datafiles\\download\\数据采集\\", "").replace("\\datafiles", "");
				//  临时文件名
				String temp_file_name = temp_file_path.substring(temp_file_path.lastIndexOf("\\") + 1);
				// 姓名
				String name = temp_file_path.substring(0, temp_file_path.indexOf("\\"));
				// 日期
				String date1 = temp_file_path.substring(temp_file_path.indexOf("\\") + 1, temp_file_path.indexOf("\\") + 9);
				// 错误的数据
				if (error_list.size() > 1) {
					System.out.println("修改后错误的数据量" + (error_list.size()-1));
					String path = error_filePath + name + "\\" + date1 + "\\error" + temp_file_name;
					// 写入错误文件
					writeTxtFile(path, error_list);
					// 错误数据写入文件 的记录
					WriteFileNameToDb.addErrorByFile(name, date1, filepath, list.size() - 1, path, error_list.size() - 1, "error");
					// 错误的数据量
					WriteFileNameToDb.updateErrorCount(filepath, error_list.size()-1);
				}
				// 超出长度的数据
				if (more_list.size() > 1) {
					System.out.println("修改后超长的数据量" + (more_list.size()-1));
					String path = error_filePath + name + "\\" + date1 + "\\more" + temp_file_name;
					// 写入文件信息
					writeTxtFile(path, more_list);
					// 记录超过长度的信息
					WriteFileNameToDb.addErrorByFile(name, date1, filepath, list.size() - 1, path, more_list.size() - 1, "more the max lengthb");
					// 记录超过长度的数据量
					WriteFileNameToDb.updateMoreCount(filepath, more_list.size()-1);
				}
			}
		} else {
			// 没找到文件，删除错误信息
			WriteFileNameToDb.delete(filepath);
		}
	}
	
	/**
	 * 
	*  方法名： count  
	*  功    能： 求得  ` 出现的次数
	*  参    数： @param text
	*  参    数： @param sub
	*  参    数： @return 
	*  返    回： int  
	*  作    者 ： wang  
	*  时    间 ： 2015年11月12日 下午3:03:45 
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static int count(String text, String sub) {
		int count = 0, start = 0;
		while ((start = text.indexOf(sub, start)) >= 0) {
			start += sub.length();
			count++;
		}
		return count;
	}
	
	// public static void main(String[] args) throws IOException {
	// String s = "D:\\zhcx_datafile_path\\datafiles\\webdownload\\数据采集\\米杨\\20151003\\datafiles\\20151003\\zhuanli_专利信息平台国外20151003.txt";
	// String temp_file_name = s.substring(s.indexOf("\\", 6));
	// System.out.println(temp_file_name);
	// start(s,"D:\\zhcx_datafile_path\\datafiles\\error_file\\");
	// }
}
