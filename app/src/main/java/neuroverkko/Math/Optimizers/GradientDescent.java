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
    public Matrix updateWeightsFromLayer(Matrix weights, Matrix deltaWeights) {
        deltaWeights = deltaWeights.scalarProd(learningRate);
        return Matrix.subtract(weights, deltaWeights);
    }

    @Override
    public Matrix updateWeights(Matrix weights, Matrix deltaWeights, int trainingDatasetSize, double l2, int minibatch_size) {
        deltaWeights = deltaWeights.scalarProd(learningRate/minibatch_size);
        return Matrix.subtract(weights, deltaWeights);//.scalarProd(this.learningRate));
    }

    @Override
    public Matrix updateBias(Matrix bias, Matrix deltaBias, int minibatch_size) {
        deltaBias.scalarProd((learningRate/(Double.valueOf(minibatch_size))));
        return Matrix.subtract(bias, deltaBias);
    }

    @Override
    public double getLearningRate() {
        return this.learningRate;
    }

    public double getL2NormSquared(Matrix weight) {
        return Matrix.sumOverAxis(Matrix.multiply(weight, weight));
    }


    
}
