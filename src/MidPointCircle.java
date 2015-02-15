/**
 * Created by Brian on 2/14/2015.
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MidPointCircle {
    private BasicBitmapStorage image;

    public MidPointCircle(final int imageWidth, final int imageHeight) {
        this.image = new BasicBitmapStorage(imageWidth, imageHeight);

        drawCircle(320,240,200);
    }

    private void drawCircle(final int centerX, final int centerY, final int radius) {
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        Color circleColor0 = Color.white;
        Color circleColor1 = Color.blue;
        Color circleColor2 = Color.green;
        Color circleColor3 = Color.red;
        Color circleColor4 = Color.yellow;
        Color circleColor5 = Color.pink;
        Color circleColor6 = Color.cyan;
        Color circleColor7 = Color.magenta;

        do {
            image.setPixel(centerX + x, centerY + y, circleColor0);
            image.setPixel(centerX + x, centerY - y, circleColor1);
            image.setPixel(centerX - x, centerY + y, circleColor2);
            image.setPixel(centerX - x, centerY - y, circleColor3);
            image.setPixel(centerX + y, centerY + x, circleColor4);
            image.setPixel(centerX + y, centerY - x, circleColor5);
            image.setPixel(centerX - y, centerY + x, circleColor6);
            image.setPixel(centerX - y, centerY - x, circleColor7);

            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
                //DIAG
                System.out.println("Y-Value: " + y);
            }
            x++;
        } while (x <= y);

    }

    public void createImageFile(){

        try {
            // retrieve image
            BufferedImage bi = image.getImageBuff();
            File outputfile = new File("saved.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
