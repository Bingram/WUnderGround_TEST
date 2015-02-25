package DataStructures;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointListTest extends TestCase {

    private PointList myList;

    @Before
    public void setUp() throws Exception {

        myList = new PointList();
        myList.addFIFO(new Point(1,1));
        myList.addFIFO(new Point(1,2));
        myList.addFIFO(new Point(2,1));


    }

    @After
    public void teardown() throws Exception{

        myList = new PointList();
    }

    @Test
    public void testAddFIFO() throws Exception {
        Point test = new Point(3,4);
        myList.addFIFO(test);

        assertEquals(test, (Point) myList.getObject(4));

    }

    @Test
    public void testAddLIFO() throws Exception {

        Point test = new Point(3,4);
        myList.addLIFO(test);

        assertEquals(test, (Point) myList.getObject(0));

    }

    @Test
    public void testGetObject() throws Exception {

        Point test = (Point)myList.getObject(0);

        int x = test.getMyX();
        int y = test.getMyY();

        assertEquals(x, 1);
        assertEquals(y, 1);

    }

    @Test
    public void testRemove() throws Exception {

        myList.remove(0);

        Point test = new Point(1,2);

        assertEquals(test,(Point)myList.getObject(0));

    }

    public void testSize() throws Exception {

    }

    public void testToString() throws Exception {

    }
}