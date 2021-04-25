package neuralnetwork.neuralnetwork.math.CostFunctions;
import neuralnetwork.neuralnetwork.math.Vector;

public class HalfQuadratic implements CostFunction {

    @Override
    public String getName() {
        return "HalfQuadratic";
    }

    @Override
    public double getCost(Vector expected, Vector actual) {
        // C = 0.5 * Sigma(y - expected)^2
        // Formula: https://towardsdatascience.com/gradient-descent-from-scratch-e8b75fa986cc
        Vector difference = actual.vecSubtraction(expected);
        double brackets = difference.vecElementProduct(difference).sumElements();
        return brackets*0.5;
    }

    @Override
    public Vector getDerivative(Vector expected, Vector actual) {
        // actual - expected
        return actual.vecSubtraction(expected);
    }
    
}
