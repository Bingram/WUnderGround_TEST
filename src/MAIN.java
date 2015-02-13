import java.io.IOException;

/**
 * Created by Brian on 2/11/2015.
 */

/*
* taken/inspired from http://stackoverflow.com/a/9470843
* */
public class MAIN {



    public static void main(String[] args){

        imageGrabber myGrabber = new imageGrabber();


        Double myLat = 47.6334;
        //GPS accuracy within 19m
        Double myLong = -122.70609;
        //not calculating accuracy for min/max
        //tested....now calculating for the min/max
        Double accuracy = 19.0;
        Double width = accuracy/2;
        //old accuracy wrong, i now know the values used in
        //the URL are for setting the bounds not the center

        //new margins based on testing using Googlemaps
        Double marginLong = 3.0;
        Double marginLat = 2.1;

        Double myMinLat,myMinLong,myMaxLat,myMaxLong;

        //calculate max/min using width for URL request
        myMaxLat = myLat+marginLat;
        myMaxLong = myLong+marginLong;
        myMinLat = myLat-marginLat;
        myMinLong = myLong-marginLong;


        String imageSourceClear = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=640&height=480&rainsnow=1&timelabel=1&timelabel.x=525&timelabel.y=41&reproj.automerc=1";

        String imageSourceFull = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=640&height=480&newmaps=1";

        //System.out.println("Clear Image URL: "+imageSourceClear);
        System.out.println("Full Image URL: "+imageSourceFull);
        try {
            //attempt to grab image, file test.png is output
            //myGrabber.getImageFromURL(imageSourceClear);
            myGrabber.getImageFromURL(imageSourceFull);

            //NOTE
            /*The scale for lat is 1' for ~111,000m@4decimals
            * The scale for long is 1' for ~76,000m@5decimals
            * .0001' lat = 11.1m~33ft
            * .00001' long = 0.76m~2.28ft
            * pixels currently @480 = .00875' lat per pixel
            * pixels currently @640 = .009375' long per pixel*/

            /*//test for output in myGrabber

            int w = myGrabber.getMyImageWidth();
            int h = myGrabber.getMyImageHeight();

            int[][] testArray = myGrabber.getImageArray();

            System.out.println();
            System.out.println("TestValue@topLeft: "+testArray[0][0]);
            System.out.println();
            System.out.println("TestValue@bottomLeft: "+testArray[h][0]);*/

            //Testing found that the alpha channel value for non alpha values
            //equals -16777216 which translates to a clear pixel or no weather

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
