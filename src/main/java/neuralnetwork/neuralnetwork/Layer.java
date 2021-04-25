package neuralnetwork.neuralnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import neuralnetwork.neuralnetwork.math.ActivationFunction;
import neuralnetwork.neuralnetwork.math.IActivationFunction;
import neuralnetwork.neuralnetwork.math.Matrix;
import neuralnetwork.neuralnetwork.math.Vector;
import neuralnetwork.neuralnetwork.utils.List;


/**
 * Layer
 * 
 * This class represents "layers" of neurons/nodes in an
 * artificial neural network. These are usually depicted
 * as a column of circles leading up and from them (the
 * edges between the nodes)
 * 
 * Each layer, except the input layer, has a previousLayer.
 * Each layer, except the output layer, has an output or a
 * following layer as well, but for ease of maintaining
 * consistent weights between all the layers in the code,
 * the layer objects only have a connection to the 
 * previous layer.
 */
public class Layer {

    private int numberOfNodes;

    // TODO: implementoi itse tai korvaa
    private final Map<Integer, Vector> output = new HashMap<>();
    private ActivationFunction activation;
    //private ArrayList<Layer> output;
    // TODO: implement optimizer
    // Optimizer
    private Matrix weights;
    private Vector bias;
    // TODO: implement L2 somehow
    double l2 = 0;
    private Layer prevLayer;

    // Temporary container for changes in weights
    private transient Matrix deltaWeights;
    private transient Vector deltaBias;
    // Keeping tracks of how many weights and biases
    // we've added allows calculating averages and let's
    // us decide, whether we apply L2 norm.
    private transient int deltaWeightsAdded = 0;
    private transient int deltaBiasAdded = 0;
    private Matrix inputs;

    public Layer(int numberOfNodes, ActivationFunction iaf) {
        this.numberOfNodes = numberOfNodes;
        this.activation = iaf;
        this.deltaBias = new Vector(numberOfNodes);

        // inputs from previous layer, i.e., the outputs
        // sent by its nodes. If there are n-nodes in this
        // layer and the previous layer has m, the weights
        // of the edges connecting the layers form a matrix
        // of size mxn.
        // TODO: tarkista ylläoleva väite matriisin koosta.
    }

    public int getSize() {
        return this.numberOfNodes;
    }

    public boolean hasPreviousLayer() {
        if (this.prevLayer != null) {
            return true;
        }
        return false;
    }

    /**
     * set previousLayer
     * 
     * Sets another Layer object as a previous layer for
     * this layer. This meas it's output will be this layer's
     * input. (The input layer is an exception to this rule,
     * as it receives its input directly)
     * 
     * It is assummed here that all nodes from the previous
     * layer are connected to all of this layer's nodes.
     * 
     * @param prev (Layer)
     */
    public void setPreviousLayer(Layer prev) {
        if (!this.hasPreviousLayer()) {
            this.prevLayer = prev;
        }
    }

    public Vector calculate(Vector input) {
        // Checks, if input layer. For the input layer, the input
        // is just saved as the output.
        if (!this.hasPreviousLayer()) {
            output.put(this.hashCode(), input);
        } else {
            output.put(this.hashCode(), activation.calcActFunc(input.matProduct(this.weights).vecAdd(bias)));
        }

        return output.get(input.hashCode());
    }

    public Vector getOutput() {
        return output.get(this.hashCode());
    }

    public Matrix getWeights() {
        return this.weights;
    }

    public Layer getPrevLayer() {
        return this.prevLayer;
    }

    public Vector getInputs() {
        return this.prevLayer.getOutput();
    }

    public void deactivateBias() {
        double[] bias = new double[this.bias.getDimensions()];
        
        for (int i = 0; i < this.bias.getDimensions(); i++) {
            bias[i] = 0.0;
        }
        this.bias = new Vector(bias);
    }

    public void activateBias() {
        double[] bias = new double[this.bias.getDimensions()];

        for (int i = 0 ; i < this.bias.getDimensions(); i++) {
            bias[i] = 1.0;
        }
        this.bias = new Vector(bias);
    }

    public Vector getBias() {
        return this.bias;
    }

    public synchronized void updateWeightsAndBias() {
        if (deltaWeightsAdded > 0) {

        }
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }











    

    
}
