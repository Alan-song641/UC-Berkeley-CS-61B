package com.song.homework05;

/* Set.java */

import com.song.homework05.List.*;

/**
 *  A Set is a collection of Comparable elements stored in sorted order.
 *  you will keep them sorted in order from least to greatest element. 即：从小到大排列
 *  Duplicate elements are not permitted in a Set. 不能有重复的元素
 **/
public class Set {
    /* Fill in the data fields here. */
        protected List setList;  //集合中用的数据结构为父类中的纯虚函数List


    /**
     * Set ADT invariants:
     *  1)  The Set's elements must be precisely the elements of the List.
     *  2)  The List must always contain Comparable elements, and those elements
     *      must always be sorted in ascending order. 以增加的顺序
     *  3)  No two elements in the List may be equal according to compareTo().
     **/

    /**
     *  Constructs an empty Set.
     *
     *  Performance:  runs in O(1) time.
     **/
    public Set() {
        // Your solution here.
        // 由于纯虚函数没有构造器，所以作者想让你在这步选择是DList还是SList
        //【如果你哪天想改成SList，只要该这一处就好了！！！】
        //【这就是纯虚函数 和 elegant的接口的目的！！！！！！！！！！！！】
        setList=new DList();
    }

    /**
     *  cardinality() returns the number of elements in this Set.
     *
     *  Performance:  runs in O(1) time.
     **/
    public int cardinality() {
        // Replace the following line with your solution.
        // Set作为package外部函数，不能直接访问setlist.size！！
        //看回List class里 size是protected声明

        return setList.length(); //这就是为什么要加length（）函数的原因。
    }

    /**
     *  insert() inserts a Comparable element into this Set.
     *
     *  Sets are maintained in sorted order.  The ordering is specified by the
     *  compareTo() method of the java.lang.Comparable interface.
     *
     *  Performance:  runs in O(this.cardinality()) time.
     **/
    public void insert(Comparable c)  {
        // Your solution here.
        //你真正传进去的是Integer，Integer implement了Comparable
        // 同时Inherent了Object，所以Integer可以作为item传入ListNode
        // 这个时候item就具有了使用compareTo的特性，可以强制转为Comparable，然后使用compareTo。

        if(setList.isEmpty()){
            setList.insertFront(c);
        }
        else{
            ListNode temp=setList.front();
            try{
                while(temp!=setList.back().next()){
                    if(c.compareTo(temp.item())==0){

                        return;
                    }
                    else if(c.compareTo(temp.item()) > 0){  //c大于temp.item
                        if(temp!=this.setList.back()){
                            //如果有下一个才下一个
                            temp = temp.next();
                        }
                        else{
                            this.setList.insertBack(c); //插尾
                            temp=temp.next();
                        }
                    }
                    else{
                        temp.insertBefore(c); //这里面已经有size++了
                        //事实上，如果你输入setList.length()++;会报错
                        // 因为我们建立接口是只读状态（不能改变return的值）
                        // 这就防止了直接引用数据结构造成的corruption
                        return;
                    }

                }

            }
            catch(InvalidNodeException e){
                System.out.println(e);
            }
        }


    }

