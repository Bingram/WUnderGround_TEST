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
        Double myMinLat,myMinLong,myMaxLat,myMaxLong;

        //calculate max/min using width for URL request
        myMaxLat = myLat+width;
        myMaxLong = myLong+width;
        myMinLat = myLat-width;
        myMinLong = myLong-width;


        String imageSource = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=" + myMaxLat +
                "&maxlon=" + myMaxLong + "&minlat=" + myMinLat + "&minlon=" + myMinLong + "&width=640&height=480&rainsnow=1&timelabel=1&timelabel.x=525&timelabel.y=41&reproj.automerc=1";

        try {
            //attempt to grab image, file test.png is output
            myGrabber.getImageFromURL(imageSource);

            //test for output in myGrabber

            int w = myGrabber.getMyImageWidth();
            int h = myGrabber.getMyImageHeight();

            int[][] testArray = myGrabber.getImageArray();

            System.out.println();
            System.out.println("TestValue@topLeft: "+testArray[0][0]);
            System.out.println();
            System.out.println("TestValue@bottomLeft: "+testArray[h][0]);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
