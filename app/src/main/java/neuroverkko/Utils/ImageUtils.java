package neuroverkko.Utils;

import java.util.Arrays;

/**
 * Training sets are sometimes expanded, in the case of
 * recogniznig digits at least, by, for example, rotating the
 * images in the existing dataset by a few degrees, -10 and +10.
 * Depending on how much you want to rotate your images, this is
 * pretty much equivalent to just shifting the rows or columns
 * of the image matrix up, down, right or left by amount n.
 * For example, a rotation of 11 degrees would have the result
 * as moving the pixel values 2 indices to the right and one down.
 * 
 * It's, however, far simpler to just implement a simple row/column
 * shift and the results are very similar to a modest rotation.
 */

public class ImageUtils {

    public static int[][] moveUp(int[][] inImage) {
        int[][] image = new int[inImage.length][inImage[0].length];

        int[] firstRow =new int[inImage[0].length];
        firstRow = Arrays.copyOfRange(inImage[0], 0, inImage[0].length);

        

        // Move rows 1-n up one row
        for (int row = 1; row < inImage.length; row++) {
            image[row-1] = Arrays.copyOfRange(inImage[row], 0, inImage[row].length);
        }

        // First row jumps as the last row
        image[image.length-1] = Arrays.copyOfRange(firstRow, 0, firstRow.length);
        
        return image;
    }

    public static int[][] moveDown(int[][] inImage) {
        int lastRowIn = inImage.length-1;

        int[][] image = new int[inImage.length][inImage[0].length];

        int[] lastRow =new int[inImage[0].length];
        lastRow = Arrays.copyOfRange(inImage[inImage.length-1], 0, inImage[inImage.length-1].length);

        // Move rows n-1 – 0 down one row
        // Move rows 1-n up one row
        for (int row = 1; row < inImage.length; row++) {
            image[row] = Arrays.copyOfRange(inImage[row-1], 0, inImage[row-1].length);
        }

        // First row jumps as the last row
        image[0] = Arrays.copyOfRange(lastRow, 0, lastRow.length);
        
        return image;
    }

    public static int[][] moveLeft(int[][] inImage) {
        int lastColumnIn = inImage[0].length-1;

        int[][] image = new int[inImage.length][inImage[0].length];

        int[] firstColumn =new int[inImage.length];
        firstColumn = Arrays.copyOfRange(inImage[0], 0, inImage[inImage.length-1].length);
        for (int row = 0; row < inImage.length; row++) {
            firstColumn[row] = inImage[row][0];
        }

        for (int col = 1; col < inImage.length; col++) {
            for (int row = 0; row < inImage[0].length; row++) {
                image[row][col-1] = inImage[row][col];
            }
        }

        for (int row = 0; row < image[0].length; row++) {
            image[row][image[0].length-1] = firstColumn[row];
        }

        return image;
    }

    /**
     * moveRight
     * 
     * Moves the pixels of an image one pixel to the right (the rightmost
     * edge of the image will jump and become the leftmost column of the
     * of the matrix, which corresponds to the image.) This operation is
     * similar to how np.roll works in numpy, when axis is 1 and offset -1
     * @param inImage (int[][])
     * @return image (int[][]) with the rightmost column moved to become
     * the leftmost column
     */
    public static int[][] moveRight(int[][] inImage) {
        int lastColumnIn = inImage[0].length-1;

        int[][] image = new int[inImage.length][inImage[0].length];

        int[] lastColumn =new int[inImage.length];
        for (int row = 0; row < inImage.length; row++) {
            lastColumn[row] = inImage[row][lastColumnIn];
        }

        for (int col = 1; col < inImage.length; col++) {
            for (int row = 0; row < inImage[0].length; row++) {
                image[row][col] = inImage[row][col-1];
            }
        }

        for (int row = 0; row < image[0].length; row++) {
            image[row][0] = lastColumn[row];
        }
        return image;
    }

}
