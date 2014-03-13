package com.quentindommerc.sentencify.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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

	public static boolean writeToFile(String fileName, String value, Context context,
			int writeOrAppendMode) {
		// just make sure it's one of the modes we support
		if (writeOrAppendMode != Context.MODE_WORLD_READABLE
				&& writeOrAppendMode != Context.MODE_WORLD_WRITEABLE
				&& writeOrAppendMode != Context.MODE_APPEND) {
			return false;
		}
		try {
			/*
			 * We have to use the openFileOutput()-method the ActivityContext
			 * provides, to protect your file from others and This is done for
			 * security-reasons. We chose MODE_WORLD_READABLE, because we have
			 * nothing to hide in our file
			 */
			FileOutputStream fOut = context.openFileOutput(fileName, writeOrAppendMode);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			// Write the string to the file
			osw.write(value);
			// save and close
			osw.flush();
			osw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		boolean b = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		return b;
	}

	public static Bitmap getCircularBitmap(Bitmap bitmap) {
		Bitmap output;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Config.ARGB_8888);
		} else {
			output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

		float r = 0;

		if (bitmap.getWidth() > bitmap.getHeight()) {
			r = bitmap.getHeight() / 2;
		} else {
			r = bitmap.getWidth() / 2;
		}

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawCircle(r, r, r, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
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

	public static int dpToPx(Context ct, int dp) {
		DisplayMetrics displayMetrics = ct.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
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

}
