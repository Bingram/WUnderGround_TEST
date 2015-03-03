package DataStructures;

import junit.framework.TestCase;

import java.util.LinkedList;

public class BoundaryBundleTest extends TestCase {

    private LinkedList<Boundary> myBounds;
    private BoundaryBundle myBundle;

    private int xCENTER =  320;
    private int yCENTER = xCENTER;
    private int RADIUS = 100;

    public void setUp() throws Exception {
        super.setUp();

        Boundary first = new Boundary(xCENTER,yCENTER,RADIUS);

        myBundle = new BoundaryBundle();

        myBounds = new LinkedList<Boundary>();

        myBounds.add(first);

    }


    public void testAddBoundary() throws Exception {

        myBundle.addBoundary(new Boundary(xCENTER,yCENTER,RADIUS));

        assertEquals(myBundle.getBoundary(0),myBounds.getFirst());
    }
}