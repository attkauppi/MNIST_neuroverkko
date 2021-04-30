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
    public double getCost(Vector expected, Vector actual) {
        Vector difference = expected.vecSubtraction(actual);
        double secondPower = difference.dotProduct(difference);
        double multiplier = (1.0/actual.getDimensions());
        return secondPower*multiplier;
    }

    @Override
    public Vector getDerivative(Vector expected, Vector actual) {
        // (1/N)*(-2).*(expected-actual);
        double[] expecteda = expected.getData();
        double[] actuala = actual.getData();

        double multiplier = (1.0/actual.getDimensions())*(-2.0);
        Vector difference = expected.vecSubtraction(actual);

        return difference.scalarProd(multiplier);
    }

    @Override
    public Matrix getDerivative(Matrix expected, Matrix actual) {
        Matrix error = Matrix.subtract(expected, actual);
        Matrix gradient = Matrix.dSigmoid(actual);

        gradient.matProduct(error);

        return gradient;
    }


    
    public Matrix getDerivative2(Matrix expected, Matrix actual, double learning_rate) {
        Matrix error = Matrix.subtract(expected, actual);
        Matrix gradient = Matrix.dSigmoid(actual);
        gradient.matProduct(error);
        return gradient;
    }

}
