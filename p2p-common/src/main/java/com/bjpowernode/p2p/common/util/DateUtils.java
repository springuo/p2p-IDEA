package com.bjpowernode.p2p.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:DateUtils
 * Package:com.bjpowernode.p2p.common.util
 * Description:
 *
 * @date:2018/9/25 9:55
 * @author:guoxin@bjpowernode.com
 */
public class DateUtils {

    /**
     * 通过添加天数返回日期
     * @param date
     * @param days
     * @return
     */
    public static Date getDateByAddDays(Date date, Integer days) {

        //创建一个日期处理类对象
        Calendar instance = Calendar.getInstance();

        //设置当前日期处理类对象的时间值
        instance.setTime(date);

        //在当前日期处理类上添加天数
        instance.add(Calendar.DATE,days);


        return instance.getTime();
    }

    /**
     * 通过添加月份获取日期值
     * @param date
     * @param monthes
     * @return
     */
    public static Date getDateByAddMonthes(Date date, Integer monthes) {

        //创建一个日期处理类对象
        Calendar instance = Calendar.getInstance();

        //设置当前日期处理类对象的时间值
        instance.setTime(date);

        //在当前日期处理类上添加月份
        instance.add(Calendar.MONTH,monthes);


        return instance.getTime();

    }

    /**
     * 获取时间戳
     * @return 格式：yyyyMMddHHmmssSSS
     */
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
    public static void main(String[] args) throws ParseException {
        System.out.println(getDateByAddMonthes(new SimpleDateFormat("yyyy-MM-dd").parse("2008-08-08"),1));

    }

}
