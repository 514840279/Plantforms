package com.dalianyijianxing.wth.bean;


/**    
*  文件名 ： RarOldBean.java  
*  包    名 ： com.dalianyijianxing.wth.bean  
*  描    述 ： TODO(用一句话描述该文件做什么)  
*  机能名称：
*  技能ID ：
*  作    者 ： Tenghui.Wang  
*  时    间 ： 2015年9月18日 下午12:53:56  
*  版    本 ： V1.0    
*/
public class RarOldBean {
	private String name;
	private String datet;
	private String oldfile;
	private String flag;
	private String date1;
	
	/**  
	 *  方法名 ： getOldfile 
	 *  功    能 ： 返回变量 oldfile 的值  
	 *  @return: String 
	 */
	public String getOldfile() {
		return oldfile;
	}
	/**  
	 *  方法名 ： setOldfile 
	 *  功    能 ： 设置变量 oldfile 的值
	 */
	public void setOldfile(String oldfile) {
		this.oldfile = oldfile;
	}
	/**  
	 *  方法名 ： getFlag 
	 *  功    能 ： 返回变量 flag 的值  
	 *  @return: String 
	 */
	public String getFlag() {
		return flag;
	}
	/**  
	 *  方法名 ： setFlag 
	 *  功    能 ： 设置变量 flag 的值
	 */
	public void setFlag(String flag) {
		if("-1".equals(flag)){
			this.flag = "解压出错";
		}else if("2".equals(flag)){
			this.flag = "解压成功";
		}else{
			this.flag = "未操作";
		}
		
	}
	/**  
	 *  方法名 ： getDate1 
	 *  功    能 ： 返回变量 date1 的值  
	 *  @return: String 
	 */
	public String getDate1() {
		return date1;
	}
	/**  
	 *  方法名 ： setDate1 
	 *  功    能 ： 设置变量 date1 的值
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	/**  
	 *  方法名 ： getName 
	 *  功    能 ： 返回变量 name 的值  
	 *  @return: String 
	 */
	public String getName() {
		return name;
	}
	/**  
	 *  方法名 ： setName 
	 *  功    能 ： 设置变量 name 的值
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**  
	 *  方法名 ： getDatet 
	 *  功    能 ： 返回变量 datet 的值  
	 *  @return: String 
	 */
	public String getDatet() {
		return datet;
	}
	/**  
	 *  方法名 ： setDatet 
	 *  功    能 ： 设置变量 datet 的值
	 */
	public void setDatet(String datet) {
		this.datet = datet;
	}
	/** 
	*  方法名 ： toString
	*  功    能 ： TODO(这里用一句话描述这个方法的作用)  
	*  参    数 ： @return  
	*  参    考 ： @see java.lang.Object#toString()  
	*  时    间 ： 2015年12月5日 上午10:05:59 
	*  作    者 ： wang  
	*/
	
	@Override
	public String toString() {
		return "RarOldBean [name=" + name + ", datet=" + datet + ", oldfile=" + oldfile + ", flag=" + flag + ", date1=" + date1 + "]";
	}
	
	
	
}
