package com.valten.calendar;

import com.alibaba.fastjson.JSONArray;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取当年中国的法定节假日和工作日等信息。 如下是当前包含的功能：
 * 01-给定日期，判断是否是法定节假日。
 * 02-给定日期，判断是否是周末（周末不一定是休息日，可能需要补班）。
 * 03-给定日期，判断是否是需要额外补班的周末。
 * 04-给定日期，判断是否是休息日（包含法定节假日和不需要补班的周末）。
 * 05-给定日期，判断是否是工作日(非休息日)。
 * 06-获取一年中总共的天数。
 * 07-获取一年中法定节假日的天数。
 * 08-获取一年中需要补班的周末天数。
 * 09-获取一年中周末的天数（周六+周日）。
 * 10-获取一年中休息日的天数（法定节假日+不需要补班的周末）。
 */

public class ChineseCalendar {
    // 法律规定的放假日期
    private final List<String> lawHolidays = new ArrayList<>(Arrays.asList(
            "2017-01-01", "2017-01-02", "2017-01-27", "2017-01-28",
            "2017-01-29", "2017-01-30", "2017-01-31", "2017-02-01",
            "2017-02-02", "2017-04-02", "2017-04-03", "2017-04-04",
            "2017-04-29", "2017-04-30", "2017-05-01", "2017-05-28",
            "2017-05-29", "2017-05-30", "2017-10-01", "2017-10-02",
            "2017-10-03", "2017-10-04", "2017-10-05", "2017-10-06",
            "2017-10-07", "2017-10-08"));
    // 由于放假需要额外工作的周末
    private final List<String> extraWorkdays = new ArrayList<>(Arrays.asList(
            "2017-01-22", "2017-02-04", "2017-04-01", "2017-05-27",
            "2017-09-30"));

    // 日期正则表达式
    private final static Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    /**
     * 判断是否是法定假日
     */
    private boolean isLawHoliday(String calendar) throws Exception {
        this.isMatchDateFormat(calendar);
        return lawHolidays.contains(calendar);
    }

    /**
     * 判断是否是周末
     */
    private boolean isWeekends(String calendar) throws Exception {
        this.isMatchDateFormat(calendar);
        // 先将字符串类型的日期转换为Calendar类型
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(calendar);
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_WEEK) == 1
                || ca.get(Calendar.DAY_OF_WEEK) == 7;
    }

    /**
     * 判断是否是需要额外补班的周末
     */
    private boolean isExtraWorkday(String calendar) throws Exception {
        this.isMatchDateFormat(calendar);
        return extraWorkdays.contains(calendar);
    }

    /**
     * 判断是否是休息日（包含法定节假日和不需要补班的周末）
     */
    private boolean isHoliday(String calendar) throws Exception {
        this.isMatchDateFormat(calendar);
        // 首先法定节假日必定是休息日
        if (this.isLawHoliday(calendar)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!this.isWeekends(calendar)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        return !this.isExtraWorkday(calendar);
    }

    /**
     * 判断是否是工作日
     *
     * @param calendar
     */
    private boolean isWorkday(String calendar) throws Exception {
        this.isMatchDateFormat(calendar);
        return !(this.isHoliday(calendar));
    }

    private int getTotalDays() {
        return new GregorianCalendar().isLeapYear(Calendar.YEAR) ? 366 : 365;
    }

    private int getTotalLawHolidays() {
        return lawHolidays.size();
    }

    private int getTotalExtraWorkdays() {
        return extraWorkdays.size();
    }

    /**
     * 获取一年中所有周末的天数
     */
    private int getTotalWeekends() {
        List<String> saturdays = new ArrayList<String>();
        List<String> sundays = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int nextYear = 1 + calendar.get(Calendar.YEAR);
        Calendar cstart = Calendar.getInstance();
        Calendar cend = Calendar.getInstance();
        cstart.set(currentYear, 0, 1);// 今年的元旦
        cend.set(nextYear, 0, 1);// 明年的元旦
        return this.getTotalSaturdays(saturdays, calendar, cstart, cend,
                currentYear)
                + this.getTotalSundays(sundays, calendar, cstart, cend,
                currentYear);
    }

    private int getTotalSaturdays(List<String> saturdays, Calendar calendar,
                                  Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周六
        calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));
        // 从上周六往这一年的元旦开始遍历，定位到去年最后一个周六
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周六
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周六往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            saturdays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return saturdays.size();
    }

    private int getTotalSundays(List<String> sundays, Calendar calendar,
                                Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周日
        calendar.add(Calendar.DAY_OF_MONTH,
                -calendar.get(Calendar.DAY_OF_WEEK) + 1);
        // 从上周日往这一年的元旦开始遍历，定位到去年最后一个周日
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周日
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周日往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            sundays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return sundays.size();
    }

    private int getTotalHolidays() {
        //先获取不需要补班的周末天数
        int noWorkWeekends = this.getTotalWeekends() - this.getTotalExtraWorkdays();
        return noWorkWeekends + this.getTotalLawHolidays();
    }

    /**
     * 使用正则表达式匹配日期格式
     */
    private void isMatchDateFormat(String calendar) throws Exception {
        Matcher matcher = DATE_PATTERN.matcher(calendar);
        boolean flag = matcher.matches();
        if (!flag) {
            throw new Exception("输入日期格式不正确，应该为2017-02-03");
        }
    }

    public static List<String> weekDays(int year) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(year, 0, 1);
        int i = 1;
        while (calendar.get(Calendar.YEAR) < year + 1) {
            calendar.set(Calendar.WEEK_OF_YEAR, i++);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                System.out.println("周日：" + sdf.format(calendar.getTime()));
                dateList.add(sdf.format(calendar.getTime()));
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            if (calendar.get(Calendar.YEAR) == year) {
                System.out.println("周六：" + sdf.format(calendar.getTime()));
                dateList.add(sdf.format(calendar.getTime()));
            }
        }
        return dateList;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("2020年所有周末：" + JSONArray.toJSONString(weekDays(2020)));
        List<String> weekDays = weekDays(2020);
        ChineseCalendar cc = new ChineseCalendar();
        weekDays = weekDays.stream().collect(ArrayList::new, (list, wkd) -> {
            try {
                if (cc.isHoliday(wkd)) {
                    list.add(wkd);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, List::addAll);
        System.out.println("2020年所有周末但是上班：" + JSONArray.toJSONString(weekDays));

        String calendar = "2020-05-09";
        System.out.println("是否是法定节假日：" + cc.isLawHoliday(calendar));
        System.out.println("是否是周末：" + cc.isWeekends(calendar));
        System.out.println("是否是需要额外补班的周末：" + cc.isExtraWorkday(calendar));
        System.out.println("是否是休息日：" + cc.isHoliday(calendar));
        System.out.println("是否是工作日：" + cc.isWorkday(calendar));
        System.out.println("今年总共有" + cc.getTotalDays() + "天");
        System.out.println("今年总共有" + cc.getTotalLawHolidays() + "天法定节假日");
        System.out.println("今年总共有" + cc.getTotalExtraWorkdays() + "天需要补班的周末");
        System.out.println("今年总共有" + cc.getTotalWeekends() + "天周末");
        System.out.println("今年总共有" + cc.getTotalHolidays() + "天休息日");
    }

}