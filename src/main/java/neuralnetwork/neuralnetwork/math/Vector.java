package neuralnetwork.neuralnetwork.math;


import java.util.Arrays;
import java.util.stream.IntStream;
/**
 * Vector class is used for
 * doing linear algebra and getting
 * information about vectors being used.
 */
public class Vector {

    private double[] data;

    /**
     * Takes an array of double values, streams
     * through it and turns it into an array, which
     * is stored as this.data.
     * @param d (array)
     */
    public Vector(double... d) {
        this.data = d;
    }

    /**
     * Takes an array of ints as parameter d and casts
     * them into doubles.
     * @param d - array of ints
     */
    public Vector(int... d) {
        this.data = Arrays.stream(d).asDoubleStream().toArray();
    }

    public Vector(int size) {
        data = new double[size];
    }

    public double[] getData() {
        return this.data;
    }
    
    /**
     * 
     * @return dimensions (int) of the vector.
     */
    public int getDimensions() {
        return this.data.length;
    }

    /**
     * dotProduct
     * Calculates a dot product of a vector and
     * another vector given as a parameter
     * @param other vector
     * @return dot product result (double)
     */
    public double dotProduct(Vector other) {
        return IntStream.range(0, this.getDimensions())
            .mapToDouble(k -> this.data[k]*other.data[k])
            .sum();
    }

    /**
     * map
     * 
     * A utility method used to apply different functions to
     * vector objects, such as vector multiplication.
     * @param f - a function object
     * @return Vector - the result of the function being applied
     */
    public Vector map(Function f) {
        double[] res = IntStream.range(0, this.getDimensions())
            .mapToDouble(k -> f.apply(this.data[k])).toArray();
        Vector v = new Vector(res);
        return v;
    }

    /**
     * scalarAdd
     * 
     * Adds a scalar to a vector
     * 
     * @param scalar (double)
     * @return Vector
     */
    public Vector scalarAdd(double scalar) {
        return map(value -> value - scalar);
    }

    /**
     * scalarSubtr
     * 
     * Subtracts a scalar given as a parameter from a vector.
     * 
     * @param scalar (double)
     * @return Vector subtracted by scalar.
     */
    public Vector scalarSubtr(double scalar) {
        return map(value -> value - scalar);
    }

    /**
     * scalarMultiplication
     * @param s double used to scale the vector
     * @return vector scaled by s
     */
    public Vector scalarProd(double s) {
        return map(value -> s * value);
    }

    ///// Vector on Vector operations

    /**
     * vecAdd
     * 
     * Sum of two vectors
     * 
     * @param other (Vector)
     * @return Vector summed with (Vector) other
     */
    public Vector vecAdd(Vector other) {
        double[] res = IntStream.range(0, this.getDimensions())
            .mapToDouble(k -> this.data[k]+other.data[k]).toArray();
        return new Vector(res);
    }

    /**
     * vecProduct
     * 
     * Calculates a vector product of 2 vectors
     * @param other vector
     * @return vector resulting from this*other
     */
    public Vector vecElementProduct(Vector other) {
        double[] res = IntStream.range(0, this.getDimensions())
            .mapToDouble(k -> this.data[k]*other.getData()[k]).toArray();
        return new Vector(res);
    }

    /**
     * vecSubtraction
     * 
     * Subtracts by a given vector
     * @param other vector
     * @return vector subtracted by other
     */
    public Vector vecSubtraction(Vector other) {
        return new Vector(IntStream.range(0, this.getDimensions())
            .mapToDouble(k -> this.data[k]-other.data[k])
            .toArray());
    }

    /**
     * getMax 
     * @return largest double in the vector array
     */
    public double getMax() {
        return Arrays.stream(this.data).reduce(Double.MIN_VALUE, Math::max);
    }

    public int getIndexOfMaxElement() {
        int maxIndex = 0;
        for (int i = 1; i < this.getDimensions(); i++) {
            if (this.data[i] > this.data[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * outerProduct
     * 
     * This is equivalent to multiplying this^T * other,
     * which results in a matrix.
     * @param other (vector)
     * @return Matrix
     */
    public Matrix outerProdut(Vector other) {
        double[][] d = new double[this.getDimensions()][other.getDimensions()];

        for (int i = 0; i < this.getDimensions(); i++) {
            for (int j = 0; j < other.getDimensions(); j++) {
                // Notice, how the indices flipped around in setting
                // d's index. This is because the other vector needs to
                // be transposed and this is equivalent to doing so.
                d[j][i] = this.data[i] * other.data[j];
            }
        }
        return new Matrix(d);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Vector)) {
            return false;
        }

        Vector vother = (Vector) other;

        return Arrays.equals(this.data, vother.data);
    }

    @Override
    public String toString() {
        return "Vector: { " + Arrays.toString(this.data) + " }";
    }

}
