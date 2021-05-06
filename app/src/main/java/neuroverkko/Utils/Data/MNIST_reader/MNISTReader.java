package neuroverkko.Utils.Data.MNIST_reader;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class MNISTReader {

    // ArrayList<Double> uusi;
    public static final int LABEL_FILE_MAGIC_NUMBER = 2049;
    public static final int IMAGE_FILE_MAGIC_NUMBER = 2051;

    public static int[] getLabels(String filename) {
        ByteBuffer bb = loadFileToByteBuffer(filename);

        assertMagicNumber(LABEL_FILE_MAGIC_NUMBER, bb.getInt());

        int numLabels = bb.getInt();
        int[] labels = new int[numLabels];

        for (int i = 0; i < numLabels; i++) {
            labels[i] = bb.get() & 0xFF;
        }
        return labels;
    }

    public static List<int[][]> getImages(String filename) {
        ByteBuffer bb = loadFileToByteBuffer(filename);

        assertMagicNumber(IMAGE_FILE_MAGIC_NUMBER, bb.getInt());

        int numImages = bb.getInt();
        int numRows = bb.getInt();
        int numColumns = bb.getInt();

        List<int[][]> images = new ArrayList<>();

        for (int i = 0; i < numImages; i++) {
            images.add(readImage(numRows, numColumns, bb));
        }
        return images;
    }

    public static int[][] readImage(int numRows, int numColumns, ByteBuffer bb) {
        int[][] image = new int[numRows][];
        for (int row = 0; row < numRows; row++) {
            image[row] = readRow(numColumns, bb);
        }
        // System.out.println("Image length: " + image.length);
        // System.out.println("Image other length: " + image[0].length);
        return image;
    }

    public static int[] readRow(int numColumns, ByteBuffer bb) {
        int[] row = new int[numColumns];

        for (int col = 0; col < numColumns; col++) {
            row[col] = bb.get() & 0xFF;
        }

        return row;
    }

    public static void assertMagicNumber(int expMagicNumber, int magicNumber) {
        if (expMagicNumber != magicNumber) {
            switch (expMagicNumber) {
                case LABEL_FILE_MAGIC_NUMBER:
                    throw new RuntimeException("Not an image file");
                case IMAGE_FILE_MAGIC_NUMBER:
                    throw new RuntimeException("Not an image file");
                default:
                    throw new RuntimeException("Expected magic number " +  Integer.toString(expMagicNumber) +", found " + Integer.toString(magicNumber));
            }
         }
    }

    public static ByteBuffer loadFileToByteBuffer(String filename) {
        return ByteBuffer.wrap(loadFile(filename));
    }

    public static byte[] loadFile(String filename) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            FileChannel ch = file.getChannel();
            long fileSize = ch.size();
            ByteBuffer bb = ByteBuffer.allocate((int) fileSize);
            ch.read(bb);
            bb.flip();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            for (int i = 0; i < fileSize; i++) {
                bout.write(bb.get());
            }

            ch.close();
            file.close();
            return bout.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String renderImage(int[][] image) {
        StringBuffer sb = new StringBuffer();

        for (int row = 0; row < image.length; row++) {
            sb.append("|");
            for (int col = 0; col < image[row].length; col++) {
                int pixelValue = image[row][col];
                if (pixelValue > 0) {
                    sb.append(" ");
                } else if (pixelValue < 256/3) {
                    sb.append(".");
                } else if (pixelValue < 2*(256/3)) {
                    sb.append("x");
                } else {
                    sb.append("X");
                }
            }

            sb.append("|\n");
        }

        return sb.toString();
    }

    public String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            sb.append(s);
        }

        return sb.toString();
    }
    
}
