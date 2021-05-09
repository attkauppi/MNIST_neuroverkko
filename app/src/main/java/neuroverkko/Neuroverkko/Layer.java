package neuroverkko.Neuroverkko;

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
    double l2;
    public int deltaBiasesAdded = 0;
    public int deltaWeightsAdded= 0;
    public ActivationFunction actFnc;
    public Optimizer opt;
    double initialBias;

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
    }

    public Layer(int nodes, ActivationFunction actFnc, Optimizer opt, double bias) {
        this.nodes = nodes;
        this.actFnc = actFnc;
        this.opt = opt;
        this.bias = new Matrix(new double[this.nodes][1]);
        this.deltaBias = new Matrix(new double[this.nodes][1]);
        this.initialBias = bias;
        this.output = new Matrix(new double[this.nodes][1]);
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
    }

    public boolean hasNextLayer() {
        if (this.nextLayer != null) {
            return true;
        }
        return false;
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
        //}
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
            this.deltaWeights = new Matrix(new double[this.getSize()][this.getPrevLayer().getSize()]);
            this.resetDeltaWeights();
        }
    }

    public void evaluate(Matrix input) {
        // First layer doesn't have a previous
        // layer and sets its input directly as
        // output or activation.
        if (!this.hasPrevLayer()) {
            this.setInput(input);
            this.setActivation(input);
        } else {
            this.setInput(input);
            this.evaluateInput();
            this.setInput(this.calculateInput());
            
        }
        //return this.getActivation();
    }

    public Matrix calculateInput(Matrix input) {
        return Matrix.multiply(weights, input).addMatrix(bias);
    }

    public Matrix calculateInput() {
        return Matrix.multiply(weights, this.getPrevLayer().getActivation()).addMatrix(bias);
    }

    // public void updateWeightsBiases() {
    //     if (deltaWeightsAdded > 0) {
            
    //     }
    // }

    public Matrix evaluateInput() {
        // System.out.println("this.input: " + this.input.toString());
        Matrix result = this.actFnc.calcActivation(Matrix.multiply(weights, input).addMatrix(bias));
        // System.out.println("this.input: " + this.input.toString());
        this.setActivation(result);
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

        double[][] weightsArray = new double[this.getSize()][this.getPrevLayer().getSize()];
        if (weights.rows == weightsArray.length && weights.cols == weightsArray[0].length) {
            for (int i = 0; i < this.getSize(); i++) {
                for (int j = 0; j < this.getPrevLayer().getSize(); j++) {
                    weightsArray[i][j] = weights.getData()[i][j];
                }
            }
        }
        this. weights = new Matrix(weightsArray);
    }

    public void setInitialWeightsRand() {
        double[][] weightsArray = new double[this.getSize()][this.getPrevLayer().getSize()];
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getPrevLayer().getSize(); j++) {
                weightsArray[i][j] = RandomNumberGenerator.getRandom();
            }
        }
        this.weights = new Matrix(weightsArray);
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

    public void setDeltaWeights(Matrix other) {
        assertNewWeightsCorrectDimensions(other);
        this.deltaWeights = other;
    }

    public Matrix getDeltaWeights() {
        return this.deltaWeights;
    }

    public void setDeltaBias(Matrix deltaBias) {
        assertNewBiasCorrectDimensions(deltaBias);
        this.deltaBias = deltaBias;
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


