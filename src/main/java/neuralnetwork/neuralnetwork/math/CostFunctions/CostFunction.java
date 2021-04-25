package neuralnetwork.neuralnetwork.math.CostFunctions;
import neuralnetwork.neuralnetwork.math.Vector;

@SuppressWarnings("unused")
public interface CostFunction {

    public enum CostFunctionENUM {
        MSE, QUADRATIC, HALFQUADRATIC
    }

    public String getName();
    
    double getCost(Vector expected, Vector actual);

    Vector getDerivative(Vector expected, Vector actual);
}
