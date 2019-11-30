package com.java.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

/**
 * @author Lee
 * @create 2019/10/9 14:29
 */
public class TestLocalDateTime {
    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        test7();
    }

    /***LocalDate**、**LocalTime**、**LocalDateTime***/
    private static void test1()
    {
        LocalDate now = LocalDate.now();
        System.out.println(now);

        LocalDateTime localDateTime = LocalDateTime.of(2019, 10, 9, 14, 31, 00);
        System.out.println(localDateTime);

        LocalDateTime localDateTime2 = localDateTime.plusYears(1);
        System.out.println(localDateTime2);

        LocalDateTime localDateTime3 = localDateTime.minusMonths(2);
        System.out.println(localDateTime3);

        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getMonthValue());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    private static void test2()
    {
        Instant instant = Instant.now();
        System.out.println(instant);

        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);

        System.out.println(instant.getNano());

        Instant instant2 = Instant.ofEpochSecond(5);
        System.out.println(instant2);

    }

    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    private static void test3()
    {
        Instant instant = Instant.now();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant instant2 = Instant.now();

        Duration between = Duration.between(instant, instant2);
        System.out.println(between);
        System.out.println(between.getSeconds());

        System.out.println("===============");

        LocalDate localDate = LocalDate.of(2015, 10, 7);
        LocalDate now = LocalDate.now();
        Period period = Period.between(localDate, now);
        System.out.println(period);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

    }

    //4. TemporalAdjuster : 时间校正器
    private static void test4()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime localDateTime2 = localDateTime.withDayOfMonth(10);
        System.out.println(localDateTime2);

        LocalDateTime localDateTime3 = localDateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(localDateTime3);

        //自定义：下一个工作日
        LocalDateTime localDateTime4 = localDateTime.with(l -> {
            LocalDateTime dateTime = (LocalDateTime) l;
            DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return dateTime.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return dateTime.plusDays(2);
            } else {
                return dateTime.plusDays(1);
            }
        });

        System.out.println(localDateTime4);
    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    private static void test5()
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;    //默认格式

        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.format(dateTimeFormatter);
        System.out.println(date);

        System.out.println("==============");

        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String date2 = localDateTime.format(dateTimeFormatter2);
        System.out.println(date2);

        System.out.println("==============");


        LocalDateTime localDateTime2 = localDateTime.parse(date2, dateTimeFormatter2);
        System.out.println(localDateTime2);


    }

    private static void test6()
    {
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    private static void test7()
    {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(now);

        LocalDateTime now2 = LocalDateTime.now(ZoneId.of("US/Pacific"));
        System.out.println(now2);
    }
}
