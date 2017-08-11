package com.cnn.android.basemodel.utils;

import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nmj on 16/9/6.
 */
public class StringUtils {


    private final static String MOBLIE_PHONE_PATTERN = "^1[3-9]\\d{9}$";// 手机号码
    private final static String EMAIL_ADDRESS_PATTERN = "\\w+@(\\w+.)+[a-z]{2,3}";

    public static boolean isEmailAddress(String email) {

        Pattern p = Pattern.compile(EMAIL_ADDRESS_PATTERN);
        Matcher m = p.matcher(email);
        boolean b = m.matches();
        return b;
    }

    public static String getUrl(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        Matcher m = android.util.Patterns.WEB_URL.matcher(src);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static boolean isMobileNumber(String number) {
        Pattern p = Pattern.compile(MOBLIE_PHONE_PATTERN);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public static boolean isLetter(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[a-zA-Z].*");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    public static int getStringWidth(Paint p, String string) {
        return getStringWidth(p, string.toCharArray(), 0, string.length());
    }

    public static int getStringWidth(Paint p, char[] string, int offset,
                                     int length) {
        boolean containsSoftHyphen = false;
        for (int i = offset; i < offset + length; ++i) {
            if (string[i] == (char) 0xAD) {
                containsSoftHyphen = true;
                break;
            }
        }
        if (!containsSoftHyphen) {
            return (int) (p.measureText(new String(string, offset, length)) + 0.5f);
        } else {
            final char[] corrected = new char[length];
            int len = 0;
            for (int o = offset; o < offset + length; ++o) {
                final char chr = string[o];
                if (chr != (char) 0xAD) {
                    corrected[len++] = chr;
                }
            }
            return (int) (p.measureText(corrected, 0, len) + 0.5f);
        }
    }

    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src) {
        if (src == null)
            return null;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else if (src.charAt(pos + 1) == ' '
                        || src.charAt(pos + 1) == ';') {
                    tmp.append(src.substring(pos, pos + 1));
                    lastPos = pos + 1;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }


    public static boolean isStartWithNumber(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        if (text.length() > 0) {
            if (text.charAt(0) >= '0' && text.charAt(0) <= '9') {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidCharacter(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        boolean isValidChar = true;
        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            if (c >= 0x4E00 && c <= 0x9FA5) {// 汉字
                continue;
            }
            if (c >= 'A' && c <= 'Z') {// A-Z
            } else if (c >= 'a' && c <= 'z') {// a-z
            } else {
                isValidChar = false;
                break;
            }
        }
        return isValidChar;
    }

    public static String getMobileNumber(String mobile) {
        if (!TextUtils.isEmpty(mobile) && mobile.length() >= 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(mobile.substring(0, 3));
            builder.append("****");
            builder.append(mobile.substring(7));
            return builder.toString();
        } else {
            return mobile;
        }
    }

    /**
     * 设置TextView部分内容删除线
     * @param textView
     * @param start
     * @param end
     * @param content
     */
    public static void setStrikethrough(TextView textView, int start, int end, String content) {
        if(TextUtils.isEmpty(content)){
            return;
        }
        if (start >= end) {
            textView.setText(content);
            return;
        }
        SpannableString spanString = new SpannableString(content);
        spanString.setSpan(new StrikethroughSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanString);
    }
}
