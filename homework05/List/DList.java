package com.song.homework05.List;

/* DList.java */



/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel node at the head of the list.
 *
 *  DO NOT CHANGE ANY METHOD PROTOTYPES IN THIS FILE.
 **/

public class DList extends List {

    /**
     *  (inherited)  size is the number of items in the list.
     *  head references the sentinel node.
     *  Note that the sentinel node does not store an item, and is not included
     *  in the count stored by the "size" field.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATION.
     **/

    protected DListNode head;
    //这里只有一个field就是DListNode head  即sentinel
    //与之前的homework不同，size field移到了List（父类纯虚函数）中

    /* DList invariants:
     *  1)  head != null.
     *  2)  For every DListNode x in a DList, x.next != null.
     *  3)  For every DListNode x in a DList, x.prev != null.
     *  4)  For every DListNode x in a DList, if x.next == y, then y.prev == x.
     *  5)  For every DListNode x in a DList, if x.prev == y, then y.next == x.
     *  6)  For every DList l, l.head.myList = null.  (Note that l.head is the
     *      sentinel.)
     *  7)  For every DListNode x in a DList l EXCEPT l.head (the sentinel),
     *      x.myList = l.
     *  8)  size is the number of DListNodes, NOT COUNTING the sentinel,
     *      that can be accessed from the sentinel (head) by a sequence of
     *      "next" references.
     **/

    /**
     *  newNode() calls the DListNode constructor.  Use this method to allocate
     *  new DListNodes rather than calling the DListNode constructor directly.
     *  That way, only this method need be overridden if a subclass of DList
     *  wants to use a different kind of node.
     *
     *  @param item the item to store in the node.
     *  @param list the list that owns this node.  (null for sentinels.)
     *  @param prev the node previous to this node.
     *  @param next the node following this node.
     **/
    protected DListNode newNode(Object item, DList list,
                                DListNode prev, DListNode next) {//这里是外面封装的函数
        return new DListNode(item, list, prev, next);
        //这里是直接访问数据结构，外面封装一层函数，其他函数要new 的话就访问函数，不要再直接访问(new)数据结构了
        //【注意这里是protected！所以Set不能访问这个函数，只能访问下面是Public的函数！】
    }

    /**
     *  DList() constructs for an empty DList.
     **/
    public DList() {
        // Your solution here.  Similar to Homework 4, but now you need to specify
        //   the `list' field (second parameter) as well.

        //l.head.myList = null Note that l.head is the sentinel.
        head=newNode(null,null, null, null); //这里prev和next不能写head因为你还没创建呢
        head.next=head;
        head.prev=head;

        size=0;


    }

    /**
     *  insertFront() inserts an item at the front of this DList.
     *
     *  @param item is the item to be inserted.
     *
     *  Performance:  runs in O(1) time.
     **/
    public void insertFront(Object item) {
        // Your solution here.  Similar to Homework 4, but now you need to specify
        //   the `list' field (second parameter) as well.

        if(size==0) {
            head.prev=newNode(item,this,head,head); //创建的新node所属的myList为【this】
            head.next=head.prev;

        }
        else{
            head.next=newNode(item,this,head,head.next);
            head.next.next.prev=head.next;
        }
        size++;
    }

    /**
     *  insertBack() inserts an item at the back of this DList.
     *
     *  @param item is the item to be inserted.
     *
     *  Performance:  runs in O(1) time.
     **/
    public void insertBack(Object item) {
        // Your solution here.  Similar to Homework 4, but now you need to specify
        //   the `list' field (second parameter) as well.

        if(size==0) {
            head.next=newNode(item,this,head,head); //创建的新node所属的myList为【this】
            head.prev=head.next;

        }
        else{
            head.prev=newNode(item,this,head.prev,head);
            head.prev.prev.next = head.prev;
        }
        size++;

    }

    /**
     *  front() returns the node at the front of this DList.  If the DList is
     *  empty, return an "invalid" node--a node with the property that any
     *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
     *
     *  DO NOT CHANGE THIS METHOD.
     *
     *  @return a ListNode at the front of this DList.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode front() {
        return head.next;
    }

    /**
     *  back() returns the node at the back of this DList.  If the DList is
     *  empty, return an "invalid" node--a node with the property that any
     *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
     *
     *  DO NOT CHANGE THIS METHOD.
     *
     *  @return a ListNode at the back of this DList.
     *
     *  Performance:  runs in O(1) time.
     */
    public ListNode back() {
        return head.prev;
    }

