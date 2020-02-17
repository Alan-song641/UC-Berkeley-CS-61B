package com.song.project3.graph;

/* WUGraph.java */

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */
import com.song.homework05.List.*;  //导入homework 05 的 DList数据结构
import com.song.homework06.dict.*;  //导入homework 06 的 HashTable数据结构

import java.util.Hashtable;

public class WUGraph {

    protected HashTableChained VertexHash;
    protected HashTableChained EdgeHash;
    protected DList VertexList;

    /**
     * WUGraph() constructs a graph having no vertices or edges.
     *
     * Running time:  O(1).
     */
    public WUGraph(){
        VertexHash = new HashTableChained();
        EdgeHash = new HashTableChained();
        VertexList = new DList();
        //EdgeList = new DList();
    }

    /**
     * vertexCount() returns the number of vertices in the graph.
     *
     * Running time:  O(1).
     */
    public int vertexCount(){
        return VertexHash.size();
    }

    /**
     * edgeCount() returns the total number of edges in the graph.
     *
     * Running time:  O(1).
     */
    public int edgeCount(){
        return EdgeHash.size();
    }

    /**
     * getVertices() returns an array containing all the objects that serve
     * as vertices of the graph.  The array's length is exactly equal to the
     * number of vertices.  If the graph has no vertices, the array has length
     * zero.
     *
     * (NOTE:  Do not return any internal data structure you use to represent
     * vertices!  Return only the same objects that were provided by the
     * calling application in calls to addVertex().)
     *
     * Running time:  O(|V|).
     */
    public Object[] getVertices() throws InvalidNodeException {

        Object[] Vertices=new Object[vertexCount()];

        if(VertexList.isEmpty()){
            return Vertices;
        }

        DListNode VertexNode = (DListNode) VertexList.front();

        for(int i=0;i<vertexCount();i++){
           Vertices[i]=VertexNode.getVertexInApp();
           VertexNode=(DListNode)VertexNode.next();
        }

        return Vertices;

    }

    /**
     * addVertex() adds a vertex (with no incident edges) to the graph.
     * The vertex's "name" is the object provided as the parameter "vertex".
     * If this object is already a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(1).
     */
    public void addVertex(Object vertex) throws InvalidNodeException {
        if(isVertex(vertex)){ //already a vertex in the graph
            return;  //如果发生collision了呢？ 细化HashTable的find函数，不能随机返回entry
        }
        //insert了新的ListNode的item是new DList()放Edge;
        VertexList.insertFront(new DList());

        //value指向VertexList中新增的node，key是object（vertex）本身来换算hashcode
        VertexHash.insert(vertex,VertexList.front());

        //上面通过key可以查到value，这个value也可以指向app中的key
        VertexList.front().setVertexInApp(vertex);

    }

    /**
     * removeVertex() removes a vertex from the graph.  All edges incident on the
     * deleted vertex are removed as well.  If the parameter "vertex" does not
     * represent a vertex of the graph, the graph is unchanged.
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public void removeVertex(Object vertex) throws InvalidNodeException {
        if(isVertex(vertex)){
            //vertex作为sentinel的DList，存放它的Edge们

            DList EdgeList= (DList)((DListNode)VertexHash.find(vertex).value()).item();  //sentinel

            if(EdgeList!=null||degree(vertex)!=0){
                DListNode EdgeListNode=(DListNode) EdgeList.front(); //设为DList的第一个
                DListNode temp;

                int length=degree(vertex); //又忘了设定循环初始值！（debug time：2h）
                for(int i=0;i<length;i++){  //我写成了i<degree(vertex),remove过程中这个degree会动的啊！！
                    temp=EdgeListNode;
                    EdgeListNode=(DListNode) EdgeListNode.next();

                    if( temp.getPartner()==temp){
                        Object v1=temp.getVertex1().getVertexInApp();
                        Object v2=temp.getVertex2().getVertexInApp();
                        VertexPair pair=new VertexPair(v1,v2);
                        EdgeHash.remove(pair);

                        temp.remove();
                    }else{
                        Object v1=temp.getVertex1().getVertexInApp();
                        Object v2=temp.getVertex2().getVertexInApp();
                        Object v3=temp.getPartner().getVertex1().getVertexInApp();
                        Object v4=temp.getPartner().getVertex2().getVertexInApp();
                        VertexPair pair=new VertexPair(v1,v2);
                        VertexPair pair2=new VertexPair(v3,v4);
                        EdgeHash.remove(pair);
                        EdgeHash.remove(pair2);

                        temp.getPartner().remove();  //它的prev的partner和本体删去
                        temp.remove();
                    }
                }

            }

            ((DListNode)VertexHash.find(vertex).value()).setVertexInApp(null);
            ((DListNode)VertexHash.find(vertex).value()).remove();
            VertexHash.remove(vertex);
        }
    }

    /**
     * isVertex() returns true if the parameter "vertex" represents a vertex of
     * the graph.
     *
     * Running time:  O(1).
     */
    public boolean isVertex(Object vertex) throws InvalidNodeException {
        if(VertexHash.find(vertex)==null){
            return false;
        }

        if(VertexHash.find(vertex).key().equals(vertex)){
            return true;
        }

        return false;
    }

