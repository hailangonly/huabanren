package com.cnn.android.basemodel.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.WindowManager;


import com.cnn.android.basemodel.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtil {
	private static final String ENCODE = "UTF-8";

	public static boolean isConnectToNetwork(Context context) {

		if (context == null)
			return false;

		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// context = null;
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable() && info.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

	private static int screenWidth = 0;

	public static int getScreenWidth(Context context) {

		if (screenWidth == 0) {
			if (context == null)
				return 0;
			screenWidth = ((WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay().getWidth();
		}
		return screenWidth;
	}

	private static int screenHeight = 0;

	public static int getScreenHeight(Context context) {

		if (screenHeight == 0) {
			if (context == null)
				return 0;
			screenHeight = ((WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay().getHeight();
		}
		return screenHeight;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Resources res, float dpValue) {
		final float scale = res.getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		/*
		 * final float scale =
		 * context.getResources().getDisplayMetrics().density; return (int)
		 * (dpValue * scale + 0.5f);
		 */
		return dp2px(context.getResources(), dpValue);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0 || str.equals("null")) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("null");
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();
	}

	public static ProgressDialog createProgressDialog(Context context,
			String msg) {
		ProgressDialog mProgressDialog = new ProgressDialog(context,
				R.style.CustomProgressLoadingDialog);
		if (isNotEmpty(msg)) {
			mProgressDialog.setMessage(msg);
		}
		mProgressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
		mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		return mProgressDialog;
	}

	public static boolean isVcode(String trim) {
		if (trim.length() == 6)
			return true;
		else
			return false;
	}

	public static byte[] imageZoom32(byte[] b) {

		if (b == null || b.length == 0)
			return b;
		try {
			Bitmap bitmap = ThumbnailUtils.extractThumbnail(
					BitmapFactory.decodeByteArray(b, 0, b.length), 150, 150,
					ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
			b = baos.toByteArray();

			Logger.log("压缩后图片大小bitmap.compress：" + b.length / 1024);

			baos.close();
		} catch (Exception e) {
		}
		return b;

		/*
		 * // 图片允许最大空间 单位：KB double maxSize = 32.00; float newWidth; float
		 * newHeight; Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
		 * b.length); // 将字节换成KB double mid = b.length / 1024; // 获取bitmap大小
		 * 是允许最大大小的多少倍 double i = mid / maxSize; // 开始压缩 此处用到平方根
		 * 将宽带和高度压缩掉对应的平方根倍 // （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
		 * newWidth = (float) (bitmap.getWidth() / Math.sqrt(i)); newHeight =
		 * (float) ((float) bitmap.getHeight() / Math.sqrt(i));
		 * 
		 * // 获取这个图片的宽和高 float width = bitmap.getWidth(); float height =
		 * bitmap.getHeight(); // 创建操作图片用的matrix对象 Matrix matrix = new Matrix();
		 * // 计算宽高缩放率 float scaleWidth = ((float) newWidth) / width; float
		 * scaleHeight = ((float) newHeight) / height; // 缩放图片动作
		 * matrix.postScale(scaleWidth, scaleHeight); bitmap =
		 * Bitmap.createBitmap(bitmap, 0, 0, (int) newWidth, (int) newHeight,
		 * matrix, true);
		 * 
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); b =
		 * baos.toByteArray(); try { baos.close(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * mid = b.length / 1024;
		 */
		// Logger.log("压缩后图片大小bitmap.compress：" + b.length);
		// return b;

	}

	public static byte[] imageZoom32(Bitmap bitmap) {
		// 图片允许最大空间 单位：KB
		double maxSize = 32.00;
		float newWidth;
		float newHeight;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			newWidth = (float) (bitmap.getWidth() / Math.sqrt(i));
			newHeight = (float) ((float) bitmap.getHeight() / Math.sqrt(i));
		} else {
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return b;
		}
		// 获取这个图片的宽和高
		float width = bitmap.getWidth();
		float height = bitmap.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height,
				matrix, true);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		b = baos.toByteArray();
		try {
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.log("压缩后图片大小bitmap.compress：" + b.length);
		return b;

	}

	public static String convertL2DFeed(long l) {
		long between = (System.currentTimeMillis() - l) / 1000;// 除以1000是为了转换成秒

		long year = between / (24 * 3600 * 30 * 12);
		long month = between / (24 * 3600 * 30);
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;

		StringBuffer sb = new StringBuffer();
		if (year != 0) {
			sb.append(year + "年");
			return sb.toString() + "前";
		}
		if (month != 0) {
			sb.append(month + "个月");
			return sb.toString() + "前";
		}
		/*
		 * if (week != 0) { sb.append(week + "周"); return sb.toString() + "前"; }
		 */
		if (day != 0) {
			sb.append(day + "天");
			return sb.toString() + "前";
		}
		if (hour != 0) {
			sb.append(hour + "小时");
			return sb.toString() + "前";
		}
		if (minute != 0) {
			sb.append(minute + "分钟");
			return sb.toString() + "前";
		}
		if (second != 0) {
			sb.append(second + "秒");
			return sb.toString() + "前";
		}

		return "刚刚";
	}

	public static String stringToData(String dataStr)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String resStr ="";
		try {
			Date date = sdf.parse(dataStr);
			resStr=convertL2DFeedNew(date.getTime());
		}catch (Exception e){

		}

		return resStr;
	}

	public static String convertL2DFeedNew(long l) {
		long between = (System.currentTimeMillis() - l) / 1000;// 除以1000是为了转换成秒

		long year = between / (24 * 3600 * 30 * 12);
		long month = between / (24 * 3600 * 30);
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		long second = between % 60 / 60;

		StringBuffer sb = new StringBuffer();
		if (year != 0) {
			sb.append(year + "年");
			return sb.toString() + "前";
		}
		if (month != 0) {
			sb.append(month + "个月");
			return sb.toString() + "前";
		}
		/*
		 * if (week != 0) { sb.append(week + "周"); return sb.toString() + "前"; }
		 */
		if (day != 0) {
			sb.append(day + "天");
			return sb.toString() + "前";
		}
		if (hour != 0) {
			sb.append(hour + "小时");
			return sb.toString() + "前";
		}
		if (minute != 0) {
			sb.append(minute + "分钟");
			return sb.toString() + "前";
		}
		if (second != 0) {
			sb.append(second + "秒");
			return sb.toString() + "前";
		}

		return "刚刚";
	}


	public static String getAssetRes(Context context, String filePath) {

		if (context == null)
			return "";
		String result = "";
		AssetManager am = context.getResources().getAssets();
		InputStream inputStream = null;
		try {
			// inputStream = am.open("data/category_tag_menu.txt");
			inputStream = am.open(filePath);
			result = inputStream2String(inputStream, ENCODE);
			// ToolUtil.log("CITY_FLITER_FILE_NAME getAssets result:"+result);
		} catch (Exception e) {
			Logger.log("readCategoryTagMenuData getAssets 发生异常:" + e.toString());
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					Logger.log("readCategoryTagMenuData close getAssets 发生异常:"
							+ e.toString());
				}
		}

		if (result == null)
			result = "";

		return result;
	}

	public static String inputStream2String(InputStream inputStream,
			String encoding) {

		String result = "";
		try {
			int lenght = inputStream.available();
			byte[] buffer = new byte[lenght];
			inputStream.read(buffer);
			//result = EncodingUtils.getString(buffer, encoding);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (result == null)
			result = "";

		return result;
	}

	/**
	 * 半角转换为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static CharSequence tagName(String categoryName) {
		if (categoryName == null || categoryName.length() == 0)
			return "";
		if (categoryName.contains(">"))
			categoryName = categoryName.substring(1, categoryName.length());
		return categoryName;
	}

	public static String toDataString(long time) {

		if (time <= 0) {
			time = System.currentTimeMillis();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");// HH:mm:ss
		Date curDate = new Date(time);
		String str = formatter.format(curDate);

		return str;

	}
	public static String toDataStringForOrder(long time) {

		if (time <= 0) {
			time = System.currentTimeMillis();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd  HH:mm");// HH:mm:ss
		Date curDate = new Date(time);
		String str = formatter.format(curDate);

		return str;

	}
	public static String toDataStringNew(long time) {

		if (time <= 0) {
			time = System.currentTimeMillis();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
		Date curDate = new Date(time);
		String str = formatter.format(curDate);

		return str;

	}

	public static String toDataStringForRcd(long time) {

		if (time <= 0) {
			time = System.currentTimeMillis();
		}
		String curDate = toDataStringNew(time);
		if (curDate.contains("-")) {
			String[] strs = curDate.split("-");
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(strs[1]);
			stringBuilder.append("\n");
			stringBuilder.append(strs[2]);
			return stringBuilder.toString();
		}
		return "";

	}

	public static String toPrice(Double price) {
		DecimalFormat df = new DecimalFormat("0.00");

		return df.format(price);
	}

	public static String md5(InputStream paramInputStream) {
		String retStr = "";
		MessageDigest localMessageDigest;

		try {
			localMessageDigest = MessageDigest.getInstance("MD5");
			byte[] arrayOfByte = new byte[8192];

			int byteCount;
			while ((byteCount = paramInputStream.read(arrayOfByte)) > 0) {
				localMessageDigest.update(arrayOfByte, 0, byteCount);
			}

			retStr = hexString(localMessageDigest.digest());
		} catch (IOException localIOException) {
			// handle exception
			throw new RuntimeException(localIOException);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {

		}

		return retStr;
	}

	public static String hexString(byte[] paramArrayOfByte) {
		String str = "%0" + (paramArrayOfByte.length << 1) + "x";
		Object[] arrayOfObject = new Object[1];
		arrayOfObject[0] = new BigInteger(1, paramArrayOfByte);
		return String.format(str, arrayOfObject);
	}

	public static String md5(String paramString) {
		// dinglei0313
		if (null == paramString || "".equalsIgnoreCase(paramString)) {
			return "playUrl is null";
		}

		return md5(new ByteArrayInputStream(paramString.getBytes()));
	}

	public static InputStream getInputStream(String pathName)
			throws FileNotFoundException {
		File file = new File(pathName);
		return new FileInputStream(file);
	}

	public static String getBASE64(String s) {
		if (s == null) {
			return null;
		}

		return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
	}

	// for Version
	public static int stringToInt(String openValue) {
		String str = openValue.replace(".", "");
		if (isNumeric(str)) {
			return Integer.valueOf(str) * 1000;
		} else {
			return 0;
		}
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public Bitmap convertToBitmap(String path) {
		return convertToBitmap(path, 0, 0);
	}

	public Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	/*
	 * return username of androidpn-client (不考虑虚拟机)
	 */
	public static String getDeviceId(Context mContext) {
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public static String getLocalIpAddress() {
		StringBuilder builder = new StringBuilder();
		try {
			Enumeration<NetworkInterface> netEnumeration = NetworkInterface
					.getNetworkInterfaces();
			while (netEnumeration.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) netEnumeration
						.nextElement();
				Enumeration<InetAddress> inetAddressEnumeration = networkInterface
						.getInetAddresses();
				while (inetAddressEnumeration.hasMoreElements()) {
					InetAddress inetAddress = inetAddressEnumeration
							.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						builder.append(inetAddress.getHostAddress().toString()
								+ ",");
					}
				}
			}
		} catch (Exception e) {
		}
		return builder.toString();
	}

	/*public static CharSequence getAddressDetial(OrderShipping orderShipping) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(orderShipping.state);
		if (!"市辖区".endsWith(orderShipping.city)) {
			buffer.append(orderShipping.city);
		}
		buffer.append(orderShipping.district);
		buffer.append(orderShipping.address);
		return buffer.toString();
	}*/

	public static String getSkuInfoColor(String sku) {
		String retStr = "";
		if (sku.contains(",")) {
          String[] strs=sku.split(",");
          retStr=getSkuInfo(strs[0]);
		}
		return retStr;
	}

	private static String getSkuInfo(String str) {
		String retStr = "";
		if(str.contains(":")){
			 String[] strs=str.split(":");
			 retStr=strs[1];
		}
		return retStr;
	}

	public static String getSkuInfoSize(String sku) {
		String retStr = "";
		if (sku.contains(",")) {
          String[] strs=sku.split(",");
          retStr=getSkuInfo(strs[1]);
		}
		return retStr;
	}

	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 38;// 默认为38，貌似大部分是这样的
		try {
			Class<?> c = Class.forName("com.android.internal.R$dimen");
			Object obj = c.newInstance();
			Field field = c.getField("status_bar_height");
			int x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

    public static String getCourseTime(String title, int seconds) {
		StringBuffer sb=new StringBuffer(title);
		sb.append(" 教学 ");
		sb.append(getMinutes(seconds));
		sb.append("'");
		sb.append(getSeconds(seconds)).append("''");
		return  sb.toString();
    }

	private static String getMinutes(int s) {
		int minutes = (int) (s/60);
		int seconds = (int) (s-minutes*60);
		if(minutes==0)
			return "00";
		if(minutes<10)
			return "0"+minutes;
		return ""+minutes;
	}

	private static String getSeconds(int s) {
		int minutes = (int) (s/60);
		int seconds = (int) (s-minutes*60);
		if(seconds==0)
			return "00";
		if(seconds<10)
			return "0"+seconds;
		return ""+seconds;
	}

	public static String getCourseTime( int seconds) {
		StringBuffer sb=new StringBuffer();
		sb.append(getMinutes(seconds));
		sb.append("'");
		sb.append(getSeconds(seconds)).append("''");
		return  sb.toString();
	}

	public static long ipToLong(String strIp){
		if(strIp.contains("。")){
			strIp=strIp.replace("。",".");
		}
		long[] ip = new long[4];
		//先找到IP地址字符串中.的位置
		String[] strs=strIp.split("\\.");
		//将每个.之间的字符串转换成整型
		for (int i=0;i<strs.length;i++){
			if(strs[i].trim().contains(" ")){
				return -1;
			}
			ip[i]=Long.parseLong(strs[i].trim());
		}
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}
}
