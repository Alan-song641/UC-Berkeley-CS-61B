package com.song.homework01;

import java.io.*;
import java.net.MalformedURLException;
import java.net.*;

public class test {

    public static void main(String[] args) throws Exception {

        //输入一个字符串，然后删掉字符串的第二个字符，再重新输出这个字符。

        //URL oracle=new URL("http://www.oracle.com/");
        BufferedReader in =new BufferedReader(new InputStreamReader(System.in));

        String inputline=in.readLine();
        String first=inputline.substring(0,1);
        int numofline=inputline.length();
        String second=inputline.substring(2,numofline);

        String output=first+second;

        System.out.println(output);

        in.close();

    }
}
