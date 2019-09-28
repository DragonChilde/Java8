package com.java.lambda.bean;

import com.java.lambda.inter.MyPredicate;

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
