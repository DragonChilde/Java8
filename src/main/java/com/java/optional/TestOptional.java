package com.java.optional;

import com.java.lambda.bean.Employee;
import com.java.optional.bean.Man;
import com.java.optional.bean.NewMan;
import com.java.optional.bean.Teacher;

import java.util.Optional;

/**
 * @author Lee
 * @create 2019/10/8 10:59
 */
public class TestOptional {

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        test5();
    }

    /*
     * 一、Optional 容器类：用于尽量避免空指针异常
     * 	Optional.of(T t) : 创建一个 Optional 实例
     * 	Optional.empty() : 创建一个空的 Optional 实例
     * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
     * 	isPresent() : 判断是否包含值
     * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
     * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
     * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
     * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
     */

    private static void test1()
    {
        /*创建Optional实例的Employee泛型*/
      /*  Optional<Employee> optional = Optional.of(new Employee());
        Employee employee = optional.get();
        System.out.println(employee);*/

        /*传入参数为null，抛出NullPointerException*/
      /*  Optional<Employee> optional = Optional.of(null);
        System.out.println(optional.get());*/

      /*抛出NoSuchElementException*/
      /* Optional<Employee> op = Optional.empty();
        System.out.println(op.get());*/

      /*
      public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
       }
        */
        Optional<Employee> op2 = Optional.ofNullable(null);
        System.out.println(op2.get());

    }

    private static void test2()
    {
        /*创建一个空的Employee对象*/
        Optional<Employee> optional = Optional.ofNullable(new Employee());
        /*判断是否有值*/
        if(optional.isPresent()) {
            System.out.println(optional.get());
        }

        /*创建一个空对象*/
        Optional<Employee> op = Optional.empty();
        /*如果Optional没值则取新值*/
        Employee employee = op.orElse(new Employee("李四"));
        System.out.println(employee);

        /*参数是函数式接口Supplier的供给型接口:接口不接受返回值,可以在里面创建任意对象*/
        Employee employee2 = op.orElseGet(() -> new Employee("张三"));
        System.out.println(employee2);
    }

    private static void test3()
    {

        Optional<Employee> optional = Optional.of(new Employee(101, "张三", 18, 9999.99));

        /*参数是函数式接口Function（类名 :: 实例方法名）*/
        Optional<String> op = optional.map(Employee::getName);
        System.out.println(op.get());

        /*参数是函数式接口Function,但要求使用Optional进行包装*/
        Optional<Integer> op2 = optional.flatMap(e -> Optional.of(e.getId()));
        System.out.println(op2.get());
    }

    /*举例旧使用时要在方法里多重判断*/
    private static void test4()
    {
        Man man = new Man();
        String teacherName = getTeacherName(man);
        System.out.println(teacherName);

    }

    private static String getTeacherName(Man man)
    {
        if (man != null)
        {
            Teacher teacher = man.getTeacher();
            if (teacher != null)
            {
                return teacher.getName();
            }
        }
        return "苍老师";
    }

    private static void test5()
    {
        Optional<Teacher> optionalTeacher = Optional.ofNullable(new Teacher("三上老师"));
        Optional<NewMan> optionalNewMan = Optional.ofNullable(new NewMan(optionalTeacher));
        String teacherName = getTeacherName2(optionalNewMan);
        System.out.println(teacherName);


    }

    private static String getTeacherName2(Optional<NewMan> man){
        return man.orElse(new NewMan())
                .getTeacher()
                .orElse(new Teacher("苍老师"))
                .getName();
    }
}
