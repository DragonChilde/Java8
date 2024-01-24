package com.java.lambda;


import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author Lee
 * @create 2019/9/23 12:02
 */
public class TestLambda1 {

    public static void main(String[] args) {
        test1();
    }

    //原来的匿名内部类
    public static void test1(){
        //方法一
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };

        TreeSet<String> tree1 = new TreeSet<>(comparator);


        //方法二
        TreeSet<String> tree2 = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }

    //现在的 Lambda 表达式
    public static void test2(){
        Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());
        TreeSet<String> ts = new TreeSet<>(com);


    }


}
