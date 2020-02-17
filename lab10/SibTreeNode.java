package com.song.lab10;

/* SibTreeNode.java */

/**
 *  A SibTreeNode object is a node in a SibTree (sibling-based general tree).
 *  @author Jonathan Shewchuk
 */

class SibTreeNode extends TreeNode {

    /**
     *  (inherited)  item references the item stored in this node.
     *  (inherited)  valid is true if and only if this is a valid node in some
     *               Tree.
     *  myTree references the Tree that contains this node.
     *  parent references this node's parent node.
     *  firstChild references this node's first (leftmost) child.
     *  nextSibling references this node's next sibling.
     *
     *  DO NOT CHANGE THE FOLLOWING FIELD DECLARATIONS.
     */

    /**
     *  ADT implementation invariants:
     *  1) if valid == true, myTree != null.
     *  2) if valid == true, then this is a descendent of myTree.root.
     *  3) if valid == true, myTree satisfies all the invariants of a
     *     SibTree (listed in SibTree.java).
     */

    protected SibTree myTree;
    protected SibTreeNode parent;
    protected SibTreeNode firstChild;
    protected SibTreeNode nextSibling;

    /**
     * Construct a valid SibTreeNode referring to a given item.
     */
    SibTreeNode(SibTree tree, Object item) {
        this.item = item;
        valid = true;
        myTree = tree;
        parent = null;
        firstChild = null;
        nextSibling = null;
    }

    /**
     * Construct an invalid SibTreeNode.
     */
    SibTreeNode() {
        valid = false;
    }

    /**
     *  children() returns the number of children of the node at this position.
     *  WARNING:  Does not run in constant time.  Actually counts the kids.
     */
    public int children() {
        if (isValidNode()) {
            int count = 0;
            SibTreeNode countNode = firstChild;
            while (countNode != null) {
                count++;
                countNode = countNode.nextSibling;
            }
            return count;
        } else {
            return 0;
        }
    }

    /**
     *  parent() returns the parent TreeNode of this TreeNode.  Throws an
     *  exception if `this' is not a valid node.  Returns an invalid TreeNode if
     *  this node is the root.
     */
    public TreeNode parent() throws InvalidNodeException {
        // REPLACE THE FOLLOWING LINE WITH YOUR SOLUTION TO PART I.

        if (isValidNode()) {
            if (this.parent == null) {
                return new SibTreeNode();
            } else {
                return this.parent;
            }
        } else {
            throw new InvalidNodeException();  //没有这个statement不会throw Exception的
        }

    }

    /**
     *  child() returns the cth child of this TreeNode.  Throws an exception if
     *  `this' is not a valid node.  Returns an invalid TreeNode if there is no
     *  cth child.
     */
    public TreeNode child(int c) throws InvalidNodeException {
        if (isValidNode()) {
            if (c < 1) {
                return new SibTreeNode();
            }
            SibTreeNode kid = firstChild;
            while ((kid != null) && (c > 1)) {
                kid = kid.nextSibling;
                c--;
            }
            if (kid == null) {
                return new SibTreeNode();
            } else {
                return kid;
            }
        } else {
            throw new InvalidNodeException();
        }
    }

    /**
     *  nextSibling() returns the next sibling TreeNode to the right from this
     *  TreeNode.  Throws an exception if `this' is not a valid node.  Returns
     *  an invalid TreeNode if there is no sibling to the right of this node.
     */
    public TreeNode nextSibling() throws InvalidNodeException {
        if (isValidNode()) {
            if (nextSibling == null) {
                return new SibTreeNode();
            } else {
                return nextSibling;
            }
        } else {
            throw new InvalidNodeException();
        }
    }

    /**
     *  insertChild() inserts an item as the cth child of this node.  Existing
     *  children numbered c or higher are shifted one place to the right
     *  to accommodate.  If the current node has fewer than c children,
     *  the new item is inserted as the last child.  If c < 1, act as if c is 1.
     *
     *  Throws an InvalidNodeException if "this" node is invalid.
     */
    public void insertChild(Object item, int c) throws InvalidNodeException {
        // FILL IN YOUR SOLUTION TO PART II HERE.

        if(isValidNode()) {

            SibTreeNode newTree = new SibTreeNode(myTree, item);
            newTree.parent = this;
            myTree.size++;

            //多个if判断时要看清是否有重合的地方！！！！！！！！！！！！！不然死循环（toString用了迭代）
            if (c <= 1||this.firstChild == null) {
                newTree.nextSibling=this.firstChild;
                this.firstChild = newTree;
                return; //不在这里停止的话，下面的会有重合的地方，
                // 当child数量为0，c为1的时候，下面的if情况也TMD符合，所以要加return
                //另一个更好的办法是将下面的if换成else if
            }

            if (c > this.children()) {
                //要改变Sibling的值，只能用.nextSibling 不能用.nextSibling();这个方法！
                //因为后者的method只能return，不能改变它的field！！你现在要改变它的field
                //error提示：需要“变量”，找到“值”
                //因此，child()返回的时候用了父类TreeNode,要cast成它的子类

                ((SibTreeNode) this.child(this.children())).nextSibling = newTree;

            } else {
                newTree.nextSibling = ((SibTreeNode) this.child(c));
                ((SibTreeNode) this.child(c - 1)).nextSibling = newTree;

            }

            }
        else{
            throw new InvalidNodeException();
        }
        
    }

    public int findchild() throws InvalidNodeException{
        if(isValidNode()&&this.parent!=null){
            int count=0;
            SibTreeNode find=this;
            while(find.nextSibling!=null){
                find=find.nextSibling;
                count++;
            }
            return this.parent.children()-count;
        }
        else{
            throw new InvalidNodeException();
        }
    }

    /**
     *  removeLeaf() removes the node at the current position from the tree if
     *  it is a leaf.  Does nothing if `this' has one or more children.  Throws
     *  an exception if `this' is not a valid node.  If 'this' has siblings to
     *  its right, those siblings are all shifted left by one.
     */
    public void removeLeaf() throws InvalidNodeException {
        // FILL IN YOUR SOLUTION TO PART III HERE.
        if(isValidNode()){
            if(this.children()==0){ //确定是不是leaf
                if(this.parent==null){ //如果this是root
                    this.valid=false;
                }
                else if(this.parent.firstChild==this){
                    this.parent.firstChild=this.nextSibling;
                    this.valid=false;
                }
                else{
                    ((SibTreeNode)parent.child(this.findchild()-1)).nextSibling=this.nextSibling;
                    this.valid=false;
                }
                myTree.size--;

            }
            else{
                return;
            }

        }
        else{
            throw new InvalidNodeException();
        }

    }

}
