package com.zjx.utils;

import com.xiaoleilu.hutool.date.DateTime;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY$MM$DD = "yyyy/MM/dd";
	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYY$MM$DD_HH_MM = "yyyy/MM/dd HH:mm";
	public static final String YYYY$MM$DD_HH_MM_A = "yyyy/MM/dd HH:mm a";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String HHMMSS = "HH:mm:ss";
	public static final String HHMM = "HH:mm";

	public static final String CHINA_YYYYMMDD = "yyyy年MM月dd日";
	public static final String CHINA_YYYYMMDDHHMMSS = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String CHINA_HHMMSS = "HH时mm分ss秒";
	public static final String CHINA_YYYYMMDDHHMMSSSSS = "yyyy年MM月dd日 HH时mm分ss秒.SSS";

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat LOCAL_DATE_TIME_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);

	/**
	 * <p>
	 * 获取当前时间
	 * <p>
	 * 统一封装获取时间、便于后期维护与扩展
	 *
	 * @return
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 自定义格式化时间
	 *
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(Date date, String formatStr) {
		if (date == null) {
			return StringUtils.EMPTY;
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(formatStr);
	}

	/**
	 * 日期时间字符串转换成时间
	 *
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date parseDate(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			if (dateStr.length() != formatStr.length()) {
				throw new IllegalArgumentException("时间字符与格式化类型不匹配");
			}
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 计算几个月之后的时间
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @param months
	 * @return
	 */
	public static String addMonth(String dateStr, String formatStr, int months) {
		String nowDate = null;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date parse = format.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parse);
			calendar.add(Calendar.MONTH, months);
			nowDate = format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowDate;
	}

	/**
	 * 比较日期
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static int compareDate(String dateStr1, String dateStr2) {
		Date date1 = parseDate(dateStr1);
		Date date2 = parseDate(dateStr2);
		if (date1.getTime() > date2.getTime())
			return -1;
		else if (date1.getTime() < date2.getTime())
			return 1;
		else
			return 0;
	}

	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() > date2.getTime())
			return -1;
		else if (date1.getTime() < date2.getTime())
			return 1;
		else
			return 0;
	}

	public static Date parseDate(String date) {
		return parseDate(date, YYYY_MM_DD);
	}

	public static Date parseChinaDate(String date) {
		return parseDate(date, CHINA_YYYYMMDD);
	}

	public static Date parseDateTime(String date) {
		return parseDate(date, YYYY_MM_DD_HH_MM_SS);
	}

	public static Date parseTime(String date) {
		return parseDate(date, HHMMSS);
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		int realMonth = month + 1;
		String realMonthStr = String.valueOf(realMonth);
		String monthStr = realMonthStr.length() == 1 ? ("0" + realMonthStr) : realMonthStr;
		return monthStr;
	}

	/**
	 * 获取天数
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dayStr = String.valueOf(day);
		String withZeroDay = dayStr.length() == 1 ? ("0" + dayStr) : dayStr;
		return withZeroDay;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(Optional.ofNullable(null).orElse(new Object()));
		System.out.println(formatDate(new Date(), YYYY_MM_DD_HH_MM_SS));
		System.out.println(parseDate("2019-04-25 18:20:30", YYYY_MM_DD_HH_MM_SS));
		System.out.println(addMonth("2019-04-25 18:20:30", YYYY_MM_DD_HH_MM_SS, 12));
		String dateStr = "2019-1-1 15:12:11";
		Date date = DATE_FORMAT.parse(dateStr);
		System.out.println(getYearByDate(date));
		System.out.println(getMonthByDate(date));
		System.out.println(getDayByDate(date));
	}
}
