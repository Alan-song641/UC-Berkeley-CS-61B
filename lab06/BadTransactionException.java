package com.song.lab06;


/**
 *  Implements an exception that should be thrown for negative money amount input.
 **/
public class BadTransactionException extends Exception{

    public int MoneyInput;  // 用来表示The invalid money amount input.（因为函数中的BadMoneyInput是局部变量）

    public BadTransactionException(int BadMoneyInput){
        super("Invalid Transaction Amount: " + BadMoneyInput);

        MoneyInput=BadMoneyInput;
    }
}
