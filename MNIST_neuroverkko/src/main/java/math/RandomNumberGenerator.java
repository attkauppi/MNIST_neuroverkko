/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.Random;

/**
 *
 * @author ari
 */
public class RandomNumberGenerator {
    
    public static long seed=0;
    public static Random r;
    
    /**
     * GenerateNext
     * @return palauttaa uuden sattumanvaraisen numeron
     */
    public static double generateNext() {
        if (r == null) {
            r = new Random(seed);
        }
        return r.nextDouble();
    }
    
    /**
     * setSeed
     * Asettaa uuden seed-arvon generaattorille
     */
    public static void setSeed(long seed) {
        seed = seed;
        r.setSeed(seed);
    }
    
}
