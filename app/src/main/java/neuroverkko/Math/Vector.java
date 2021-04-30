package neuroverkko.Math;

import java.util.Arrays;

public class Vector {

    private double[] data;

    /**
     * takes a list of doubles 1.0, 2.0...
     * as input and turns it into a 1d
     * array which is stored as the data
     * variable.
     * @param d
     */
    public Vector(double... d) {
        this.data = d;
    }

    public Vector(int... d) {
        this.data = Arrays.stream(d).asDoubleStream().toArray();
    }

    public Vector(int size) {
        data = new double[size];
    }

    public double[] getData() {
        return this.data;
    }
    
}
