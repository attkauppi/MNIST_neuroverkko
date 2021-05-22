package neuroverkko.Neuroverkko;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import neuroverkko.Math.*;
import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.Optimizers.*;
import neuroverkko.Utils.*;

public class Layer {
    public int nodes;
    public Matrix bias;
    public Matrix weights;
    public Layer prevLayer;
    public Layer nextLayer;
    public Matrix input;
    public Matrix activation;
    public Matrix output;
    public Matrix deltaBias;
    public Matrix deltaWeights;
    public Matrix weightsDropout;
    double l2;
    public int deltaBiasesAdded = 0;
    public int deltaWeightsAdded= 0;
    public ActivationFunction actFnc;
    public Optimizer opt;
    double initialBias;
    int minibatch_size = 0;
    public Matrix error;
    public boolean dropout;
    public Matrix dropoutMatrix;
    public double dropped;

    public Layer(int nodes) {
        this.nodes = nodes;
    }

    public Layer(int nodes, ActivationFunction actFnc, double bias) {
        this.nodes = nodes;
        this.actFnc = actFnc;
        this.bias = new Matrix(new double[this.nodes][1]);
        this.deltaBias = new Matrix(new double[this.nodes][1]);
        this.initialBias = bias;
        this.output = new Matrix(new double[this.nodes][1]);
        this.l2 = 0.0;

        this.initializeWeights();
    }

    public Layer(int nodes, ActivationFunction actFnc, Matrix bias) {
        this.nodes = nodes;
        this.actFnc = actFnc;
        this.bias = bias;
        this.deltaBias = new Matrix(new double[this.nodes][1]);
        this.output = new Matrix(new double[this.nodes][1]);
        this.l2 = 0.0;
        this.initializeWeights();
    }

    public Layer(int nodes, ActivationFunction actFnc, Optimizer opt, double bias) {
        this.nodes = nodes;
        this.actFnc = actFnc;
        this.opt = opt;
        this.bias = new Matrix(new double[this.nodes][1]);
        this.deltaBias = new Matrix(new double[this.nodes][1]);
        this.initialBias = bias;
        this.output = new Matrix(new double[this.nodes][1]);
        this.l2 = 0.0;
        this.initializeWeights();

    }

    public Layer(int nodes, ActivationFunction actFnc, Optimizer opt, double l2, double bias) {
        this.nodes = nodes;
        this.actFnc = actFnc;
        this.opt = opt;
        this.l2 = l2;
        this.bias = new Matrix(new double[this.nodes][1]);
        this.deltaBias = new Matrix(new double[this.nodes][1]);
        this.initialBias = bias;
        this.output = new Matrix(new double[this.nodes][1]);
        this.initializeWeights();
    }

    public Layer(int nodes, ActivationFunction actFnc) {
        this.nodes = nodes;
        this.actFnc = actFnc;
    }

    public boolean hasNextLayer() {
        if (this.nextLayer != null) {
            return true;
        }
        return false;
    }

