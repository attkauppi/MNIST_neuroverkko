package neuroverkko.Math.Optimizers;

import java.util.HashMap;
import java.util.Map;

import neuroverkko.Math.*;

public class Momentum implements Optimizer {

    double learningRate;
    double momentum;
    public Matrix prevDeltaWeights;
    public Matrix prevDeltaBias;
    public Map<Integer, Matrix> prevDeltaWeightsMap = new HashMap<>();
    public Map<Integer, Matrix> prevDeltaBiasMap = new HashMap<>();

    public Momentum(double learningRate, double momentum) {
        this.learningRate = learningRate;
        this.momentum = momentum;
    }

    public Momentum(double learningRate) {
        this(learningRate, 0.9);
    }

    @Override
    public String toString() {
        return "Momentum : " + this.learningRate;
    }

    public void setPrevDeltaWeights(Matrix deltaWeights) {
        this.prevDeltaWeights = deltaWeights;
    }

    @Override
    public Matrix updateWeights(Matrix weights, Matrix deltaWeights, int trainingDatasetSize, double l2, int minibatch_size) {
       
        if (this.prevDeltaWeightsMap.get(deltaWeights.cols) == null) {
            
            this.prevDeltaWeightsMap.put(deltaWeights.cols, deltaWeights.scalarProd(this.learningRate));

            // this.prevDeltaWeights = deltaWeights.copy();
            // this.prevDeltaWeights = prevDeltaWeights.scalarProd(this.learningRate);

            // System.out.println("prevDelta: " + prevDeltaWeights.toString());
            // System.out.println("Delta: " + deltaWeights.toString());
        } else {
            // System.out.println("prevDelta: " + prevDeltaWeights.toString());
            // System.out.println("Delta: " + deltaWeights);
            // System.out.println("prevDelta: " + prevDeltaWeights.rows + " " + prevDeltaWeights.cols);
            // System.out.println("Delta: " + deltaWeights.rows + " " + deltaWeights.cols);
            this.prevDeltaWeightsMap.put(deltaWeights.cols, deltaWeights.scalarProd(this.learningRate));

            deltaWeights = deltaWeights.scalarProd(learningRate);
            Matrix prevDelta = this.prevDeltaWeightsMap.get(deltaWeights.cols);
            prevDelta = prevDelta.scalarProd(this.momentum);
            prevDelta = Matrix.add(prevDelta, deltaWeights);
            this.prevDeltaWeightsMap.put(deltaWeights.cols, prevDelta);
            // this.prevDeltaWeights = this.prevDeltaWeights.scalarProd(this.momentum);
            // deltaWeights = deltaWeights.copy().scalarProd(learningRate);
            // this.prevDeltaWeights = Matrix.add(this.prevDeltaWeights, deltaWeights);
        }
        Matrix prevDelta = this.prevDeltaWeightsMap.get(deltaWeights.cols);

        weights = Matrix.subtract(weights, prevDelta);
        return weights; 
    }

    @Override
    public Matrix updateBias(Matrix bias, Matrix deltaBias, int minibatch_size) {
        Matrix prevDBias = this.prevDeltaBiasMap.get(deltaBias.cols);

        if (prevDBias == null) {
            prevDBias = deltaBias.scalarProd(this.learningRate);
        } else {
            prevDBias = prevDBias.scalarProd(this.momentum);
            deltaBias = deltaBias.copy().scalarProd(this.learningRate);
            prevDBias = Matrix.add(this.prevDeltaBias, deltaBias);
            this.prevDeltaBiasMap.put(deltaBias.cols, prevDBias);
        }
        bias = Matrix.subtract(bias, prevDBias);
        return (Matrix) bias;
    }

    @Override
    public Matrix costDerivative(Matrix output, Matrix target) {
        return Matrix.subtract(output, target);
    }

    @Override
    public double getLearningRate() {
        return this.learningRate;
    }

    @Override
    public Matrix updateWeightsFromLayer(Matrix weights, Matrix deltaWeights) {
        deltaWeights = deltaWeights.scalarProd(learningRate);
        return Matrix.subtract(weights, deltaWeights);
    }






}