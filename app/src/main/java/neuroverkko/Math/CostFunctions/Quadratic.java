package neuroverkko.Math.CostFunctions;

import neuroverkko.Math.*;
public class Quadratic implements CostFunctions {

    @Override
    public String getName() {
        return "Quadratic";
    }

    @Override
    public double getCost(Matrix target, Matrix output, int minibatch_size) {
        Matrix difference = Matrix.subtract(output, target);
        //return difference.dotProduct(difference);

        double norm = Matrix.frobeniusNorm(difference);
        return Math.pow(0.5*norm, 2);
    }


    //double Matrix (Matrix target, Matrix output);

    // Vector getDerivative(Vector target, Matrix output);

    @Override
    public Matrix getDerivative(Matrix target, Matrix output) {
        Matrix difference = Matrix.subtract(output, target);
        return difference;//;.scalarProd(2.0);
    }
    
}
