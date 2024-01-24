package com.java.myinterface;

/**
 * @author Lee
 * @create 2019/10/8 16:13
 */
public class SubClass extends MyClass implements MyFun,MyInterface{
    @Override
    public String getName() {
        return MyInterface.super.getName();
    }
}
