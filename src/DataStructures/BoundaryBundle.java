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
public class BoundaryBundle {

    private static final int LEVELS = 4;

    private int mySize = 0;

    //private final int myX, myY;

    //private LinkedList<Boundary> myBoundaries;

    private Boundary[] myBounds;

    public BoundaryBundle(){

        //myBoundaries = new LinkedList<Boundary>();
        myBounds = new Boundary[LEVELS];
    }

    /**
     * Adds a circle with given radius to quad array.
     * New circle is centered on original.
     *
     * @param theRadius int the radius of new circle
     */
    private void addBoundary(int x, int y, int theRadius){

        if (theRadius > 0){

            //make a circle and print number of points in radius
            System.out.println(this.getClass());
            System.out.println("Circle created, number of points: " + makeCircle(x,y,theRadius) );

            System.out.println("Circle of size: "+theRadius+" added to Map @ "+x+","+y);
        }
    }

    /**
     * Adds a new boundary to the bundle
     *
     * @param theBoundary Boundary object with center and radius
     */
    public void addBoundary(Boundary theBoundary){

        if(theBoundary != null){
            int x = theBoundary.getMyCenter().getMyX();
            int y = theBoundary.getMyCenter().getMyY();
            int radius = theBoundary.getMyRadius();

            addBoundary(x,y,radius);
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

        Boundary newBoundary = new Boundary();


        //invalid values of circle
        if(centerX < 0 || centerY < 0 || radius <=0){
            result = false;
        } else {
            newBoundary = new Boundary(centerX, centerY, radius);
        }

        //myBoundaries.add(newBoundary);

        myBounds[mySize++] = newBoundary;

        return result;
    }

    public Boundary getBoundary(int level) {

        return myBounds[level];
    }

    public void testQuad(LinkedList<Point> thePoints){

    }

    public boolean checkCircle(int level){
        Boundary temp = getBoundary(level);

        return false;
    }

    public boolean checkCircle(Boundary theBound){
        Boundary tempBound = theBound;



        return false;
    }

    public int getMySize() {
        return mySize;
    }


}
