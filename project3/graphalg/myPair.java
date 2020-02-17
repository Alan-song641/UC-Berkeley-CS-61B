package com.song.project3.graphalg;
/*
因为graph package中的VertexPair不能用，所以自己写一个

 */
public class myPair implements Comparable{

    protected Object vertex1;
    protected Object vertex2;
    protected int weight;

    //constructor1
    public myPair()
    {
        this(null, null, 0);
    }

    //constructor2
    public myPair(Object vertex1, Object vertex2, int weight)
    {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public Object getVertex1()
    {
        return vertex1;
    }

    public Object getVertex2()
    {
        return vertex2;
    }

    public int getWeight()
    {
        return weight;
    }

    public int hashCode()
    {
        int hashcode = vertex1.hashCode()+vertex2.hashCode();
        return hashcode;
    }

    //因为QuickSort是一个compare-based的算法，需要override(别忘了在signature处加implement Comparable)
    public int compareTo(Object o)
    {
        myPair e = (myPair)o;
        if (e.getWeight() < getWeight()){
            return 1;
        }
        else if (e.getWeight() > getWeight()){
            return -1;
        }
        else if (e.getVertex1().equals(getVertex1()) && e.getVertex2().equals(getVertex2()) && e.getWeight() == getWeight()){
            return 0;
        }

        return -1;
    }


}
