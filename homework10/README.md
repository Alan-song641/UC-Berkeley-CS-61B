### Homework10

主要是利用**radix sort** 进行排序，利用了LSD的算法（Least-Significant digit first）从低位到高位排序，每一位用counting sort来排。


**counting sort**涉及到一些bit operation，与& 操作和移位<< >>操作，取到某一位的数字进行counting sort
radix sort就是迭代地利用counting sort函数得到最终结果。

另外就是int 32位里面16进制和2进制的关系要弄清楚，其他的根据讲义上的来填即可。相当于复习了两种sorting 方法。