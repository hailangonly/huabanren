package com.cnn.android.basemodel.utils;

/**
 * Created by nmj on 2017/3/31.
 */

public class IPUtil {
    public static long ipToLong(String strIp){
        if(!strIp.contains("。")&&!strIp.contains("."))
            return -2;//
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
