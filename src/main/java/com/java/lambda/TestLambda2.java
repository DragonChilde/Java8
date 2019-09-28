package com.java.lambda;

import com.java.lambda.bean.Employee;
import com.java.lambda.bean.FilterEmployeeForAge;
import com.java.lambda.bean.FilterEmployeeForSalary;
import com.java.lambda.inter.MyPredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lee
 * @create 2019/9/23 12:24
 */
public class TestLambda2 {

   static List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    public static void main(String[] args) {
        List<Employee> employees1 = filterEmployeeForAge(emps);
        System.out.println(employees1);
        System.out.println("============");
        List<Employee> employees2 = filterEmployeeForSalary(emps);
        System.out.println(employees2);

        System.out.println("-------------------------------------");
        System.out.println("优化1:");
        List<Employee> employees3 = filterEmployee1(emps, new FilterEmployeeForAge());
        System.out.println(employees3);
        List<Employee> employees4 = filterEmployee1(emps, new FilterEmployeeForSalary());
        System.out.println(employees4);
        System.out.println("============");
        System.out.println("优化2:");
        List<Employee> employees5 = filterEmployee2(emps);
        System.out.println(employees5);
        System.out.println("============");
        System.out.println("优化3:");
        filterEmployee3(emps);
        System.out.println("============");
        System.out.println("优化4:");
        filterEmployee4(employees5);
    }

    //需求1：获取公司中年龄小于 35 的员工信息
    private static List<Employee> filterEmployeeForAge(List<Employee> emps){
        List<Employee> list = new ArrayList<>();
        for(Employee employee:emps){
            if (employee.getAge()<35){
                list.add(employee);
            }
        }
        return list;
    }

    //需求2：获取公司中工资大于 5000 的员工信息
    private static List<Employee> filterEmployeeForSalary(List<Employee> emps)
    {
        List<Employee> list = new ArrayList<>();
        for(Employee employee:emps){
            if (employee.getSalary()>5000){
                list.add(employee);
            }
        }
        return list;
    }

    //优化方式一：策略设计模式
    private static List<Employee> filterEmployee1(List<Employee> emps, MyPredicate<Employee> filter)
    {
        List<Employee> list = new ArrayList<>();
        for (Employee employee:emps){
            if(filter.filter(employee))
            {
                list.add(employee);
            }
        }

        return list;
    }

    //优化二:匿名内部类
    private static List<Employee> filterEmployee2(List<Employee> emps)
    {

        List<Employee> list = new ArrayList<>();

        list = filterEmployee1(emps, new MyPredicate<Employee>() {
            @Override
            public boolean filter(Employee employee) {
               if (employee.getSalary() >5000){
                   return true;
               }
               return false;
            }
        });
        return list;
    }

    //优化方式三：Lambda 表达式
    private static void filterEmployee3(List<Employee> emps)
    {

        List<Employee> employees1 = filterEmployee1(emps, (e) -> e.getAge() < 35);
        employees1.forEach(System.out::println);
        System.out.println("=======================");
        List<Employee> employees2 = filterEmployee1(emps, (e) -> e.getSalary() > 5000);
        employees2.forEach(System.out::println);
    }

    //优化方式四：Stream API
    private static void filterEmployee4(List<Employee> emps){
        emps.stream().filter((e)->e.getSalary()>5000).forEach(System.out::println);

        System.out.println("==================");
        emps.stream().filter((e)->e.getAge()<35).forEach(System.out::println);
    }
}
