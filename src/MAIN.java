import java.io.IOException;

/**
 * Created by Brian on 2/11/2015.
 */

/*
* taken/inspired from http://stackoverflow.com/a/9470843
* */
public class MAIN {

    private imageGrabber myImageGrabber;

    public static void main(String[] args){

       imageGrabber myGrabber = new imageGrabber();

        String imageSource = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=47.709&maxlon=-69.263&minlat=31.596&minlon=-97.388&width=640&height=480&rainsnow=1&timelabel=1&timelabel.x=525&timelabel.y=41&reproj.automerc=1";

        try {
            myGrabber.getImageFromURL(imageSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
