package DataStructures;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PointListTest extends TestCase {

    private PointList myList;

    @Before
    public void setUp() throws Exception {
        System.out.println("Creating List...");

        myList = new PointList();
        myList.addFIFO(new Point(1,1));
        myList.addFIFO(new Point(1,2));
        myList.addFIFO(new Point(2,1));


        System.out.println("The list: "+myList.toString());


    }

    @Test
    public void testAddFIFO() throws Exception {

        System.out.println("Adding Node FIFO...");

        Point test = new Point(3,4);
        myList.addFIFO(test);

        assertEquals(test, (Point) myList.getObject(myList.size()-1));

        System.out.println("The list: "+myList.toString());

    }

    @Test
    public void testAddLIFO() throws Exception {

        System.out.println("Adding Node LIFO...");

        Point test = new Point(3,4);
        myList.addLIFO(test);

        assertEquals(test, (Point) myList.getObject(0));


        System.out.println("The list: "+myList.toString());
    }

    @Test
    public void testGetObject() throws Exception {

        System.out.println("Getting object...");

        Point test = (Point)myList.getObject(0);

        int x = test.getMyX();
        int y = test.getMyY();

        assertEquals(x, 1);
        assertEquals(y, 1);

        System.out.println("The list: "+myList.toString());

    }

    @Test
    public void testRemove() throws Exception {

        System.out.println("Removing First Node...");
        System.out.println("Current list: "+myList.toString());
        myList.remove(0);

        Point test = new Point(1,2);

        System.out.println("Modified list: "+myList.toString());

        assertEquals(test,myList.getObject(0));

        myList.addLIFO(new Point(1,1));

        System.out.println("Removing Last Node...");
        System.out.println("Current list: "+myList.toString());
        myList.remove(myList.size());

        test = new Point(1,2);

        System.out.println("Modified list: "+myList.toString());

        assertEquals(test,myList.getObject(myList.size()));


    }

    public void testSize() throws Exception {

    }

    public void testToString() throws Exception {

    }
}