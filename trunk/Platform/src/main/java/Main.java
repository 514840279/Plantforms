import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPClient;

import com.dalianyijianxing.wth.bean.FTPBean;
import com.dalianyijianxing.wth.bean.FileDirectory;
import com.dalianyijianxing.wth.bean.FileFilter;
import com.dalianyijianxing.wth.bean.FileListBean;
import com.dalianyijianxing.wth.bean.RarOldBean;
import com.dalianyijianxing.wth.changerartofile.CompressFile;
import com.dalianyijianxing.wth.changerartofile.OldRarFile;
import com.dalianyijianxing.wth.getfilefromftp.DownloadFromFTP;
import com.dalianyijianxing.wth.remove.RemoveErrorMessage;
import com.dalianyijianxing.wth.runbattodb.WriteFileNameToDb;
import com.dalianyijianxing.wth.runsp.RunSP;
import com.dalianyijianxing.wth.utils.Down_by_http;
import com.dalianyijianxing.wth.utils.FTPUtil;
import com.dalianyijianxing.wth.utils.ListFiles;
import com.dalianyijianxing.wth.utils.ReadFTPFile;

/**    
*  文件名 ： Main.java  
*  包    名 ：   
*  描    述 ：  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年8月31日 下午3:17:52  
*  版    本 ： V1.0    
*/
public class Main {
	private static FTPBean				bean		= new FTPBean("");
	private static FTPClient			ftpClient	= FTPUtil.getFTPClient(bean.getUrl(), bean.getPassword(), bean.getUsername(), Integer.parseInt(bean.getPort()));
	private static FileDirectory		dir			= new FileDirectory();
	private static WriteFileNameToDb	wf			= new WriteFileNameToDb();
	private static FileFilter			fileFilter	= new FileFilter();
	
	/**
	 * 
	 * 方法名： main 
	 * 功 能： 应用入口 
	 * 参 数： @param args 
	 * 参 数： @throws Exception 
	 * 返 回： void 
	 * 作 者： Tenghui.Wang
	 * 
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
		timeTask();
		
	}
	
	/**  
	*  方法名： task  
	*  功    能： 任务主体  
	*  参    数： @param main
	*  参    数： @throws IOException 
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private static void task(Main main) throws IOException {
		
		// 集合
		List<String> list =  null;
		// 获取服务器上的文件列表
		list = main.rungetFtpfileList();
		// 将文件全部下载到本地dir.downloadDirectory
		main.downLoadFileFromFtp(list);
		// 将web文件全部下载到本地dir.downloadDirectory
		main.downLoadFileFromFtpWeb();
		// 将文件类型是（rar） 文件解压到本地dir.pressDirectory
		main.comPress();
		// 同步数据到mysql
		updateOldrar();
		// 遍历取出本地dir.pressDirectory的所有文件
		Map<String, String> map = main.getLocalFileList();
		// 将数据保存进数据库中（sqlite，oracle）
		main.insertWf(map);
		// 执行数据文件修改
		new RemoveErrorMessage(dir);
		// 执行sp
		main.runsp();
		
		System.out.println("本次执行完毕，24小时候再次执行");
	}
	
	/**  
	*  方法名： updateOldrar  
	*  功    能： TODO(这里用一句话描述这个方法的作用)  
	*  参    数： @return 
	*  返    回： Object  
	*  作    者 ： wang  
	*  时    间 ： 2015年12月4日 下午2:10:13 
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private static void updateOldrar() {
		 List<RarOldBean> list = wf.updateOldrar();
		 wf.updateOldrarToMySql(list);
		 
	}

	/**
	 * @throws UnsupportedEncodingException   
	*  方法名： downLoadFileFromFtpWeb  
	*  功    能： 下载web服务器上的数据文件
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private void downLoadFileFromFtpWeb() throws UnsupportedEncodingException {
		Down_by_http down = new Down_by_http();
		down.doDownload(dir.getWebdownLoadDirectory());
		
	}

	/**
	 * 
	*  方法名： timeTask  
	*  功    能： 计时任务
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws
	 */
	public static void timeTask() {
		Timer timer = new Timer();
		// 第一次试探timer的使用
		timer.schedule(new TimerTask() {                  // 要做的事情，在规则里面进行声明
			@Override
			public void run() {
				Main main = new Main();
				try {
					task(main);
				} catch (IOException e) {
					e.printStackTrace();
				}
				main=null;
			}
		}, 0, 1000 * 60 * 60 * 24);
	}
	
