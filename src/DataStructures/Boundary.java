package DataStructures;


/**
 * Created by Brian on 2/20/2015.
 */
public class Boundary {

    private final Point myCenter;
    private final int myRadius;
    private int myPointCount;

    private final String myName;

    private Quadrant[] myQuads = new Quadrant[8];//quadrants of the boundary

    //private PointList myPoints = new PointList();//all the points contained in a circle

    /**
     * Blank Boundary with default values
     */
    public Boundary(){
        this.myCenter = new Point(0,0);
        this.myRadius = 1;
        myName = "BLANK";
    }

    /**
     * Boundary object with center at X,Y and Radius
     * Default order of points is LIFO/counter-clockwise
     *
     * @param theX int x-center of boundary
     * @param theY int y-center of boundary
     * @param theRadius int radius of boundary
     */
    public Boundary(final int theX, final int theY, final int theRadius){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        myName = "NEW";

        setQuads(theX,theY,theRadius);
    }

    /**
     * Boundary object with center at X,Y and Radius and name
     * Default order of points is LIFO/counter-clockwise
     *
     * @param theX int x-center of boundary
     * @param theY int y-center of boundary
     * @param theRadius int radius of boundary
     * @param theName String name of boundary
     */
    public Boundary(final int theX, final int theY, final int theRadius, final String theName){
        this.myCenter = new Point(theX,theY);
        this.myRadius = theRadius;
        this.myName = theName;

        setQuads(theX,theY,theRadius);
    }

    /**
     * Prime the quads
     */
    private void quadSetup(){
        for (int i = 0; i < 8; i++) {
            myQuads[i] = new Quadrant();
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
            myQuads[0].getLevel0().addLIFO(p0);
            myQuads[1].getLevel0().addLIFO(p1);
            myQuads[2].getLevel0().addLIFO(p2);
            myQuads[3].getLevel0().addLIFO(p3);
            myQuads[4].getLevel0().addLIFO(p4);
            myQuads[5].getLevel0().addLIFO(p5);
            myQuads[6].getLevel0().addLIFO(p6);
            myQuads[7].getLevel0().addLIFO(p7);

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }

            x++;
        }

        addPoints();

    }

    private void addLine(Point p){

    }

    private void addPoints(){
        for(PointList list: myQuads){
            for(int i = 0; i < list.size(); i++){
                myPoints.addFIFO(list.getObject(i));
            }
        }
    }

    public String toString(){
        String temp = "";

        for (Point p: myPoints){
            temp += p.toString() + "\n";
        }

        return temp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boundary boundary = (Boundary) o;

        if (myRadius != boundary.myRadius) return false;
        if (myCenter != null ? !myCenter.equals(boundary.myCenter) : boundary.myCenter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = myCenter != null ? myCenter.hashCode() : 0;
        result = 31 * result + myRadius;
        return result;
    }
}
