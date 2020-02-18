### Homework 09

##### 主要思想是如何利用Disjoint Set来进行应用

在这里，各个wall都是独立的item，且没有重复。所以满足并查集的要求。

1、将编好号的wall进行随机排列（如果按顺序排列呢？见project 03）

2、随机地取某一个wall（vertical或者horizontal），这个wall同时表示了它上方（horiz）或左方（vert）的cell。如果两边（左右）或者上下的cell的parent不是同一个的话，说明它们不属于同一个disjointset，即没有通路，将这个wall设为false。

3、最后用DFS来看是否是一个valid 的maze。事实上走maze的过程就是DFS的过程。

注意的是wall编号的时候，如何表达不同种类的wall（竖着还是横着）用正负表示，以及如何将编号后的wall转换为它的位置（用取余和整除），见code