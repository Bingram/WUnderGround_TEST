import DataStructures.Boundary;

/**
 * Created by Brian on 2/20/2015.
 */
public class BoundaryChecker {

    private final int[][] myWeatherArray;
    private final Boundary myBounds;

    private BoundaryChecker(){
        myWeatherArray = new int[0][0];
        myBounds = new Boundary(0,0,1);
    }

    public BoundaryChecker(int[][] theArray, Boundary theBounds){
        myWeatherArray = theArray;
        myBounds = theBounds;
    }




}
