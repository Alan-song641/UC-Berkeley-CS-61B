package com.song.lab11;

/* BinaryTreeNode.java */



/**
 *  BinaryTreeNode represents a node in a binary tree.
 *
 *  DO NOT CHANGE THIS FILE.
 **/
class BinaryTreeNode {

    /**
     *  entry is a (key, value) pair stored in this node.
     *  parent is the parent of this node.
     *  leftChild and rightChild are the children of this node.
     **/
    Entry entry;
    BinaryTreeNode parent;
    BinaryTreeNode leftChild, rightChild;

    /**
     *  Construct a BinaryTreeNode with a specified entry; parent and children
     *  are null.
     **/
    BinaryTreeNode(Entry entry) {
        this(entry, null, null, null);
    }

    /**
     *  Construct a BinaryTreeNode with a specified entry and parent; children
     *  are null.
     **/
    BinaryTreeNode(Entry entry, BinaryTreeNode parent) {
        this(entry, parent, null, null);
    }

    /**
     *  Construct a BinaryTreeNode, specifying all four fields.
     **/
    BinaryTreeNode(Entry entry, BinaryTreeNode parent,
                   BinaryTreeNode left, BinaryTreeNode right) {
        this.entry = entry;
        this.parent = parent;
        leftChild = left;
        rightChild = right;
    }

    /*
        找以这个node为root，它的子树的具有key最小值的node，并返回它
        基本思想就是一直查leftTree，直至leftTree==null;
     */
    public BinaryTreeNode min(){
        BinaryTreeNode node=this;

        if(node.leftChild==null){
            return node;
        }
        else{
            node=node.leftChild;
            node.min();
        }

        return node;
    }

    /*
        找以这个node为root，它的子树的具有key最大值的node，并返回它
        与上面相反
     */
    public BinaryTreeNode max(){
        BinaryTreeNode node=this;

        if(node.rightChild==null){
            return node;
        }
        else{
            node=node.rightChild;
            node.max();
        }

        return node;
    }

    /**
     *  Express a BinaryTreeNode as a String.
     *
     *  @return a String representing the BinaryTreeNode.
     **/
    public String toString() {
        String s = "";

        if (leftChild != null) {
            s = "(" + leftChild.toString() + ")";
        }
        s = s + entry.key().toString() + entry.value();
        if (rightChild != null) {
            s = s + "(" + rightChild.toString() + ")";
        }
        return s;
    }
}
