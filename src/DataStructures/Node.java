package DataStructures;

/**
 * Created by Brian on 3/3/2015.
 *
 * Node type object used to create a singley linked
 * list of objects.
 */
public class Node{

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
