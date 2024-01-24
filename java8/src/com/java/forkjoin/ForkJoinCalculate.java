package com.java.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author Lee
 * @create 2019-10-01 17:43
 */
/*RecursiveAction没返回值,RecursiveTask有返回值*/
public class ForkJoinCalculate extends RecursiveTask<Long> {
    private long start;
    private long end;

    private static final long THRESHOLD = 10000L;   //临界值

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long lenght = end - start;

        if (lenght <= THRESHOLD)
        {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum+=i;
            }
            return sum;
        } else {
            /*左右拆分计算后再合并*/
           long middle =  (start + end) /2;
            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            return left.join()+right.join();
        }

    }
}
