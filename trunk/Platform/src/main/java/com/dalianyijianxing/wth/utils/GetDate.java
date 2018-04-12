package com.dalianyijianxing.wth.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate {
	/**
	 * 获取前一天的日期
	 * @return yyyyMMdd
	 */
	public static String getYesterday(){
		String new_date_f = new SimpleDateFormat("yyyyMMdd").format(new Date()); // 将日期时间格式化
		Calendar c = Calendar.getInstance();
		Date date=null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(new_date_f);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE,day-1);
		String dayBefore=new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return dayBefore;
	}
	  /**
     * 获得当前日期
     * 
     * @return yyyyMMdd
     */
public static String getdate() {
	java.text.SimpleDateFormat formatter_f = new java.text.SimpleDateFormat(
			"yyyyMMdd");
	java.util.Date currentTime_f = new java.util.Date(); // 得到当前系统时间
	String new_date_f = formatter_f.format(currentTime_f); // 将日期时间格式化
	return new_date_f;
}
}
