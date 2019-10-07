package com.java.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * @author Lee
 * @create 2019-10-07 16:48
 */
public class TestForkJoin {
    public static void main(String[] args) {
        //test1();
        test2();
        //test3();
    }

    private static void test1()
    {
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0L, 10000000000L);
        Long sum = forkJoinPool.invoke(task);
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start));
    }

    private static void test2()
    {
        long start = System.currentTimeMillis();

        long sum = 0L;

        for (long i = 0L; i < 10000000000L; i++) {
            sum+=i;
        }
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start));
    }

    private static void test3()
    {
        long start = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(0L, 10000000000L)
                .parallel()
                .sum();
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费的时间为: " + (end - start));
    }
}