    /**
     * degree() returns the degree of a vertex.  Self-edges add only one to the
     * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
     * of the graph, zero is returned.
     *
     * Running time:  O(1).
     */
    public int degree(Object vertex) throws InvalidNodeException {
        if(!isVertex(vertex)){
            return 0;
        }
        return ((DList)((DListNode)VertexHash.find(vertex).value()).item()).length();
    }

    /**
     * getNeighbors() returns a new Neighbors object referencing two arrays.  The
     * Neighbors.neighborList array contains each object that is connected to the
     * input object by an edge.  The Neighbors.weightList array contains the
     * weights of the corresponding edges.  The length of both arrays is equal to
     * the number of edges incident on the input vertex.  If the vertex has
     * degree zero, or if the parameter "vertex" does not represent a vertex of
     * the graph, null is returned (instead of a Neighbors object).
     *
     * The returned Neighbors object, and the two arrays, are both newly created.
     * No previously existing Neighbors object or array is changed.
     *
     * (NOTE:  In the neighborList array, do not return any internal data
     * structure you use to represent vertices!  Return only the same objects
     * that were provided by the calling application in calls to addVertex().)
     *
     * Running time:  O(d), where d is the degree of "vertex".
     */
    public Neighbors getNeighbors(Object vertex) throws InvalidNodeException {
        if(!isVertex(vertex)||degree(vertex)==0){
            return null;
        }
        int j=0;
        Neighbors nei= new Neighbors();  //constructor 竟然没有初始化（新建）它的field
        nei.neighborList=new Object[degree(vertex)];
        nei.weightList=new int[degree(vertex)];

        DListNode EdgeNei= (DListNode) ((DList)((DListNode)VertexHash.find(vertex).value()).item()).front();
        for(int i=0;i<degree(vertex);i++){
            if(!EdgeNei.getVertex1().getVertexInApp().equals(vertex)){
                nei.neighborList[j]=EdgeNei.getVertex1().getVertexInApp();
                nei.weightList[j]=EdgeNei.getWeight();
                j++;
            } else if(!EdgeNei.getVertex2().getVertexInApp().equals(vertex)){
                nei.neighborList[j]=EdgeNei.getVertex2().getVertexInApp();
                nei.weightList[j]=EdgeNei.getWeight();
                j++;
            }else{
                nei.neighborList[j]=EdgeNei.getVertex1().getVertexInApp(); //回环:自己是自己的partner
                nei.weightList[j]=EdgeNei.getWeight();
                j++;
            }

            EdgeNei=(DListNode) EdgeNei.next();
        }

        return nei;
    }

