package com.quentindommerc.sentencify.utils;

import android.util.Log;

public class Logger {

	enum Level {
		DEBUG, INFO, ERROR, SILENT
	};

	public static String TAG = "Sentencify";
	public static int level = Level.DEBUG.ordinal();

	public static void e(Object... s) {
		if (level <= Level.ERROR.ordinal()) {
			String e = "";
			for (int i = 0; i < s.length; i++) {
				e += String.valueOf(s[i]) + " ";
			}
			Log.e(TAG, e);
		}
	}

	public static void d(Object... s) {
		if (level <= Level.DEBUG.ordinal()) {
			String e = "";
			for (int i = 0; i < s.length; i++) {
				e += String.valueOf(s[i]) + " ";
			}
			Log.d(TAG, e);
		}
	}

	public static void i(Object... s) {
		if (level <= Level.INFO.ordinal()) {
			String e = "";
			for (int i = 0; i < s.length; i++) {
				e += String.valueOf(s[i]) + " ";
			}
			Log.i(TAG, e);
		}
	}
}
