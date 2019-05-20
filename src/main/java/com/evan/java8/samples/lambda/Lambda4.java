package com.evan.java8.samples.lambda;


public class Lambda4 {

    static int outerStaticNum;

    int outerNum;

    void testScopes() {
        int num = 1;

        Lambda2.Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);

        String convert = stringConverter.convert(2);
        System.out.println(convert);    // 3

        Lambda2.Converter<Integer, String> stringConverter2 = (from) -> {
            outerNum = 13;
            return String.valueOf(from+outerNum);
        };

        System.out.println(stringConverter2.convert(15));


        ///因为这时的outerNum是全局变量， 所以是可以再修改的， 不会被隐式地转成final类型
        // 像上面的num变量，如果经过lambdabiao表达式内部改变了值， 就不能再改了， 因为已经被隐式转成final类型
        outerNum = 14;
        System.out.println("outerNum: " + outerNum);

        String[] array = new String[1];
        Lambda2.Converter<Integer, String> stringConverter3 = (from) -> {
            array[0] = "Hi there";
            return String.valueOf(from);
        };

        stringConverter3.convert(23);

        System.out.println(array[0]);
    }

    public static void main(String[] args) {
        new Lambda4().testScopes();
    }

}