    /**
     * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
     * u and v does not represent a vertex of the graph, the graph is unchanged.
     * The edge is assigned a weight of "weight".  If the graph already contains
     * edge (u, v), the weight is updated to reflect the new value.  Self-edges
     * (where u.equals(v)) are allowed.
     *
     * Running time:  O(1).
     */
    public void addEdge(Object u, Object v, int weight) throws InvalidNodeException {
        if(!isVertex(u)||!isVertex(v)){
            return;
        }

        if(isEdge(u,v)){
            VertexPair test=new VertexPair(u,v);
            ((DListNode)EdgeHash.find(test).value()).setWeight(weight); //update weight
            ((DListNode)EdgeHash.find(test).value()).getPartner().setWeight(weight); //its partner should also be updated;
            return;        //又忘记加return！不加的话会继续执行下面的if！！（有多个if时要小心！！）
        }

        VertexPair newEdge=new VertexPair(u,v);

        //邻接表中的vertex，其item作为EdgeDList的sentinel
        DListNode UNode = (DListNode)VertexHash.find(u).value();
        DListNode VNode = (DListNode)VertexHash.find(v).value();

        if(u.equals(v)){
            ((DList)UNode.item()).insertFront(newEdge);
            ((DList)UNode.item()).front().setWeight(weight);
            //edge 指向 vertex的node的引用,在node类中
            ((DList)UNode.item()).front().setVertex1(UNode);
            ((DList)UNode.item()).front().setVertex2(UNode);
            //myNode函数让我们知道pair所属的Node是哪个，在node类中
            ((DList)UNode.item()).front().setPartner((DListNode)((DList)UNode.item()).front());
            //System.out.println("circle created!");
        }else{
            ((DList)UNode.item()).insertFront(newEdge);
            ((DList)VNode.item()).insertFront(newEdge);

            ((DList)UNode.item()).front().setWeight(weight);
            ((DList)UNode.item()).front().setVertex1(UNode);
            ((DList)UNode.item()).front().setVertex2(VNode);
            ((DList)UNode.item()).front().setPartner((DListNode)((DList)VNode.item()).front());

            ((DList)VNode.item()).front().setWeight(weight);
            ((DList)VNode.item()).front().setVertex1(VNode);
            ((DList)VNode.item()).front().setVertex2(UNode);
            ((DList)VNode.item()).front().setPartner((DListNode)((DList)UNode.item()).front());
            //System.out.println("created!");
        }

        EdgeHash.insert(newEdge,((DList)UNode.item()).front());
        //EdgeHash 的key是newedge，value是里面的node，node里面新增一个field叫weight
        //它的partner不用插入hashcode，因为有partner这个field链接着



    }

    /**
     * removeEdge() removes an edge (u, v) from the graph.  If either of the
     * parameters u and v does not represent a vertex of the graph, the graph
     * is unchanged.  If (u, v) is not an edge of the graph, the graph is
     * unchanged.
     *
     * Running time:  O(1).
     */
    public void removeEdge(Object u, Object v) throws InvalidNodeException {
        if(!isEdge(u,v)){
            return;
        }
        VertexPair newEdge=new VertexPair(u,v); //无需再建（v,u），因为它们有相同的hashcode
        //删除的顺序不能调转！
        if(u.equals(v)){
            ((DListNode)EdgeHash.find(newEdge).value()).remove();
        }else{
            ((DListNode)EdgeHash.find(newEdge).value()).getPartner().remove();
            ((DListNode)EdgeHash.find(newEdge).value()).remove();
        }

        //除了要细化find之外，还得细化remove，之前的remove是直接删除bucket中的第一个node
        EdgeHash.remove(newEdge);



    }

    /**
     * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
     * if (u, v) is not an edge (including the case where either of the
     * parameters u and v does not represent a vertex of the graph).
     *
     * Running time:  O(1).
     */
    public boolean isEdge(Object u, Object v) throws InvalidNodeException {
        if(!isVertex(u)||!isVertex(v)){
            return false;
        }

        VertexPair test=new VertexPair(u,v);
        if(EdgeHash.find(test)==null){
            return false;
        }else{
            return true;
        }

    }

    /**
     * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
     * an edge (including the case where either of the parameters u and v does
     * not represent a vertex of the graph).
     *
     * (NOTE:  A well-behaved application should try to avoid calling this
     * method for an edge that is not in the graph, and should certainly not
     * treat the result as if it actually represents an edge with weight zero.
     * However, some sort of default response is necessary for missing edges,
     * so we return zero.  An exception would be more appropriate, but also more
     * annoying.)
     *
     * Running time:  O(1).
     */
    public int weight(Object u, Object v) throws InvalidNodeException {
        VertexPair pair=new VertexPair(u,v);
        if(EdgeHash.find(pair)==null){
            return 0;
        }

        return ((DListNode)EdgeHash.find(pair).value()).getWeight();

    }

}