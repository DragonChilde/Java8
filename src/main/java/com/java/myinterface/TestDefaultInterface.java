package com.java.myinterface;

/**
 * @author Lee
 * @create 2019/10/8 16:10
 */
public class TestDefaultInterface {
    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        System.out.println(subClass.getName());

        MyInterface.show();

    }
}
