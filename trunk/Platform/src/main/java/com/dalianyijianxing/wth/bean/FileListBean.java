package com.dalianyijianxing.wth.bean;

import java.util.Date;

/**    
*  文件名 ： FileListBean.java  
*  包    名 ： com.dalianyijianxing.wth.bean  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月17日 下午3:33:46  
*  版    本 ： V1.0    
*/
public class FileListBean {
	private String	filepath;				// VARCHAR2(1000) Y 文件绝对路径（含名称）
	private String	filename;				// VARCHAR2(200) Y 文件名
	private String	stateflag;				// VARCHAR2(10) Y 0 0：文件录入，-1：执行出错，1：执行开始，2：执行完了,3:sp执行完了，10：废弃,13:新加字段了，11：未配置表的列，12：文件和表的关系都没有配置
	private Date	date1;					// DATE Y sysdate 文件录入，最后一次操作 日期 12月后只表示收入时间
	private String	reason;				// VARCHAR2(4000) Y 说明废弃的原因
	private String	removeerrorcode;		// VARCHAR2(10) Y 0 进行删除错误行，0未执行，1执行过
	private String	uuid;					// CHAR(36) Y 唯一编号
	private int		countNumber;			// NUMBER Y 文件的数据总量
	private int		errorNumber;			// NUMBER Y 错误的数据量
	private int		moreNumber;			// NUMBER Y 超出长度的数据量
	private Date	storageEndDateTime;	// DATE Y 入库的结束时间
	private Date	storageStartDateTime;	// DATE Y 入库的开始时间
	private String	storageTableName;		// NVARCHAR2(50) Y 入库的表名
	private int		storageNumber;			// NUMBER Y 入库的数据量
	private String	lrdTaskId;				// NVARCHAR2(500) Y 任务单号
	private Date	updateTime;			// DATE Y 更新时间
											
	/**  
	*  构造方法： 
	*  描    述： TODO(这里用一句话描述这个方法的作用)  
	*  参    数： 
	*  作    者 ： Tenghui.Wang  
	*  @throws  
	*/
	public FileListBean() {
		super();
	}
	
	/**  
	*  构造方法： 
	*  描    述： TODO(这里用一句话描述这个方法的作用)  
	*  参    数： @param filepath
	*  参    数： @param filename
	*  参    数： @param stateflag
	*  作    者 ： Tenghui.Wang  
	*  @throws  
	*/
	public FileListBean(String filepath , String filename , String stateflag) {
		super();
		this.filepath = filepath;
		this.filename = filename;
		this.stateflag = stateflag;
	}
	
	/**  
	 *  方法名 ： getFilepath 
	 *  功    能 ： 返回变量 filepath 的值  
	 *  @return: String 
	 */
	public String getFilepath() {
		return filepath;
	}
	
	/**  
	 *  方法名 ： setFilepath 
	 *  功    能 ： 设置变量 filepath 的值
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	/**  
	 *  方法名 ： getFilename 
	 *  功    能 ： 返回变量 filename 的值  
	 *  @return: String 
	 */
	public String getFilename() {
		return filename;
	}
	
	/**  
	 *  方法名 ： setFilename 
	 *  功    能 ： 设置变量 filename 的值
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**  
	 *  方法名 ： getStateflag 
	 *  功    能 ： 返回变量 stateflag 的值  
	 *  @return: String 
	 */
	public String getStateflag() {
		return stateflag;
	}
	
	/**  
	 *  方法名 ： setStateflag 
	 *  功    能 ： 设置变量 stateflag 的值
	 */
	public void setStateflag(String stateflag) {
		this.stateflag = stateflag;
	}

	/**  
	 *  方法名 ： getDate1 
	 *  功    能 ： 返回变量 date1 的值  
	 *  @return: Date 
	 */
	public Date getDate1() {
		return date1;
	}

	/**  
	 *  方法名 ： setDate1 
	 *  功    能 ： 设置变量 date1 的值
	 */
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	/**  
	 *  方法名 ： getReason 
	 *  功    能 ： 返回变量 reason 的值  
	 *  @return: String 
	 */
	public String getReason() {
		return reason;
	}

	/**  
	 *  方法名 ： setReason 
	 *  功    能 ： 设置变量 reason 的值
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**  
	 *  方法名 ： getRemoveerrorcode 
	 *  功    能 ： 返回变量 removeerrorcode 的值  
	 *  @return: String 
	 */
	public String getRemoveerrorcode() {
		return removeerrorcode;
	}