    /**
     *  toString() returns a String representation of this DList.
     *
     *  DO NOT CHANGE THIS METHOD.
     *
     *  @return a String representation of this DList.
     *
     *  Performance:  runs in O(n) time, where n is the length of the list.
     */
    public String toString() {
        String result = "[  ";
        DListNode current = head.next;
        while (current != head) {
            result = result + current.item + "  ";
            current = current.next;
        }
        return result + "]";
    }

    private static void testInvalidNode(ListNode p) {
        System.out.println("p.isValidNode() should be false: " + p.isValidNode());
        try {
            p.item();
            System.out.println("p.item() should throw an exception, but didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.item() should throw an exception, and did.");
        }
        try {
            p.setItem(new Integer(0));
            System.out.println("p.setItem() should throw an exception, but didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.setItem() should throw an exception, and did.");
        }
        try {
            p.next();
            System.out.println("p.next() should throw an exception, but didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.next() should throw an exception, and did.");
        }
        try {
            p.prev();
            System.out.println("p.prev() should throw an exception, but didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.prev() should throw an exception, and did.");
        }
        try {
            p.insertBefore(new Integer(1));
            System.out.println("p.insertBefore() should throw an exception, but " +
                    "didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.insertBefore() should throw an exception, and did."
            );
        }
        try {
            p.insertAfter(new Integer(1));
            System.out.println("p.insertAfter() should throw an exception, but " +
                    "didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.insertAfter() should throw an exception, and did."
            );
        }
        try {
            p.remove();
            System.out.println("p.remove() should throw an exception, but didn't.");
        } catch (InvalidNodeException lbe) {
            System.out.println("p.remove() should throw an exception, and did.");
        }
    }

    private static void testEmpty() {
        List l = new DList();
        System.out.println("An empty list should be [  ]: " + l);
        System.out.println("l.isEmpty() should be true: " + l.isEmpty());
        System.out.println("l.length() should be 0: " + l.length());
        System.out.println("Finding front node p of l.");
        ListNode p = l.front();
        testInvalidNode(p);
        System.out.println("Finding back node p of l.");
        p = l.back();
        testInvalidNode(p);
        l.insertFront(new Integer(10));
        System.out.println("l after insertFront(10) should be [  10  ]: " + l);
    }

    public static void main(String[] argv) {
        testEmpty();
        List l = new DList();
        l.insertFront(new Integer(3));
        l.insertFront(new Integer(2));
        l.insertFront(new Integer(1));
        System.out.println("l is a list of 3 elements: " + l);
        try {
            ListNode n;
            int i = 1;
            for (n = l.front(); n.isValidNode(); n = n.next()) {
                System.out.println("n.item() should be " + i + ": " + n.item());
                n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
                System.out.println("n.item() should be " + 2 * i + ": " + n.item());
                i++;
            }
            System.out.println("After doubling all elements of l: " + l);
            testInvalidNode(n);

            i = 6;
            for (n = l.back(); n.isValidNode(); n = n.prev()) {
                System.out.println("n.item() should be " + i + ": " + n.item());
                n.setItem(new Integer(((Integer) n.item()).intValue() * 2));
                System.out.println("n.item() should be " + 2 * i + ": " + n.item());
                i = i - 2;
            }
            System.out.println("After doubling all elements of l again: " + l);
            testInvalidNode(n);

            n = l.front().next();
            System.out.println("Removing middle element (8) of l: " + n.item());
            n.remove();
            System.out.println("l is now: " + l);
            testInvalidNode(n);
            n = l.back();
            System.out.println("Removing end element (12) of l: " + n.item());
            n.remove();
            System.out.println("l is now: " + l);
            testInvalidNode(n);

            n = l.front();
            System.out.println("Removing first element (4) of l: " + n.item());
            n.remove();
            System.out.println("l is now: " + l);
            testInvalidNode(n);
        } catch (InvalidNodeException lbe) {
            System.err.println ("Caught InvalidNodeException that should not happen."
            );
            System.err.println ("Aborting the testing code.");
        }
    }
}
