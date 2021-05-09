package neuroverkko.Utils;

import java.util.Random;

public class RandomNumberGenerator {

    public static long seed = 0;
    public static Random r;

    public static double getRandom() {
        if (r == null) {
            r = new Random(seed);
        }
        return r.nextDouble();
    }

    public void setSeed(long seed) {
        seed = seed;
        r.setSeed(seed);
    }

    public static double generateBetween(double min, double max) {
        if (r == null) {
            r = new Random(seed);
        }
        if (max < min) {
            return min;
        }
        return min+(r.nextDouble()*(max-min));
    }
    
}