    public void setMiniBatchSize(int minibatch) {
        this.minibatch_size = minibatch;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bias == null) ? 0 : bias.hashCode());
        result = prime * result + ((nextLayer == null) ? 0 : nextLayer.hashCode());
        result = prime * result + nodes;
        result = prime * result + ((output == null) ? 0 : output.hashCode());
        result = prime * result + ((prevLayer == null) ? 0 : prevLayer.hashCode());
        return result;
    }

    public void setOptimizer(Optimizer opt) {
        this.opt = opt;
    }

    public void setInitialBias(double bias) {
        double[][] b = new double[this.getSize()][1];

        for (int i = 0; i < this.bias.rows; i++) {
            for (int j = 0; j < this.bias.cols; j++) {
                b[i][j] = bias;
            }
        }
        this.bias = new Matrix(b);
    }

    /**
     * update deltas
     * @param deltaWeights
     * @param deltaBiases
     */
    public void updateDeltas(Matrix deltaWeights, Matrix deltaBias) {
        this.deltaWeights = this.deltaWeights.addMatrix(deltaWeights);
        this.deltaWeightsAdded++;
        this.deltaBias = this.deltaBias.addMatrix(deltaBias);
        this.deltaBiasesAdded++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Layer other = (Layer) obj;
        if (bias == null) {
            if (other.bias != null)
                return false;
        } else if (!bias.equals(other.bias))
            return false;
        if (nextLayer == null) {
            if (other.nextLayer != null)
                return false;
        } else if (!nextLayer.equals(other.nextLayer))
            return false;
        if (nodes != other.nodes)
            return false;
        if (output == null) {
            if (other.output != null)
                return false;
        } else if (!output.equals(other.output))
            return false;
        if (prevLayer == null) {
            if (other.prevLayer != null)
                return false;
        } else if (!prevLayer.equals(other.prevLayer))
            return false;
        return true;
    }

    public Optimizer getOpt() {
        return this.opt;
    }

    public double getInitialBias() {
        return this.initialBias;
    }

    public boolean hasPrevLayer() {
        if (this.prevLayer != null) {
            return true;
        }
        return false;
    }

    /**
     * setPrevLayer
     * 
     * Sets the layer given as parameter as a the previous
     * layer of the object calling the function, but also sets
     * the object calling the function as the nextLayer of the
     * object given as parameter -- setNextLayer()-method lacks 
     * this latter functionality.
     * @param i (Layer)
     */
    public void setPrevLayer(Layer l) {
        //if (!this.hasPrevLayer() && !prevLayer.hasNextLayer() ) {
        this.prevLayer = l;
        l.nextLayer = this;
        this.initializeWeights();
        //}
    }

    public void setOutput(Matrix output) {
        this.output = new Matrix(this.nodes, 1);
        this.output = output;
    }

    public Layer getPrevLayer() {
        return this.prevLayer;
    }

    public Layer getNextLayer() {
        return this.nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;

    }


    /**
     * getActivationFunction
     * 
     * returns the activation function of the object calling
     * the method
     * @return ActivationFunction object
     */
    public ActivationFunction getActivationFunction() {
        return this.actFnc;
    }

    public void setActivationFunction(ActivationFunction actFnc) {
        this.actFnc = actFnc;
    }

    public int getSize() {
        return this.nodes;
    }

    public Matrix getBias() {
        return this.bias;
    }

    public Matrix getDeltaBias() {
        return this.deltaBias;
    }

    /**
     * initializeWeights
     * 
     * It isn't possible to set the correct dimensions to the weights matrices
     * until we know the previouslayer.
     */
    public void initializeWeights() {
        if (this.hasPrevLayer()) {
            this.weights = new Matrix(new double[this.getSize()][this.getPrevLayer().getSize()]);
            this.weightsDropout = new Matrix(new double[this.getSize()][this.getPrevLayer().getSize()]);
            this.deltaWeights = new Matrix(new double[this.getSize()][this.getPrevLayer().getSize()]);
            this.resetDeltaWeights();
        }
    }

    /***
     * storeOriginalWeights
     * 
     * Stores the current weights into weightsDropout
     * when dropout is used for optimization.
     */
    public void storeOriginalWeights() {
        this.weightsDropout = this.weights.copy();
    }

    /**
     * getStoredWeights
     * 
     * Returns the weights that were stored during dropout.
     */
    public Matrix getStoredWeights() {
        return this.weightsDropout;
    }

    /**
     * Runs training like evaluate(input, boolean validation
     * but doesn't require setting the boolean value.
     * @param input (Matrix)
     */
    public void evaluate(Matrix input) {
        evaluate(input, false);
    }

    /**
     * evaluate
     * 
     * Receives an input matrix (really a column vector)
     * either from an earlier layer or as input to the
     * network. Evaluates what happens to the input, when
     * the layer's activation function is applied to it and
     * sets it as the layer's activation.
     * 
     * @param input (Matrix)
     */
    public void evaluate(Matrix input, boolean validation) {
        // First layer doesn't have a previous
        // layer and sets its input directly as
        // output or activation.
        Matrix in = input;

        if (!this.hasPrevLayer()) {
            this.setInput(in);
            this.setActivation(in);
        } else {
            this.setInput(in);
            Matrix correctInput = this.calculateInput();
            this.setInput(correctInput);
            Matrix result = this.evaluateInput(in);

            // If not in validation state and dropout is on
            // for the layer
            if (!validation && dropout && this.hasNextLayer()) {
                this.setDropoutMatrix();
                result = Matrix.transpose(result).elementProduct(this.getDropoutMatrix()).scalarProd(this.dropped);
                result = Matrix.transpose(result);
                correctInput = Matrix.transpose(correctInput).elementProduct(this.getDropoutMatrix());
                correctInput = Matrix.transpose(correctInput);
            }
            this.setActivation(result);
            this.setOutput(result);
            this.setInput(correctInput);
        }
    }

    public Matrix calculateInput(Matrix input) {
        return Matrix.multiply(weights, input).addMatrix(bias);
    }

    public Matrix calculateInput() {
        return this.calculateInput(this.getPrevLayer().getActivation());
    }

    public Matrix L2RegularizeWeights(Matrix weights) {
        double[][] l2Regularized = weights.getData();

        for (int i = 0; i < weights.rows; i++) {
            for (int j = 0; j < weights.cols; j++) {
                l2Regularized[i][j] -= weights.getData()[i][j] * l2;
            }
        }
        return new Matrix(l2Regularized);
    }

    public int[] chooseNNeurons() {
        int neurons = this.getSize();
        int[] chosenNeurons = new int[neurons];

        int[] neuronsArray  = ThreadLocalRandom.current().ints(0, neurons).distinct().limit(neurons/2).toArray();
        for (int i = 0; i < chosenNeurons.length; i++) {
            int indeksi = i;
            if (Arrays.stream(neuronsArray).filter(x -> x==indeksi).findFirst().orElse(Integer.MIN_VALUE) != Integer.MIN_VALUE) {
                chosenNeurons[i] = 0;
            } else {
                chosenNeurons[i] = 1;
            }
        }
        return chosenNeurons;
    }

    public void setDropoutMatrix() {
        int[] chooseNeurons = chooseNNeurons();
        double[] cd = Arrays.stream(chooseNeurons).mapToDouble(a -> Double.valueOf(a)).toArray();
        Matrix dropout = new Matrix(new double[][] {cd});
        this.dropped = this.getSize()/2.0;
        this.dropoutMatrix = dropout;
    }

    public Matrix getDropoutMatrix() {
        return this.dropoutMatrix;
    }

    public void addDeltaWeightsBiases(Matrix deltaw, Matrix deltab) {
        this.deltaWeights.add(deltaw);
        this.deltaWeightsAdded++;
        this.deltaBias.add(deltab);
        this.deltaBiasesAdded++;
    }

    public void updateWeightsBiases() {
        // System.out.println("Delta weights added: " + this.deltaWeightsAdded);
        if (!this.hasPrevLayer()) {
            return;
        }
        if (deltaWeightsAdded > 0) {
            // if (l2 > 0) {
            //     weights = L2RegularizeWeights(weights);
            // }
            // System.out.println("Pääsi eteenpäin deltaweighst ehdosta koko on: " + this.getSize());
            // if (this.l2 != 0.0 || !Double.isNaN(this.l2)) {
            if (this.l2 > 0.0) {
                weights.map(v -> v - l2 * v);
            }

            Matrix deltaWeightsAverage = deltaWeights.scalarProd(1.0/(Double.valueOf(this.deltaWeightsAdded)));
            // System.out.println("Annettavan ewights muoto: " + weights.rows + ", " + weights.cols);
            Matrix updatedWeights = this.opt.updateWeights(weights, deltaWeightsAverage, 100, this.l2, 10);
            Matrix painotEnnenAsetusta = this.weights;
            this.setWeights(updatedWeights);
            Matrix uudetPainot = this.weights;
            if (painotEnnenAsetusta.equals(uudetPainot)) {
                System.out.println("Painot eivät muuttuneet");
            }
            
            resetDeltaWeights();
            this.deltaWeightsAdded = 0;
        }

        if (deltaBiasesAdded > 0) {
            Matrix avgBias = deltaBias.scalarProd(1.0/(Double.valueOf(deltaBiasesAdded)));
            this.bias = opt.updateBias(bias, avgBias, 1);
            this.setBias(bias);

            this.deltaBias.fillWithZeros();
            this.deltaBiasesAdded = 0;
        }
    }

    public Matrix evaluateInput(Matrix input) {
        Matrix z = this.calculateInput();
        this.setInput(z);
        Matrix result = this.actFnc.calcActivation(this.getInput());
        return result;
    }

    public void resetDeltaWeights() {
        double[][] dweights = new double[this.getSize()][this.getPrevLayer().getSize()];
        
        dweights = this.fillWithZeros(dweights);

        this.deltaWeights = new Matrix(dweights);
        this.deltaWeightsAdded = 0;
    }

    public void resetDeltaBias() {
        double[][] dbias = new double[this.bias.rows][this.bias.cols];

        dbias = this.fillWithZeros(dbias);
        this.deltaBias = new Matrix(dbias);
        this.deltaBiasesAdded = 0;
    }

    public double[][] fillWithZeros(double[][] dArray) {
        for (int i = 0; i < dArray.length; i++) {
            for (int j = 0; j < dArray[0].length; j++) {
                dArray[i][j] = 0d;
            }
        }
        return dArray;
    }

    public Matrix getInput() {
        return this.input;
    }

    public void setInput(Matrix input) {
        this.input = input;
    }

    public void setActivation(Matrix activation) {
        this.activation = activation;
    }

    public Matrix getActivation() {
        return this.activation;
    }

    public void setInitialWeights(Matrix weights) {

        if (this.weights == null) {
            this.weights = new Matrix(this.getSize(), this.getPrevLayer().getSize());
        }

        double[][] weightsArray = new double[this.getSize()][this.getPrevLayer().getSize()];
        if (weights.rows == weightsArray.length && weights.cols == weightsArray[0].length) {
            for (int i = 0; i < this.getSize(); i++) {
                for (int j = 0; j < this.getPrevLayer().getSize(); j++) {
                    weightsArray[i][j] = weights.getData()[i][j];
                }
            }
        }
        this.setWeights(new Matrix(weightsArray));
    }

    public void setInitialWeightsRand() {
        double[][] weightsArray = new double[this.getSize()][this.getPrevLayer().getSize()];
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getPrevLayer().getSize(); j++) {
                weightsArray[i][j] = RandomNumberGenerator.getRandom();
            }
        }
        this.setWeights(new Matrix(weightsArray));
    }

    public Matrix getWeights() {
        return this.weights;
    }

    public void setWeights(Matrix weights) {
        assertNewWeightsCorrectDimensions(weights);
        this.weights = weights;
    }

    public void setBias(Matrix bias) {
        assertNewBiasCorrectDimensions(bias);
        this.bias = bias;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }

    /**
     * addDeltaWeights
     * 
     * use this, when doing batch training.
     * @param other
     */
    public void addDeltaWeights(Matrix other) {
        this.deltaWeights.add(other);
        this.deltaWeightsAdded++;
    }

    public void setDeltaWeights(Matrix other) {
        assertNewWeightsCorrectDimensions(other);
        this.deltaWeights = other;
        // FIXME: jos aiheuttaa ongelmia, voi olla täältä peräisin
        this.deltaWeightsAdded++;
    }

    public Matrix getDeltaWeights() {
        return this.deltaWeights;
    }

    public void setDeltaBias(Matrix deltaBias) {
        assertNewBiasCorrectDimensions(deltaBias);
        this.deltaBias = deltaBias;
        // FIXME: jos aiheuttaa ongelmia, voi olla täältä peräisin
        this.deltaBiasesAdded++;
    }

    public void setZeroBias() {
        double[][] b = new double[this.nodes][1];
        b  = this.fillWithZeros(b);
        this.bias = new Matrix(b);
    }

    // ------ utils -----
    public void assertNewBiasCorrectDimensions(Matrix newBias) {
        if (this.bias.cols != newBias.cols || this.bias.rows != newBias.rows) {
            throw new IndexOutOfBoundsException("Biasin koko ei voi muuttua kesken kaiken!");
        }
    }

    public void assertNewWeightsCorrectDimensions(Matrix newWeights) {
        if (this.weights.cols != newWeights.cols || this.weights.rows != newWeights.rows) {
            throw new IndexOutOfBoundsException("Painojen koko ei voi muuttua kesken kaiken!");
        }
    }

    @Override
    public String toString() {
        return "Layer [actFnc=" + actFnc + ", activation=" + activation.toString() + ", bias=" + bias.toString() + ", deltaBiasesAdded="
                + deltaBiasesAdded + ", deltaBias=" + deltaBias.toString() + ", deltaWeights=" + deltaWeights + ", initialBias="
                + initialBias + ", input=" + input + ", l2=" + l2 + ", nextLayer=" + nextLayer + ", nodes=" + nodes
                + ", opt=" + opt + ", output=" + output + ", prevLayer=" + prevLayer + ", weights=" + weights.toString()
                + ", deltaWeightsAdded=" + deltaWeightsAdded + "]";
    }

    public LayerState getState() {
        return new LayerState(this);
    }

    public static class LayerState {
        double[][] weights;
        double[][] bias;
        String activation;

        public LayerState(Layer layer) {
            weights = layer.getWeights() != null ? layer.getWeights().getData() : null;
            bias = layer.getBias().getData();
            activation = layer.actFnc.getName();
        }

        public double[][] getWeights() {
            return weights;
        }

        public double[][] getBias() {
            return bias;
        }
    }







    






    
}


