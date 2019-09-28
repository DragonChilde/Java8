package com.java.lambda.bean;

import com.java.lambda.inter.MyPredicate;

/**
 * @author Lee
 * @create 2019/9/23 14:59
 */
public class FilterEmployeeForSalary implements MyPredicate<Employee> {
    @Override
    public boolean filter(Employee employee) {
        if (employee.getSalary()>5000){
            return true;
        }
        return false;
    }
}
