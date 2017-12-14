package com.sinnowa.middlewareweb.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZingBug on 2017/11/14.
 */
public class Utils {
    private static final Logger logger=Logger.getLogger(Utils.class);

    private static final SimpleDateFormat sdfLong=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdfShort=new SimpleDateFormat("yyyy-MM-dd");

    private static final Calendar calendar=Calendar.getInstance();

    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }

    //yyyy-MM-dd HH:mm:ss
    public static String DateToLongFormate(Date date)
    {
        return sdfLong.format(date);
    }

    //yyyy-MM-dd
    public static String DateToShortFormate(Date date)
    {
        return sdfShort.format(date);
    }

    //得到今天零点时刻
    public static Date GetZeroDate()
    {
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    //得到制定天零点时刻
    public static Date GetZeroDate(Date date)
    {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    //根据年龄获得大体出生日期
    public static Date AgeToDate(String age)
    {

        int ageInterge;
        try
        {
            ageInterge=Integer.parseInt(age);
        }catch (NumberFormatException e)
        {
            ageInterge=0;
        }
        return AgeToDate(ageInterge);
    }

    public static Date AgeToDate(int age)
    {
        calendar.setTime(new Date());
        int year=calendar.get(Calendar.YEAR)-age;
        calendar.set(Calendar.YEAR,year);
        return calendar.getTime();
    }
}
