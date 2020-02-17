package com.song.homework04;

public class LockDListNode extends DListNode {

    //Added field 多加一个para判断是否lock住 【这让我们知道如何新加功能】
    protected boolean isLock;


    /**
     * DListNode() constructor.
     *
     * @param i the item to store in the node.
     * @param p the node previous to this node.
     * @param n the node following this node.
     *          【这三个para已经在父类中继承】
     */
    LockDListNode(Object i, DListNode p, DListNode n) {
        super(i,p,n);  //父类的构造器不能被继承，但必须被子类构造器调用！
        isLock=false;  //刚开始创建时不锁
    }
}
