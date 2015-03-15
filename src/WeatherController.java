import DataStructures.Boundary;
import DataStructures.Point;

import java.io.IOException;

/**
 * Created by Brian on 3/12/2015.
 */
public class WeatherController implements Runnable{

    private final Double MAPDIMENSION = 1280.0;
    private static Double myMaxLat = 49.0;//Just North of border WA
    private static Double myMaxLong = -116.8;//Just East of border WA
    private static Double myMinLat = 42.6;//Halfway into OR South
    private static Double myMinLong = -125.5;//A few miles of West coast WA

    private int WAIT_TIME,MINUTES;

    private String BASE_URL = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat="+ myMaxLat +
            "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=" + MAPDIMENSION + "&height=" + MAPDIMENSION;
    private String clearURL = BASE_URL + "&rainsnow=1&reproj.automerc=1";
    private String bgURL = BASE_URL + "&newmaps=1";

    private Point myPoint;

    private weatherMap myWeatherMap;

    public WeatherController(String name){
        myWeatherMap = new weatherMap(name,MAPDIMENSION.intValue(),MAPDIMENSION.intValue());
        myWeatherMap.setClearURL(clearURL);
        myWeatherMap.setBGURL(bgURL);
        myPoint = new Point();
        MINUTES = 10;
        WAIT_TIME = MINUTES*60*1000;
    }

    public void setBoundary(double theLong, double theLat, int theRadius){
        Point tempPoint = gpsToXY(theLong,theLat);

        if (theRadius > 100){theRadius = 100;}//ensure radius is no more than 100

        Boundary temp = new Boundary(tempPoint.getMyY(),tempPoint.getMyX(),theRadius);

        myWeatherMap.addBoundary(temp);
    }

    public void setINTBoundary(int theX, int theY, int theRadius){

        if (theRadius > 100){theRadius = 100;}//ensure radius is no more than 100

        Boundary temp = new Boundary(theX,theY,theRadius);

        myWeatherMap.addBoundary(temp);
    }

    /*The scale for lat is 1' for ~111,000m@4decimals
    * The scale for long is 1' for ~76,000m@5decimals
    * .0001' lat = 11.1m~33ft
    * .00001' long = 0.76m~2.28ft
    * pixels currently @480 = .00875' lat per pixel
    * pixels currently @640 = .009375' long per pixel
    *
    * lat is x
    * lon is y*/
    private Point gpsToXY(Double lon, Double lat){
        Point temp = new Point();

        Double degreesWide = myMaxLong - myMinLong;//8.7
        Double degreesTall = myMaxLat - myMinLat;//6.4

        Double scaleLon = degreesWide/MAPDIMENSION;
        Double scaleLat = degreesTall/MAPDIMENSION;

        Double latDiff = myMaxLat - lat;
        Double lonDiff = lon - myMinLong;

        temp.setMyX((int) ((latDiff) / scaleLat));
        temp.setMyY((int) ((lonDiff) / scaleLon));

        return temp;
    }

    public void setWaitTime(int time) {
        MINUTES = time;
        WAIT_TIME = MINUTES * 60 * 1000;
    }

    @Override
    public void run() {

        while (true){
            try {


                myWeatherMap.run();


                System.out.println();
                System.out.println("Current Coverage % : "+myWeatherMap.getCoverage());


                Thread.sleep(WAIT_TIME);//sleep 10 minutes

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
