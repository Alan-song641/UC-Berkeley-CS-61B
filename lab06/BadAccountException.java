package com.song.lab06;

/* BadAccountException.java */

/**
 *  Implements an exception that should be thrown for nonexistent accounts.
 **/
public class BadAccountException extends Exception {

    public int accountNumber;
    // 用来记录The invalid account number.（因为函数中的badAcctNumber是局部变量）

    /**
     *  Creates an exception object for nonexistent account "badAcctNumber".
     **/
    public BadAccountException(int badAcctNumber) {
        super("Invalid account number: " + badAcctNumber);

        accountNumber = badAcctNumber;
    }
}