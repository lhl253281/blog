package com.lhl.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {



    public static String newDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        String format = sdf.format(new Date());

        return format;
    }


    public static void main(String[] args) {


        System.out.println(DateUtil.newDate());


    }

}
