package com.java.stream.practice;

import com.java.lambda.bean.Employee;
import com.java.lambda.bean.Employee.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Lee
 * @create 2019/9/29 11:15
 */
public class TestStreamAPI {
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
        System.out.println("-------------");
        test2();
    }

    	/*
	  	1.	给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
		，给定【1，2，3，4，5】， 应该返回【1，4，9，16，25】。
	 */

    	private static void test1()
        {
            Integer[] integers = {1, 2, 3, 4, 5};
           Arrays.stream(integers).map(x -> x * x).forEach(System.out::println);

        }

    /*
     2.	怎样用 map 和 reduce 方法数一数流中有多少个Employee呢？
     */
        private static void test2()
        {
            Optional<Integer> count = emps.stream().map(e -> 1).reduce(Integer::sum);
            System.out.println(count);
        }
}
