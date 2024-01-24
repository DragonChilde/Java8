package com.java.lamabda.inter;

/**
 * @author Lee
 * @create 2019/9/25 14:53
 */
@FunctionalInterface
public interface MyFunction2<T,R> {
    public R getValue(T value1,T value2);
}
