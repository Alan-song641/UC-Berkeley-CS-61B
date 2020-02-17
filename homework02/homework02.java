package com.song.homework02;/* Date.java */

import java.io.*;
import java.lang.*;

/*【分析】如果让我们自己写Date类的构造呢？以后要学会自己写出这样可以层层调用结构清晰的类。
        从程序功能入手，依次去想
        【这个类可以怎样被构造、需要哪些static方法来完成与对象无关的子功能、可否添加一些方法降低算法复杂度、toString()方法是否需要重写等】

        域：int moth,day,year;
        构造函数：两个
        一种是由int型直接创建，一种是用String创建

    static方法（想一下一个日期类需要哪些【与特定（日期）对象无关】的方法）：
        判断日期是否合法——public static boolean isValidDate(int month, int day, int year);
        是否是闰年——public static boolean isLeapYear(int year)
        每个月有多少天——public static int daysInMonth(int month, int year)

    普通方法：
        需要重写——public String toString();
        计算某一天是当年的第几天——public int dayInYear();
        自己添加的辅助方法——public int dayInAll();
        实现最终要求的功能——public boolean isBefore(Date d)/isAfer(Date d);和public int difference(Date d);
*/

class Date {

    /* Put your private data fields here. */
    private static final int[] DAYS1= { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; //闰年
    private static final int[] DAYS2 = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; //非闰年
    private int month;
    private int day;
    private int year;
    private static int max_day;
    /** Constructs a date with the given month, day and year.   If the date is
     *  not valid, the entire program will halt with an error message.
     *  @param month is a month, numbered in the range 1...12.
     *  @param day is between 1 and the number of days in the given month.
     *  @param year is the year in question, with no digits omitted.
     */
    public Date(int month, int day, int year){
        //可能需要提前得到max_day的值? 不需要 因为是static 会先进行static函数的计算

//        if (!isValidDate(month, day, year))
//            throw new IllegalArgumentException("Invalid date");

            this.day=day;
            this.year=year;
            this.month=month;

    }

    /** Constructs a Date object corresponding to the given string.
     *  @param s should be a string of the form "month/day/year" where month must
     *  be one or two digits, day must be one or two digits, and year must be
     *  between 1 and 4 digits.  If s does not match these requirements or is not
     *  a valid date, the program halts with an error message.
     */
    public Date(String s) {
        String [] field=s.split("/");

        month=Integer.parseInt(field[0]);
        day=Integer.parseInt(field[1]);
        year=Integer.parseInt(field[2]);

//        if(!isValidDate(month,day,year))
//            System.exit(0);
    }

    /** Checks whether the given year is a leap year.
     *  @return true if and only if the input year is a leap year.
     */
    public static boolean isLeapYear(int year) {
        if(year%400==0||(year%4==0&&year%100!=0))
        {
            return true;
        }
        else
            return false;

    }

    /** Returns the number of days in a given month.
     *  @param month is a month, numbered in the range 1...12.
     *  @param year is the year in question, with no digits omitted.
     *  @return the number of days in the given month.
     */
    public static int daysInMonth(int month, int year){
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
                max_day=31;
                break;
            case 2:
                if(isLeapYear(year))
                    max_day=29;
                else
                    max_day=28;
                break;
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
                max_day=30;
                break;
        }

        return max_day;
    }

    /** Checks whether the given date is valid.
     *  @return true if and only if month/day/year constitute a valid date.
     *
     *  Years prior to A.D. 1 are NOT valid.
     */
    public static boolean isValidDate(int month, int day, int year) {
        if (1 <= day && day <= max_day && 1 <= month && month <= 12 && year >= 1 && year <= 9999)
            if(day==29&&(!isLeapYear(year)))
                return false;
            else
                return true;

        else
            return false;

    }

    /** Returns a string representation of this date in the form month/day/year.
     *  The month, day, and year are expressed in full as integers; for example,
     *  12/7/2006 or 3/21/407.
     *  @return a String representation of this date.
     */
    public String toString() {
        return this.month+"/"+this.day+"/"+this.year;
    }

    /** Determines whether this Date is before the Date d.
     *  @return true if and only if this Date is before d.
     */
    public boolean isBefore(Date d) {
        if(this.year<d.year)
            return true;
        else if(this.year>d.year)
            return false;
        else
        {
            if(this.month<d.month)
                return true;
            else if (this.month>d.month)
                return false;
            else
            {
                if(this.day<d.day)
                    return true;
                else if (this.day>d.day)
                    return false;
                else
                    return false;
            }
        }

    }

    /** Determines whether this Date is after the Date d.
     *  @return true if and only if this Date is after d.
     */
    public boolean isAfter(Date d) {
        if(this.year==d.year&&this.month==d.month&&this.day==d.day)
        {
            return false;
        }
        else
            return (!(isBefore(d)));
    }

