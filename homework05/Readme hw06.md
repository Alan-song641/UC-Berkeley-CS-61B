### Homework06
 

其实主要考察的就是HashCode()和CompressionFunction()函数的实现及其作用：

###### 一句话概括
就是为了使得某种object（例如String类，SimpleBoard类）作为key可以尽可能离散地分布在Dictionary（由HashTable或SearchTree实现）中。

因此，hashcode与compression function函数的好坏主要由keys能否在buckets里面实现随机分布决定，loadfactor越大，collision越小，离散性越好。

#### 1、关于hashcode

对于不同的object，有不同的hashcode求法，尽可能使object在不同状态时，hashcode没有重复的数字，所以hashcode的求法最好范围比较大，事实上，在int 的范围内（±2^32）都可以，超过也可以考虑，因为java会将超出的高位截掉。

对于homework06而言，对于一个8*8的棋盘，每个点有三种状态（0,1,2）（空，白棋，黑棋），我们视棋盘的第1到第64个棋点分别为（k=1-64）,k设为权数。然后每个棋点视为以3为基数的digit。即3^k。

这样保证了每个不同的棋点的离散性很好。最后的整个棋盘的hashcode即为3^k乘上对应的棋点的状态（0,1,2）。超出int 的部分被截取掉了。

事实上这又有点blackart，不能用很严谨的说法说明这个的正确性，这也是离散令人着迷的地方。（同时说明学什么最后都是学数学TAT）


#### 2、关于CompressionFunction

【Compression Function的作用：】 
###### Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE to a value in the range 0...(size of hash table) - 1.

h(i) = |i| mod N，h(i) = ((ai + b) mod p) mod N)其实效果差不多，得到的结果都比较接近expected number，可能是样本量太小的原因。

##### 2.1 取质数的原因

因为被除和p如果有公约数c，则该公约数c也是余数r的约数，即r必须是c的倍数；这就限制了余数的分布

##### 2.2 ((ai + b) mod p) mod N)的合理性

Ar+b 在（B,Ar+B)这个长度为Ar的区间里，用它去%n会得到平均间隔为A的分布，相当于把原来在(0,r)里面 %n 的分布稀释了A倍。

然而，Ar和r形成了对r而言的【线性映射】，比如原来长度为r的区间里可能的取值为(0,1,2),长度为3；假设A＝2，B＝0,
那么映射到长度为3*2的区间里就是(0,2,4);给的空间大了，但是间隔也相应变大了，1,3,5这些值没有可能取到！

所以collision的概率是一样的。那么只要破坏这种线性映射就好了，取mod明显是非线性的，而且是mod质数。
 
##### 2.3 为什么p>>N？

因为mod p 就会映射到(0,p)，第二步mod n要从(0,p)映射到(0,n), 如果这两个区间大小差不多的话，就没有第二步的必要了，直接认为p就是n就可以了。

另外一个理由：因为要一直resize hash table, 通常就是翻倍, 所以N必须比P小很多。

#### 3、HashTable的结构

哈希表是由一个static type为List的Array，以及很多个链表（一般是单向链表）组成，Array的size由constructor来定，homework06里面给了两种方法。

其中Array中的每个项都指向一个List，这个一般是SList，所以可以直接用homework05中的SList和SListNode，在后面implementation时用了这两个class中的public method，非常的方便。让我们重新复习了Elegant Interface的重要性。

这里SList又可以视为Chain，SListNode中储存item（Entry类）和next（SListNode类），如果有collision，next指向新增的那个Node，否则指向null；

