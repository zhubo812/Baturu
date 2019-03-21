package org.bhu.time.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeHelper {

	SimpleDateFormat format;

	public TimeHelper(String formatStr) {
		this.format = getDateFormat(formatStr);
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	public long GetCurrentDateStamp() {
		Date date = new Date();
		return date.getTime();
	}

	public Date GetDate() {
		return new Date();
	}

	private SimpleDateFormat getDateFormat(String format) {
		return new SimpleDateFormat(format);
	}

	public Date getLastOneDate() {
		Date currentDate = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(currentDate);
		cd.add(Calendar.DATE, -1);// 减一天
		return cd.getTime();
	}
	
	public Date getLastOneDate(Date date) {

		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DATE, -1);// 减一天
		return cd.getTime();
	}

	public String getLastTwoDate() {
		Date currentDate = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(currentDate);
		cd.add(Calendar.DATE, -2);// 减两天
		return format.format(cd.getTime()).toString();
	}

	public Date getDate(String source) {
		Date date = null;
		try {
			date = format.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public List<String> getDates(Date minDate, Date maxDate) {
		List<String> list = new ArrayList<String>();
		Date begin = minDate;
		Date end = maxDate;
		Calendar cd;
		double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		double day = between / (24 * 3600);

		for (int i = 0; i <= day; i++) {
			cd = Calendar.getInstance();
			cd.setTime(minDate);
			cd.add(Calendar.DATE, i);// 增加一天
			list.add(format.format(cd.getTime()));
			// System.out.println(sdf.format(cd.getTime()));
		}
		return list;
	}

	public List<String> getWorkDates(Date minDate, Date maxDate) {
		List<String> list = new ArrayList<String>();
		Date begin = minDate;
		Date end = maxDate;
		Calendar cd;
		double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		double day = between / (24 * 3600);

		for (int i = 0; i <= day; i++) {
			cd = Calendar.getInstance();
			cd.setTime(minDate);
			cd.add(Calendar.DATE, i);// 增加一天
			Date currentDate = cd.getTime();
			int daynum = dayForWeek(currentDate);
			if (daynum > 5) {
				continue;
			}
			list.add(format.format(cd.getTime()));
		}
		return list;
	}

	/**
	 * 返回星期几
	 * 
	 * @param pTime
	 * @return
	 * @throws Exception
	 */
	public int dayForWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	// ====================================================================

	public long GetDateStamp(Date date) {
		return date.getTime();
	}

	public long GetDateStamp(SimpleDateFormat sf, String str) {
		Date date = null;
		try {
			date = sf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return GetDateStamp(date);
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @return
	 */
	public String getDateStr(long time) {
		return format.format(time);
	}

	public String getDateStr(Date date) {
		return format.format(date).toString();
	}

	/**
	 * 获取给定日期前一天的日期
	 * 
	 * @param currentDate
	 * @return
	 */
	public Date getLastOneDate(String currentDate) {
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(format.parse(currentDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cd.add(Calendar.DATE, -1);// 增加一天
		return cd.getTime();
	}

	public Date getLastDayofYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date date = calendar.getTime();
		return date;
	}

	public List<String> getLastDates(String date, int num) {
		Calendar cd = Calendar.getInstance();
		List<String> dates = new ArrayList<String>();
		for (int i = 1; i <= num; i++) {
			try {
				cd.setTime(format.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			cd.add(Calendar.DATE, -1 * i);// 增加一天
			dates.add(format.format(cd.getTime()).toString());
		}
		return dates;
	}

	public Date GetAfterOneDate(Date currentDate) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(currentDate);
		cd.add(Calendar.DATE, 1);// 增加一天
		return cd.getTime();
	}

	public Date getLastAnyDate(int i) {
		Date currentDate = new Date();
		Calendar cd = Calendar.getInstance();
		cd.setTime(currentDate);
		cd.add(Calendar.DATE, -i);// 增加一天
		return cd.getTime();
	}

	public String DateTransation(String time) {
		return format.format(Long.parseLong(time) * 1000L).toString();
	}

	public String getYear(String date) {
		return date.substring(0, 4);
	}

	public String getYear() {
		Calendar c = Calendar.getInstance();
		return String.valueOf(c.get(Calendar.YEAR));
	}

	public List<String> getDatesAfterOneDay(String startDate, String maxDate) {
		List<String> list = new ArrayList<String>();
		try {
			Date begin = format.parse(startDate);
			Date end = format.parse(maxDate);
			Calendar cd;
			double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			double day = between / (24 * 3600);

			for (int i = 1; i <= day; i++) {
				cd = Calendar.getInstance();
				cd.setTime(format.parse(startDate));
				cd.add(Calendar.DATE, i);// 增加一天
				list.add(format.format(cd.getTime()));
				// System.out.println(sdf.format(cd.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getDatesAfterOneDay(Date begin, Date end) {
		List<String> list = new ArrayList<String>();
		Calendar cd;
		double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		double day = between / (24 * 3600);

		for (int i = 1; i <= day; i++) {
			cd = Calendar.getInstance();
			cd.setTime(begin);
			cd.add(Calendar.DATE, i);// 增加一天
			list.add(format.format(cd.getTime()));
			System.out.println(format.format(cd.getTime()));
		}
		return list;
	}

	public List<String> getMonthsBetween(String minDate, String maxDate) {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(getDate(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(getDate(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

	public List<String> getMongoDates(String startDate, String endDate) {
		List<String> list = new ArrayList<String>();
		Date begin = new Date(startDate);
		Date end = new Date(endDate);
		Calendar cd;
		double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		double day = between / (24 * 3600);

		for (int i = 0; i <= day; i++) {
			cd = Calendar.getInstance();
			cd.setTime(begin);
			cd.add(Calendar.DATE, i);// 增加一天
			list.add(format.format(cd.getTime()));
			System.out.println(format.format(cd.getTime()));
		}
		return list;
	}

	public Date timeStamp2Date(long time) {
		return new Date(time);
	}

	public String timeStamp2FromIso(long sdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(sdate);
		cal.add(Calendar.HOUR, +8);
		return format.format(cal.getTime());
	}

	public Date timeStamp2FromIsoDate(long sdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(sdate);
		cal.add(Calendar.HOUR, +8);
		return cal.getTime();
	}

	@SuppressWarnings("deprecation")
	public String DateGregorian(String srcdate) {

		Date date = null;
		try {
			date = format.parse(srcdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date.toGMTString();
	}

	public List<String> getYearBetween(String minYear, String maxYear) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		try {
			min.setTime(format.parse(minYear));
			max.setTime(format.parse(maxYear));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar curr = min;
		while (curr.before(max) || curr.equals(max)) {
			result.add(format.format(curr.getTime()));
			curr.add(Calendar.YEAR, 1);
		}

		return result;
	}

	public List<String> getYearsByPeriod(String minYear, int period) {
		ArrayList<String> result = new ArrayList<String>();
		Calendar min = Calendar.getInstance();
		try {
			min.setTime(format.parse(minYear));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar curr = min;
		for (int i = 0; i < period; i++) {
			result.add(format.format(curr.getTime()));
			curr.add(Calendar.YEAR, 1);
		}

		return result;
	}

	@SuppressWarnings("-access")
	public String getFirstDayofMonth(int sYear, int sMonth) {
		Calendar c = Calendar.getInstance();
		String tStartdate = "";
		c.set(c.YEAR, sYear);
		c.set(c.MONTH, sMonth);
		tStartdate = String.valueOf(sYear) + "/" + String.valueOf(sMonth) + "/" + c.getActualMinimum(c.DAY_OF_MONTH);
		return tStartdate;
	}

	@SuppressWarnings("-access")
	public String getEndDayofMonth(int sYear, int sMonth) {
		Calendar c = Calendar.getInstance();
		String tEnddate = "";
		c.set(c.YEAR, sYear);
		c.set(c.MONTH, sMonth);
		tEnddate = String.valueOf(sYear) + "/" + String.valueOf(sMonth) + "/" + c.getActualMaximum(c.DAY_OF_MONTH);
		return tEnddate;
	}

	public String getLastAnyDate(String Date, int num) {
		Calendar cd = Calendar.getInstance();
		try {
			cd.setTime(format.parse(Date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cd.add(Calendar.DATE, -num);//
		return format.format(cd.getTime()).toString();
	}
}
