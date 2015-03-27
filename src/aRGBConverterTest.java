import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Brian on 3/26/2015.
 */
public class aRGBConverterTest extends TestCase {

    private String THE_URL = "http://api.wunderground.com/api/abc6b9854cebd997/radar/image.png?maxlat=49.0&maxlon=-116.8&minlat=42.6&minlon=-125.5&width=1280.0&height=1280.0&rainsnow=1&reproj.automerc=1";

    private BufferedImage theImage;

    private aRGBConverter theConverter;

    public void setUp() throws Exception {
        super.setUp();

        if (theImage == null){
            theImage = getImageFromURL(THE_URL);
        }

        theConverter = new aRGBConverter();

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

    public void testGet2DArray() throws Exception {

        int[][] original = theConverter.get2DArray(theImage);
        int[][] newVer1 = theConverter.convertTo2DWithoutUsingGetRGB(theImage);
        int[][] bruteVer1 = theConverter.convertTo2DBRUTEFORCE(theImage);

        assertEquals(original[255][24], bruteVer1[255][24]);

    }

    public void testConvertTo2DWithoutUsingGetRGB() throws Exception {

    }
}