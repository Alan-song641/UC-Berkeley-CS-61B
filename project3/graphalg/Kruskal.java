package com.song.project3.graphalg;

/* Kruskal.java */


import com.song.homework05.List.InvalidNodeException;
import com.song.homework06.dict.*;
import com.song.homework08.List.LinkedQueue;
import com.song.homework08.List.QueueEmptyException;
import com.song.homework08.ListSorts;
import com.song.project3.graph.*;
import com.song.project3.set.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

    /**
     * minSpanTree() returns a WUGraph that represents the minimum spanning tree
     * of the WUGraph g.  The original WUGraph g is NOT changed.
     *
     * @param g The weighted, undirected graph whose MST we want to compute.
     * @return A newly constructed WUGraph representing the MST of g.
     */
    public static WUGraph minSpanTree(WUGraph g) throws InvalidNodeException, QueueEmptyException {
        //新建所有vertices的array
        Object[] vertices = g.getVertices();

        //新建要返回的minimum spanning tree
        WUGraph m = new WUGraph();

        //新建所有vertices的neighbor vertices 和他们之间edge的weight
        Neighbors[] NeiArray=new Neighbors[g.vertexCount()];

        //homework08 用于进行quickSort
        LinkedQueue SortedEdges = new LinkedQueue();

        myPair pairIn;
        for(int i=0;i<g.vertexCount();i++){
            m.addVertex(vertices[i]);
            NeiArray[i]=g.getNeighbors(vertices[i]);

            for(int j=0;j<NeiArray[i].neighborList.length;j++){
                pairIn= new myPair(vertices[i],NeiArray[i].neighborList[j],NeiArray[i].weightList[j]);
                SortedEdges.enqueue(pairIn); //将新建的pair放到homework08的LinkedQueue中
            }

        }

        ListSorts.quickSort(SortedEdges);  //用homework08的ListSorts class 进行quicksort

        //并查集是以array的形式实现的，因此作为vertex的object必须转换为int
        //并查集刚开始是空的，find开始才不为空（相当于新加）
        DisjointSets EdgeSet = new DisjointSets(g.vertexCount());

        //vertex与某个int一一对应的最好办法是用Dictionary，which implemented by HashTable！
        HashTableChained HashVert = new HashTableChained(g.vertexCount());

        for (int i = 0; i < vertices.length; i++)
        {
            HashVert.insert(vertices[i], i);  //相当于将每个vertices标号，key:vertex value:int
        }

        while(!SortedEdges.isEmpty()){

            myPair pairOut=(myPair) SortedEdges.dequeue();  //从最小的开始dequeue

            int v1=(int)HashVert.find(pairOut.getVertex1()).value();  //找到vertex对应的int
            int v2=(int)HashVert.find(pairOut.getVertex2()).value();

            int v1Parent=EdgeSet.find(v1);  //vertex1的parent
            int v2Parent=EdgeSet.find(v2);  //vertex2的parent

            if(v1Parent!=v2Parent){  //说明这两个vertex不在一个set里，需要用edge连接起来
                EdgeSet.union(v1Parent,v2Parent);//里面放的是要union元素的【root】而不是本身
                m.addEdge(pairOut.getVertex1(),pairOut.getVertex2(),pairOut.getWeight());
            }
        }

        return m;
    }
}