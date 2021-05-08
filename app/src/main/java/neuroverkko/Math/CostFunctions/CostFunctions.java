package neuroverkko.Math.CostFunctions;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.*;

public interface CostFunctions {

    public enum CostFunctionEnum {
        MSE//, QUADRATIC HALFQUADRATIC
    }

    public String getName();

    double getCost(Matrix target, Matrix output, int minibatch_size);

    

    //double Matrix (Matrix target, Matrix output);

    // Vector getDerivative(Vector target, Matrix output);

    Matrix getDerivative(Matrix target, Matrix output);
    
}
