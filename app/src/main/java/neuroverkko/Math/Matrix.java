package neuroverkko.Math;

import java.util.Arrays;
import java.util.stream.IntStream;
import static java.lang.System.arraycopy;

public class Matrix {
    
    private double[][] data;
    public int rows;
    public int cols;

    public Matrix(double[][] d) {
        this.data = d;
        this.rows = d.length;
        this.cols = d[0].length;
    }

    public Matrix(int rows, int cols) {
        this(new double[rows][cols]);
    }

    /**
     * Fills in the matrix with the given 2d array
     */
    public Matrix(double[][] d, int rows, int cols) {
        this(new double[rows][cols]);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = (double) d[i][j];
            }
        }
    }

    public void fillWithZeros() {
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                this.getData()[row][col] = 0;
            }
        }
    }

    public static Matrix fillWithZeros(Matrix matrix) {
        double[][] m = new double[matrix.rows][matrix.cols];

        for (int row = 0; row < matrix.rows; row++) {
            for (int col = 0; col < matrix.cols; col++) {
                m[row][col] = 0;
            }
        }
        return new Matrix(m);
    }

    public static Matrix dSigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = a.getData()[i][j] * (1.0-a.getData()[i][j]);
            }
        }

        return new Matrix(t);
    }



    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(this.data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void add(double scaler) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] += scaler;
            }
        }
    }

    public void add(Matrix other) {
        //assertCorrectDimensions(other);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] += other.data[i][j];
            }
        }
    }

    public static Matrix fromArray(double[] x) {
        double[][] t = new double[x.length][1];

        for (int i = 0; i < x.length; i++) {
            t[i][0] = x[i];
        }

        return new Matrix(t);
    }

    public static Matrix subtract(Matrix a, Matrix other) {
        double[][] t = new double[a.rows][a.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = a.data[i][j] - other.data[i][j];
            }
        }

        return new Matrix(t);
    }

    public static Matrix transpose(Matrix a) {
        //Matrix aT = new Matrix(a.cols, a.rows);
        double[][] mat = new double[a.cols][a.rows];
        
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                // indeksit i ja j eri järjestyksessä, koska
                // transponoidaan
                mat[j][i] = a.data[i][j];
            }
        }
        return new Matrix(mat);
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix t = new Matrix(a.rows, b.cols);

        for (int i = 0; i < t.rows; i++) {
            for (int j = 0; j < t.cols; j++) {
                double sum = 0.0;

                for (int k=0; k < a.cols; k++) {
                    sum += a.getData()[i][k] * b.getData()[k][j];
                }
                t.getData()[i][j] = sum;
            }
        }

        return t;
    }

    public static Matrix hadamardProduct(Matrix a, Matrix b) {
        
        double[][] had = new double[a.rows][a.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                had[i][j] = a.getData()[i][j] * b.getData()[i][j];
            }
        }

        return new Matrix(had);
    }


    
    // public static Matrix elementProductM(Matrix a, Matrix b) {
    //     double[][] t = new double[a.rows][b.cols];
        
    //     for (int i = 0; i < a.rows; i++) {
    //         for (int j = 0; j < b.cols; j++) {
    //             double sum = 0.0;

    //             for (int k = 0; k < a.cols; k++ ) {
    //                 sum += a.getData()[i][k] * b.getData()[k][j];
    //             }
    //             t[i][j] = sum;
    //         }
    //     }
    //     return new Matrix(t);
    // }

    /**
     * elementProduct
     * 
     * Element product
     * @param other
     */
    public void elementProduct(Matrix other) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] *= other.data[i][j];
            }
        }
    }

    // public static Matrix fromArray(double[] x) {
    //     double[][] t = new double[x.length][1];

    //     for (int i = 0; i < x.length; i++) {
    //         t[i][0] = x[i];
    //     }
    //     return new Matrix(t);
    // }

    // @Override
    // public String toString() {
    //     String string = "";
    //     StringBuilder sb = new StringBuilder(string);
        

    //     for (int i = 0; i < this.rows; i++) {
    //         for (int j = 0; j < this.cols; j++) {
    //             sb.append(String.valueOf(this.data[i][j]) + " ");
    //         }
    //         sb.append("\n");
    //     }
    //     return sb.toString();
    // }

    /**
     * vecelementProduct
     * 
     * Used to calculate outputs in forward passes
     * 
     * @param v Vector
     * @return
     */
    // public Vector vecelementProduct(Vector v) {

    //     // Transpose of current matrix
    //     Matrix t = Matrix.transpose(this);

    //     double[] output = new double[rows];
    //     for (int i = 0; i < rows; i++) {
    //         Vector a = new Vector(t.data[i]);
    //         output[i] = a.dotProduct(v);
    //     }
    //     System.out.println("Output: " + Arrays.toString(v.getData()));
    //     return new Vector(output);
    // }
    public Vector elementProduct(Vector v) {
        double[] out = new double[rows];
        for (int y = 0; y < rows; y++)
            out[y] = new Vector(data[y]).dotProduct(v);

        return new Vector(out);
    }
    

    public Matrix map(Function fn) {
       for (int i = 0; i < this.data.length; i++) {
           for (int j = 0; j < this.data[i].length; j++) {
               this.data[i][j] = fn.apply(data[i][j]);
           }
       }
       return this;
    }

    public double[][] getData() {
        return this.data;
    }

    /**
     * scalarProd
     * 
     * Scalar product of matrix and a scalar (double)
     * @param scalar
     * @return scalar * this (Matrix)
     */
    public Matrix scalarProd(double scalar) {
        return map(value -> scalar * value);
    }

    public static Matrix scalarProduct(Matrix m, double scalar) {
        double[][] mProduct = new double[m.rows][m.cols];
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                mProduct[i][j] = m.getData()[i][j] * scalar;
            }
        }
        return new Matrix(mProduct);
    }

    public Matrix setToValue(double scalar) {
        return map(value -> scalar);
    }

    /**
     * matSum
     * 
     * Sum of two matrices
     * @param m (Matrix)
     * @return this + m
     */

    public Matrix fillFrom(Matrix other) {
        assertCorrectDimensions(other);

        for (int i = 0; i < rows; i++) {
            if (cols >= 0) arraycopy(other.data[i], 0, data[i], 0, cols);
        }
        return this;
    }

    public Matrix addMatrix(Matrix m) {
        
        
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] += m.data[i][j];
            }
        }
        return this;
    }

    public Vector vecMultiply(Vector v) {

            // Transpose of current matrix
            Matrix t = Matrix.transpose(this);
    
            double[] output = new double[rows];
            for (int i = 0; i < rows; i++) {
                Vector a = new Vector(t.data[i]);
                output[i] = a.dotProduct(v);
            }
            System.out.println("Output: " + Arrays.toString(v.getData()));
            return new Vector(output);
        }

    // public static Vector multiply(Vector v) {
    //     double[] out = new double[rows];
    //     for (int y = 0; y < rows; y++)
    //         out[y] = new Vector(data[y]).dotProduct(v);

    //     return new Vector(out);
    // }

    /**
     * matSubtract
     * 
     * matrix subtracted by another matrix
     * @param other (Matrix)
     * @return this-other
     */
    public Matrix matSubract(Matrix other) {
        System.out.println("other: (" + other.rows + ", " + other.cols + ")");
        System.out.println("tämä: (" + this.rows + ", " + this.cols + ")");

        //assertCorrectDimensions(other);
        if (this.rows > other.rows) {
            Matrix otherT = transpose(other);
            //Matrix transposed = this.copy();
            //Matrix transposed = transpose(transposed);
            
            //rows = d.length;
            //this.cols = d[0].length;
            //this.cols = this.cols;
            //this.rows = this.data.length;
            
        }
        System.out.println("other: (" + other.rows + ", " + other.cols + ")");
        System.out.println("tämä: (" + this.rows + ", " + this.cols + ")");

        for (int i = 0; i < other.getData().length; i++) {
            for (int j = 0; j < other.getData()[0].length; j++) {
                this.data[i][j] -= other.data[i][j];
            }
        }
        return this;
    }

        /**
     * matProduct
     * Product of two matrices
     * @param o (Matrix)
     * @return this*o
     */
    public Matrix matProduct(Matrix o) {
        //assertCorrectDimensions(o);

        this.data = IntStream.range(0, rows)
            .mapToObj(i -> IntStream.range(0, o.cols)
                    .mapToDouble(j -> IntStream.range(0, this.cols)
                            .mapToDouble(k -> this.data[i][k] * o.data[k][j])
                            .sum())
                    .toArray()).toArray(double[][]::new);
        return this;
    }

    public static Matrix hadamardProductVector(Matrix a, Matrix vector) {
        double[][] d = new double[a.rows][a.cols];
        for (int i = 0; i < vector.cols; i++) {
            for (int j = 0; j < a.rows; j++) {
                d[i][j] = a.getData()[i][j] * vector.getData()[i][0];
            } 
        }
        return new Matrix(d);
    } 

    


    

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Matrix)) {
            return false;
        }

        Matrix mOther = (Matrix) other;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.data[i][j] != mOther.data[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Matrix: " + Arrays.deepToString(this.data);
        
    }





    /// Utility methods ////
    public void assertCorrectDimensions(Matrix other) {
        if (this.cols != other.cols || this.rows != other.rows) {
            throw new IndexOutOfBoundsException("matriisierotuksessa matriiseilla oltava yhtä monta saraketta ja riviä!");
        }
    }

    public Matrix copy() {
        Matrix m = new Matrix(this.rows, this.cols);

        for (int i = 0; i < rows; i++) {
            m.data[i] = Arrays.copyOf(this.data[i], cols);
        }
        return m;
    }

    public void scalarSum(double skalaari) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] += skalaari;
            }
        }
    }



    

    /// Toiminnot 
}
