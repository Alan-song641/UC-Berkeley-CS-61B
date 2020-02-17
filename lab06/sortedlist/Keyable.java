package com.song.lab06.sortedlist;

/* Keyable.java */

//无实现 记得纯虚函数吗？
public interface Keyable {
    public int getKey();
    public boolean lessThan(Keyable x);
}