package neuroverkko.Math.CostFunctions;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.CostFunctions.CostFunctions;

public class MSE implements CostFunctions {

    @Override
    public String getName() {
        return "MSE";
    }

    @Override
    public double getCost(Matrix target, Matrix output, int minibatch_size) {
        Matrix difference = Matrix.subtract(output, target);
        double scalarPart = (0.5*minibatch_size);

        // return difference.scalarProd(scalarPart);

        double secondPower = difference.dotProduct(difference);
        double multiplier = (1.0/output.rows);
        return secondPower*multiplier;
    }

    // @Override
    // public Vector getDerivative(Vector expected, Vector actual) {
    //     // (1/N)*(-2).*(expected-actual);
    //     double[] expecteda = expected.getData();
    //     double[] actuala = actual.getData();

    //     double multiplier = (1.0/actual.getDimensions())*(-2.0);
    //     Vector difference = expected.vecSubtraction(actual);

    //     return difference.scalarProd(multiplier);
    // }

    @Override
    public Matrix getDerivative(Matrix expected, Matrix actual) {
        return Matrix.subtract(actual, expected).scalarProd(2.0/actual.cols);// = Matrix.subtract(expected, actual);

        // Matrix gradient = Matrix.dSigmoid(actual);

        // gradient.matProduct(error);

        // return gradient;
    }

    public Matrix getCost(Matrix output, Matrix expectedOutput) {
        return Matrix.subtract(output, expectedOutput);
    }

    public Matrix getDerivative2(Matrix expected, Matrix actual, double learning_rate) {
        Matrix error = Matrix.subtract(expected, actual);
        Matrix gradient = Matrix.dSigmoid(actual);
        gradient.matProduct(error);
        return gradient;
    }

}
