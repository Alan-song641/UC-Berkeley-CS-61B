### Project 03

#### 【part 1】：

生成一个Weighted Undirected Graph

重点在于理解搞这么复杂数据结构的意义，是为了保证时间复杂度在一定范围内，只要理解了数据结构。其实现就不难。

对于实现你需要进行Augmenting Data Structure，满足readme中的reference指向。

例如EdgeNode中新加的Vertex1和Vertex2 这两个field指向这个edge的两端的Vertex的Node；或是VertexList中node的指向VertexInApp的reference，还需加上它们的getter和setter。如图（文件夹中的png）是我加的一些笔记：（V是Vertex的缩写）


因为利用了homework05中的链表（DList和DListNode），上面的Augment是写在了它的父类（ListNode）中。

#### 【part 2】：
利用Kruskal算法求最小生成树（Minimum Spanning Tree）

1、
因为VertexPair类是protected的，不能被package外引用。所以我自己写了个myPair类，里面维护着vertex1 vertex2和weight三个field。

2、
其次利用了homework08中的QuickSort算法，将myPair enqueue到LinkedQueue中，进行quicksort，因为quicksort是compare-based的算法，所以myPair类的签名需要implement Comparable，并override
compareTo算法（只compare里面的weight即可）。

3、
最后一部分是利用并查集（DisjointSet）来实现，和homework09一样，find两个vertex，如果他们的parent不是同一个，说明不在同一个set里，两个vertex增加edge，因为是edge weight从小到大进行判断，所以保证了生成的树为最小。

4、
还有一个问题在于Vertex 类是object，并查集是通过Array实现的，所以需要将每个Vertex映射（map）一个独一无二的int，最好的办法是通过Dictionary（key+value），字典通过HashTable实现最方便。