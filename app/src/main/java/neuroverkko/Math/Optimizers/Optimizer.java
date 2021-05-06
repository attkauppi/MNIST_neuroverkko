package neuroverkko.Math.Optimizers;

import neuroverkko.Math.*;

public interface Optimizer {

    /**
     * costDerivative
     * 
     * Calculates the cost derivative using for a particular
     * optimizer.
     * 
     * @param output (Matrix) the output of any/all output layer neurons.
     * @param target (Matrix) the output we're aiming to get.
     * @return (Matrix) depends on the optimizer being used.
     */
    Matrix costDerivative(Matrix output, Matrix target);

    Matrix updateWeights(Matrix weights, Matrix deltaWeights);

    Matrix updateBias(Matrix bias, Matrix deltaBias);

    double getLearningRate();
}
