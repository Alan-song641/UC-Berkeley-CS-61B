### Homework 07

花了三个小时，一个小时写，两个小时debug（手动微笑）

主要考验的是对数据结构的熟悉以及特殊情况的分析：

#### 只说特殊情况吧，主要有三点值得注意的地方：
##### 1、当root有三个key和当non-root 有三个key时有不同的解法

前者是root中间的key 不变，将两边的key各新创建一个node（left和right）；后者是 将node中间的key2提到parent里， 因为parent也是这么处理的，所以parent最多有两个key，可以有一个空位给key2插。【但是插完后parent有三个key了】只新创建一个node即可。

##### 2、root的parent问题

假设我们depth+1，那么原来root里面的child1-4（如果不是null的话）分别分配给新创建的left和right。但是它们的parent变量指向的还是原来的root，所以要重新分配。

##### 3、有点迷糊的一个点，也是花时间最长的一个地方在于
##### 【restructure的顺序和insert判断的顺序之间的关系】

比如说从root出发，一路上遇到三个key的进行restruct，然后在进行判断要insert的key应该在哪个child方向走。

但是注意这时node所在的中间点已经upstair到它的parent了，所以我们如果进行了restructure，我们要使node=node.parent。

回到parent重新选择要走的方向，直至到leaf。（insert一定在leaf上，restructure的目的就是确保我们插入leaf的时候，有空位给我们插入）


###### 3.1、这里有一个很特殊的情况在于如果parent原来有两个，restruct后变成了三个key。而我们回滚到parent判断时要插入的key比这三个key都大，因此我们要走child4，这个child4是必有的。（因为我们刚在下面restruct，原来有三个child，新建了个就是四个child）

