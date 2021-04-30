package neuroverkko.Math.CostFunctions;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.*;

public interface CostFunctions {

    public enum CostFunctionEnum {
        MSE//, QUADRATIC HALFQUADRATIC
    }

    public String getName();

    double getCost(Vector expected, Vector actual);

    Vector getDerivative(Vector expected, Vector actual);

    Matrix getDerivative(Matrix expected, Matrix actual);
    
}
