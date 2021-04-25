package neuralnetwork.neuralnetwork.math.CostFunctions;

import neuralnetwork.neuralnetwork.math.Vector;

public class Quadratic implements CostFunction {

    @Override
    public String getName() {
        return "Quadratic";
    }

    @Override
    public double getCost(Vector expected, Vector actual) {
        // C = sigma(y - expected)^2
        return actual.vecSubtraction(expected).dotProduct(actual.vecSubtraction(expected));
    }

    @Override
    public Vector getDerivative(Vector expected, Vector actual) {
        // (actual-expected)*2
        return (actual.vecSubtraction(expected)).scalarProd(2.0);
    }
    
}
