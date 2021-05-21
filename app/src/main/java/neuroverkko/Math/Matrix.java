package neuroverkko.Math;

import java.util.Arrays;
import java.util.stream.DoubleStream;
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

    // public Matrix multiply(Matrix b) {
    //     Matrix x = new Matrix(this.rows, b.cols);
    //     double[][] C = x.getData();
    //     double[][] BA = b.A;
    // }

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

    public Matrix add(double scaler) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] += scaler;
            }
        }
        return this;
    }

    public Matrix add(Matrix other) {
        //assertCorrectDimensions(other);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] += other.data[i][j];
            }
        }
        return this;
    }

    public static Matrix fromArray(double[] x) {
        double[][] t = new double[x.length][1];

        for (int i = 0; i < x.length; i++) {
            t[i][0] = x[i];
        }

        return new Matrix(t);
    }

    public static Matrix add(Matrix a, Matrix other) {
        double[][] t = new double[a.rows][a.cols];



        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = a.data[i][j] + other.data[i][j];
            }
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

    // Jokin tässä mukamas meni pieleen
    public static Matrix multiply2(Matrix a, Matrix b) {
        // Matrix t = new Matrix(a.rows, b.cols);
        double[][] t = new double[a.rows][b.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < b.cols; j++) {
                double sum = 0.0;

                for (int k=0; k < b.rows; k++) {
                    sum += a.getData()[i][k] * b.getData()[k][j];
                }
                t[i][j] = sum;
            }
        }
        return new Matrix(t);
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

    //    
    public String getShape() {
        return "( " +this.rows + ", " + this.cols+" )";
    }

    // public Matrix outerProduct(Matrix u) {
    //     double[][] result = new double[u.rows][this.cols];

    //     for (int i = 0; i < data.length; i++)
    //         for (int j = 0; j < u.getData().length; j++)
    //             result[j][i] = this.data[i][0] * u.data[i][0];
    //     return new Matrix(result);
    // }

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
    public Matrix elementProduct(Matrix other) {
        double[][] n = new double[this.rows][this.cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                n[i][j] = this.data[i][j] * other.data[i][j];
            }
        }
        return new Matrix(n);
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

    // public Matrix elementProduct(Matrix v) {

    //     double[] out = new double[rows];
    //     for (int y = 0; y < rows; y++)
    //         out[y] = new Vector(data[y]).dotProduct(v);

    //     return new Vector(out);
    // }

    // public Matrix dotProductNew(Matrix b) {
        
    //     for (int col = 0; col < this.cols; col++) {
    //         double sum = 0;
    //         for (int row = 0; row < this.rows; row++) {
    //             sum += this.getData()[row][col] * b.getData()[col][row];
    //         }
    //     }
    // }




    public static Matrix dotProduct(Matrix sameRows, Matrix asColumns) {
        double[][] m = new double[sameRows.rows][asColumns.cols];

        for (int j = 0; j < sameRows.rows; j++) {
            for (int i = 0; i < sameRows.rows; i++) {
                m[j][i] = sameRows.getData()[j][0] * asColumns.getData()[0][i];
            }
        }
        return new Matrix(m);
    }

    public static double[] getColumn(Matrix m, int colIndex) {
        double[] col = new double[m.getData().length];
        System.out.println("col length: " + col.length);

        for (int i = 0; i < col.length; i++) {
            col[i] = m.getData()[i][colIndex];
        }
        return col;
    }
    // public static double[] getColumn(Matrix m, int colIndex) {
    //     double[] col  = new double[m.getData()[0].length];

    //     return IntStream.range(0, m.getData().length).map(i -> )
    // }

    // public static Matrix dotProductWVectors(Matrix sameRows, Matrix asColumns) {
    //     double[][] n = new double[sameRows.rows][asColumns.cols];

    //     for (int i = 0; i < sameRows.rows; i++) {

    //         Vector r = new Vector(sameRows.getData()[i]);

    //         double[] c = new double[asColumns.cols];
    //         for (int j = 0; j < asColumns.cols; j++) {
    //             double[] col = getColumn(asColumns, j);
    //             // if (asColumns.rows == 1) {
    //             //     Vector c = new Vector(col);
    //             // }
    //             c[j] = getColumn(asColumns, j)[0];
    //             // Vector c = new Vector(col);//asColumns.getData()[i][j]);

    //             //r.dotProduct(c);
    //             //System.out.println(r.toString());
    //             //n[i] = r.getData();

    //         }
    //         r.dotProduct(new Vector(c));
    //         System.out.println(r.toString());

            
    //     }
    //     return new Matrix(n);
    // }

    public static Matrix pistetulo(Matrix a, Matrix b) {
       Matrix temp=new Matrix(a.rows,b.cols);
		for(int i=0;i<temp.rows;i++) {
			for(int j=0;j<temp.cols;j++) {
				double sum=0;

				for(int k=0; k < a.cols;k++) {
					sum+=a.getData()[i][k]*b.getData()[k][j];
				}
				temp.getData()[i][j]=sum;
                System.out.print(temp.getData()[i][j] + " ");
			}
                        System.out.println("");
		}
		return temp;
    }
    // public Matrix times(Matrix B) {
    //     Matrix A = this;
    //     if (A.rows != B.cols) throw new RuntimeException("Illegal matrix dimensions.");
    //     Matrix C = new Matrix(A.cols, B.rows);
    //     for (int i = 0; i < C.cols; i++)
    //         for (int j = 0; j < C.rows; j++)
    //             for (int k = 0; k < A.rows; k++)
    //                 C.data[i][j] += (A.data[i][k] * B.data[k][j]);
    //     return C;
    // }

    public double dotProduct(Matrix other) {
        //assertCorrectDimensions(other);

       
            
        
        if (this.rows > other.rows) {
            return IntStream.range(0, this.rows)
            .mapToDouble(k -> other.data[0][k]*this.data[k][0])
            .sum();
        } else {
            return IntStream.range(0, other.rows)
                .mapToDouble(k -> this.data[0][k]*other.data[k][0])
                .sum();
        }
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

    public Matrix outerProduct(Matrix vectorlike) {
        double[][] result = new double[vectorlike.rows][this.rows];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < vectorlike.rows; j++) {
                result[j][i]  = this.getData()[i][0] * vectorlike.getData()[i][0];
            }
        }

        return new Matrix(result);
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

    /**
     * matSubtract
     * 
     * matrix subtracted by another matrix
     * @param other (Matrix)
     * @return this-other
     */
    public Matrix matSubract(Matrix other) {
        assertCorrectDimensions(other);
        if (this.rows > other.rows) {
            Matrix otherT = transpose(other);
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

    /**
     * getMatrixMax
     * 
     * Returns the index of the highest number present in the
     * matrix. This is essentially used to figure out, which number
     * the output and target matrices represent. If the highest number
     * is in the 3rd index, the highest number == 2.
     * @param mat (Matrix)
     * @return double max value
     */
    public static int getMatrixMax(Matrix mat) {
        double max_value = Double.MIN_VALUE;

        // System.out.println("Mat: " + mat.toString());

        int index = -1;
        for (int row = 0; row < mat.rows; row++) {
            for (int col = 0; col < mat.cols; col++) {
                
                if (mat.getData()[row][col] > max_value) {
                    max_value = mat.getData()[row][col];
                    index = row;
                }
            }
        }
        return index;
    }

    public static double frobeniusNorm(Matrix mat) {

        double sumSquared = 0.0;

        for (int i = 0; i < mat.rows; i++) {
            for (int j = 0; j < mat.cols; j++) {
                sumSquared += Math.pow(mat.getData()[i][j], 2);
            }
        }

        return Math.sqrt(sumSquared);
    }

    public static double getAverage(Matrix m) {
        return Arrays.stream(m.getData()).flatMapToDouble(Arrays::stream).average().getAsDouble();
    }

    public static double sumOverAxis(Matrix a) {
        
        double sum =  0.0;
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                sum += a.getData()[i][j];
            }
        }
        return sum;
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
