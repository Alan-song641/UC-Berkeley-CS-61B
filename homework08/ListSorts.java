package com.song.homework08;

/* ListSorts.java */

import com.song.homework08.List.LinkedQueue;
import com.song.homework08.List.QueueEmptyException;

import java.util.Random;

public class ListSorts {

    private final static int SORTSIZE = 100000;

    /**
     *  makeQueueOfQueues() makes a queue of queues, each containing one item
     *  of q.  Upon completion of this method, q is empty.
     *  @param q is a LinkedQueue of objects.
     *  @return a LinkedQueue containing LinkedQueue objects, each of which
     *    contains one object from q.
     **/
    public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
        // Replace the following line with your solution.
        LinkedQueue newQueue=new LinkedQueue();
        int size = q.size();
        try {
            for(int i=1; i<=size; i++){
                Object o = q.dequeue();
                LinkedQueue QueueInside=new LinkedQueue();
                QueueInside.enqueue(o);
                newQueue.enqueue(QueueInside);
            }
        } catch (QueueEmptyException e) {
            System.out.println(e);
        }
        return newQueue;
    }

    /**
     *  mergeSortedQueues() merges two sorted queues into a third.  On completion
     *  of this method, q1 and q2 are empty, and their items have been merged
     *  into the returned queue.
     *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest
     *    to largest.
     *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest
     *    to largest.
     *  @return a LinkedQueue containing all the Comparable objects from q1
     *   and q2 (and nothing else), sorted from smallest to largest.
     *
     **/
    public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
        // Replace the following line with your solution.
        LinkedQueue newQueue=new LinkedQueue();

        try {
            //这里while的条件和里面的if条件很精简，学习一下
            // 不要while前dequeue，会改变size，先利用front函数判断，需要dequeue再做，行数更少更精简
            while(q1.size() > 0 && q2.size() > 0){
                if(((Comparable)q1.front()).compareTo(q2.front())>=0){ //q1的第一个object比q2大

                    newQueue.enqueue(q2.dequeue());

                } else{
                    newQueue.enqueue(q1.dequeue());
                }
            }
            if(q2.isEmpty()){
                newQueue.append(q1);
            }
            if(q1.isEmpty()){
                newQueue.append(q2);
            }

        } catch (QueueEmptyException e) {
            e.printStackTrace();
        }

        return newQueue;

    }

    /**
     *  mergeSort() sorts q from smallest to largest using mergesort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void mergeSort(LinkedQueue q) {
        // Your solution here.
        if(q.size()==0||q.size()==1)
        {
            return;
        }

        LinkedQueue newQueue = makeQueueOfQueues(q);

        try {
            while(newQueue.size()>1){

                LinkedQueue q1=(LinkedQueue) newQueue.dequeue();
                LinkedQueue q2=(LinkedQueue) newQueue.dequeue();
                LinkedQueue MergedQueue=mergeSortedQueues(q1,q2);
                newQueue.enqueue(MergedQueue);
            }

            q.append((LinkedQueue) newQueue.dequeue());
            //newQueue是QueueofQueues，dequeue后还是LinkedQueue


        } catch (QueueEmptyException e) {
            System.out.println(e);
        }


    }

    /**
     *  partition() partitions qIn using the pivot item.  On completion of
     *  this method, qIn is empty, and its items have been moved to qSmall,
     *  qEquals, and qLarge, according to their relationship to the pivot.
     *  @param qIn is a LinkedQueue of Comparable objects.
     *  @param pivot is a Comparable item used for partitioning.
     *  @param qSmall is a LinkedQueue, in which all items less than pivot
     *    will be enqueued.
     *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
     *    will be enqueued.
     *  @param qLarge is a LinkedQueue, in which all items greater than pivot
     *    will be enqueued.
     **/
    public static void partition(LinkedQueue qIn, Comparable pivot,
                                 LinkedQueue qSmall, LinkedQueue qEquals,
                                 LinkedQueue qLarge)  {
        // Your solution here.
        //for (int i = 1; i <= qIn.size(); i++)
        // 用这个的弊端在于随着i++，qIn.size也是减少的（应是不变的才对）以后要注意，学习一下
        try {
            while(qIn.size() > 0){

                Comparable com = (Comparable) qIn.dequeue();

                if (com.compareTo(pivot) > 0) { //比pivot大
                    qLarge.enqueue(com);
                }else if(com.compareTo(pivot) < 0){ //比pivot小
                    qSmall.enqueue(com);
                }else{ //与pivot相等
                    qEquals.enqueue(com);
                }
            }

        }catch(QueueEmptyException e){
            System.out.println(e);
        }

    }



    /**
     *  quickSort() sorts q from smallest to largest using quicksort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void quickSort(LinkedQueue q) {
        // Your solution here.

        Random rand = new Random();
        int length = q.size();
        int num = rand.nextInt(length) + 1; //取从1-length的方法，学习一下

        Comparable pivot = (Comparable) q.nth(num);

        LinkedQueue qSmall =new LinkedQueue();
        LinkedQueue qEquals=new LinkedQueue();
        LinkedQueue qLarge =new LinkedQueue();

        partition(q,pivot,qSmall,qEquals,qLarge);

        if(qSmall.size()>0){  //迭代的用法
            quickSort(qSmall);
        }
        if(qLarge.size()>0){ //迭代的用法
            quickSort(qLarge);
        }

        q.append(qSmall);
        q.append(qEquals);
        q.append(qLarge);

    }

    /**
     *  makeRandom() builds a LinkedQueue of the indicated size containing
     *  Integer items.  The items are randomly chosen between 0 and size - 1.
     *  @param size is the size of the resulting LinkedQueue.
     **/
    public static LinkedQueue makeRandom(int size) {
        LinkedQueue q = new LinkedQueue();
        for (int i = 0; i < size; i++) {
            q.enqueue(new Integer((int) (size * Math.random())));
        }
        return q;
    }

    /**
     *  main() performs some tests on mergesort and quicksort.  Feel free to add
     *  more tests of your own to make sure your algorithms works on boundary
     *  cases.  Your test code will not be graded.
     **/
    public static void main(String [] args) {

        LinkedQueue q = makeRandom(10);
        System.out.println(q.toString());
        mergeSort(q);
        System.out.println(q.toString());

        q = makeRandom(10);
        System.out.println(q.toString());
        quickSort(q);
        System.out.println(q.toString());


    Timer stopWatch = new Timer();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    mergeSort(q);
    stopWatch.stop();
    System.out.println("Mergesort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");

    stopWatch.reset();
    q = makeRandom(SORTSIZE);
    stopWatch.start();
    quickSort(q);
    stopWatch.stop();
    System.out.println("Quicksort time, " + SORTSIZE + " Integers:  " +
                       stopWatch.elapsed() + " msec.");


    }

}
