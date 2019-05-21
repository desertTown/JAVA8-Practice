package com.evan.java8.samples.stream;

import java.util.OptionalInt;
import java.util.stream.IntStream;


public class Streams4 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            }
        }

        IntStream.range(0, 10)
                .forEach(i -> {
                    if (i % 2 == 1) System.out.println(i);
                });

        IntStream.range(0, 10)
            .filter(i -> i % 2 == 1)
            .forEach(System.out::println);

        OptionalInt reduced1 =
            IntStream.range(0, 10)
                .reduce((a, b) -> a + b);
        System.out.println(reduced1.getAsInt());


        // 这里reduce 的第一个参数用来指定Stream循环的初始值
        int reduced2 =
            IntStream.range(0, 10)
                .reduce(7, (a, b) -> a + b);
        System.out.println(reduced2);
    }
}