	/**  
	*  方法名： updateWf  
	*  功    能： 更新状态
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private void updateWf(String type, String path, String name) {
		wf.update(type, path, name);
		
	}
	
	/**  
	*  方法名： insertWf  
	*  功    能： 插入文件列表
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private void insertWf(Map<String, String> map) {
		wf.insert(map);
		
	}
	
	/**  
	*  方法名： runsp  
	*  功    能： 执行存储过程
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private void runsp() {
		List<FileListBean> list = wf.select();
		RunSP runsp = new RunSP();
		String dir = "";
		String file = "";
		try {
			for (FileListBean bean : list) {
				dir = bean.getFilepath().replace("/", "\\");
				file = bean.getFilename();
				
				// 根据类型，进行相应的runsp
				String type = file.substring(file.lastIndexOf(".") + 1).toLowerCase();
				if ("txt".equals(type)) {
					updateWf("2", dir, file);
					 // 写入入库开始时间
//					WriteFileNameToDb.updateStarttime(dir);
					// 执行入库 过程
					runsp.runSp(dir.replaceAll(file, ""), file);
					// 写入结束时间
					WriteFileNameToDb.updateEndtime(dir);
					// 获取收集好的的文件入库信息
					bean = WriteFileNameToDb.queryFileListBean(dir);
					// 同步到mysql中
					WriteFileNameToDb.updateToMySqlDB(bean);
				}
			}
		} catch (Exception e) {
			updateWf("-1", dir, file);
		}
		
	}
	
	/**  
	*  方法名： getLocalFileList  
	*  功    能：获取本地文件的列表
	*  参    数：  
	*  返    回： map  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private Map<String, String> getLocalFileList() {
		ListFiles filesList = new ListFiles();
		// 扫描解压后ftp文件
		Map<String, String> map = filesList.getFileList(dir.getPressFileDirectory());
		// 扫描解压后webftp文件
		map.putAll(filesList.getFileList(dir.getWebpressFileDirectory()));
		return map;
	}
	
	/**  
	*  方法名： comPress  
	*  功    能： 解压文件（rar，zip）
	*  参    数：  
	*  返    回： void  
	*  作    者 ： Tenghui.Wang  
	*  版    本 ： V1.0 
	*  @throws  
	*/
	private void comPress() {
		List<String> list = OldRarFile.getOldfile();
		// 循环取文件
		for (String bean : list) {
			// 判断文件已经解压过
			
			try {
				if(bean.contains(dir.getWebdownLoadDirectory())){
					CompressFile.generalRARFile(bean, dir.getWebpressFileDirectory());
				}else{
					// CompressFile.deCompress(dir.getDownLoadDirectory() + string, dir.getPressFileDirectory());
					CompressFile.generalRARFile(bean, dir.getPressFileDirectory());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**  
	 *  方法名： downLoadFileFromFtp  
	 *  功    能： 下载文件到dir.download.directory
	 *  参    数： @param list
	 *  参    数： @throws IOException 
	 *  返    回： void  
	 *  作    者 ： Tenghui.Wang  
	 *  @throws  
	 */
	private void downLoadFileFromFtp(List<String> list) throws IOException {
		List<String> listrar = OldRarFile.getfullOldfile();
		DownloadFromFTP download = new DownloadFromFTP();
		// 所有文件循环下载
		for (String string : list) {
			String pathstring = (dir.getDownLoadDirectory()+string).replace("/", "\\").replace("d:", "D:");
			if (listrar.contains(pathstring)) {
				continue;
			}
			boolean flag = false;
			for (String str : fileFilter.getStrlist()) {
				if (string.contains(str)) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			download.downLoadFromFTP(ftpClient, string, string);
		}
	}

	
	/**  
	 *  方法名： rungetFtpfileList  
	 *  功    能： 获取文件列表信息
	 *  参    数： @throws IOException 
	 *  返    回： void  
	 *  作    者 ： Tenghui.Wang  
	 *  @throws  
	 */
	private List<String> rungetFtpfileList() throws IOException {
		List<String> list = new ArrayList<String>();
		ReadFTPFile readftpfile = new ReadFTPFile();
		// 改变目录
		try {
			// 获取ftp文件列表
			list = readftpfile.readFullPath(ftpClient, bean.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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
		Main.bean = bean;
	}
	
	/**  
	 *  方法名 ： getFtpClient 
	 *  功    能 ： 取得 ftpClient 的值  
	 *  @return: FTPClient  ftpClient
	 */
	public synchronized org.apache.commons.net.ftp.FTPClient getFtpClient() {
		return ftpClient;
	}
	
	/**  
	 *  方法名 ： setFtpClient 
	 *  功    能 ： 设置 ftpClient 的值
	 */
	public synchronized void setFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) {
		Main.ftpClient = ftpClient;
	}
	
	/**  
	 *  方法名 ： getDir 
	 *  功    能 ： 返回变量 dir 的值  
	 *  @return: FileDirectory 
	 */
	public FileDirectory getDir() {
		return dir;
	}
	
	/**  
	 *  方法名 ： setDir 
	 *  功    能 ： 设置变量 dir 的值
	 */
	public void setDir(FileDirectory dir) {
		Main.dir = dir;
	}
	
	/**  
	 *  方法名 ： getWf 
	 *  功    能 ： 返回变量 wf 的值  
	 *  @return: WriteFileNameToDb 
	 */
	public WriteFileNameToDb getWf() {
		return wf;
	}
	
	/**  
	 *  方法名 ： setWf 
	 *  功    能 ： 设置变量 wf 的值
	 */
	public void setWf(WriteFileNameToDb wf) {
		Main.wf = wf;
	}
	
}
