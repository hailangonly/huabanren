package com.cnn.android.basemodel.utils;

import android.util.Log;


/**
 * @功能 print logcat
 * @author 创建人 mj
 * @date 创建日期 2013-4-17
 * @author 修改人 Alvin
 * @date 修改日期 2013-4-17
 * @author 修改说明
 * @version 1.0
 */
public class Logger {
	
	public final static String JSON_ERROR = "解析json异常";

	public static void log(Object showMessage) {

		if (AppConstants.isDebug) {// 防止上线之后出现打印语句
			Log.i("ting", showMessage + "");
		}
		
	}

	public static void throwRuntimeException(Object exceptionMessage) {
		if (AppConstants.isDebug) {
			throw new RuntimeException("出现异常：" + exceptionMessage);
		}
	}

	public static String getLineInfo() {
		if (AppConstants.isDebug) {
			StackTraceElement ste = new Throwable().getStackTrace()[1];
			return "@" + ste.getFileName() + ": Line " + ste.getLineNumber();
		} else {
			return "";
		}
	}


	private static final int LOG_LEVEL = 0;
	public static void log(String tag, String message) {
		if (null != message && isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, message);
		}
	}

	public static void log(String tag, String message, boolean show) {
		if (null != message && isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, message);
		}
	}

	public static void v(String tag, String msg) {
		if (null != msg && isLoggable(tag, Log.VERBOSE)) {
			Log.v(tag, msg);
		}
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (null != msg && isLoggable(tag, Log.VERBOSE)) {
			Log.v(tag, msg, tr);
		}
	}

	public static void d(String tag, String msg) {
		if (null != msg && isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, msg);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (null != msg && isLoggable(tag, Log.DEBUG)) {
			Log.d(tag, msg, tr);
		}
	}

	public static void i(String tag, String msg) {
		if (null != msg && isLoggable(tag, Log.INFO)) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (null != msg && isLoggable(tag, Log.INFO)) {
			Log.i(tag, msg, tr);
		}
	}

	public static void w(String tag, String msg) {
		if (null != msg && isLoggable(tag, Log.WARN)) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		if (null != msg && isLoggable(tag, Log.WARN)) {
			Log.w(tag, msg, tr);
		}
	}

	public static void w(String tag, Throwable tr) {
		if (null != tr && isLoggable(tag, Log.WARN)) {
			Log.w(tag, tr);
		}
	}
	
	public static void e(Exception e){
		e(JSON_ERROR,
				JSON_ERROR + e.getMessage() + Logger.getLineInfo());
	}

	public static void e(String tag, String msg) {
		if (null != msg && isLoggable(tag, Log.ERROR)) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (null != msg && isLoggable(tag, Log.ERROR)) {
			Log.e(tag, msg, tr);
		}
	}

	public static boolean isLoggable(String tag, int level) {
		
		if(!AppConstants.isDebug)
			return false;
		
		return level >= LOG_LEVEL;
	}

}
