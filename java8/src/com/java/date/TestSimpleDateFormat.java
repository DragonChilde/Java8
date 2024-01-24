package com.java.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Lee
 * @create 2019/10/9 11:03
 */
public class TestSimpleDateFormat {
    /*旧的时间日期API会有线程安全问题*/
   /* public static void main(String[] args) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Callable<Date> callable = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return simpleDateFormat.parse("20191009");
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<Date>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(executorService.submit(callable));
        }

        for (Future<Date> future:results)
        {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }*/

    //解决多线程安全问题
   /*public static void main(String[] args) throws Exception {

       Callable<Date> callable = new Callable<Date>() {
           @Override
           public Date call() throws Exception {
               return DateFormatThreadLocal.convert("20191009");
           }
       };
       ExecutorService executorService = Executors.newFixedThreadPool(10);
       List<Future<Date>> results = new ArrayList<>();
       for (int i = 0; i < 10; i++) {
           results.add(executorService.submit(callable));
       }

       for (Future<Date> future:results)
       {
           System.out.println(future.get());
       }
       executorService.shutdown();

   }*/

    public static void main(String[] args) throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Callable<LocalDate> callable = new Callable<LocalDate>() {
            @Override
            public LocalDate call() throws Exception {
                return LocalDate.parse("20191009",dateTimeFormatter);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<LocalDate>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(executorService.submit(callable));
        }

        for (Future<LocalDate> future:results)
        {
            System.out.println(future.get());
        }
        executorService.shutdown();
    }
}
