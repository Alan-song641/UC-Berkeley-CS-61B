package com.song.lab12;

/* YourSort.java */

public class YourSort {
//数组A是我们要sort 的含int的数组
    public static void sort(int[] A) {
        // Place your Part III code here.
        //根据四个文件（select.dat, insert.dat, merge.dat和quick.dat）可以看出，quick总体更好
        // 但当A.length小于100时，insert更好
        if(A.length<100){
            Sort.insertionSort(A);
        }else{
            Sort.quicksort(A);
        }
        //program argument 输入 mysort 5 175 200 mysort.dat即可测试
    }
}
