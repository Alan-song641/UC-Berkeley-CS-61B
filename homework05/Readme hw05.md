###  Homework 05
  
重点在于Set.java 里面的实现是利用了链表这个结构实现的，在这个基础上保证：元素从小到大排列，不能有重复的元素，可进行并集交集插入等运算。

#### 先说链表数据结构的优化：

##### 1、myList的作用

之前List里面有一个ListNode head，指向对应的Node；现在在Node里面也有一个List myList的field，引用回对应的List，这样的好处是防止这个ListNode与List不对应的情况。

其次，如果我们删除一个Node，实际上还是会访问到它，所以现在remove（）时使这个node的myList=null;  如果其他method要调用这个Node，会throws一个InvalidNodeException

###### 【注意】

双向链表里面的sentinel也是invalid的，它的myList也是null。这样我们就可以保证head这个对外是访问不到的。也保证了当没有head时（例如单向链表）我们不用改变一些method的implementation（就是说如果一些method用到了sentinel，当换成SList还得改，不用sentinel就不用改）。

我们在做implementation时也要时刻注意链表头尾的访问不要越界，非常容易出错
例如：
```
setList.next().prev();
```
 如果ListNode到头了，setList.next()返回的就是head，那它的prev()就会throw Exception，不仔细检查真的很难发现！

##### 2、Protected的作用：


```
protected DListNode newNode(Object item, DList
list, DListNode prev, DListNode next)
```


这里是外面封装的函数 


      
```
return new DListNode(item, list, prev, next);
```
这里直接访问了数据结构

这里是直接访问数据结构，外面封装一层函数，其他函数（在package内的）要new 的话就访问函数，不要再直接访问(new)数据结构了 

【注意这里是protected！所以Set不能访问这个函数，只能访问下面是Public的函数（只要Public函数才是对【外】的接口）

##### 3、改变数据结构的method放在哪里好呢？

一般直接放在能直接访问数据结构的那个class里（见homework05 的readme）
例如：ListNode里面经常放一些可以【直接】修改数据结构的方法 
       
如果是以前，我们需要调用的时候会写 list.insertAfter(item,node); 
如果list中没有那个node呢？？？ 

所以要直接改变数据结构的函数放在这个数据结构node里面，比如我们将remove（）这个方法放在ListNode而不是List里面：调用时写node.insertAfter(item); 
就不需要判断是否这个node在这个list里面了。因为调用函数不用知道它的list了！ 

#### Set（集合）作为外部调用链表的类，一些关键点和算法：

##### 1、Set中维护的全局变量

或称field为父类中的纯虚函数List setList; ，它的子类分别为单向链表和双向链表
 由于纯虚函数没有构造器，所以在Set（）构造器的时候选择是DList还是SList 
 
【如果你哪天想改成SList，只要该这一处就好了】 

要尽量保证单向链表和双向链表的接口一样，做法就是将它们统一写一个父类纯虚函数，只有声明没有implementation，让子类去继承，从而有不同的实现！

【这就是纯虚函数 和 elegant的接口的目的！！】

##### 2、接口的隐私性再次强调

Set作为package外部函数，不能直接访问setlist.size！！ 看回List class里 size是protected声明

所以，要访问size，要用setList.length(), length就是访问size的接口。
最重要的是，避免了size的改变，如果编写length（）++；是错误的。不能改变return的值，只能读取。

##### 3、Comparable 接口


```
Comparable c;

c.compareTo(temp.item()) 成立
```


如果要用这个接口，需要cast一下，例如((Comparable) temp.item()).conparableTo.(c); 也是成立的

真正传进去的item是Integer，Integer implement了Comparable

同时Inherent了Object，所以Integer可以作为item传入ListNode 

这个时候item就具有了使用compareTo的特性，可以强制转为Comparable，然后使用compareTo。

##### 4、Union（求并集）的算法

ListNode thistemp = this.setList.front(); //如果setList里面一个node都没有，就不能输入这两行 ，所以空集要单独讨论
ListNode temp = s.setList.front();

由于两个set都是sorted的，所以第一个thistemp和第一个temp比，比thistemp大，下一个thistemp

若等于，输出中文字符，下一个thistemp 

若小于，那就是temp是没见过的，把它中的【item】作为new出来的node中的item插入this里面，下一个temp 

【没有重置thistemp！】比过的就不倒车了

###### 注意特殊情况：空集，两个集合元素数量不同的情况

最后改变的是this.的元素，而传入的参数（括号里的）set 不变化。

##### 5、Intersection（求交集）的算法

和上面相反，看这俩元素有没有相同的，如果有保留thistemp，没有的删除。

###### 注意特殊情况：空集，两个集合元素数量不同的情况 

最后改变的是this.的元素，而传入的参数（括号里的）set 不变化。

