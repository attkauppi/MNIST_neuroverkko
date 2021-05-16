package neuroverkko.Math.CostFunctions;

import neuroverkko.Math.*;
public class Quadratic implements CostFunctions {

    @Override
    public String getName() {
        return "Quadratic";
    }

    @Override
    public double getCost(Matrix target, Matrix output, int minibatch_size) {
        // C(w,b) = (1/2n)*Sigma||y(x)-a||^2
        // TODO: päätä käytätkö 0.5 tässä vai et, molempia laskentatapoja näkee.
        

        System.out.println("Output: " + output.toString());
        double part = 0.5*Math.pow(Matrix.frobeniusNorm(Matrix.subtract(output, target)),2);
        // double part = Math.pow(Matrix.frobeniusNorm(Matrix.subtract(output, target)),2);

        return part;       
    }


    //double Matrix (Matrix target, Matrix output);

    // Vector getDerivative(Vector target, Matrix output);

    @Override
    public Matrix getDerivative(Matrix target, Matrix output) {
        Matrix difference = Matrix.subtract(output, target);
        // return difference.scalarProd(2.0);//;.scalarProd(2.0);


        /**
         * Sigmoidin derivaatta: f'(net_{j}) = o_{j}*(1-o_{j})
         * Tulosneuronin virhe = f'_{j}*(net_{j})*(d_{j} - o_{j}) 
         * Tulosneuronin virhe: o_{j}*(1-o_{j}) * (d_{j} - o_{j})
         */
        return difference;
    }
    
}
