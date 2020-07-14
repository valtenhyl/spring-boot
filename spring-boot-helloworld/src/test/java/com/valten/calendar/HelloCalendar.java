package com.valten.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HelloCalendar {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        List<String> daysDay = getTwoDaysDay("2019-1-1", "2019-12-31");
        daysDay.forEach(System.out::println);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, 15);
//        System.out.println(SDF.format(calendar.getTime()));
    }

    //取一段时间的每一天
    private static List<String> getTwoDaysDay(String dateStart, String dateEnd) {
        List<String> dateList = new ArrayList<>();
        try {
            Date dateOne = SDF.parse(dateStart);
            Date dateTwo = SDF.parse(dateEnd);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTwo);

            dateList.add(dateEnd);
            //倒序时间,顺序after改before其他相应的改动。
            while (calendar.getTime().after(dateOne)) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                    dateList.add(SDF.format(calendar.getTime()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }
}
