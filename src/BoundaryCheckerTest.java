import DataStructures.Boundary;
import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BoundaryCheckerTest extends TestCase {

    private BoundaryChecker myChecker;

    private Boundary myBound;

    private int[][] myArray;

    private String NAME = "BoundaryXTest";

    private String THE_URL = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=49.0&maxlon=-116.8&minlat=42.6&minlon=-125.5&width=1280.0&height=1280.0&rainsnow=1&reproj.automerc=1";

    private BufferedImage theImage;

    private aRGBConverter theConverter;


    public void setUp() throws Exception {
        super.setUp();

        if (theImage == null){
            theImage = getImageFromURL(THE_URL);
        }

        theConverter = new aRGBConverter();

        myBound =  new Boundary(410,273,100);

        myChecker = new BoundaryChecker();
        myChecker.setMyBoundary(myBound);

    }

    public BufferedImage getImageFromURL(String theUrl) throws IOException {

        BufferedImage tempImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);

        //TODO add filtering for wunderground URL only? OR leave vanilla?
        try {

            URL imageURL = new URL(theUrl);

            tempImage = ImageIO.read(imageURL);

        } catch (Exception e){
            System.out.println("no image returned");
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

        return tempImage;

    }

    public void testArrayValues() throws Exception{
        int[][] original = theConverter.get2DArray(theImage);

        int[][] bruteVer1 = theConverter.convertTo2DBRUTEFORCE(theImage);

        myChecker.updateWeather(bruteVer1);

        int[][] temp = myChecker.getWeather();

        assertEquals(temp[256][1000], original[256][1000]);

        assertEquals(temp[1000][1000], bruteVer1[1000][1000]);

    }

    public void testGetMyBoundary() throws Exception {

    }

    public void testQuickCheckOuter() throws Exception {

    }

    public void testCheckBoundaryQuad() throws Exception {

    }

    public void testFullCheckOuter() throws Exception {

    }

    public void testUpdateWeather() throws Exception {

    }

    public void testSetMyBoundary() throws Exception {

    }

    public void testSetTHRESHOLD() throws Exception {

    }

    public void testGetCLEAR() throws Exception {

    }

    public void testSetCLEAR() throws Exception {

    }

    public void testGetCoveragePercent() throws Exception {

    }
}