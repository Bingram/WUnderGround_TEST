package DataStructures;

import java.util.LinkedList;

/**
 * Created by Brian on 2/20/2015.
 */
public class Boundary {
    //TODO Add a simple linkedlist to iterate through the points in a quad
    //http://www.dreamincode.net/forums/topic/249375-circular-linked-list-class-and-define-methods-to-add-elements-to-the/

    private final Point myCenter;
    private final int myRadius;

    private final String myName;

    private LinkedList<Point>[] myQuads =  new LinkedList[8];

    private Boundary(){
        this.myCenter = new Point(0,0);
        this.myRadius = 1;
        myName = "NEW";
    }

    public Boundary(final int theX, final int theY, final int theRadius){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        myName = "NEW";
    }

    public Boundary(final int theX, final int theY, final int theRadius, final String theName){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        this.myName = theName;
    }

    public LinkedList<Point>[] getQuads() {
        return myQuads;
    }

    public void setQuads(LinkedList<Point>[] quads) {
        this.myQuads = quads;
    }

    public Point getMyCenter() {
        return myCenter;
    }

    public String getMyName() {
        return myName;
    }

    public int getMyRadius() {
        return myRadius;
    }
}
