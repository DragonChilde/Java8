package com.java.myinterface;

/**
 * @author Lee
 * @create 2019/10/8 16:11
 */
public interface MyInterface {
    default String getName()
    {
        return "this is myinterface";
    }

    public static void show()
    {
        System.out.println("this is static method in interface");
    }

}
