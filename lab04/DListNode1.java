package com.song.lab04;

/**
 *  A DListNode1 is a node in a DList1 (doubly-linked list).
 */

public class DListNode1 {

    /**
     * item references the item stored in the current node.
     * prev references the previous node in the DList.
     * next references the next node in the DList.
     * <p>
     * DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    public int item;
    public DListNode1 prev;
    public DListNode1 next;

    /**
     * DListNode1() constructor.【 这个是重载（overload）！！】
     */
    DListNode1() {
        item = 0;
        prev = null;
        next = null;
    }

    DListNode1(int i) {
        item = i;
        prev = null;
        next = null;
    }
}
