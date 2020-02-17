package com.song.homework10;

/* Sorts.java */


public class Sorts {

    /**
     *  Place any final static fields you would like to have here.
     **/


    /**
     *  countingSort() sorts an array of int keys according to the
     *  values of _one_ of the base-16 digits of each key.  "whichDigit"
     *  indicates which digit is the sort key.  A zero means sort on the least
     *  significant (ones) digit; a one means sort on the second least
     *  significant (sixteens) digit; and so on, up to a seven, which means
     *  sort on the most significant digit.
     *  @param keys is an array of ints.  Assume no key is negative.
     *  @param whichDigit is a number in 0...7 specifying which base-16 digit
     *    is the sort key.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys sorted according to the chosen digit.
     *
     *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
     **/
    public static int[] countingSort(int[] keys, int whichDigit) {
        // Replace the following line with your solution.
        int[] counts=new int[16];
        //决定以哪个16进制数为排序的index，以Digit表示。一个16进制数占4bit（0000-1111）
        int Digit;  //【digit的范围：0-15】作为index
        for(int i=0;i<keys.length;i++){
            Digit = keys[i]>>(4*whichDigit);  //bit operation 右移四位，移出去的抛弃，高位补零
            Digit = Digit & 15;  //bit operator 与15（"0000...00001111"）取“”“”"与"操作，留下最低四位，高28位为零
            counts[Digit]++;
        }

        //counts里面存储的原来是16进制数（其以whichDigit判断的Digit作为index）的个数，现在是【小于那个16进制数的个数】
        int total=0;
        for(int j=0;j<counts.length;j++){
            int c=counts[j];
            counts[j]=total;
            total+=c;
        }


        int[] result=new int[keys.length];

        for(int j=0;j<keys.length;j++){
            //注意这里也要换成Digit(范围0-15)而不是keys[j](这是个高28位不为零的8*4位数)作为index
            Digit=( keys[j]>>(4*whichDigit) ) & 15;
            result[counts[Digit]]=keys[j];
            counts[Digit]++;
        }

        return result;
    }

    /**
     *  radixSort() sorts an array of int keys (using all 32 bits
     *  of each key to determine the ordering).
     *  @param keys is an array of ints.  Assume no key is negative.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys in sorted order.
     *
     *    Note:  Return a _newly_ created array.  DO NOT CHANGE THE ARRAY keys.
     **/
    public static int[] radixSort(int[] keys) {
        // Replace the following line with your solution.
        int[] result=keys;  //无需new一个array，只是新加了个array reference指向keys，在新的这个reference上操作

        //从i=0开始，从最低位向高位进行排序（LSD）
        for(int i=0;i<8;i++){
            result=countingSort(result,i);   //注意这里result 引用的对象已经换了，不再是keys了，所以keys没变化
        }

        return result;
    }

    /**
     *  yell() prints an array of int keys.  Each key is printed in hexadecimal
     *  (base 16).
     *  @param keys is an array of ints.
     **/
    public static void yell(int[] keys) {
        System.out.print("keys are [ ");
        for (int i = 0; i < keys.length; i++) {
            System.out.print(Integer.toString(keys[i], 16) + " ");
        }
        System.out.println("]");
    }

    /**
     *  main() creates and sorts a sample array.
     *  We recommend you add more tests of your own.
     *  Your test code will not be graded.
     *  int 在电脑里占32bit（二进制），用16进制的数表示，一个16进制数占4bit（0000-1111），一共就是8位数字，
     **/
    public static void main(String[] args) {
        int[] keys = { Integer.parseInt("60013879", 16),
                Integer.parseInt("11111119", 16),
                Integer.parseInt("2c735010", 16),
                Integer.parseInt("2c732010", 16),
                Integer.parseInt("7fffffff", 16),
                Integer.parseInt("4001387c", 16),
                Integer.parseInt("10111119", 16),
                Integer.parseInt("529a7385", 16),
                Integer.parseInt("1e635010", 16),
                Integer.parseInt("28905879", 16),
                Integer.parseInt("00011119", 16),
                Integer.parseInt("00000000", 16),
                Integer.parseInt("7c725010", 16),
                Integer.parseInt("1e630010", 16),
                Integer.parseInt("111111e5", 16),
                Integer.parseInt("61feed0c", 16),
                Integer.parseInt("3bba7387", 16),
                Integer.parseInt("52953fdb", 16),
                Integer.parseInt("40013879", 16) };

        yell(keys);
        keys = radixSort(keys);
        yell(keys);
    }

}
