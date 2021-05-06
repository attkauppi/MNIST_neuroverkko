package neuroverkko.Math.Optimizers;

import neuroverkko.Math.*;

public class GradientDescent implements Optimizer {

    double learningRate;

    public GradientDescent(double learningRate) {
        this.learningRate = learningRate;
    }

    @Override
    public String toString() {
        return "gradient descent " + learningRate;
    }

    @Override
    public Matrix costDerivative(Matrix output, Matrix target) {
        return Matrix.subtract(output, target);
    }

    @Override
    public Matrix updateWeights(Matrix weights, Matrix deltaWeights) {
        Matrix deltaWeightsLRate = deltaWeights.scalarProd(this.learningRate);
        Matrix updatedWeights = Matrix.subtract(weights, deltaWeightsLRate);
        return updatedWeights;
    }

    @Override
    public Matrix updateBias(Matrix bias, Matrix deltaBias) {
        Matrix deltaBiasLearningRate = deltaBias.scalarProd(this.learningRate);
        Matrix updatedBias = Matrix.subtract(bias, deltaBiasLearningRate);
        return updatedBias;
    }

    @Override
    public double getLearningRate() {
        return this.learningRate;
    }


    
}