	/**  
	 *  方法名 ： setRemoveerrorcode 
	 *  功    能 ： 设置变量 removeerrorcode 的值
	 */
	public void setRemoveerrorcode(String removeerrorcode) {
		this.removeerrorcode = removeerrorcode;
	}

	/**  
	 *  方法名 ： getUuid 
	 *  功    能 ： 返回变量 uuid 的值  
	 *  @return: String 
	 */
	public String getUuid() {
		return uuid;
	}

	/**  
	 *  方法名 ： setUuid 
	 *  功    能 ： 设置变量 uuid 的值
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**  
	 *  方法名 ： getCountNumber 
	 *  功    能 ： 返回变量 countNumber 的值  
	 *  @return: int 
	 */
	public int getCountNumber() {
		return countNumber;
	}

	/**  
	 *  方法名 ： setCountNumber 
	 *  功    能 ： 设置变量 countNumber 的值
	 */
	public void setCountNumber(int countNumber) {
		this.countNumber = countNumber;
	}

	/**  
	 *  方法名 ： getErrorNumber 
	 *  功    能 ： 返回变量 errorNumber 的值  
	 *  @return: int 
	 */
	public int getErrorNumber() {
		return errorNumber;
	}

	/**  
	 *  方法名 ： setErrorNumber 
	 *  功    能 ： 设置变量 errorNumber 的值
	 */
	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	/**  
	 *  方法名 ： getMoreNumber 
	 *  功    能 ： 返回变量 moreNumber 的值  
	 *  @return: int 
	 */
	public int getMoreNumber() {
		return moreNumber;
	}

	/**  
	 *  方法名 ： setMoreNumber 
	 *  功    能 ： 设置变量 moreNumber 的值
	 */
	public void setMoreNumber(int moreNumber) {
		this.moreNumber = moreNumber;
	}

	/**  
	 *  方法名 ： getStorageEndDateTime 
	 *  功    能 ： 返回变量 storageEndDateTime 的值  
	 *  @return: Date 
	 */
	public Date getStorageEndDateTime() {
		return storageEndDateTime;
	}

	/**  
	 *  方法名 ： setStorageEndDateTime 
	 *  功    能 ： 设置变量 storageEndDateTime 的值
	 */
	public void setStorageEndDateTime(Date storageEndDateTime) {
		this.storageEndDateTime = storageEndDateTime;
	}

	/**  
	 *  方法名 ： getStorageStartDateTime 
	 *  功    能 ： 返回变量 storageStartDateTime 的值  
	 *  @return: Date 
	 */
	public Date getStorageStartDateTime() {
		return storageStartDateTime;
	}

	/**  
	 *  方法名 ： setStorageStartDateTime 
	 *  功    能 ： 设置变量 storageStartDateTime 的值
	 */
	public void setStorageStartDateTime(Date storageStartDateTime) {
		this.storageStartDateTime = storageStartDateTime;
	}

	/**  
	 *  方法名 ： getStorageTableName 
	 *  功    能 ： 返回变量 storageTableName 的值  
	 *  @return: String 
	 */
	public String getStorageTableName() {
		return storageTableName;
	}

	/**  
	 *  方法名 ： setStorageTableName 
	 *  功    能 ： 设置变量 storageTableName 的值
	 */
	public void setStorageTableName(String storageTableName) {
		this.storageTableName = storageTableName;
	}

	/**  
	 *  方法名 ： getStorageNumber 
	 *  功    能 ： 返回变量 storageNumber 的值  
	 *  @return: int 
	 */
	public int getStorageNumber() {
		return storageNumber;
	}

	/**  
	 *  方法名 ： setStorageNumber 
	 *  功    能 ： 设置变量 storageNumber 的值
	 */
	public void setStorageNumber(int storageNumber) {
		this.storageNumber = storageNumber;
	}

	/**  
	 *  方法名 ： getLrdTaskId 
	 *  功    能 ： 返回变量 lrdTaskId 的值  
	 *  @return: String 
	 */
	public String getLrdTaskId() {
		return lrdTaskId;
	}

	/**  
	 *  方法名 ： setLrdTaskId 
	 *  功    能 ： 设置变量 lrdTaskId 的值
	 */
	public void setLrdTaskId(String lrdTaskId) {
		this.lrdTaskId = lrdTaskId;
	}

	/**  
	 *  方法名 ： getUpdateTime 
	 *  功    能 ： 返回变量 updateTime 的值  
	 *  @return: Date 
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**  
	 *  方法名 ： setUpdateTime 
	 *  功    能 ： 设置变量 updateTime 的值
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
