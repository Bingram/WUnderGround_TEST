import DataStructures.Boundary;
import DataStructures.Point;
import DataStructures.PointList;

/**
 * Created by Brian on 2/20/2015.
 */
public class BoundaryChecker {

    private final int[][] myWeatherArray;
    private final Boundary myBound;
    private final int CLEAR = -16777216;

    private BoundaryChecker(){
        myWeatherArray = new int[0][0];
        myBound = new Boundary(0,0,1);
    }

    public BoundaryChecker(int[][] theArray, Boundary theBound){
        myWeatherArray = theArray;
        myBound = theBound;
    }

    public double checkPercent(PointList theList){
        double result = 0.0;
        int size = theList.size();
        int count = 0;

        //TODO

        for (int i = 0; i < size; i++) {
            Point p = (Point)theList.getObject(i);

            if (myWeatherArray[p.getMyX()][p.getMyY()] != CLEAR) {
                count++;
            }

        }

        result += (count/size);


        return result;
    }


    /**
     * Currently checks the default direction of W->NW
     * Does not check levels, only defaults to outer bound 0
     * @return boolean of percentage > 30%
     */
    public boolean quickCheckOuter(){
        boolean result = false;

        double test = checkPercent(myBound.getQuads()[5]);

        if (test > 0.3){
            result = true;
        }

        return result;
    }

    public boolean quickCheckQuadrant(int quad){
        boolean result = false;

        double test = checkPercent(myBound.getQuads()[quad]);

        if (test > 0.3){
            result = true;
        }

        return result;
    }

    public boolean fullCheckOuter(){
        boolean result = false;


        if (quickCheckOuter()){

            int count = 0;

            int size = myBound.getQuads().length;

            for (int i = 0; i < size; i++) {

                if (checkPercent(myBound.getQuads()[i]) >= 0.3){
                    count++;
                }

                if (count > (size/2)){//greater than 50% coverage
                    result = true;
                    return result;
                }

            }

        }


        return result;
    }





}
