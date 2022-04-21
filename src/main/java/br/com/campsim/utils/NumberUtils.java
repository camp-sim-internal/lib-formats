package br.com.campsim.utils;

public class NumberUtils {

    private NumberUtils(){}

    public static boolean isMultipleOfTwo(int number){
        String binaryNumber = Integer.toBinaryString(number);
        int sum = 0;

        for(char let : binaryNumber.toCharArray())
            sum += let;

        return sum == 1;
    }
}
