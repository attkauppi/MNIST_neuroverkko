package neuroverkko.Utils;

import java.util.Random;

public class RandomNumberGenerator {

    public static long seed = 1;
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
    
}
