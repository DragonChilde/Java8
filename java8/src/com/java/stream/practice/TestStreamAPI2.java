package com.java.stream.practice;

import com.java.stream.bean.Trader;
import com.java.stream.bean.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Lee
 * @create 2019/9/29 14:13
 */
public class TestStreamAPI2 {

    private static List<Transaction> transactions = Arrays.asList(
            new Transaction(new Trader("Brian", "Cambridge"), 2011, 300),
            new Transaction(new Trader("Raoul", "Cambridge"), 2012, 1000),
            new Transaction(new Trader("Raoul", "Cambridge"), 2011, 400),
            new Transaction(new Trader("Mario", "Milan"), 2012, 710),
            new Transaction(new Trader("Mario", "Milan"), 2012, 700),
            new Transaction(new Trader("Alan", "Cambridge"), 2012, 950)

    );

    public static void main(String[] args) {
        test1();
        System.out.println("--------------");
        test2();
        System.out.println("--------------");
        test3();
        System.out.println("------------");
        test4();
        System.out.println("-------------");
        test5();
        System.out.println("--------------");
        test6();
        System.out.println("--------------");
        test7();
        System.out.println("--------------");
        test8();
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
    private static void test1()
    {
        transactions.stream().filter(t -> t.getYear() == 2011).sorted((t1,t2) -> Integer.compare(t1.getValue(),t2.getValue())).forEach(System.out::println);
    }

    //2. 交易员都在哪些不同的城市工作过？
    private static void test2()
    {
        transactions.stream().map(t -> t.getTrader().getCity()).distinct().forEach(System.out::println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    private static void test3()
    {
        transactions.stream()
                .filter(x -> x.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted((t1,t2) -> t1.getName().compareTo(t2.getName()))
                .distinct()
                .forEach(System.out::println);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    private static void test4()
    {
        /*方法一*/
        transactions.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                .forEach(System.out::println);

        System.out.println("====================");

        /*方法二*/
        String str = transactions.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                .reduce("", String::concat);
        System.out.println(str);

        System.out.println("===============");

        /*方法三(不区分大小写排序)*/
        transactions.stream()
                .map(t -> t.getTrader().getName())
                .flatMap(TestStreamAPI2::filterCharacter)
                .sorted((s1,s2) -> s1.compareToIgnoreCase(s2))
                .forEach(System.out::println);


    }

    private static Stream<String> filterCharacter(String str)
    {
        ArrayList<String> list = new ArrayList<>();
        for (Character ch : str.toCharArray())
        {
            list.add(ch.toString());
        }
        return list.stream();
    }

    //5. 有没有交易员是在米兰工作的？
    private static void test5()
    {
        boolean b = transactions.stream()
                .anyMatch(x -> x.getTrader().getCity().equals("Milan"));
        System.out.println(b);
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    private static void test6()
    {
        transactions.stream()
                .filter(x -> x.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    //7. 所有交易中，最高的交易额是多少
    private static void test7()
    {
        Optional<Integer> max = transactions.stream()
                .map(x -> x.getValue())
                .max(Integer::compareTo);
        System.out.println(max.get());
    }

    //8. 找到交易额最小的交易
    private static void test8()
    {
        Optional<Transaction> min = transactions.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
        System.out.println(min);
    }
}
