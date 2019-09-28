package com.java.stream;

import com.java.lambda.bean.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Lee
 * @create 2019/9/27 11:43
 */
/*
 * 一、Stream API 的操作步骤：
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 */
public class TestStreamApi {

   private static List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    public static void main(String[] args) {
        test1();
        System.out.println("-----------");
        test2();
        System.out.println("------------");
        test3();
        System.out.println("-------------");
        test4();
        System.out.println("-------------");
        test5();
        System.out.println("------------");
        test6();
        System.out.println("-------------");
        test7();
        System.out.println("------------");
        test8();
        System.out.println("-------------");
        test9();
    }

    //创建Stream有以下几种方式
    private static void test1()
    {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();      //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream();      //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] integers = new Integer[10];
        Stream<Integer> integerStream = Arrays.stream(integers);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> strem2 = Stream.of(1, 2, 3, 4, 5, 6, 7);

        //4. 创建无限流
        Stream<Integer> stream3 = Stream.iterate(0, x -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> doubleStream = Stream.generate(Math::random).limit(10);
        doubleStream.forEach(System.out::println);
    }

    //2. 中间操作
    /*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	 */
    //内部迭代：迭代操作 Stream API 内部完成
    private static void test2()
    {
        //所有的中间操作不会做任何的处理
        Stream<Employee> stream = emps.stream().filter((e) -> {
            System.out.println("测试中间操作");
            return e.getAge() <= 35;
        });
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代
    private static void test3()
    {
        Iterator<Employee> employeeIterator = emps.iterator();
        while (employeeIterator.hasNext())
        {
            System.out.println(employeeIterator.next());
        }
    }

    private static void test4()
    {
        emps.stream().filter((e) -> {
            System.out.println("短路!");
            return e.getSalary() >= 5000;
        }).limit(3).forEach(System.out::println);
    }

    private static void test5()
    {
        emps.parallelStream().filter(e -> e.getSalary() >= 5000).skip(2).forEach(System.out::println);
    }

    private static void test6()
    {
        emps.stream().distinct().forEach(System.out::println);
    }

    //2. 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
	private static void test7()
    {
        Stream<String> stringStream = emps.stream().map(e -> e.getName());
        stringStream.forEach(System.out::println);

        System.out.println("===========");

        List<String> stringList = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        Stream<String> stream = stringList.stream().map(String::toUpperCase);
        stream.forEach(System.out::println);

        System.out.println("=============");
        Stream<Stream<Character>> strem2 = stringList.stream().map(TestStreamApi::filterCharacter);
        strem2.forEach(sm -> sm.forEach(System.out::println));

        System.out.println("===========");
        Stream<Character> strem3 = stringList.stream().flatMap(TestStreamApi::filterCharacter);
        strem3.forEach(System.out::println);
    }

    private static Stream<Character> filterCharacter(String str)
    {
        List<Character> list = new ArrayList<>();

        for(Character chr:str.toCharArray())
        {
            list.add(chr);
        }
        return list.stream();
    }

    private static void test8()
    {
        List<String> stringList = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        ArrayList<Object> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add(stringList);
        System.out.println(list);
        ArrayList<Object> list2 = new ArrayList<>();
        list2.add("11");
        list2.add("22");
        list2.addAll(stringList);
        System.out.println(list2);
    }

    /*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
    private static void test9()
    {
        List<String> stringList = Arrays.asList("aaa", "ddd", "bbb", "ccc");
        stringList.stream().sorted().forEach(System.out::println);

        System.out.println("==============");

        emps.stream().sorted((e1,e2) -> {
            if(e1.getAge() == e2.getAge())
            {
                return e1.getName().compareTo(e2.getName());
            }
            return Integer.compare(e1.getAge(),e2.getAge());
        }).forEach(System.out::println);
    }
}