    /** Returns the number of this Date in the year.
     *  @return a number n in the range 1...366, inclusive, such that this Date
     *  is the nth day of its year.  (366 is used only for December 31 in a leap
     *  year.)
     */
    public int dayInYear() {
        int whole_day=0;

        if(isLeapYear(year))
        {
            for(int i=month;i>0;i--)
            {
                whole_day+=DAYS1[i-1];
            }
            whole_day+=day;
        }
        else
        {
            for(int i=month;i>0;i--)
            {
                whole_day+=DAYS2[i-1];
            }
            whole_day+=day;
        }

        return whole_day;
    }

    /** Determines the difference in days between d and this Date.  For example,
     *  if this Date is 12/15/2012 and d is 12/14/2012, the difference is 1.
     *  If this Date occurs before d, the result is negative.
     *  @return the difference in days between d and this date.
     */
    public int difference(Date d) {

        if(this.year==d.year)
            return this.dayInYear()-d.dayInYear();
        else if(this.year>d.year)
        {
            int diffDays=0;

            int tempYear1 = this.year;
            int tempYear2 = d.year;

            //int diffYear=0;
            while(tempYear1!=tempYear2)
            {
                if(isLeapYear(tempYear2))
                    diffDays+=366;
                else
                    diffDays+=365;

                tempYear2++;
            }
            diffDays+=this.dayInYear()-d.dayInYear();
            return diffDays;
        }
        else
        {
            int diffDays=0;

            int tempYear2 = this.year;
            int tempYear1 = d.year;

            while(tempYear1!=tempYear2)
            {
                if(isLeapYear(tempYear2))
                    diffDays+=366;
                else
                    diffDays+=365;

                tempYear2++;
            }
            diffDays+=d.dayInYear()-this.dayInYear();

            return -diffDays;
        }
    }

    public static void main(String[] argv) throws IllegalAccessException {
        System.out.println("\nTesting constructors.");
        Date d1 = new Date(1, 1, 1);
        System.out.println("Date should be 1/1/1: " + d1);
        d1 = new Date("2/4/2");
        System.out.println("Date should be 2/4/2: " + d1);
        d1 = new Date("2/29/2000");
        System.out.println("Date should be 2/29/2000: " + d1);
        d1 = new Date("2/29/1904");
        System.out.println("Date should be 2/29/1904: " + d1);

        d1 = new Date(12, 31, 1975);
        System.out.println("Date should be 12/31/1975: " + d1);
        Date d2 = new Date("1/1/1976");
        System.out.println("Date should be 1/1/1976: " + d2);
        Date d3 = new Date("1/2/1976");
        System.out.println("Date should be 1/2/1976: " + d3);

        Date d4 = new Date("2/27/1977");
        Date d5 = new Date("8/31/2110");

        /* I recommend you write code to test the isLeapYear function! */

        System.out.println("\nTesting before and after.");
        System.out.println(d2 + " after " + d1 + " should be true: " +
                d2.isAfter(d1));
        System.out.println(d3 + " after " + d2 + " should be true: " +
                d3.isAfter(d2));
        System.out.println(d1 + " after " + d1 + " should be false: " +
                d1.isAfter(d1));
        System.out.println(d1 + " after " + d2 + " should be false: " +
                d1.isAfter(d2));
        System.out.println(d2 + " after " + d3 + " should be false: " +
                d2.isAfter(d3));

        System.out.println(d1 + " before " + d2 + " should be true: " +
                d1.isBefore(d2));
        System.out.println(d2 + " before " + d3 + " should be true: " +
                d2.isBefore(d3));
        System.out.println(d1 + " before " + d1 + " should be false: " +
                d1.isBefore(d1));
        System.out.println(d2 + " before " + d1 + " should be false: " +
                d2.isBefore(d1));
        System.out.println(d3 + " before " + d2 + " should be false: " +
                d3.isBefore(d2));

        System.out.println("\nTesting difference.");
        System.out.println(d1 + " - " + d1  + " should be 0: " +
                d1.difference(d1));
        System.out.println(d2 + " - " + d1  + " should be 1: " +
                d2.difference(d1));
        System.out.println(d3 + " - " + d1  + " should be 2: " +
                d3.difference(d1));
        System.out.println(d3 + " - " + d4  + " should be -422: " +
                d3.difference(d4));
        System.out.println(d5 + " - " + d4  + " should be 48762: " +
                d5.difference(d4));

        System.out.println("\ntest day in year");
        System.out.println(d1+":"+d1.dayInYear());
        System.out.println(d2+":"+d2.dayInYear());
        System.out.println(d3+":"+d3.dayInYear());
        System.out.println(d4+":"+d4.dayInYear());
        System.out.println(d5+":"+d5.dayInYear());

    }
}
