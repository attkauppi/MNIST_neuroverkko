package neuroverkko.Math.CostFunctions;

import java.util.stream.IntStream;

import neuroverkko.Math.*;

public class CrossEntropy implements CostFunctions {

    @Override
    public String getName() {
        return "CrossEntropy";
    }

    @Override
    public double getCost(Matrix target, Matrix output, int minibatch_size) {
        /**
         * - (1/n) Sigma_{x} [target * output + (1 - target) * ln(1 - output)]
         */

        Matrix target_copy = target.copy();
        Matrix output_copy = output.copy();

        target_copy.scalarProd(-1);

        output_copy = applyLogarithm(output_copy);

        Matrix erotuksenEnsimmainen = Matrix.hadamardProduct(target_copy, output_copy);

        System.out.println("Target: " + target.toString());
        System.out.println("Output: " + output.toString());
        
        // erotuksen toista
        output.scalarProd(-1.0);
        output.scalarSum(1.0);
        Matrix outputLogMinusOne = applyLogarithm(output);

        target.scalarProd(-1.0);
        target.scalarSum(1.0);
        Matrix latterInDifference = Matrix.hadamardProduct(target, outputLogMinusOne);

        Matrix difference = Matrix.subtract(erotuksenEnsimmainen, latterInDifference);
        
        return Matrix.sumOverAxis(difference);

    }

    @Override
    public Matrix getDerivative(Matrix target, Matrix output) {
        return Matrix.subtract(output, target);
    }

    
    // public static Matrix matLogarithm(Matrix a, Matrix o) {
    //     //assertCorrectDimensions(o);
    //     //double[][] c = new double[a.rows][a.cols];
    //     double[][] c = IntStream.range(0, a.rows)
    //         .mapToObj(i -> IntStream.range(0, o.cols)
    //                 .mapToDouble(j -> IntStream.range(0, a.cols)
    //                         .mapToDouble(k -> (-1.0)*a.getData()[i][k] * Math.log(o.getData()[k][j]) )))
    //                 .toArray()).toArray(double[][]::new);
    //     return new Matrix(c);
    // }

    public static Matrix applyLogarithm(Matrix a) {
        double[][] uusi = new double[a.rows][a.cols];
        for (int i = 0; i < a.rows; i++) {
            for (int j =0; j < a.cols; j++) {
                uusi[i][j] = Math.log(a.getData()[i][j]);
            }
        }
        System.out.println(new Matrix(uusi).toString());

        return new Matrix(uusi);
    }


}
