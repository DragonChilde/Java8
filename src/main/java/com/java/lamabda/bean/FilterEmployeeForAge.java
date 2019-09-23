package com.java.lamabda.bean;

import com.java.lamabda.inter.MyPredicate;

/**
 * @author Lee
 * @create 2019/9/23 14:58
 */
public class FilterEmployeeForAge implements MyPredicate<Employee> {
    @Override
    public boolean filter(Employee employee) {
        if (employee.getAge()<35){
            return true;
        }
        return false;
    }
}
