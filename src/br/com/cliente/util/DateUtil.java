package br.com.cliente.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final String FORMAT = "dd/MM/yyyy";

	public static Date parse(String dateStr, String formatStr) {

		Date d1 = null;
		try {
			if (formatStr == null || "".equals(formatStr.trim())) {
				formatStr = FORMAT;
			}
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			d1 = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d1;
	}
	
	public static int getParcelas(Date startDate, Date endDate) {
		int qtParcelas = 0;
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int mesInicial = start.get(Calendar.MONTH);
		int mesEnd = end.get(Calendar.MONTH);
		qtParcelas = mesEnd - mesInicial;
		return qtParcelas;
	}
	
	
	public static String formatDate(Date date, String formatStr) {

		String d1 = null;
		try {
			if (formatStr == null || "".equals(formatStr.trim())) {
				formatStr = FORMAT;
			}
			SimpleDateFormat format = new SimpleDateFormat(formatStr);
			d1 = format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d1;
	}

}
