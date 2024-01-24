package com.java.lamabda;


/*
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 *
 * Supplier<T> : 供给型接口
 * 		T get();
 *
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 *
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Lee
 * @create 2019/9/25 16:58
 */
public class TestLamabda4 {
    public static void main(String[] args) {
        test1();
        System.out.println("---------");
        test2();
        System.out.println("----------");
        test3();
        System.out.println("----------");
        test4();
    }


    //Consumer<T> 消费型接口 :
    private static void test1()
    {
        show("hello world!",x -> System.out.println(x));
    }

    private static void show(String str, Consumer<String> consumer)
    {
        consumer.accept(str);
    }


    //Supplier<T> 供给型接口 :
    private static void test2()
    {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        System.out.println(numList);
    }

    private static List<Integer> getNumList(Integer num, Supplier<Integer> supplier)
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer integer = supplier.get();
            list.add(integer);
        }
        return list;

    }

    //Function<T, R> 函数型接口：
    private static void test3()
    {
        String s1 = handleStr("\t\t\t hello world!   ", x -> x.trim());
        System.out.println(s1);

        String s2 = handleStr("hello world!", x -> x.substring(2,6));
        System.out.println(s2);
    }

    private static String handleStr(String str , Function<String,String> function)
    {
        return function.apply(str);
    }

    //Predicate<T> 断言型接口：
    private static void test4()
    {
        List<String> list = Arrays.asList("aaa", "bbbb", "cccccc", "ddddddd");
        List<String> stringList = filterStr(list, x -> x.length() > 4);
        System.out.println(stringList);
    }

    private static List<String> filterStr(List<String> list, Predicate<String> predicate)
    {
        List<String> strList = new ArrayList<>();

        for (String str : list) {
            if(predicate.test(str)){
                strList.add(str);
            }
        }
        return strList;

    }
}
