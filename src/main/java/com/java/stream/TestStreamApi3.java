package com.java.stream;

import com.java.lambda.bean.Employee;
import com.java.lambda.bean.Employee.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lee
 * @create 2019/9/28 15:46
 */
public class TestStreamApi3 {

    private static List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Status.BUSY)
    );

    public static void main(String[] args) {
        test1();
        System.out.println("--------------");
        test3();
        System.out.println("-------------");
        test4();
        System.out.println("-------------");
        test5();
        System.out.println("-------------");
        test6();
        System.out.println("--------------");
        test7();
        System.out.println("-------------");
        test8();
        System.out.println("------------");
        test9();
    }

    //3. 终止操作
	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */

	private static void test1()
    {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7,8,9,10);
        Integer reduce = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        System.out.println("===============");
        Optional<Double> op = emps.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(op.get());
    }


    private static void test3()
    {
        List<String> list = emps.stream().map(Employee::getName).collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("============");

        Set<String> set = emps.stream().map(Employee::getName).collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("==============");

        HashSet<String> hashSet = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

    private static void test4()
    {
        Optional<Double> max = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compareTo));
        System.out.println(max.get());

        System.out.println("==============");

        Optional<Employee> op = emps.stream().collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println(op.get());

        System.out.println("================");

        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        System.out.println("================");

        Double avg = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        System.out.println("==================");

        Long count = emps.stream().collect(Collectors.counting());
        System.out.println(count);

        System.out.println("==============");

        DoubleSummaryStatistics dss = emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax());
    }

    //分组
    private static void test5()
    {
        Map<Status, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
    }

    //多级分组
    private static void test6()
    {
        Map<Status, Map<String, List<Employee>>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
            if (e.getAge() >= 60) {
                return "老年";
            } else if (e.getAge() >= 35) {
                return "中年";
            } else {
                return "成年";
            }
        })));
        System.out.println(map);
    }

    //分区
    private static void test7()
    {
        Map<Boolean, List<Employee>> map = emps.stream().collect(Collectors.partitioningBy(e -> e.getSalary()>= 5000));
        System.out.println(map);
    }

    private static void test8()
    {
        String collect = emps.stream().map(Employee::getName).collect(Collectors.joining(",", "---", "==="));
        System.out.println(collect);
    }

    private static void test9()
    {
        Optional<Double> sum = emps.stream().map(Employee::getSalary).collect(Collectors.reducing(Double::sum));
        System.out.println(sum);
    }
}
