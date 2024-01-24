package com.java.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author Lee
 * @create 2019/10/9 11:19
 */
public class DateFormatThreadLocal {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };


    public static final Date convert(String source) throws Exception
    {
        return df.get().parse(source);
    }
}
