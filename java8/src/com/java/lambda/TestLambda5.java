package com.java.lambda;

import com.java.lambda.bean.Employee;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/*
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 *
 * 1. 对象的引用 :: 实例方法名
 *
 * 2. 类名 :: 静态方法名
 *
 * 3. 类名 :: 实例方法名
 *
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 *
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 *
 * 1. 类名 :: new
 *
 * 三、数组引用
 *
 * 	类型[] :: new;
 *
 *
 */
/**
 * @author Lee
 * @create 2019/9/26 16:51
 */
public class TestLambda5 {
    public static void main(String[] args) {
        test1();
        System.out.println("----------");
        test2();
        System.out.println("----------");
        test3();
        System.out.println("-----------");
        test4();
        System.out.println("-----------");
        test5();
        System.out.println("-----------");
        test6();
        System.out.println("-----------");
        test7();
        System.out.println("-----------");
        test8();
        System.out.println("------------");
    }

    public static void test1()
    {
        /*新使用*/
        PrintStream printStream = System.out;
        Consumer<String> consumer = (str) -> printStream.println(str);
        consumer.accept("hello world!");
        System.out.println("==========");
        Consumer<String> consumer2 = printStream::println;
        consumer2.accept("hello java8");

        /*原有使用*/
        Consumer<String> consumer3 = System.out::println;
    }

    public static void test2()
    {
        Employee employee = new Employee(101, "李四", 18, 9999.99);

        /*原有使用*/
        Supplier<String> sup = () -> employee.getName();
        System.out.println(sup.get());

        System.out.println("=============");

        /*新使用*/
        Supplier<String> sup2 = employee::getName;
        System.out.println(sup2.get());
    }

    public static void test3()
    {
        /*原有使用*/
        BiFunction<Double,Double,Double> biFunction = (x, y) -> Math.max(x, y);
        System.out.println(biFunction.apply(1.5, 22.2));

        System.out.println("==============");

        /*新使用*/
        BiFunction<Double, Double, Double> biFunction2 = Math::max;
        System.out.println(biFunction.apply(1.2, 1.5));
    }

    //类名 :: 静态方法名
    public static void test4()
    {
        /*原有使用*/
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
        System.out.println(comparator.compare(1, 2));

        System.out.println("===========");

        /*新使用*/
        Comparator<Integer> comparator2 = Integer::compare;
        System.out.println(comparator2.compare(3, 4));

    }

    //类名 :: 实例方法名
    public static void test5()
    {
        /*原有使用*/
        BiPredicate<String,String> biPredicate1 = (x, y) -> x.equals(y);
        System.out.println(biPredicate1.test("abc", "abc"));

        System.out.println("=============");

        /*新使用*/
        BiPredicate<String, String> biPredicate2 = String::equals;
        System.out.println(biPredicate2.test("efg", "efg"));

        System.out.println("=============");

        /*原有使用*/
        Function<Employee,String> function1 = (e) -> e.show();
        System.out.println(function1.apply(new Employee()));

        System.out.println("===============");

        /*新使用*/
        Function<Employee, String> function2 = Employee::show;
        System.out.println(function2.apply(new Employee()));
    }

    //构造器引用
    public static void test6()
    {
        Supplier<Employee> supplier1 = () -> new Employee();
        System.out.println(supplier1.get());

        System.out.println("===============");

        Supplier<Employee> supplier2 = Employee::new;
        System.out.println(supplier2.get());
    }

    //构造器引用
    public static void test7()
    {
        /*新使用*/
        Function<String, Employee> function = Employee::new;

        /*新使用*/
        BiFunction<String, Integer, Employee> biFunction = Employee::new;
    }

    //数组引用
    public static void test8()
    {
        /*原有使用*/
        Function<Integer,String[]> function1 = (length) -> new String[length];
        String[] strs = function1.apply(10);
        System.out.println(strs.length);
        System.out.println("================");

        /*新使用*/
        Function<Integer,Employee[]> function2 = Employee[]::new;
        Employee[] employees = function2.apply(20);
        System.out.println(employees.length);
    }
}
