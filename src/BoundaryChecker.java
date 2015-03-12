import DataStructures.Boundary;
import DataStructures.Point;
import DataStructures.PointList;

/**
 * Created by Brian on 2/20/2015.
 */
public class BoundaryChecker {

    private final int CLEAR = -16777216;

    private int[][] myWeatherArray;

    public BoundaryChecker(){
        myWeatherArray = new int[0][0];
    }

    public BoundaryChecker(int[][] theArray){
        myWeatherArray = theArray;

    }

    private double checkPercent(PointList theList){
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
    public double quickCheckOuter(Boundary theBound){

        return checkPercent(theBound.getQuads()[5]);

    }

    public double CheckBoundaryQuad(Boundary theBound, int quad){

        return checkPercent(theBound.getQuads()[quad]);

    }

    public double fullCheckOuter(Boundary theBound){

        int count = 0;

        int size = theBound.getQuads().length;

        for (int i = 0; i < size; i++) {

            if (checkPercent(theBound.getQuads()[i]) >= 0.3){
                count++;
            }

            if (count > (size/2)){//greater than 50% coverage
                
                i=size;//stop loop here
            }

        }



        return result;
    }

    public void updateWeather(int[][] newWeather){
        myWeatherArray = newWeather;
    }





}
