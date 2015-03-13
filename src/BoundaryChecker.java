import DataStructures.Boundary;
import DataStructures.Point;
import DataStructures.PointList;

/**
 * Created by Brian on 2/20/2015.
 */
public class BoundaryChecker implements Runnable{

    private double THRESHOLD = 0.3;//percentage of points with rain before considered "covered"
    private final int CLEAR = -16777216;

    private Boolean boundChecked;

    private int[][] myWeatherArray;

    private Boundary myBoundary;

    private double coveragePercent;

    public BoundaryChecker(){
        myWeatherArray = new int[0][0];
        boundChecked = false;
        myBoundary = null;
        coveragePercent = 0.0;
    }

    public BoundaryChecker(int[][] theArray){
        myWeatherArray = theArray;
        myBoundary = null;
        coveragePercent = 0.0;
    }

    public BoundaryChecker(int[][] theArray, Boundary thebound){
        myWeatherArray = theArray;
        myBoundary = thebound;
        coveragePercent = 0.0;

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

    public double fullCheckOuter(){

        int count = 0;

        int size = myBoundary.getQuads().length;

        for (int i = 0; i < size; i++) {

            if (checkPercent(myBoundary.getQuads()[i]) >= THRESHOLD){
                count++;
            }

            if (count > (size/2)){//greater than 50% coverage
                
                i=size;//stop loop here
            }

        }

        boundChecked = true;

        coveragePercent = count/8;

        return coveragePercent;
    }

    public void updateWeather(int[][] newWeather){
        myWeatherArray = newWeather;
    }

    public void setMyBoundary(Boundary theBound){
        myBoundary = theBound;
    }

    public void setTHRESHOLD(double newThreshold) {
        this.THRESHOLD = THRESHOLD;
    }


    @Override
    public void run() {

        while(!boundChecked){
            fullCheckOuter();
        }

        boundChecked = false;
    }

    public double getCoveragePercent() {
        return coveragePercent;
    }
}
