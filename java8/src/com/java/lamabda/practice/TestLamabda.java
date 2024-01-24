package com.java.lamabda.practice;

import com.java.lamabda.bean.Employee;
import com.java.lamabda.inter.MyFunction;
import com.java.lamabda.inter.MyFunction2;

import java.util.Arrays;
import java.util.Collections;
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
            new Employee(105, "田七", 38, 5555.55)
    );

    public static void main(String[] args) {
        test1();
        System.out.println("------------");
        test2();
        System.out.println("-------------");
        test3();
    }

    private static void test1(){
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

    private static void test2()
    {
        String str = show("Hello World", x -> x.toUpperCase());
        System.out.println(str);
        String substr = show("Hello World", x -> x.substring(2, 4));
        System.out.println(substr);
    }

    private static String show(String str, MyFunction myFunction){
        return myFunction.getValue(str);
    }

    private static void test3()
    {
        operation(100L,200L,(x,y) -> x+y);
        operation(300L,5L,(x,y) -> x*y);
    }

    private static void operation(Long l1, Long l2, MyFunction2<Long,Long> myFunction2){
        System.out.println(myFunction2.getValue(l1, l2));
    }
}
