### Lab06


##### 举例说明BadAccountException的传递过程：

在virtualTeller函数中，调用了findaccount函数


```
if(account==null)
        {
            throw new BadAccountException(acct);
          }
```


一切从源头发现账号不存在开始，若account==null
//在findaccount函数中，丢出一个new BadAccountException（记住是new了一个出来）

在声明中加throws关键词

在virtualTeller函数中，deposit, withdraw,balanceInquiry函数调用了findaccount函数
每个运用到这个函数都要传递一下，加“throws”关键词
例如


```
public int balanceInquiry(int acct) throws
BadAccountException {
```



在BankApp函数中，dodeposit，dowithdraw，doinquire调用了上面的三个函数
//每个运用到这个函数都要传递一下，加“throws”关键词，例如：


```
private void doInquire() throws
IOException, BadAccountException
```



最后在main函数中，注意整体有个while函数，使得若输入错误继续进行。

利用try-catch函数接住throw出的exception，让它不结束到结尾，如果到了结尾还没接到，程序中断。


```
try {
    bankApp.doDeposit();
} catch (BadAccountException e) {
    System.out.println(e);
}
```



##### 总结：
从函数要判断的源头开始进行丢出exception，多用if语句：if（something wrong）{throw new XXXException}，如果花括号里面的执行了，那么下面的code直接跳出，不执行。

最终在main函数中用try和catch函数，确保catch住后，可以继续while循环。

设计得很好，try和catch的作用在于当发现exception时，不退出，而是继续下去这个while循环。

而throws 语句就是将exception不断地从调用函数抛向main函数。