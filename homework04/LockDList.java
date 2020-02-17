package com.song.homework04;

public class LockDList extends DList{




    //声明中的signature不能改变！否则不是override（重写）! return的是它的子类
    //与overload（重载）不同，重载可见lab04.
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
       return new LockDListNode(item, prev, next);

    }

    //    有一点要补充的是，newnode（）的作用：
//    如果在insertFront（） 和insertBack（）函数中都用newnode（）函数而不是用DListNode的constructor，
//    即
//    head.prev = newNode(item, head, head);
//      而不是
//    head.prev=new DListNode(item, head, head);
//
//    重写时就不用重写insertFront（） 和insertBack（）函数，只用一个newnode（）函数就解决了！！！
//
//    这就是为什么要将constructor封装进newnode的作用！！因为constructor不能被override！


//    public void insertFront(Object item) {
//        if(size==0) {
//            head.prev= new LockDListNode(item,head,head);
//            head.next=head.prev;
//            size = 1;
//        }
//        else{
//            head.next=new LockDListNode(item,head,head.next);
//            head.next.next.prev=head.next;
//            size++;
//        }
//
//    }
//
//
//    public void insertBack(Object item) {
//        // Your solution here.
//        if (size == 0) {
//            head.prev = new LockDListNode(item, head, head);
//            head.next = head.prev;
//            size = 1;
//        } else {
//            head.prev = new LockDListNode(item, head.prev, head);
//            head.prev.prev.next = head.prev;
//            size++;
//        }
//    }

  //新加功能让这个node永久锁住！【这让我们知道如何新加功能】
    public void lockNode(DListNode node) {
         //cast：让父类转为子类,因为只有子类中LockDListNode中才有isLock属性
        //但是，如果用lockNode方法来处理。将那些需要lock的node由DListNode强制转换为LockDListNode.
        // 然后报错了【因为传入的DListNode其本质还是DListNode】...不是 LockDListNode.所以不能强制转换
            ((LockDListNode) node).isLock=true;

        //所以必须想一种方法，在LockDList中每次生成新的结点时
        // 该结点必须都是LockDListNode类型!!(dynamic type)然后返回值是DListNode,从而在返回过程中完成向上转型。
        // 之后，那些方法虽然传入的仍然是DListNode类型，但其【本质】（new 出来的）已经全部变成了LockDListNode类型。

        //你new出来才有isLock这个field，如果new出来的还是父类，那就没有这个field了
        //如何改变new出来的值？利用重写！！！！

        // 也就是说node new出来的必须是它的子类，看的是它的dynamic type！
        // 所以要重写（override）所有的与node中new相关的函数

        //我们不需要知道LockDListNode到底是怎么实现的，甚至不用知道LockDListNode这个类的名字。这些都有人写好。
        // 然后我们直接使用LockDList来完成我们需要的操作（测试code中创建的dynamic type必须是子类的！见TestLockDList中第九行）
        // 同时依然用DListNode来接收相应操作返回的结点，其实此时这些结点的dynamic type已经发生了改变，但Java保证我们依然可以用超类来接收他们。
        //
    }

    /**
     *  remove() removes "node" from this DList.  If "node" is null, do nothing.
     *  Performance:  runs in O(1) time.
     */

    //还是那句话，signature不能改变！
    public void remove(DListNode node) {
        // Your solution here.

        if(node==null||((LockDListNode)node).isLock==true){
            return;
        }
        else{
            node.prev.next=node.next;
            node.next.prev=node.prev;
            size--;
        }
    }

}
