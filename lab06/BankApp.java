package com.song.lab06;
/* BankApp.java */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *  A bank application.  Allows a user to create and manipulate
 *  banking accounts, using an ATM that is shared by all banking applications.
 */
public class BankApp {
    private BufferedReader bReader =
            new BufferedReader(new InputStreamReader(System.in));
    private VirtualTeller ATM = new VirtualTeller();

    public static void main(String[] args) throws IOException {
        greeting();
        usage();
        BankApp bankApp = new BankApp();

        String command = bankApp.readLine("--> ");
        //readline显示  Unhandled exception: java.io.IOException
        //此时加上throws IOException就可 因为要接着readline声明上的throw IOException，然后用try-catch

        while (!command.equals("quit")) {
            try {
                if (command.equals("open")) {
                    bankApp.open();
                    command = bankApp.readLine("--> ");
                } else if (command.equals("deposit")) {
                    try {
                        bankApp.doDeposit();//移动command命令到这行之下。
                        // 【如果这句函数丢出异常，会直接跳到catch语句中，而不会执行下面的command命令！】
                        command = bankApp.readLine("--> ");//这句code被跳过
                    } catch (BadAccountException e) {
                        System.out.println(e);
                    }
                    //最终在这里用try和catch函数，确保catch掉后，继续while循环
                    //设计得很好 try和catch的作用在于当发现exception时，不退出，而是继续下去这个while循环
                    //而throws 语句就是将exception不断地从调用函数抛向main函数
                } else if (command.equals("withdraw")) {
                    try {
                        bankApp.doWithdraw();
                        //同上
                        command = bankApp.readLine("--> ");
                    }
                    catch (BadAccountException e1) {
                        System.out.println(e1);
                    }   //同上
                    catch(BadTransactionException e2){
                        System.out.println(e2);
                    }  //这个原理和上面一致，用于抓住查出当withdraw功能输入为负数的时候，deposit不减反加的异常
                } else if (command.equals("inquire")) {
                    try {
                        bankApp.doInquire();
                        //同上
                        command = bankApp.readLine("--> ");
                    } catch (BadAccountException e) {
                        System.out.println(e);
                    }    //同上

                } else {
                    System.err.println("Invalid command: " + command);
                    usage();

                }

            } catch(IOException e) {
                System.err.println(e);
            }

        }
    }

    public BankApp() {
        // The field declarations have initializers;
        //   no initialization is needed here.
    }

    /**
     *  open() prompts the user to create an account and creates one in the ATM.
     *  @exception IOException if there are problems reading user input.
     */
    private void open() throws IOException {
        String name = readLine("Enter name: ");
        int newNum = ATM.openAccount(name);

        System.out.println(name + ", your new account number is: " + newNum);
        System.out.println("Thanks for opening an account with us!");
    }

    /**
     *  doDeposit() prompts the user for an account number and tries to perform a
     *  deposit transaction on that account.
     *  @exception IOException if there are problems reading user input.
     */
    private void doDeposit() throws IOException, BadAccountException {
        // Get account number.

            int acctNumber = readInt("Enter account number: ");
            int amount = readInt("Enter amount to deposit: ");


        ATM.deposit(acctNumber, amount); //每个运用到这个函数都要传递一下，加“throws”关键词
        System.out.println("New balance for #" + acctNumber + " is " +
                ATM.balanceInquiry(acctNumber));//每个运用到这个函数都要传递一下，加“throws”关键词
    }

    /**
     *  doWithdraw() prompts the user for an account number and tries
     *  to perform a withdrawal transaction from that account.
     *  @exception IOException if there are problems reading user input.
     */
    private void doWithdraw() throws IOException, BadAccountException, BadTransactionException {
        // Get account number.
        int acctNumber = readInt("Enter account number: ");
        int amount = readInt("Enter amount to withdraw: ");

        ATM.withdraw(acctNumber, amount);

            System.out.println("New balance for #" + acctNumber + " is " +
                    ATM.balanceInquiry(acctNumber));


    }

    /**
     *  doInquire() prompts the user for an account number, then attempts to
     *  discover and print that account's balance.
     *  @exception IOException if there are problems reading user input.
     */
    private void doInquire() throws IOException, BadAccountException {
        int acctNumber = readInt("Enter account number: ");

        System.out.println("Balance for #" + acctNumber + " is " +
                ATM.balanceInquiry(acctNumber));
    }

    /**
     *  greeting() displays a greeting message on the screen.
     */
    private static void greeting() {
        System.out.println("-------------------");
        System.out.println("Welcome to the bank");
        System.out.println("-------------------");
        System.out.println();
    }

    /**
     *  usage() displays instructions on using the command line arguments.
     */
    private static void usage() {
        System.out.println("Valid commands are: " +
                "open, deposit, withdraw, inquire, quit");
    }

    /**
     *  readLine() prints the given prompt and returns a string from the
     *  input stream.
     *  @param prompt is the string printed to prompt the user.
     */
    private String readLine(String prompt) throws IOException {
        System.out.print(prompt);
        System.out.flush();
        return bReader.readLine();
    }

    /**
     *  readInt() returns an integer from the input stream after prompting
     *  the user.
     *  @param prompt is the string printed to prompt the user.
     *  @return an int read from the user.
     */
    private int readInt(String prompt) throws IOException {
        String text = readLine(prompt);
        return Integer.valueOf(text).intValue();
    }
}