    /**
     *  union() 求并集【modifies "this"" Set】 so that it contains all the elements it
     *  started with, plus all the elements of s.  The Set s is NOT modified.
     *  Make sure that duplicate elements are not created.
     *
     *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     *
     *  Your implementation should NOT copy elements of s or "this", though it
     *  will copy _references_ to the elements of s.  【Your implementation will
     *  create new _nodes_ for the elements of s that are added to "this", but
     *  you should reuse the nodes that are already part of "this".】
     *
     *  DO NOT MODIFY THE SET s.
     *  DO NOT ATTEMPT TO COPY ELEMENTS; just copy _references_ to them.
     **/
    public void union(Set s)throws InvalidNodeException {
        // Your solution here.
        //算法：由于两个set都是sorted的，所以第一个thistemp和第一个temp比，比thistemp大，下一个thistemp
        //若等于，输出中文字符，下一个thistemp
        //若小于，那就是temp是没见过的，把它中的【item】作为new出来的node中的item插入this里面，下一个temp
        //【没有重置thistemp！】比过的就不倒车了

        //try {
        if (s.setList.isEmpty()) {
            return;
        }
        if (this.setList.isEmpty()&&!s.setList.isEmpty()) {
            ListNode temp = s.setList.front(); //如果s.setList为空的话就不能用这句
            while (temp!= s.setList.back().next()) {
                this.setList.insertBack(temp.item());
                temp = temp.next();
            }

        } else {
            ListNode thistemp = this.setList.front();  //如果setList里面一个node都没有，就不能输入这两行
            ListNode temp = s.setList.front();

            while(temp!=s.setList.back().next()){   //循环完所有s集合中的元素

                // while循环里少写了 s. ！！所以思路一定要清晰！！这两个temp很容易搞乱！！

                Comparable c= (Comparable) temp.item();

                if (c.compareTo(thistemp.item())==0) {

                    if(thistemp!=this.setList.back()){
                    //如果有下一个才下一个，避免set s里面元素比this.set多的情况
                    thistemp = thistemp.next();
                    }
                    temp=temp.next();

                } else if (c.compareTo(thistemp.item()) > 0) {  //c大于thistemp.item
                    if(thistemp!=this.setList.back()) {
                        thistemp = thistemp.next();
                    }
                    else{ //表示thistemp到头了,temp还是比它大
                        this.setList.insertBack(c); //插尾
                        temp=temp.next();
                    }

                } else {
                    thistemp.insertBefore(c);
                    //把Set s里面的没有的元素插进去
                    //别忘了这是new出来的，没有用Set s里面的元素,只是copy了里面的item

                    temp=temp.next();  //判断完后，判断下一个Set s里面的元素
                }

            }

        }
       // }
        //catch(InvalidNodeException e){
         //       System.out.println(e);
       // }
    }


    /**
     *  intersect() 求交集 【modifies this Set】 so that it contains the intersection of
     *  its own elements and the elements of s.  The Set s is NOT modified.
     *
     *  Performance:  Must run in O(this.cardinality() + s.cardinality()) time.
     *
     *  Do not construct any new ListNodes during the execution of intersect.
     *  Reuse the nodes of "this" that will be in the intersection.
     *
     *  DO NOT MODIFY THE SET s.
     *  DO NOT CONSTRUCT ANY NEW NODES.
     *  DO NOT ATTEMPT TO COPY ELEMENTS.
     **/
    public void intersect(Set s) throws InvalidNodeException {
        // Your solution here.
        //算法：和上面相反，看这俩元素有没有相同的，如果有保留thistemp，没有的删除
        //try {
        if (s.setList.isEmpty()) {
            while(!this.setList.isEmpty()){
                ListNode temp=this.setList.front();
                temp.remove();
            }
            return;

        }
        if (this.setList.isEmpty()) {
            return;
        }

        ListNode thistemp = this.setList.front();  //如果setList里面一个node都没有，就不能输入这两行
        ListNode temp = s.setList.front();

        while (thistemp!=this.setList.back().next()){//循环完所有this集合中的元素

            Comparable c= (Comparable) temp.item();

            if (c.compareTo(thistemp.item())==0) {

                thistemp = thistemp.next(); //这个this判断完了，下一个this
                                            //这里不用if判断是否到头, 这个while循环条件是循环完this元素就跳出
                if(temp!=s.setList.back()) {
                    temp = temp.next();
                }
                //相当于啥都不干，保留

            }
            else if(c.compareTo(thistemp.item())>0) {  //this比c小

                ListNode localtemp = thistemp.next();
                thistemp.remove();
                thistemp=localtemp;
            }
            else{                                       //this比c大
                    if(temp==s.setList.back()) {        //如果到头了，说明this这个元素比所有的c都大，删掉它
                        ListNode localtemp = thistemp.next();     //这个this判断完了，下一个this
                                                        //这里不用if判断是否到头, 这个while循环条件是循环完this元素
                        thistemp.remove();
                        thistemp=localtemp;
                    }
                    else{
                        temp=temp.next();
                    }
                }

            }
        }


//        }
//        catch(InvalidNodeException e){
//            System.out.println(e);
//        }



