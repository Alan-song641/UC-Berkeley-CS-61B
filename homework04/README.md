### Homework04

主要的感想如下：

##### 1、关于override和overload：

声明中的signature不能改变！否则不是override（重写）! return的是它的子类。signature中不包括花括号里面的return，里面的return可以是return type的子类

与overload（重载）不同，重载可见lab04

##### 2、个人理解的dynamic type的作用之一

新加功能让这个node永久锁住！【这让我们知道如何新加功能】


```
public void lockNode(DListNode node)
```


cast：让父类转为子类,因为只有子类中LockDListNode中才有isLock属性

但是，如果用lockNode方法来处理。将那些需要lock的node由DListNode强制转换为LockDListNode.

 然后报错了【因为传入的DListNode其本质还是DListNode】...不是 LockDListNode.所以不能强制转换


```
((LockDListNode) node).isLock=true;
```



你new出来才有isLock这个field，如果new出来的还是父类，那就没有这个field了

如何改变new出来的值？利用重写（override）！！！！

也就是说node new出来的必须是它的子类，看的是它的dynamic type！

所以要重写（override）所有的与node中new相关的函数（newnode、constructor、insertfront、insertback）


##### 3、那么static type有什么用呢？

我们知道在LockDList中每次生成新的结点时，该结点必须都是LockDListNode类型!!(dynamic type）

之后，那些方法虽然传入的仍然是DListNode的static type，但其【本质】（new 出来的）已经全部变成了LockDListNode类型。

然后我们直接使用LockDList来完成我们需要的操作（测试code中创建的dynamic type必须是子类的！见TestLockDList中第九行）

同时依然用DListNode来接收相应操作返回的结点，其实此时这些结点的dynamic type已经发生了改变，但Java保证我们依然可以用父类来接收他们。

这个超类就是static type。即public void lockNode(DListNode node) 中括号里的东西，这样做的好处是：

##### 如果我们在父类基础上新加功能，不用影响到main中测试的代码，因为它接收用的static type为父类。只需要new出来的dynamic type为有相关功能的子类即可：Father father=new son(.....)；
