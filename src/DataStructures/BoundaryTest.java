package DataStructures;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoundaryTest extends TestCase {

    private Boundary myBoundary;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        myBoundary = new Boundary(15,12,2);

        System.out.println("Original Boundary");
        System.out.println(myBoundary.toString());

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetQuads() throws Exception {

        Boundary testList = new Boundary(15,12,2);


        assertEquals(testList.toString(), myBoundary.toString());

    }

    public void testModQuads() throws Exception {

        Boundary testList = new Boundary(15,12,2);

        PointList[] testQuads = testList.getQuads();
        PointList[] mainQuads = myBoundary.getQuads();

        PointList one = testQuads[0];
        PointList two = mainQuads[0];

        one.remove(0);

        assertEquals(one.getObject(0), two.getObject(1));
    }

    @Test
    public void testGetMyPoints() throws Exception {

        Boundary test = new Boundary(15,12,2);

        assertEquals(myBoundary.getMyPoints(),test.getMyPoints());

    }

    @Test
    public void testGetMyCenter() throws Exception {

    }

    @Test
    public void testGetMyName() throws Exception {

    }

    @Test
    public void testGetMyRadius() throws Exception {

    }
}