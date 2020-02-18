#### Homework 03

##### 第一点
要明白这行代码的意义

```
node.next = new SListNode(node.item, node.next);
```


要理解的点在于这里面第一个next和第二个next的意义是不一样的，视频中的old value 和new value的区别（要从右往左看）

##### 第二点
明白SLink这个的意义，里面的head是SListNode类的对象，它表示永远指向第一个列表节点，head和size封装，相当于间接让人们获取列表，解决了两个问题（见上课的hard copy）

##### 第三点
原函数SList中的InsertEnd mothod可以利用for循环 就可以将数组转变成SListNode的type。其实我觉得加上for循环独立封装一个函数叫“ArrayConvertList”会不会更好。