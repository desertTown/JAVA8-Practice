package com.evan.java8.samples.lambda;


public class Interface1 {

    interface Formula {
        double calculate (int a);

        default double sqrt(int a) {return Math.sqrt(positive(a));};

        static int positive(int a) {return a >= 0 ? a : 0;};
    }

    public static void main(String[] args) {
        Formula formula1 = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        // 默认方法无法在lambda表达式内部被访问。因此下面的代码是无法通过编译的：
//        Formula formula1 = (form) -> sqrt(a * 100);

        System.out.println(formula1.calculate(100));     // 100.0
        System.out.println(formula1.sqrt(-23));          // 0.0
        System.out.println(Formula.positive(-4));        // 0.0

//        Formula formula2 = (a) -> sqrt( a * 100);
    }

}