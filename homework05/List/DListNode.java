package com.song.homework05.List;

/* DListNode.java */

/**
 *  A DListNode is a mutable node in a DList (doubly-linked list).
 **/

public class DListNode extends ListNode {

    /**
     *  (inherited)  item references the item stored in the current node.
     *  (inherited)  myList references the List that contains this node.
     *  prev references the previous node in the DList.
     *  next references the next node in the DList.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     **/

    protected DListNode prev;
    protected DListNode next;

    /**
     *  DListNode() constructor.
     *  @param i the item to store in the node.
     *  @param l the list this node is in.
     *  @param p the node previous to this node.
     *  @param n the node following this node.
     */
    DListNode(Object i, DList l, DListNode p, DListNode n) {
        item = i;
        myList = l;
        prev = p;
        next = n;
    }

    public void setVertex1(DListNode node){  //用于project 3 edge指向vertex1
        this.vertex1=node;
    }

    public void setVertex2(DListNode node){  //用于project 3 edge指向vertex2 自环的vertex2为null
        this.vertex2=node;
    }

    public void setWeight(int weight){      //用于project 3
        this.weight=weight;
    }

    public void setPartner(DListNode partner){
        this.partner=partner;
    }

    public void setVertexInApp(Object o){
        this.VertexInApp=o;
    }

    public DListNode getVertex1(){  //用于project 3
        return this.vertex1;
    }

    public DListNode getVertex2(){  //用于project 3
        return this.vertex2;
    }

    public int getWeight(){      //用于project 3
        return this.weight;
    }

    public DListNode getPartner(){
        return this.partner;
    }

    public Object getVertexInApp(){
        return this.VertexInApp;
    }

    /**
     *  isValidNode returns true if this node is valid; false otherwise.
     *  An invalid node is represented by a `myList' field with the value null.
     *  Sentinel nodes are invalid, and nodes that don't belong to a list are
     *  also invalid.
     *
     *  @return true if this node is valid; false otherwise.
     *
     *  Performance:  runs in O(1) time.
     */
    public boolean isValidNode() {
        return myList != null;
    }

    /**
     *  next() returns the node following this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the node following this node.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode next() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("next() called on invalid node");
        }
        return next;
    }

    /**
     *  prev() returns the node preceding this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the node preceding this node.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode prev() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("prev() called on invalid node");
        }
        return prev;
    }

    /**
     *  insertAfter() inserts an item immediately following this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void insertAfter(Object item) throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("insertAfter() called on invalid node");
        }

        this.next=((DList)myList).newNode(item, (DList) myList,prev.next,next);
        this.next.next.prev=this.next;
        myList.size++;  //SList和DList中的size field都在List纯虚函数中

        //如果是以前，我们需要调用的时候会写 list.insertAfter(item,node);
        //如果list中没有那个node呢？？？
        //所以要直接改变数据结构的函数放在这个数据结构node里面，调用时写node.insertAfter(item);
        // 就不需要判断是否这个node在这个list里面了。因为调用函数不用知道它的list了！


        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what DList it's in.  You should use myList.newNode() to create the
        //   new node.
    }

    /**
     *  insertBefore() inserts an item immediately preceding this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void insertBefore(Object item) throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("insertBefore() called on invalid node");
        }

        this.prev=((DList)myList).newNode(item, (DList) myList,prev,next.prev);
        this.prev.prev.next=this.prev;
        myList.size++;  //SList和DList中的size field都在List纯虚函数中


        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what DList it's in.  You should use myList.newNode() to create the
        //   new node.
    }

    /**
     *  remove() removes this node from its DList.  If this node is invalid,
     *  throws an exception.
     *
     *  @exception InvalidNodeException if this node is not valid.
     *
     *  Performance:  runs in O(1) time.
     */
    public void remove() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException("remove() called on invalid node");
        }

        this.prev.next=this.next;  //lecture 19 中第二页的工作
        this.next.prev=this.prev;

        myList.size--;

        // Make this node an invalid node, so it cannot be used to corrupt myList.
        this.myList = null;//将这个node的mylist field设为空，即剔除出mylist范围

        // Set other references to null to improve garbage collection.
        next = null;
        prev = null;

        //太tm精妙了。不用l.remove（n）,而是将remove放在ListNode class里面，直接n.remove()即可


        // Your solution here.  Will look something like your Homework 4 solution,
        //   but changes are necessary.  For instance, there is no need to check if
        //   "this" is null.  Remember that this node's "myList" field tells you
        //   what DList it's in.





    }

}