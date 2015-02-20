import DataStructures.CircleBoundary;
import DataStructures.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Brian on 2/12/2015.
 *
 * Creates a 2D array of integer values from an image given a URL.
 */
public class weatherMap {

    private int[][] myOriginalConvertedImageArray;//Working array of pixels for weather
    private int[][] myWorkingImageArray;//Working array of pixels for weather
    private LinkedList<CircleBoundary> myCircles;//TODO weatherMap only needs ONE CircleBoundary
    private int myImageWidth,myImageHeight;
    private BufferedImage myMapImage,boundaryImage;

    private String myName;

    private aRGBConverter myConverter;

    private weatherMap(){
        myImageWidth = 1;
        myImageHeight = 1;
        myOriginalConvertedImageArray = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        myCircles = new LinkedList<CircleBoundary>();
        myMapImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        myName = "BLANK";
    }

    public weatherMap(String theName, int theWidth, int theHeight){
        myImageWidth = theWidth;
        myImageHeight = theHeight;
        myOriginalConvertedImageArray = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        myCircles = new LinkedList<CircleBoundary>();
        myMapImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        myName = theName;
    }

    public void getImageFromURL(String theUrl, String theName) throws IOException {

        try {

            URL imageURL = new URL(theUrl);

            myMapImage = ImageIO.read(imageURL);

            myOriginalConvertedImageArray = myConverter.get2DArray(myMapImage);

            writeImageFile(myMapImage,theName);

            boundaryImage = readImageFile(theName + ".png");

        } catch (Exception e){
            System.out.println("no image returned");
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }


    }

    //temporary limitation of 100 px radius
    public void addBoundary(CircleBoundary circle){

        BufferedImage temp = boundaryImage;

        myCircles.add(circle);

        //modify temp image with boundary
        LinkedList<Point> tempList = circle.getMyPoints();


        for(int i = 0; i < tempList.size(); i++){
            Point p = tempList.get(i);

            int x = p.getMyX();
            int y = p.getMyY();


            myOriginalConvertedImageArray[x][y] = Color.RED.getRGB();

            try {

                temp.setRGB(x, y, Color.RED.getRGB());

            } catch (Exception e){
                System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                        "encountered an Error \nERROR:: " + e.toString());

            }

        }


        try {

            writeImageFile(temp,"CurrentBounds - " + myName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void writeImageFile(BufferedImage theImage,String fileName) throws IOException{
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(theImage, "png", baos );

            //Persist - in this case to a file for later reference
            FileOutputStream fos = new FileOutputStream(new File(fileName+".png"));
            baos.writeTo(fos);
            fos.close();
        } catch (Exception e){
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

    }

    public BufferedImage readImageFile(String fileName) throws IOException{

        BufferedImage temp = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);

        try {

            // open image
            File imageFile = new File(fileName);
            temp = ImageIO.read(imageFile);

        } catch (Exception e){
            System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                    "encountered an Error \nERROR:: " + e.toString());
        }

        return temp;
    }

    private void getImageSize(BufferedImage image){
        myImageWidth = image.getWidth();
        myImageHeight = image.getHeight();
    }

    /**
     * Does this do a deep copy?
     *
     * @param theImage BufferedImage to copy?
     * @return BufferedImage deep copy?
     */
    private BufferedImage copyImage(BufferedImage theImage){
        BufferedImage temp = new BufferedImage(myImageWidth,myImageHeight, theImage.getType());

        int row,col,width,height;
        width = theImage.getWidth();
        height = theImage.getHeight();

        for(row = col = 0; row < height; ){
            temp.setRGB(row,col, theImage.getRGB(row,col));
            col++;
            if(col == width){
                col = 0;
                row++;
            }

        }

        return temp;
    }

    public int[][] getImageArray(){
        return myOriginalConvertedImageArray;
    }

    public int getMyImageWidth() {
        return myImageWidth;
    }

    public int getMyImageHeight() {
        return myImageHeight;
    }
}
