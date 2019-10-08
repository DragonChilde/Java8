package com.java.myinterface;

/**
 * @author Lee
 * @create 2019/10/8 16:11
 */
public interface MyFun {
    default String getName()
    {
        return "this is myfun";
    }
}
