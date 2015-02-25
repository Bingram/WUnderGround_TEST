package DataStructures;

import java.util.LinkedList;

/**
 * Created by Brian on 2/20/2015.
 */
public class Boundary {

    private final Point myCenter;
    private final int myRadius;

    private final String myName;

    private PointList[] myQuads = new PointList[8];

    private PointList myPoints = new PointList();

    public Boundary(){
        this.myCenter = new Point(0,0);
        this.myRadius = 1;
        myName = "BLANK";
    }

    public Boundary(final int theX, final int theY, final int theRadius){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        myName = "NEW";

        setQuads(theX,theY,theRadius);
    }

    public Boundary(final int theX, final int theY, final int theRadius, final String theName){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        this.myName = theName;

        setQuads(theX,theY,theRadius);
    }

    private void quadSetup(){
        for (int i = 0; i < 8; i++) {
            myQuads[i] = new PointList();
        }
    }

    private void setQuads(int centerX, int centerY, int radius){

        quadSetup();

        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        while (x <= y){

            //create points
            Point p0 = new Point(centerX + x, centerY + y);
            Point p1 = new Point(centerX + x, centerY - y);
            Point p2 = new Point(centerX - x, centerY + y);
            Point p3 = new Point(centerX - x, centerY - y);
            Point p4 = new Point(centerX + y, centerY + x);
            Point p5 = new Point(centerX + y, centerY - x);
            Point p6 = new Point(centerX - y, centerY + x);
            Point p7 = new Point(centerX - y, centerY - x);

            //Creates a clockwise order of points
            //relative to position
            myQuads[0].addLIFO(p0);
            myQuads[1].addLIFO(p1);
            myQuads[2].addLIFO(p2);
            myQuads[3].addLIFO(p3);
            myQuads[4].addLIFO(p4);
            myQuads[5].addLIFO(p5);
            myQuads[6].addLIFO(p6);
            myQuads[7].addLIFO(p7);

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }

            x++;
        }

    }

    private void addPoints(){
        for(PointList list: myQuads){
            for(int i = 0; i < list.size(); i++){
                myPoints.addFIFO(list.getObject(i));
            }
        }
    }

    public PointList[] getQuads() {
        return myQuads;
    }

    public PointList getMyPoints(){
        return myPoints;
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