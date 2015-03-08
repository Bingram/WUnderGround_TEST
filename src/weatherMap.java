import DataStructures.*;
import DataStructures.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Brian on 2/12/2015.
 *
 * Creates a 2D array of integer values from an image given a URL.
 */
public class weatherMap {

    private int[][] currentWeather;
    private BoundaryBundle myBoundaries;
    private int myImageWidth,myImageHeight;
    private BufferedImage boundaryImage;

    private String mapName,bgName,clearName;

    private aRGBConverter myConverter;

    private weatherMap(){
        myImageWidth = 1;
        myImageHeight = 1;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        myBoundaries = new BoundaryBundle();
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = "BLANK";
        bgName = mapName + " - BG";
        clearName = mapName + " - CLEAR";
    }

    public weatherMap(String theName, int theWidth, int theHeight){
        myImageWidth = theWidth;
        myImageHeight = theHeight;
        currentWeather = new int[myImageWidth][myImageHeight];
        myConverter = new aRGBConverter();
        myBoundaries = new BoundaryBundle();
        boundaryImage = new BufferedImage(myImageWidth,myImageHeight, BufferedImage.TYPE_INT_ARGB);
        mapName = theName;
        bgName = mapName + " - BG";
        clearName = mapName + " - CLEAR";
    }

    public void updateWeatherArray(String theUrl) throws IOException {

        BufferedImage tempImage = getImageFromURL(theUrl);

        currentWeather = myConverter.get2DArray(tempImage);

        //TESTING
        writeImageFile(tempImage,clearName);

    }

    public void updateBG(String theUrl) throws IOException {

        boundaryImage = getImageFromURL(theUrl);

        writeImageFile(boundaryImage,bgName);

        updateBoundaryImage();

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

    /**
     * Draw outer boundary for current Bundle
     */
    private void updateBoundaryImage(){


        drawBound(myBoundaries.getBoundary(0));

        try {

            writeImageFile(boundaryImage,"CurrentBounds - " + mapName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void drawBound(Boundary theBound) {

        PointList thePoints = theBound.getMyPoints();

        Node current = thePoints.getNode(0);

        while(current.getMyNext() != null){
            Point p = (Point)current.getMyItem();

            int x = p.getMyX();
            int y = p.getMyY();

            try {

                boundaryImage.setRGB(x, y, Color.RED.getRGB());

            } catch (Exception e){
                System.err.println(this.getClass()+" call to "+ this.getClass().getEnclosingMethod() +
                        "encountered an Error \nERROR:: " + e.toString());

            }

            current = current.getMyNext();

        }

    }

    //temporary limitation of 100 px radius
    public void addBoundary(Boundary theBound){

        myBoundaries.addBoundary(theBound);

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

    public int getMyImageWidth() {
        return myImageWidth;
    }

    public int getMyImageHeight() {
        return myImageHeight;
    }
}
