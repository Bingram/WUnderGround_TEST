package DataStructures;

/**
 * Created by b on 2/23/15.
 */
public class PointList {

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

    private class Node{

        Object myItem;

        Node myNext;

        public Node(Object theItem){
            myItem = theItem;
            myNext = new Node(null);

        }

        public Node(Object theItem, Node theNextNode){
            myItem = theItem;
            myNext = theNextNode;

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
    }
}
