package DataStructures;

import java.util.LinkedList;

/**
 * Created by Brian on 2/15/2015.
 *
 * Given X&Y coords with a radius, a circle of points
 * is generated relative to the point. Points are
 * stored in LinkedLists, each in their corresponding
 * quadrant of the circle. These are stored in an array
 * of LinkedLists.
 */
public class CircleBoundary {

    private final int myX, myY;

    private LinkedList<Circle> myBoundaries;

    private LinkedList<Point> myPoints;

    private CircleBoundary(){
        myX = myY = 0;

    }

    /**
     * Accepts int for center of circle (x,y), and int for radius
     * Generates LinkedList<Point>[] for circle
     *
     * @param x int for x-value of center
     * @param y int for y-value of center
     * @param radius int for radius of circle
     */
    public CircleBoundary(int x, int y, int radius){

        myX = x;
        myY = y;

        myBoundaries = new LinkedList<Circle>();
        myPoints = new LinkedList<Point>();

        addCircle(radius);//main perimeter
        addCircle(5);//center marking

    }



    //fill quads with empty linkedlist of Points
    private void setupQuads(LinkedList<Point>[] theQuads){
        for(int i = 0; i<8; i++){
            theQuads[i] = new LinkedList<Point>();
        }
    }

    /**
     * Creates a circle using MidPoint Circle algorithm
     * Accepts XY values of center as int, along with circle
     * radius. Adds points one at a time to each quadrant,
     * calculated from bottom center in a counter-clockwise
     * direction.
     *
     * @param centerX int for center x-value
     * @param centerY int for center y-value
     * @param radius int for radius of circle
     * @return boolean value if circle can be made
     */
    private boolean makeCircle(final int centerX, final int centerY, final int radius) {
        boolean result = true;

        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        LinkedList<Point>[] newQuads =  new LinkedList[8];

        setupQuads(newQuads);

        Circle newCircle = new Circle(centerX,centerY, radius);

        //invalid values of circle
        if(centerX < 0 || centerY < 0 || radius <=0){
            result = false;

            x = y++;//ensures loop is never entered
        }

        do {

            //create points
            Point p0 = new Point(centerX + x, centerY + y);
            Point p1 = new Point(centerX + x, centerY - y);
            Point p2 = new Point(centerX - x, centerY + y);
            Point p3 = new Point(centerX - x, centerY - y);
            Point p4 = new Point(centerX + y, centerY + x);
            Point p5 = new Point(centerX + y, centerY - x);
            Point p6 = new Point(centerX - y, centerY + x);
            Point p7 = new Point(centerX - y, centerY - x);

            //add a point in each quadrant in new Circle
            newQuads[0].add(p0);
            newQuads[1].add(p1);
            newQuads[2].add(p2);
            newQuads[3].add(p3);
            newQuads[4].add(p4);
            newQuads[5].add(p5);
            newQuads[6].add(p6);
            newQuads[7].add(p7);

            //add points to complete list
            myPoints.add(p0);
            myPoints.add(p1);
            myPoints.add(p2);
            myPoints.add(p3);
            myPoints.add(p4);
            myPoints.add(p5);
            myPoints.add(p6);
            myPoints.add(p7);

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        } while (x <= y);

        newCircle.setQuads(newQuads);

        myBoundaries.add(newCircle);

        return result;
    }

    /**
     * Adds a circle with given radius to quad array.
     * New circle is centered on original.
     *
     * @param theRadius int the radius of new circle
     */
    public void addCircle(int theRadius){

        if (theRadius > 0){
            makeCircle(myX,myY,theRadius);
            System.out.println("Circle of size: "+theRadius+" added to Map @ "+myX+","+myY);
        }
    }


    public LinkedList<Point> getMyPoints() {
        return myPoints;
    }

    private class Circle{

        private final Point myCenter;
        private final int myRadius;

        private LinkedList<Point>[] myQuads =  new LinkedList[8];

        protected Circle(final int theX,final int theY,final int theRadius){
            this.myCenter = new Point(theX,theY);
            this.myRadius = theRadius;
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

        public int getMyRadius() {
            return myRadius;
        }
    }
}
