package DataStructures;

import java.util.Iterator;

/**
 * Created by b on 2/23/15.
 *
 * Custom linked list for use with Boundary object
 * Each quad on the boundary is a PointList
 * The idea is to be able to easily traverse in
 * either direction
 */
public class PointList implements Iterable<Point>{

    protected Node myhead;

    private int mySize;

    private String FLAG;

    public PointList(){
        myhead = new Node(null);
        mySize = 0;

        FLAG = "BLANK";

    }

    /**
     * Add objects in a FIFO order, or
     * in the case of a quad in a circle,
     * a clockwise direction
     * right to left.
     *
     * This allows easier checking based on
     * cardinal directions relative to wind.
     *
     * @param theObject Object to be added to list
     */

    public void addFIFO(Object theObject){

        if(theObject != null){

            FLAG = "FIFO";

            Node temp = new Node(theObject);
            Node current = myhead;

            while (current.getMyNext() != null){
                current = current.getMyNext();
            }

            current.setMyNext(temp);
            mySize++;
        }
    }

    /**
     * Add objects in a LIFO order, or
     * in the case of a quad in a circle,
     * a counter-clockwise direction
     * left to right.
     *
     * This allows easier checking based on
     * cardinal directions relative to wind.
     *
     * @param theObject Object to be added to list
     */
    public void addLIFO(Object theObject){

        if(theObject != null){

            if (FLAG.equals("BLANK")){
                FLAG = "LIFO";
            }

            Node temp = new Node(theObject);
            Node current = myhead;

            temp.setMyNext(current.getMyNext());
            current.setMyNext(temp);

            mySize++;

        }
    }

    /**
     * Takes the current order of list and reverses it
     */
    public void reverseList(){
        PointList temp = new PointList();

        if (FLAG.equals("FIFO")) {
            for (int i = 0; i < mySize; i++) {
                temp.addFIFO(this.getObject(i));
            }
            temp.myhead = this.myhead;
        }

        if (FLAG.equals("LIFO")) {
            for (int i = 0; i < mySize; i++) {
                temp.addLIFO(this.getObject(i));
            }
            temp.myhead = this.myhead;
        }
    }

    /**
     * Returns the object held in the node at the given index
     *
     * @param index position in list
     * @return Object contained in node
     */
    public Object getObject(int index){
        return getNode(index).getMyItem();
    }

    /**
     * Iterate and return node at given index
     *
     * @param index position in list
     * @return Node at index
     */
    public Node getNode(int index){

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

    /**
     * Removes Node at given index
     *
     * @param index position in list
     * @return Boolean if removal successful
     */
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

    /**
     * Get Iterator for Point
     *
     * @return Point Iterator
     */
    public Iterator<Point> iterator() {
        return new PointIterator(this);
    }

    /**
     * return int of current list size
     *
     * @return int size of list
     */
    public int size(){
        return mySize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointList points = (PointList) o;

        if (mySize != points.mySize) return false;

        for (int i = 0; i < size(); i++) {
            if (!points.getObject(i).equals(this.getObject(i))){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {//Basic hash implementation
        int result = myhead != null ? myhead.hashCode() : 0;
        result = 31 * result + mySize;
        return result;
    }

    /**
     * Reutrns string representation of list
     * @return String of point values
     */
    public String toString(){
        String temp = "[ ";

        for(int i = 0; i<mySize; i++){
            temp += "{" + getNode(i).toString() + "}";

            if(i<mySize-1){//fencepost
                temp += "|";
            }

        }

        return temp += " ]";
    }


}
