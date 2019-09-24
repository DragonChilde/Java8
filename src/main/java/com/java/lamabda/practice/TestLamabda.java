package com.java.lamabda.practice;

import com.java.lamabda.bean.Employee;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Lee
 * @create 2019/9/24 17:46
 */
public class TestLamabda {

    static List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55),
            new Employee(106, "田七", 8, 7777.77)
    );

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        Collections.sort(emps,(e1,e2) -> {
            if(e1.getAge() == e2.getAge())
            {
                return e1.getName().compareTo(e2.getName());
            }
            return Integer.compare(e1.getAge(),e2.getAge());
        });

        for (Employee employee:emps)
        {
            System.out.println(employee);
        }
    }
}
