package neuralnetwork.neuralnetwork;

import neuralnetwork.neuralnetwork.math.ActivationFunction;
import neuralnetwork.neuralnetwork.math.Matrix;
import neuralnetwork.neuralnetwork.math.Vector;

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
    private transient int deltaBiasAdded;

    

    
}
