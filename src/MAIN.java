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

    private static Double myLong = -122.70609;

    //new margins based on testing using Googlemaps
    private static Double marginLong = 3.0;
    private static Double marginLat = 2.1;

    private static Double myMinLat,myMinLong,myMaxLat,myMaxLong;

    private static Point myCenter;

    private static CircleBoundary radiusOne;

    public static void main(String[] args){

        //calculate max/min using width for URL request
        myMaxLat = myLat+marginLat;
        myMaxLong = myLong+marginLong;
        myMinLat = myLat-marginLat;
        myMinLong = myLong-marginLong;

        String imageSourceClear = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=640&height=480&rainsnow=1&timelabel=1&timelabel.x=525&timelabel.y=41&reproj.automerc=1";

        String imageSourceFull = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=640&height=480&newmaps=1";

        System.out.println("Clear Image URL: "+imageSourceClear);
        System.out.println("Full Image URL: "+imageSourceFull);
        try {
            //attempt to grab image, file test.png is output

            myCenter = gpsToXY(myLat, myLong);

            radiusOne = new CircleBoundary(myCenter.getMyY(),myCenter.getMyX(),100);

            myGrabber.getImageFromURL(imageSourceFull,radiusOne);
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
    * lat is y
    * lon is x*/
    private static Point gpsToXY(Double lat, Double lon){
        Point temp = new Point();

        Double xLonOffset = 0.009375;
        Double yLatOffset = 0.00875;

        System.out.println("Inside gpsToXY...");
        System.out.println("Input: Lat|" + lat + " Lon|" + lon);
        System.out.println("Start Point: Lat|" + myMaxLat + " Lon|" + myMinLong);
        System.out.println("Difference: Lat|" + (myMaxLat-lat) + " Lon|" + (lon - myMinLong));

        temp.setMyX((int) ((myMaxLat - lat)/yLatOffset));
        temp.setMyY((int) ((lon - myMinLong) / xLonOffset));

        System.out.println("XY Values: X|" + temp.getMyX() + " Y|" + temp.getMyY());
        System.out.println();

        return temp;
    }
}
