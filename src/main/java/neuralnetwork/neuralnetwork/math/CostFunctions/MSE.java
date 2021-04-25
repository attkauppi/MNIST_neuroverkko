package neuralnetwork.neuralnetwork.math.CostFunctions;

import neuralnetwork.neuralnetwork.math.Vector;

class MSE implements CostFunction {

    @Override
    public String getName() {
        return "MSE";
    }

    @Override
    public double getCost(Vector expected, Vector actual) {
        // C = (1/n) * Sigma(expected - actual)^2

        double multiplier = (1.0/actual.getDimensions());
        Vector difference = expected.vecSubtraction(actual);
        double secondPower = difference.dotProduct(difference);
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
    
}