    /**
     *  toString() returns a String representation of this Set.  The String must
     *  have the following format:
     *    {  } for an empty Set.  No spaces before "{" or after "}"; two spaces
     *            between them.
     *    {  1  2  3  } for a Set of three Integer elements.  No spaces before
     *            "{" or after "}"; two spaces before and after each element.
     *            Elements are printed with their own toString method, whatever
     *            that may be.  The elements must appear in sorted order, from
     *            lowest to highest according to the compareTo() method.
     *
     *  WARNING:  THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
     *            FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS.  ANY
     *            DEVIATIONS WILL LOSE POINTS.
     **/
    public String toString() { //这里不能throws Exception因为它的super原型是String class，没有throws
       // Replace the following line with your solution.
           String result = "{  ";
           if(setList.isEmpty())
           {
               result=result+" ";
           }
           else {
               ListNode current = this.setList.front(); //如果set集合为空，就不能用这句！
               try{
                   while (current != setList.back().next())
                   {
                       result = result + current.item() + "  ";   //也不能用这句
                       current = current.next();                  //也不能用这句
                       // 因为head是不可以访问的（head的mylist为null）
                   }
               }
               catch(InvalidNodeException e){
                System.out.println(e);
                }
           }
        return result + "}";
}



    public static void main(String[] argv) throws InvalidNodeException {
        System.out.println("Testing insert()");
        Set s = new Set();
        s.insert(new Integer(3));
        s.insert(new Integer(4));
        s.insert(new Integer(3));
        System.out.println("Set s should be { 3 4 }: " + s);

        Set s2 = new Set();
        s2.insert(new Integer(4));
        s2.insert(new Integer(5));
        s2.insert(new Integer(5));
        System.out.println("Set s2 should be { 4 5 }: " + s2);

        Set s3 = new Set();
        s3.insert(new Integer(5));
        s3.insert(new Integer(3));
        s3.insert(new Integer(8));
        System.out.println("Set s3 should be { 3 5 8 }: " + s3);

        System.out.println();
        System.out.println("Tesing union()");
        s.union(s2);
        System.out.println("After s.union(s2), s should be { 3 4 5 }: " + s);
        s2.union(s3);
        System.out.println("After s2.union(s3), s2 should be { 3 4 5 8 }: " + s2);
        Set s4 = new Set();
        System.out.println("Empty set s4 = " + s4);
        s.union(s4);
        System.out.println("After s.union(s4), s should be { 3 4 5 }: " + s);
        s4.union(s);
        System.out.println("After s4.union(s), s4 should be { 3 4 5 }: " + s4);

        System.out.println();
        System.out.println("Tesing intersect()");
        Set s5 = new Set();
        Set s6 = new Set();
        s6.insert(new Integer(1));
        s5.intersect(s6);
        System.out.println("{}.intersect({1}) should be { }: " + s5);
        s6.intersect(s5);
        System.out.println("{1}.intersect({}) should be { }: " + s6);
        s6.insert(new Integer(1));
        Set s7 = new Set();
        s7.insert(new Integer(1));
        s7.insert(new Integer(2));
        s6.intersect(s7);
        System.out.println("{1}.intersect({1 2}) should be { 1 }: " + s6);
        s6.insert(new Integer(2));
        s6.insert(new Integer(3));
        s6.intersect(s7);
        System.out.println("{1 2 3}.intersect({1 2}) should be { 1 2 }: " + s6);
        s6.insert(new Integer(3));
        s6.insert(new Integer(5));
        s7.insert(new Integer(4));
        s7.insert(new Integer(7));
        s7.intersect(s6);
        System.out.println("{1 2 4 7}.intersect({1 2 3 5}) should be { 1 2 }: " + s7);

        System.out.println();
        System.out.println("Tesing cardinality()");
        System.out.println("s.cardinality() should be 3: " + s.cardinality());
        System.out.println("s4.cardinality() should be 3: " + s4.cardinality());
        System.out.println("s5.cardinality() should be 0: " + s5.cardinality());
        System.out.println("s6.cardinality() should be 4: " + s6.cardinality());
        System.out.println("s7.cardinality() should be 2: " + s7.cardinality());
    }
}
