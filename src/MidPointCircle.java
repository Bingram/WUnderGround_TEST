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
        drawCircle(320,240,23);
    }

    private void drawCircle(final int centerX, final int centerY, final int radius) {
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;
        Color circleColor = Color.white;

        do {
            image.setPixel(centerX + x, centerY + y, circleColor);
            image.setPixel(centerX + x, centerY - y, circleColor);
            image.setPixel(centerX - x, centerY + y, circleColor);
            image.setPixel(centerX - x, centerY - y, circleColor);
            image.setPixel(centerX + y, centerY + x, circleColor);
            image.setPixel(centerX + y, centerY - x, circleColor);
            image.setPixel(centerX - y, centerY + x, circleColor);
            image.setPixel(centerX - y, centerY - x, circleColor);
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
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
