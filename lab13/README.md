### Lab 13

part2的思想和part1类似，只是加上了recursion。

因此每个迭代的level就是一个BFS的level。但是和lecture28中的BFS实现不一样，DFS才需要使用迭代，而BFS一般用while循环配合queue，然后loop每个刚标为visited的vertices。

这里不需要while和queue，而且是无向图，只需要recurse length即可。

个人认为是recursion部分值得学习，利用的精华在于：

##### public UDGraph paths(int length)的返回值是UDGraph itself
所以我们可以应用path方法的返回值（UDGraph类）的方法进行迭代，迭代至length=2，就可以使用part I中的 length2Paths()方法了。

```
paths(length-1).hasEdge(i, j)
```


忽略了它的返回值，导致想了半天

它的return值 若某两点（i和j）之间有edge的话，edge的长度为length-1，我们再用part I的办法判断j和所有的vertices k 比较就能得出length为“length”的edge（i和k）了

另外想知道part I有什么更好的loop方法，目前只能想到三层的loop！