package com.song.lab06;

/* AccountData.java */

import com.song.lab06.sortedlist.*;

/**
 *  Implements a customer's account profile for the virtual teller machine.
 **/
public class AccountData implements Keyable {
    private String name;        // Customer name.
    private int    balance;     // Starting balance.
    private int    number;      // Account number.

    /**
     *  Constructs a new account for customer "newName" with acount number "num"
     *  and a $0 starting balance.
     **/
    public AccountData(String newName, int num) {
        name = newName;
        number = num;
        balance = 0;
    }

    /**
     *  lessThan() returns true if this account's number is less than the
     *  argument's account number.
     *  keyable 是 accountdata的接口类
     *  其声明在keyable里（无实现 记得纯虚函数吗） 而实现在其子类里
     **/
    public boolean lessThan(Keyable x) {
        return number < ((AccountData) x).number;  //接口类（父）转子类
    }

    /**
     *  getOwner() returns the name of this account's owner.
     **/
    public String getOwner() {
        return name;
    }

    /**
     *  toString() returns a String version of this account's number.
     **/
    public String toString() {
        return "" + number;
    }


    /**
     *  getBalance() returns the balance of this account.
     **/
    public int getBalance() {
        return balance;
    }

    /**
     *  withdraw() reduces the balance by the withdrawal amount "amt".
     **/
    public void withdraw(int amt) throws BadTransactionException{

        if (amt < 0) {
            throw new BadTransactionException(amt);
        }//如果花括号里面的内容执行，直接跳过这个method，下面的代码不会执行！

        if (amt <= balance) {
            balance = balance - amt;

            //这里有问题了，这里只判断了amt是否小于deposit。但如果当amt为负数时，balance=balance-（-2）就成为了+2
            //严重影响了withdraw功能和整个系统的安全性，所以必须加一个exception！

        } else {
            System.out.println("Error:  Insufficient funds: " + amt);
        }
    }

    /**
     *  deposit() deposits "amt" dollars into this account.
     **/
    public void deposit(int amt) {
        if (amt >= 0) {
            balance = balance + amt;
        } else {
            System.out.println("Error:  Tried to deposit less than 0: " + amt);
        }
    }

    /**
     *  getNumber() returns this account's number.
     **/
    public int getNumber() {
        return number;
    }

    /**
     *  getKey() returns this account's account number as the key to use for
     *  sorting and comparison.
     **/
    public int getKey() {
        return number;
    }
}