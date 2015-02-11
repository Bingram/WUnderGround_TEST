import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by Brian on 2/11/2015.
 */

/*http://stackoverflow.com/a/9470843*/
public class aRGBConverter {

    public aRGBConverter(){

    }

    public int[][] get2DArray(BufferedImage image){

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];

        final int pixelLength = (hasAlphaChannel) ? 4 : 3;

        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            int argb = (hasAlphaChannel) ? 0 : -16777216;

            argb += ((int) pixels[pixel] & 0xff); // blue, no offset in result
            argb += (((int) pixels[pixel++] & 0xff) << 8); // green, shift left 8bits
            argb += (((int) pixels[pixel++] & 0xff) << 16); // red, shift left next 8bits

            result[row][col] = argb;
            col++;

            if (col == width) {//reached end of 'row'
                col = 0;
                row++;
            }
        }


        return result;

    }
}
