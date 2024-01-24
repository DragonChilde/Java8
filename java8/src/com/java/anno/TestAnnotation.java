package com.java.anno;

import java.lang.reflect.Method;

/**
 * @author Lee
 * @create 2019/10/11 14:33
 */
public class TestAnnotation {
    //private  /*@NotNull*/ Employee employee = null;

    public static void main(String[] args) throws Exception{
        Class<TestAnnotation> testAnnotationClass = TestAnnotation.class;
        Method method = testAnnotationClass.getMethod("show");

        MyAnnotation[] annotationsByType = method.getAnnotationsByType(MyAnnotation.class);

       for (MyAnnotation s:annotationsByType)
        {
            System.out.println(s.value());
        }


       get("111");

    }

    @MyAnnotation("hello")
    @MyAnnotation("world")
    public void show()
    {

    }


    public static void get(@MyAnnotation("abc") String str)
    {
        System.out.println(str);
    }
}
