import DataStructures.CircleBoundary;
import DataStructures.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Brian on 2/12/2015.
 *
 * Creates a 2D array of integer values from an image given a URL.
 */
public class weatherMap {

    private int[][] myOriginalConvertedImageArray;
    private int[][] myWorkingImageArray;
    private LinkedList<CircleBoundary> myCircles;
    private int myImageWidth,myImageHeight;
    private BufferedImage myOriginal;

    private String myName;

    private aRGBConverter myConverter;

    private weatherMap(){

    }

    public weatherMap(String theName){
        myOriginalConvertedImageArray = new int[0][0];
        myConverter = new aRGBConverter();
        myCircles = new LinkedList<CircleBoundary>();
        myOriginal = null;
        myName = theName;
    }

    public void getImageFromURL(String theUrl, CircleBoundary theCircle) throws IOException {

        URL imageURL = new URL(theUrl);

        BufferedImage originalImage = myOriginal= ImageIO.read(imageURL);

        if(originalImage==null){
            System.out.println("no image returned");

        } else {
            myOriginalConvertedImageArray = myConverter.get2DArray(originalImage);

            myWorkingImageArray = myOriginalConvertedImageArray.clone();

            addBoundary(theCircle);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", baos );

        //Persist - in this case to a file
        FileOutputStream fos = new FileOutputStream(new File("test.png"));
        baos.writeTo(fos);
        fos.close();

    }

    public void addBoundary(CircleBoundary circle){
        BufferedImage temp = myOriginal;

        myCircles.add(circle);

        //modify temp image with boundary
        LinkedList<Point> tempList = circle.getMyPoints();

        //Draw a line column @ width
        int height = myOriginal.getHeight();
        int width = myOriginal.getWidth();

        for(int i = 0; i < height; i++){
            temp.setRGB(width/3,i,Color.RED.getRGB());
        }
        for(int i = 0; i < width; i++){
            temp.setRGB(i,height/5,Color.RED.getRGB());
        }

        for(int i = 0; i < tempList.size(); i++){
                Point p = tempList.get(i);

                int x = p.getMyX();
                int y = p.getMyY();

                try {

                    temp.setRGB(x, y, Color.RED.getRGB());
                    myWorkingImageArray[x][y] = Color.RED.getRGB();
                } catch (Exception e){
                    System.err.println("Array size Width: " + myWorkingImageArray[0].length +" and Height: " + myWorkingImageArray.length);
                    System.err.println();
                    System.err.println("Error: "+e.toString()+" at point: " + p.getMyX()+"," +p.getMyY());
                    System.err.println("Error: "+e.toString()+" at point: " + x+"," +y);
                    System.err.println();
                    System.err.println("");
                    System.err.println();
                }


        }


        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(temp, "png", baos );

            //Persist - in this case to a file
            FileOutputStream fos = new FileOutputStream(new File("CurrentBounds - "+myName+".png"));
            baos.writeTo(fos);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
