package neuralnetwork.neuralnetwork;

import neuralnetwork.neuralnetwork.math.ActivationFunction;
import neuralnetwork.neuralnetwork.math.Matrix;
import neuralnetwork.neuralnetwork.math.Vector;


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
    private ActivationFunction activation;
    // TODO: implement optimizer
    // Optimizer
    private Matrix weights;
    private Vector bias;
    // TODO: implement L2 somehow
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

    public Layer(int numberOfNodes, ActivationFunction af) {
        this.numberOfNodes = numberOfNodes;
        this.activation = af;
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



    

    
}
