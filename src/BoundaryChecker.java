import DataStructures.Boundary;
import DataStructures.Point;
import DataStructures.PointList;

import java.awt.*;

/**
 * Created by Brian on 2/20/2015.
 */
public class BoundaryChecker implements Runnable{

    private double THRESHOLD = 0.3;//percentage of points with rain before considered "covered"

    private int CLEAR = -16777216;

    private Boolean boundChecked;

    private int[][] myWeatherArray;

    public Boundary getMyBoundary() {
        return myBoundary;
    }

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
        myBoundary = new Boundary(0,0,1);
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
        double count = 0.0;

        //TODO

        for (int i = 0; i < size; i++) {
            Point p = (Point)theList.getObject(i);

            int pixel = myWeatherArray[p.getMyX()][p.getMyY()];

            if (pixel != CLEAR) {
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

        double percent = 0.0;

        int size = myBoundary.getQuads().length;

        for (int i = 0; i < size; i++) {

            double temp = checkPercent(this.myBoundary.getQuads()[i]);
            percent += temp;

        }

        boundChecked = true;


        coveragePercent = percent/8.0;

        return percent/8.0;
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

    public int getCLEAR() {
        return CLEAR;
    }

    public void setCLEAR(int CLEAR) {
        this.CLEAR = CLEAR;
    }

    @Override
    public void run() {

        fullCheckOuter();

    }

    public double getCoveragePercent() {
        return coveragePercent;
    }
}
