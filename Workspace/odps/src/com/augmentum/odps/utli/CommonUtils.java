package com.augmentum.odps.utli;

import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

	// Adds days according to the input date.
	public static Date addDays(Date date, int days) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);

			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	// Converts date's type to UNIX format. 
	public static long convertToUnixTime(Date date) {
		String timeStr = (date.getTime() + "").substring(0, 10);
		return Long.parseLong(timeStr);
	}
}
