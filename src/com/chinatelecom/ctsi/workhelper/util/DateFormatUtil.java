/**
 * 2011-10-24 上午11:27:59
 * TODO TODO
 * version 1.0
 */
package com.chinatelecom.ctsi.workhelper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateFormatUtil
 * @Description:
 * @version V1.0
 * @date 2011-10-24 上午11:27:59
 * 
 */
public class DateFormatUtil {

	public static String now17() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
    public static String toyyyyMMddHHmmss(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(time);// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
	public static String now() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
	public static String toreadTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String str = formatter.format(date);
		return str;
	}
	public static String nowMonth() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	// 2012年08月14日23:55
	public static String toRead(String dateStr) {
		Date d = todate(dateStr);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = formatter.format(d);
		return str;
	}
    public static String toRead(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(date);
        return str;
    }
	public static String getReadNow() {
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = formatter.format(curDate);
		return str;
	}

	public static Date todate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = null;
		// String str = formatter.format(curDate);
		try {
			curDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return curDate;
	}

	public static int getBetweenDaysFromNow(String t1) throws ParseException {
		return getBetweenDays(now(), t1);
	}

	public static int getBetweenDays(String t1, String t2)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		int betweenDays = 0;
		Date d1 = format.parse(t1);
		Date d2 = format.parse(t2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		boolean little0 = false;
		if (c1.after(c2)) {
			c1.setTime(d2);
			c2.setTime(d1);
			little0 = true;
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		if (little0) {
			betweenDays = 0 - betweenDays;
		}
		return betweenDays;
	}

	public static String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins);
		return sbBuffer.toString();
	}
	

	public static Date todateFromRead(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = null;
		// String str = formatter.format(curDate);
		try {
			curDate = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return curDate;
	}

}