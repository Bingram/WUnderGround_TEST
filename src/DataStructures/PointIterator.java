package DataStructures;

import java.util.Iterator;

/**
 * Created by Brian on 2/27/2015.
 */
public class PointIterator<Point> implements Iterator<Object> {

    private PointList myList;
    private int position;

    public PointIterator(PointList theList){
        this.myList = theList;
    }

    @Override
    public boolean hasNext() {
        if (position < myList.size())
            return true;
        else
            return false;
    }

    @Override
    public Object next() {
        Point theObject = (Point) myList.getObject(position);
        position++;
        return theObject;
    }

    @Override
    public void remove() {
        myList.remove(position);
    }
}
