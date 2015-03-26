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

    public Boundary getMyBoundary() {
        return myBoundary;
    }

    /**
     * Iterates through the given PointList
     * and counting the number of non CLEAR
     * pixels.
     *
     * THRESHOLD is a double that needs to
     * be set, default is 1.0, this value is
     * a decimal value with a max of 1.0
     *
     * @param theList PointList to iterate
     * @return Double percent of points not CLEAR
     */
    private double checkPercent(PointList theList){
        double result = 0.0;
        int size = theList.size();
        double count = 0.0;

        //TODO

        Point p = new Point();
        int pixel;

        for (int i = 0; i < size; i++) {
            p = (Point)theList.getObject(i);


            int x = p.myX;
            int y = p.myY;

            pixel = myWeatherArray[y][x];

            if (pixel != CLEAR && count<=(size/THRESHOLD*10)) {
                count++;
            } else if (count>(size/THRESHOLD*10)){//check if Threshold met by mutiplying by 10 to make int
                i = size;
            }

        }

        result += (count/size)*100.0;

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

    public double[] quadCoverage(){
        double[] result = new double[8];

        for (int i = 0; i < 8; i++) {
            result[i] = checkPercent(myBoundary.getQuads()[i]);

        }

        return result;
    }

    /**
     * Attempts to check all the quadrants of the current boundary
     * Returning the average coverage of all 8 quads.
     * @return
     */
    public double fullCheckOuter(){

        double percent = 0.0;

        int size = myBoundary.getQuads().length;

        for (int i = 0; i < size; i++) {

            double temp = checkPercent(myBoundary.getQuads()[i]);
            System.out.println("Current Quad: " + i + " Coverage: " + temp);
            percent += temp;

        }

        boundChecked = true;


        coveragePercent = percent;

        return percent;
    }

    public void updateWeather(int[][] newWeather){
        myWeatherArray = newWeather;
    }

    public void setMyBoundary(Boundary theBound){
        myBoundary = theBound;
    }

    public void setTHRESHOLD(double newThreshold) {
        THRESHOLD = newThreshold;
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
