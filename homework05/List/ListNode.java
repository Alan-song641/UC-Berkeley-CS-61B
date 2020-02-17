package com.song.homework05.List;

/* ListNode.java */



/**
 *  A ListNode is a mutable node in a list.  No implementation is provided.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public abstract class ListNode {

    /**
     * ListNode里面经常放一些可以【直接】修改数据结构的方法
     * 例如，如果我们将remove（）这个方法放在listnode而不是list里面
     * 我们可以避免调用的node不在this.list里面的情况（直接见DListNode里面的remove函数）
     *
     *  item references the item stored in the current node.
     *  myList references the List that contains this node.
     *  【myList指向包含这个node的List class】
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    protected Object item;
    protected List myList;  //之前List里有Node的field，现在Node反过来有一个List的field了
                            // 注意是父类，使用时转为子类先

    protected int weight;  //用于project 3 EdgeList中
    protected DListNode vertex1; //用于project 3 EdgeList中
    protected DListNode vertex2; //用于project 3 EdgeList中
    protected DListNode partner; //用于project 3 EdgeList中

    protected Object VertexInApp;  //用于project 3 vertexList中

    public abstract void setVertex1(DListNode node);  //用于project 3 edge指向vertex1
    public abstract void setVertex2(DListNode node);  //用于project 3 edge指向vertex2 自环的vertex2为vertex1
    public abstract void setWeight(int weight);      //用于project 3 edge
    public abstract void setPartner(DListNode partner);//用于project 3 edge
    public abstract void setVertexInApp(Object o);  //用于pj3 vertex

    public abstract DListNode getVertex1();  //用于project 3
    public abstract DListNode getVertex2(); //用于project 3
    public abstract int getWeight();      //用于project 3
    public abstract DListNode getPartner();//上面四个都是pj 3 的edge node
    public abstract Object getVertexInApp();//这个是vertex node


    /**
     *  isValidNode returns true if this node is valid; false otherwise.
     *  By default, an invalid node is one that doesn't belong to a list (myList
     *  is null), but subclasses can override this definition.
     *
     *  @return true if this node is valid; false otherwise.
     *
     *  Performance:  runs in O(1) time.
     */
    public boolean isValidNode() {
        return myList != null;
    }

    /**
     *  item() returns this node's item.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the item stored in this node.
     *
     *  Performance:  runs in O(1) time.
     */
    public Object item() throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException();
        }
        return item;
    }

    /**
     *  setItem() sets this node's item to "item".  If this node is invalid,
     *  throws an exception.
     *
     *  Performance:  runs in O(1) time.
     */
    public void setItem(Object item) throws InvalidNodeException {
        if (!isValidNode()) {
            throw new InvalidNodeException();
        }
        this.item = item;
    }

    /**
     *  next() returns the node following this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @return the node following this node.
     *  @exception InvalidNodeException if this node is not valid.
     */
    public abstract ListNode next() throws InvalidNodeException;

    /**
     *  prev() returns the node preceding this node.  If this node is invalid,
     *  throws an exception.
     *
     *  @para Node node whose predecessor is sought.
     *  @return the node preceding this node.
     *  @exception InvalidNodeException if this node is not valid.
     */
    public abstract ListNode prev() throws InvalidNodeException;

    /**
     *  insertAfter() inserts an item immediately following this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     */
    public abstract void insertAfter(Object item) throws InvalidNodeException;

    /**
     *  insertBefore() inserts an item immediately preceding this node.  If this
     *  node is invalid, throws an exception.
     *
     *  @param item the item to be inserted.
     *  @exception InvalidNodeException if this node is not valid.
     */
    public abstract void insertBefore(Object item) throws InvalidNodeException;

    /**
     *  remove() removes this node from its List.  If this node is invalid,
     *  throws an exception.
     *
     *  @exception InvalidNodeException if this node is not valid.
     */
    public abstract void remove() throws InvalidNodeException;

}