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

    // @Override
    // public Matrix updateWeights(Matrix weights, Matrix deltaWeights) {
    //     Matrix deltaWeightsLRate = deltaWeights.scalarProd(this.learningRate);
    //     Matrix updatedWeights = Matrix.subtract(weights, deltaWeightsLRate);
    //     return updatedWeights;
    // }

    @Override
    public Matrix updateWeights(Matrix weights, Matrix deltaWeights, int trainingDatasetSize, double l2, int minibatch_size) {
        //weights.map(value -> ((1.0-learningRate)*(l2/(double) trainingDatasetSize))*value);
        deltaWeights.map(value -> (-learningRate/(double) minibatch_size)*value);
        System.out.println("Paino muuttumassa: ");// + Matrix.subtract(weights, deltaWeights).toString());
        return Matrix.subtract(weights, deltaWeights);
    }

    @Override
    public Matrix updateBias(Matrix bias, Matrix deltaBias, int minibatch_size) {
        deltaBias.scalarProd((learningRate/(double)minibatch_size));
        return Matrix.subtract(bias, deltaBias);

        // Matrix deltaBiasLearningRate = deltaBias.scalarProd(this.learningRate);
        // Matrix updatedBias = Matrix.subtract(bias, deltaBiasLearningRate);
        // return updatedBias;
    }

    @Override
    public double getLearningRate() {
        return this.learningRate;
    }


    
}
