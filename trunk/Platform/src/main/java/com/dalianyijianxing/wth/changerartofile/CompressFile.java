package com.dalianyijianxing.wth.changerartofile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import net.lingala.zip4j.exception.ZipException;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**    
*  文件名 ： CompressFile.java  
*  包    名 ： com.dalianyijianxing.wth.changerartofile  
*  描    述 ： 解压缩文件（.rar，.zip）
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月16日 下午4:30:09  
*  版    本 ： V1.0    
*/
public class CompressFile {

	private static final int BUFFEREDSIZE = 1024 * 1024 * 10;
	private static final String PASSWORD = "Asinidea1509.";
	private static Logger logger = Logger.getLogger(CompressFile.class);

	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
		byte[] buf = new byte[1024];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				String str = srcfile[i].getName();
				System.out.println(str.toString());
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
			System.out.println("压缩完成.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * 压缩文件-File 
	 * @param zipFile  zip文件 
	 * @param srcFiles 被压缩源文件 
	 * @author isea533 
	 */
	public static void ZipFiles(ZipOutputStream out, String path, File... srcFiles) {
		path = path.replaceAll("\\*", "/");
		if (!path.endsWith("/")) {
			path += "/";
		}
		byte[] buf = new byte[1024];
		try {
			for (int i = 0; i < srcFiles.length; i++) {
				if (srcFiles[i].isDirectory()) {
					File[] files = srcFiles[i].listFiles();
					String srcPath = srcFiles[i].getName();
					srcPath = srcPath.replaceAll("\\*", "/");
					if (!srcPath.endsWith("/")) {
						srcPath += "/";
					}
					out.putNextEntry(new ZipEntry(path + srcPath));
					ZipFiles(out, path + srcPath, files);
				}
				else {
					FileInputStream in = new FileInputStream(srcFiles[i]);
					System.out.println(path + srcFiles[i].getName());
					out.putNextEntry(new ZipEntry(path + srcFiles[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * 解压到指定目录 
	 * @param zipPath 
	 * @param descDir 
	 * @author isea533 
	 */
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/** 
	 * 解压文件到指定目录 
	 * @param zipFile 
	 * @param descDir 
	 * @author isea533 
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.getEntries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************解压完毕********************");
	}

	/** 
	* 根据原始rar路径，解压到指定文件夹下.      
	* @param srcRarPath 原始rar路径 
	* @param dstDirectoryPath 解压到的文件夹      
	*/
	public static void unRarFile(String srcRarPath, String dstDirectoryPath) {
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			System.out.println("非rar文件！");
			return;
		}
		File dstDiretory = new File(dstDirectoryPath);
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive a = null;
		try {
			// a = new Archive(srcRarPath, PASSWORD, false);
			a = new Archive(new File(srcRarPath));
			if (a.isEncrypted()) {

			}
			if (a != null) {
				a.getMainHeader().print(); // 打印文件信息.
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {
					if (fh.isDirectory()) { // 文件夹
						File fol = new File(dstDirectoryPath + File.separator
								+ fh.getFileNameString());
						fol.mkdirs();
					} else { // 文件
						File out = new File(dstDirectoryPath + File.separator
								+ fh.getFileNameString().trim());
						// System.out.println(out.getAbsolutePath());
						try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);
							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}
				a.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	  * @SuppressWarnings({ "rawtypes", "resource" }) public static String
	  *                     unzip(File zipFile, String unzipDir, String
	  *                     zipFileName) throws IOException { ZipFile zf = new
	  *                     ZipFile(zipFile); Enumeration enu = zf.entries();
	  *                     String result = ""; while (enu.hasMoreElements()) {
	  *                     ZipEntry entry = (ZipEntry) enu.nextElement(); String
	  *                     name = entry.getName(); //
	  *                     如果解压entry是目录，直接生成目录即可，不用写入，如果是文件，要将文件写入 String
	  *                     pathParent = unzipDir + "/" + zipFileName; if (!(new
	  *                     File(pathParent).exists())) new
	  *                     File(pathParent).mkdirs(); String path = pathParent +
	  *                     "/" + name; result = result + path + "<br/>
	  *                     "; File file = new File(path); if
	  *                     (entry.isDirectory()) { file.mkdir(); } else { //
	  *                     建议使用如下方式创建流和读取字节，不然会有乱码 InputStream is =
	  *                     zf.getInputStream(entry); byte[] buf1 = new
	  *                     byte[1024]; int len; if (!file.exists()) {
	  *                     file.getParentFile().mkdirs(); file.createNewFile();
	  *                     } OutputStream out = new FileOutputStream(file);
	  *                     while ((len = is.read(buf1)) > 0) { String buf = new
	  *                     String(buf1, 0, len); out.write(buf1, 0, len); } } }
	  *                     result = "文件解压成功，解压文件：/n" + result; return result; }
	  **/

	/**
	* 解压zip或者rar包的内容到指定的目录下，可以处理其文件夹下包含子文件夹的情况
	* 
	* @param zipFilename
	*            要解压的zip或者rar包文件
	* @param outputDirectory
	*            解压后存放的目录
	* 
	*/
	public static void unzipZipRar(String zipFilename, String outputDirectory)
			throws Exception {
		File outFile = new File(outputDirectory);
		if (!outFile.exists()) {
			outFile.mkdirs();
		}

		ZipFile zipFile = new ZipFile(zipFilename);
		@SuppressWarnings("rawtypes")
		Enumeration en = zipFile.getEntries();
		ZipEntry zipEntry = null;
		while (en.hasMoreElements()) {
			zipEntry = (ZipEntry) en.nextElement();
			if (zipEntry.isDirectory()) {
				// mkdir directory
				String dirName = zipEntry.getName();
				// System.out.println("=dirName is:=" + dirName + "=end=");
				dirName = dirName.substring(0, dirName.length() - 1);
				File f = new File(outFile.getPath() + File.separator + dirName);
				f.mkdirs();
			} else {
				// unzip file
				String strFilePath = outFile.getPath() + File.separator
						+ zipEntry.getName();
				File f = new File(strFilePath);

				// 判断文件不存在的话，就创建该文件所在文件夹的目录
				if (!f.exists()) {
					String[] arrFolderName = zipEntry.getName().split("/");
					String strRealFolder = "";
					for (int i = 0; i < (arrFolderName.length - 1); i++) {
						strRealFolder += arrFolderName[i] + File.separator;
					}
					strRealFolder = outFile.getPath() + File.separator
							+ strRealFolder;
					File tempDir = new File(strRealFolder);
					// 此处使用.mkdirs()方法，而不能用.mkdir()
					tempDir.mkdirs();
				}
				// the codes remedified by can_do on 2010-07-02 =end=
				f.createNewFile();
				InputStream in = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(f);
				try {
					int c;
					byte[] by = new byte[BUFFEREDSIZE];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					// out.flush();
				} catch (IOException e) {
					throw e;
				} finally {
					out.close();
					in.close();
				}
			}
		}
	}

	/**
	/**
	* 解压zip格式压缩包 对应的是ant.jar
	*/
	private static void unzip(String sourceZip, String destDir)
			throws Exception {
		try {
			Project p = new Project();
			Expand e = new Expand();
			e.setProject(p);
			e.setSrc(new File(sourceZip));
			e.setOverwrite(false);
			e.setDest(new File(destDir));
			/*
			 * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 所以解压缩时要制定编码格式
			 */
			e.setEncoding("gbk");
			e.execute();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	* 解压rar格式压缩包。
	* 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar
	*/
	private static void unrar(String sourceRar, String destDir)
			throws Exception {
		Archive a = null;
		FileOutputStream fos = null;
		try {
			a = new Archive(new File(sourceRar));
			FileHeader fh = a.nextFileHeader();
			while (fh != null) {
				if (!fh.isDirectory()) {
					// 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName UTF-8 GB2312 GBK ISO8859_1
					String compressFileName = new String(fh.getFileNameString().toString());
					String destFileName = "";
					String destDirName = "";
					// 非windows系统
					if (File.separator.equals("/")) {
						destFileName = (destDir
								+ compressFileName).replaceAll("\\\\", "/");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("/"));
						// windows系统
					} else {
						destFileName = (destDir
								+ compressFileName).replaceAll("/", "\\\\");
						destDirName = destFileName.substring(0,
								destFileName.lastIndexOf("\\"));
					}
					// 2创建文件夹
					File dir = new File(destDirName);
					if (!dir.exists() || !dir.isDirectory()) {
						dir.mkdirs();
					}
					// 3解压缩文件
					fos = new FileOutputStream(new File(destFileName));
					a.extractFile(fh, fos);
					fos.close();
					fos = null;
				}
				fh = a.nextFileHeader();
			}
			a.close();
			a = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (a != null) {
				try {
					a.close();
					a = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	* 解压缩
	*/
	public static void deCompress(String sourceFile, String destDir)
			throws Exception {
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = destDir.charAt(destDir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			destDir += File.separator;
		}
		// 根据类型，进行相应的解压缩
		String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1).toLowerCase();
		if (type.equals("zip")) {
			CompressFile.unzip(sourceFile, destDir);
		} else if (type.equals("rar")) {
			CompressFile.unrar(sourceFile, destDir);
		}
	}

	/**
	 * 
	*  方法名： ExtractAllFiles  
	*  功    能： 解压加密文件
	*  参    数： @param file
	*  参    数： @param dir 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static void ExtractAllFiles(String file, String dir) {
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = dir.charAt(dir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			dir += File.separator;
		}
		// 根据类型，进行相应的解压缩
		String type = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
		if (type.equals("zip") || type.equals("rar")) {

			try {
				net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile(file);
				if (zipFile.isEncrypted()) {
					// if yes, then set the password for the zip file
					zipFile.setPassword(PASSWORD);
				}
				zipFile.extractAll(dir);

			} catch (ZipException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	*  方法名： generalZipFile  
	*  功    能： CMD模式解压带密码的rar文件
	*  参    数： @param file
	*  参    数： @param dir 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static void generalRARFile(String file, String dir) {
		OldRarFile.update(file,"1");
		// 保证文件夹路径最后是"/"或者"\"
		char lastChar = dir.charAt(dir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			dir += File.separator;
		}
		// 根据类型，进行相应的解压缩
		String type = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
		dir = file.replace(file.substring(file.lastIndexOf("\\") + 1), "");
		if (type.equals("rar")) {
			System.out.println("解压文件：" + file);
			StringBuffer cmd = new StringBuffer();// 安装的winrar路径
			cmd.append("  unrar x -o+ -p");
			cmd.append(PASSWORD);// 压缩密码
			cmd.append(" ");//
			cmd.append(file);// 原文件的路径
			cmd.append(" ");
			cmd.append(dir);// 目标路径
			System.out.println(cmd.toString());
			logger.info(cmd.toString());
			try {
				Process proc = Runtime.getRuntime().exec(cmd.toString());
				if (proc.waitFor() != 0) {// waitFor将返回exitValue的值0表示正常结束 {
					System.out.println("出错了");
					OldRarFile.update(file,"-1");
					return;
				} else {
					OldRarFile.update(file,"2");
					// 解压完的删除rar文件
//					File files = new File(file);
//					files.delete();
				}
				proc.destroy();
			} catch (Exception e) {
				System.out.println(file + " ====解压失败" + e);
				OldRarFile.update(file,"-1");
			}
		}
		return;
	}

}
