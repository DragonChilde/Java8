package com.java.lamabda;

import com.java.lamabda.inter.MyFun;

import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Lee
 * @create 2019/9/24 14:11
 */
public class TestLamabda3 {
    public static void main(String[] args) {
        test1();
        System.out.println("---------");
        test2();

        test6();
    }


    private static void test1()
    {
        int num = 0;//jdk 1.7 前，必须是 final

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world!"+num);
            }
        };
        runnable1.run();


        Runnable runnable2 = () -> System.out.println("Hello world!");
        runnable2.run();

    }

    private static void test2()
    {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("hello world!");

    }

    private static void test3()
    {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

    private static void test4()
    {
        Comparator<Integer> com = (x , y) -> Integer.compare(x , y);

    }

    private static void test5()
    {
        String[] strs = {"aaa","bbb","ccc"};

        List<String> list = new ArrayList<>();
        show(new HashMap<>());
    }

    private static void show(Map<String,Integer> map){

    }


    //需求：对一个数进行运算
    private static void test6()
    {
        Integer num = operation(100, x -> x * x);
        System.out.println(num);

        System.out.println(operation(100, x -> 200 + x));

    }

    private static Integer operation(Integer num, MyFun myFun)
    {
        return myFun.getValue(num);
    }

}
