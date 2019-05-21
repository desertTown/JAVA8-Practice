package com.evan.java8.samples.stream;

import java.util.Arrays;
import java.util.List;

public class Streams11 {

    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        List<Person> persons =
            Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

//        test1(persons);
//        test2(persons);
//        test3(persons);
//        test4(persons);
//        test5(persons);
        test6(persons);
    }

    private static void test1(List<Person> persons) {

        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);    // Pamela
    }

    private static void test2(List<Person> persons) {
        Person result =
            persons
                .stream()
                .reduce(new Person("", 0), (p1, p2) -> {
                    p1.age += p2.age;
                    p1.name += p2.name;
                    return p1;
                });

        System.out.format("name=%s; age=%s", result.name, result.age);
    }

    // 第三个参数参考连接  https://blog.csdn.net/IO_Field/article/details/54971679
    private static void test3(List<Person> persons) {
        Integer ageSum = persons
            .stream()
            //  第一个参数返回实例u，传递你要返回的U类型对象的初始化实例u
            //  第二个参数累加器accumulator，可以使用二元?表达式（即二元lambda表达式），声明你在u上累加你的数据来源t的逻辑
            //  例如(u,t)->u.sum(t),此时lambda表达式的行参列表是返回实例u和遍历的集合元素t，函数体是在u上累加t，
            //  第三个参数组合器combiner，同样是二元?表达式，(u,t)->u
            .reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);

        System.out.println(ageSum);

        /**
         * Stream是支持并发操作的，为了避免竞争，对于reduce线程都会有独立的result，combiner的作用在于合并每个线程的result得到最终结果。
         * 这也说明了了第三个函数参数的数据类型必须为返回数据类型了。
         *
         *
         * 需要注意的是，因为第三个参数用来处理并发操作，如何处理数据的重复性，应多做考虑，否则会出现重复数据！
         *
         */
    }

    private static void test4(List<Person> persons) {
        Integer ageSum = persons
            .stream()
            .reduce(0,
                (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                    return sum += p.age;
                },
                (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                    return sum1 + sum2;
                });

        System.out.println(ageSum);
    }

    private static void test5(List<Person> persons) {
        Integer ageSum = persons
            .parallelStream()
            .reduce(0,
                (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                    return sum += p.age;
                },
                (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                    return sum1 + sum2;
                });

        System.out.println(ageSum);
    }

    private static void test6(List<Person> persons) {
        Integer ageSum = persons
            .parallelStream()
            .reduce(0,
                (sum, p) -> {
                    System.out.format("accumulator: sum=%s; person=%s; thread=%s\n",
                        sum, p, Thread.currentThread().getName());
                    return sum += p.age;
                },
                (sum1, sum2) -> {
                    System.out.format("combiner: sum1=%s; sum2=%s; thread=%s\n",
                        sum1, sum2, Thread.currentThread().getName());
                    return sum1 + sum2;
                });

        System.out.println(ageSum);
    }
}
