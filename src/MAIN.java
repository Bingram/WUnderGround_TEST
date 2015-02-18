import DataStructures.CircleBoundary;
import DataStructures.Point;

import java.io.IOException;

/**
 * Created by Brian on 2/11/2015.
 */

/*
* taken/inspired from http://stackoverflow.com/a/9470843
* */
public class MAIN {

    private static weatherMap myGrabber = new weatherMap("Washington");

    private static Double myLat = 47.6334;

    private static Double myLong = -121.70609;

    //new margins based on testing using Googlemaps
    /*private static Double marginLong = 3.0;
    private static Double marginLat = 2.1;*/

    private static Double myMaxLat = 49.0;//Just North of border WA
    private static Double myMaxLong = -116.8;//Just East of border WA
    private static Double myMinLat = 42.6;//Halfway into OR South
    private static Double myMinLong = -125.5;//A few miles of West coast WA

    private static Double degreesWide,degreesTall,scaleLat,scaleLon;

    private static Point myCenter;

    private static CircleBoundary radiusOne,radiusTwo;

    private static int mapWidth,mapHeight;

    public static void main(String[] args){

        mapWidth = 643;
        mapHeight = 643;

        degreesWide = myMaxLong - myMinLong;//8.7
        degreesTall = myMaxLat - myMinLat;//6.4

        scaleLon = degreesWide/mapWidth;
        scaleLat = degreesTall/mapHeight;

        String imageSourceClear = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=" + mapWidth + "&height=" + mapHeight + "&rainsnow=1&timelabel=1&timelabel.x=525&timelabel.y=41&reproj.automerc=1";

        String imageSourceFull = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=" + mapWidth + "&height=" + mapHeight + "&newmaps=1";

        System.out.println("Clear Image URL: "+imageSourceClear);
        System.out.println("Full Image URL: "+imageSourceFull);
        try {
            //attempt to grab image, file test.png is output

            myCenter = gpsToXY(myLong,myLat);

            radiusOne = new CircleBoundary(myCenter.getMyY(),myCenter.getMyX(),100);
            radiusTwo = new CircleBoundary(255,136,75);

            myGrabber.getImageFromURL(imageSourceFull);
            myGrabber.addBoundary(radiusTwo);
            myGrabber.addBoundary(radiusOne);
            //myGrabber.getImageFromURL(imageSourceClear,radiusOne);

            //Testing found that the alpha channel value for non alpha values
            //equals -16777216 which translates to a clear pixel or no weather

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean checkWeatherQuad(){
        boolean result = false;



        return result;
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
    private static Point gpsToXY(Double lon, Double lat){
        Point temp = new Point();

        Double latDiff = myMaxLat - lat;
        Double lonDiff = lon - myMinLong;

        System.out.println("Inside gpsToXY...");
        System.out.println("Input: Lat|" + lat + " Lon|" + lon);
        System.out.println("Start Point: Lat|" + myMaxLat + " Lon|" + myMinLong);
        System.out.println("Difference: Lat|" + latDiff + " Lon|" + lonDiff);

        temp.setMyX((int) ((latDiff) / scaleLat));
        temp.setMyY((int) ((lonDiff) / scaleLon));

        System.out.println("XY Values: X|" + temp.getMyX() + " Y|" + temp.getMyY());
        System.out.println();

        return temp;
    }
}
