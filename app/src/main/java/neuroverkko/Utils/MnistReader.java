package neuroverkko.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class MnistReader {

    public static int[] getLabels(Path labelsFile) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(decompress(Files.readAllBytes(labelsFile)));

        if (bb.getInt() != 2049) {
            throw new IOException("Not a labels file");
        }

        int numLabels = bb.getInt();
        int[] labels = new int[numLabels];

        for (int i = 0; i < numLabels; i++) {
            labels[i] = bb.get() & 0xFF;
        }

        return labels;
    }

    public static List<int[][]> getImages(Path imagesFile) throws IOException {
        ByteBuffer bb = ByteBuffer.wrap(decompress(Files.readAllBytes(imagesFile)));
        if (bb.getInt() != 2051) {
            throw new IOException("Not an images file");
        }

        int numImages = bb.getInt();
        int numRows = bb.getInt();
        int numColumns = bb.getInt();
        List<int[][]> images = new ArrayList<>();

        for (int i = 0; i < numImages; i++) {
            int[][] image = new int[numRows][];
            for (int row = 0; row < numRows; row++) {
                image[row] = new int[numColumns];
                for (int col = 0; col < numColumns; col++) {
                    image[row][col] = bb.get() & 0xFF;
                }
            }
            images.add(image);
        }

        return images;
    }

    public static byte[] decompress(final byte[] input) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(input);
            GZIPInputStream gis = new GZIPInputStream(bais);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                byte[] buf = new byte[8192];
                int n;
                while ((n = gis.read(buf)) > 0) {
                    out.write(buf, 0, n);
                }
                return out.toByteArray();
            }
            
    }

    
}
