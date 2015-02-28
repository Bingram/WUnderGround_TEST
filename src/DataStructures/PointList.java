package DataStructures;

import java.util.Iterator;

/**
 * Created by b on 2/23/15.
 */
public class PointList implements Iterable<Point>{

    private Node myhead;

    private int mySize;

    public PointList(){
        myhead = new Node(null);
        mySize = 0;

    }

    public void addFIFO(Object theObject){

        if(theObject != null){
            Node temp = new Node(theObject);
            Node current = myhead;

            while (current.getMyNext() != null){
                current = current.getMyNext();
            }

            current.setMyNext(temp);
            mySize++;
        }
    }

    public void addLIFO(Object theObject){

        if(theObject != null){
            Node temp = new Node(theObject);
            Node current = myhead;

            temp.setMyNext(current.getMyNext());
            current.setMyNext(temp);

            mySize++;

        }
    }

    public Object getObject(int index){
        return get(index).getMyItem();
    }

    private Node get(int index){
        if (index<0 || index>mySize){
            System.err.println("Retrieved Node index out of range: " + index);
            return null;

        }

        Node current = myhead.myNext;

        for(int i = 0; i<index; i++){

            if (current.getMyNext() != null){
                current = current.getMyNext();
            }

        }

        return current;
    }

    public boolean remove(int index){
        Node current = myhead;


        boolean result = false;

        for (int i = 1; i <index; i++) {
            if (current.getMyNext()==null){return result;}
            current = current.getMyNext();
        }

        current.setMyNext(current.getMyNext().getMyNext());
        mySize--;

        return result;

    }

    public Iterator<Point> iterator() {
        return new PointIterator(this);
    }

    public int size(){
        return mySize;
    }

    public String toString(){
        String temp = "[ ";

        for(int i = 0; i<mySize; i++){
            temp += "{" + get(i).toString() + "}";

            if(i<mySize-1){
                temp += "|";
            }
        }

        return temp += " ]";
    }


    private class Node{

        Object myItem;

        Node myNext;

        public Node(Object theItem){
            myItem = theItem;
            myNext = null;

        }

        public Node getMyNext(){
            return myNext;
        }

        public Object getMyItem(){
            return myItem;
        }

        public void setMyItem(Object theItem){
            myItem = theItem;
        }

        public void setMyNext(Node theNext){
            myNext = theNext;
        }

        public String toString(){
            return myItem.toString();
        }
    }
}
