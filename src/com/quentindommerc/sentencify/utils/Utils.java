package com.quentindommerc.sentencify.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

public class Utils {

	public static void writeFileToInternalStorage(String fileName, Context ct, String content) {
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new OutputStreamWriter(ct.openFileOutput(fileName,
					Context.MODE_PRIVATE), "UTF-8"));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
					Logger.d("file writing done.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String iso2CountryCodeToIso3CountryCode(String iso2CountryCode) {
		Locale locale = new Locale("", iso2CountryCode);
		return locale.getISO3Country();
	}

	public static boolean hasInternet(Context ct) {
		if (ct != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) ct
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		} else
			return false;
	}

	public static String formatDate(String in, String inFormat, String outFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(inFormat, Locale.FRANCE);
		SimpleDateFormat sdfout = new SimpleDateFormat(outFormat, Locale.FRANCE);
		try {
			return sdfout.format(sdf.parse(in));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String formatDate(String in, String inFormat, String outFormat, int secondsToAdd) {
		SimpleDateFormat sdf = new SimpleDateFormat(inFormat, Locale.FRANCE);
		SimpleDateFormat sdfout = new SimpleDateFormat(outFormat, Locale.FRANCE);
		try {
			return sdfout.format(new Date(sdf.parse(in).getTime() + (secondsToAdd * 1000)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String formatDate(Date time, String out) {
		SimpleDateFormat sdf = new SimpleDateFormat(out);
		return sdf.format(time);
	}

	public static CharSequence minutesToHoursAndMinutes(int duration) {
		int hours = duration / 60;
		int minutes = duration % 60;
		return hours + "h" + String.format("%02d", minutes);
	}

	public static String timestampToTime(long optLong) {
		Date d = new Date(optLong);
		SimpleDateFormat f = new SimpleDateFormat("HH:mm", Locale.FRANCE);
		return f.format(d);
	}

	public static Date parseDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static int dpToPx(Context ct, int dp) {
		DisplayMetrics displayMetrics = ct.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	public static String getSharedPref(Context ct, String key) {
		SharedPreferences pref = ct.getSharedPreferences(Logger.TAG, Context.MODE_PRIVATE);
		return pref.getString(key, "");
	}

	public static void setSharedPref(Context ct, String key, String value) {
		SharedPreferences.Editor pref = ct.getSharedPreferences(Logger.TAG, Context.MODE_PRIVATE)
				.edit();
		pref.putString(key, value);
		pref.commit();
	}

	public static void rmSharedPref(Context ct, String key) {
		SharedPreferences.Editor pref = ct.getSharedPreferences(Logger.TAG, Context.MODE_PRIVATE)
				.edit();
		pref.remove(key);
		pref.commit();
	}

}
