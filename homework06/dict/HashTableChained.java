package com.song.homework06.dict;

/* HashTableChained.java */


import com.song.homework05.List.DListNode;
import com.song.homework05.List.InvalidNodeException;
import com.song.homework05.List.List;
import com.song.homework05.List.DList;

import java.text.DecimalFormat;


/**
 * Dictionary 由 HashTable（主）或SearchTree两种数据结构实现
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  【All objects used as keys must have a unique hashCode() method,见SimpleBoard class】, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.
 *  The HashTableChained class implements only the 【compression function】,
 *  which maps the hash code to a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

    /**
     *  Place any data fields here.
     **/

     private List[] HashTable;  //一个可以引用对象为List的Array

     private int entrysize;   //HashTable 中总的entry的个数

    /**
     * 第一个constructor
     *  Construct a new empty hash table intended to hold roughly sizeEstimate
     *  entries.  (The precise number of buckets is up to you, but we recommend
     *  you use a prime number, and shoot for【争取】 a load factor between 0.5 and 1.)
     **/

    public HashTableChained(int sizeEstimate) {
        // Your solution here.
        HashTable=new List[sizeEstimate];
//        for(int i=0; i<sizeEstimate; i++)
//        {
//            HashTable[i]=new SList();
//        }

        entrysize=0;
    }

    /**
     * 第二个constructor（default）
     *
     *  Construct a new empty hash table with a default size.  Say, a prime in
     *  the neighborhood of 100. 或许101
     **/

    public HashTableChained() {
        // Your solution here.
        HashTable=new DList[179];

        for(int i=0;i<179;i++)
        {
            HashTable[i]=new DList();
        }

        entrysize=0;
    }

    /**
     * 【Compression Function的作用：】
     *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
     *  to a value in the range 0...(size of hash table) - 1.
     *  【而hashcode() 函数是只将某object，例String类转换为Interger的方法，在不同的object中有不同的实现，求法】
     *  This function should have package protection (so we can test it), and
     *  should be used by insert, find, and remove.
     *  package protection不用有protection声明
     **/

    int compFunction(int code) {
        // Replace the following line with your solution.

//        int a=7;
//        int b=13;
//        int p=HashTable.length+1;
//
//
//         int comp=((code*a+b)%p)%(HashTable.length-1);

           int comp=code%(HashTable.length-1); //两种算法 目前差别不大，可能是样本量太小的原因
            //减一是因为应该从0到99共100个数，若不减一就outofArrayBound了
            //别忘了index为负数的情况
            if(comp<0){
                comp=-comp;
            }
         return comp;
    }

    /**
     *  Returns the number of entries stored in the dictionary.  Entries with
     *  the same key (or even the same key and value) each still count as
     *  a separate entry.
     *  @return number of entries in the dictionary.
     **/

    public int size() {
        // Replace the following line with your solution.
        return entrysize;
    }

    /**
     *  Tests if the dictionary is empty.
     *
     *  @return true if the dictionary has no entries; false otherwise.
     **/

    public boolean isEmpty() {
        // Replace the following line with your solution.
        return entrysize==0;
    }

    /**
     *  Create a new Entry object referencing the input key and associated value,
     *  and insert the entry into the dictionary.  Return a reference to the new
     *  entry.  Multiple entries with the same key (or even the same key and
     *  value) can coexist in the dictionary.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the key by which the entry can be retrieved(检索).
     *  @param value an arbitrary object.
     *  @return an entry containing the key and value.
     **/

    public Entry insert(Object key, Object value) {
        // Replace the following line with your solution.
        int code = key.hashCode();   //Object 类中声明了hashCode这个函数
                                    // 具体的implementation由具体的对象（继承了Object类）实现

        int bucket=compFunction(code);  //将其compress到bucket的范围内，bucket表示它在数组中的位置

        //创建一个局部变量Entry item,放入key和value
        Entry item = new Entry();
        item.key=key;
        item.value=value;

        //因为要insert新的ListNode，直接调用了homework05中的public method.在尾部插入新的entry
        //当为空的时候，再在这个bucket位置新建SList, 节省内存
        if(HashTable[bucket]==null){
            HashTable[bucket]=new DList();

        }
        HashTable[bucket].insertBack(item);

        entrysize++;

        return item;
    }

    /**
     *  Search for an entry with the specified key.  If such an entry is found,
     *  return it; otherwise return null.  If several entries have the specified
     *  key, choose one arbitrarily and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     **/

    public Entry find(Object key) throws InvalidNodeException {
        // Replace the following line with your solution.
        int code = key.hashCode();
        int bucket=compFunction(code);  //这两步和上面的insert一样，简称为【Hash the key】

        if(HashTable[bucket]==null){
            return null;
        }

        DListNode FindNode=(DListNode) HashTable[bucket].front();

        if(HashTable[bucket].length()==1)
        {
            if(((Entry) FindNode.item()).key().hashCode()==key.hashCode()){
                return (Entry)FindNode.item();
            }
        }else if(HashTable[bucket].length() > 1){

            for(int i=0;i<HashTable[bucket].length();i++){
                if(((Entry) FindNode.item()).key().hashCode()==key.hashCode()){
                    return (Entry)FindNode.item();
                }else{
                    FindNode=(DListNode) FindNode.next();
                }
            }
        }

        return null;

    }

    /**
     *  Remove an entry with the specified key.  If such an entry is found,
     *  remove it from the table and return it; otherwise return null.
     *  If several entries have the specified key, choose one arbitrarily, then
     *  remove and return it.
     *
     *  This method should run in O(1) time if the number of collisions is small.
     *
     *  @param key the search key.
     *  @return an entry containing the key and an associated value, or null if
     *          no entry contains the specified key.
     */

    public Entry remove(Object key) throws InvalidNodeException {
        // Replace the following line with your solution.
        int code = key.hashCode();
        int bucket=compFunction(code);  //这两步和上面的insert一样，简称为【Hash the key】

        if(HashTable[bucket]!=null) {
            Entry temp=null;
            DListNode FindNode=(DListNode) HashTable[bucket].front();

            if(HashTable[bucket].length()==1)
            {
                if(((Entry) FindNode.item()).key().hashCode()==key.hashCode()){
                    temp=(Entry) FindNode.item();
                    FindNode.remove();
                    entrysize--;
                }
                else{
                    return null;
                }
            }else if(HashTable[bucket].length() > 1){
                for(int i=0;i<HashTable[bucket].length();i++){
                    if(((Entry) FindNode.item()).key().hashCode()==key.hashCode()){
                        temp=(Entry) FindNode.item();
                        FindNode.remove();
                        entrysize--;
                        return temp;
                    }else{
                        FindNode=(DListNode) FindNode.next();
                    }
                }
            }
        }


            return null;

    }

    /**
     *  Remove all entries from the dictionary.
     */
    public void makeEmpty() {
        // Your solution here.
        for(int i=0; i<HashTable.length; i++)
        {
            HashTable[i]=null;
        }

        entrysize=0;
    }

    protected double loadfactor(){
        double loadnum=0;
        for(int i=0;i<HashTable.length;i++){
            if(HashTable[i]!=null){
                loadnum++;
            }
        }

        return loadnum/HashTable.length;
    }

    public void histograph(){
        double collisions=this.entrysize-HashTable.length+HashTable.length*Math.pow((1-(double)(1.0/(double)HashTable.length)),(double) entrysize);
        DecimalFormat df=new DecimalFormat("#.00");
        System.out.println("The expected collisions are: "+df.format(collisions));

        System.out.println("The number of the entry is:"+entrysize);
        System.out.println("The number of the bucket is: "+HashTable.length);

        System.out.println("The loadfactor is: "+loadfactor());

        int count=1;
        int realCollisions=0;
        for(int i=0;i<HashTable.length;i++){

                if(HashTable[i]==null){
                    System.out.print("["+0+"]");
                }else {
                    System.out.print("[" + HashTable[i].length() + "]");

                    if (HashTable[i].length() > 1) {
                        realCollisions += HashTable[i].length() - 1;
                    }
                }

                     if(count%10==0){
                          System.out.println();
                          count=0;
                     }
                count++;

           }
         System.out.println();
        System.out.println("The real collisions are: "+realCollisions);
      }
 }




