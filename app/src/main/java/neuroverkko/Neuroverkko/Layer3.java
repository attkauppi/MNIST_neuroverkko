package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import neuroverkko.Math.*;
import neuroverkko.Math.ActivationFunctions.IActivationFunction;
import neuroverkko.Math.ActivationFunctions.ActivationFunction;

/**
 * Ylimääräinen luokka, johon toimintoja toteutettu käyttäen yksittäisiä neuroneja.
 */

public class Layer3 {

    public ArrayList<Neuron> neurons;
    Layer3 nextLayer;
    Layer3 previousLayer;
    int size;
    String name;
    public double[][] matrix;
    public ActivationFunction iaf;
    public double bias;
    public Matrix weights;
    public Matrix deltaWeights;
    public Vector deltaBiasV;
    public double deltaBias;
    public Vector weightedInput;
    public int deltaWeightsAdded;
    public int deltaBiasesAdded;
    public Matrix deltaBiasMatrix;

    // Delta weights
    public Vector deltaw;



    public Layer3(int size, String name) {
        this.name = name;
        this.size = size;
        this.neurons = new ArrayList<>();
        this.deltaWeightsAdded =0;
        this.deltaBias = 0.0;
        this.deltaBiasV = new Vector(size);
        this.deltaWeights = new Matrix(size, size);
        this.deltaBiasMatrix = new Matrix(size, 1);
        this.deltaBiasesAdded=0;
        this.weights = new Matrix(size, size);
        createNeurons();
    }

    /**
     * uusi
     * @param deltaWeights
     */
    public void setDeltaWeightsMatrix(Matrix deltaWeights) {
        this.deltaWeights = deltaWeights.copy();
    }

    /**
     * uusi
     * @return
     */
    public Matrix getDeltaWeightsMatrix() {
        return this.deltaWeights;
    }

    public void setDeltaWeights(Vector deltaw) {
        this.deltaw = deltaw;
    }

    public Vector getDeltaWeights() {
        return this.deltaw;
    }

    public Layer3(int size, String name, ActivationFunction iaf) {
        this.name = name;
        this.size = size;
        this.neurons = new ArrayList<>();
        this.iaf = iaf;
        this.deltaWeightsAdded =0;
        this.deltaBias = 0.0;
        this.deltaBiasV = new Vector(size);
        this.deltaBiasesAdded=0;
        this.deltaWeights = new Matrix(size, size);
        this.weights = new Matrix(size, size);
        createNeurons(iaf);
    }

    /** inputs in the correct shape */
    public Matrix getInputsMat() {
        double[] inputm = this.getInputVector().getData();

        double[][] matrix = new double[inputm.length][1];
        for (int i = 0; i < inputm.length; i++) {
            matrix[i][0] = inputm[i];
        }

        return new Matrix(matrix);
    }

    public void setDeltaWeightDeltaBias(Matrix dCdW, Vector dCdI) {
        // if (this.deltaWeights == null) {
        //     this.deltaWeights = new Matrix(weights.rows, weights.cols);
        // }
        //this.deltaWeights = new Matrix(this.getOutputMatrix().getData());
        this.deltaWeights = this.getOutputMatrix().copy();
        System.out.println("Ennen yhteenlaskua: " + dCdW.toString());
        this.deltaWeights.addMatrix(dCdW);
        this.deltaWeightsAdded++;
        this.deltaBiasV = deltaBiasV.vecAdd(dCdI);
        this.deltaBiasesAdded++;
    }

    public void createNeurons(ActivationFunction iaf) {
        for (int i = 0; i < this.size; i++) {
            //n.setName(i);
            //n.setActivationFunction(iaf);
            this.neurons.add(new Neuron(i, iaf));
        }
    }

    /**
     * Sets the activation function for the layer and each neuron
     * individually
     * @param iaf
     */
    public void setActivationFunction(ActivationFunction iaf) {
        this.iaf = iaf;
        for (Neuron n: this.neurons) {
            n.setActivationFunction((ActivationFunction) iaf);
        }
    }

    // public void setWeights(double[][] weights) {
    //     int weightIndex = 0;
    //     for (Neuron n: neurons) {

    //         // minkä neuronin ulostuloja otetaan
    //         for (int i = 0; i < weights[0].length; i++) {
    //             for (int j = 0; j < weights.length; i++) {
                    
    //             }

    //         }

    //         n.setWeights(weights[weightIndex]);
    //         weightIndex++;
    //     }

    // }

    /**
     * setBias 
     * sets the bias for the layer and
     * each neuron individually
     * @param bias (double)
     */
    public void setBias(double bias) {
        this.bias = bias;
        for (Neuron n: this.neurons) {
            n.setBias(bias);
        }
    }
    
    public void createNeurons() {
        for (int i = 0; i < this.size; i++) {
            //Neuron n = ;
            //n.setName(i);
            this.neurons.add(new Neuron(i, iaf));
        }
    }

    public Layer3 getPrevLayer() {
        return this.previousLayer;
    }

    public Layer3 getNextLayer() {
        return this.nextLayer;
    }

    public int getSize() {
        return this.size;
    }

    public void printWeights() {
        //System.out.println("printWeights");
        for (int i = 0; i < this.neurons.size(); i++) {
            for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                System.out.print(this.neurons.get(i).inputs.get(j).weight + ", ");
            }
            System.out.println("");
            
        }
    }

    public void printDeltaWeights() {
        //System.out.println("printWeights");
        for (int i = 0; i < this.neurons.size(); i++) {
            for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                System.out.print(this.neurons.get(i).inputs.get(j).deltaWeight + ", ");
            }
            System.out.println("");
            
        }
    }

    



    public void setWeightsFromMatrix(double[][] matrix) {
        //this.matrix = new double[this.neurons.size()][this.neurons.get(0).inputs.size()];
        
        //if (this.hasPreviousLayer()) {
        if (this.neurons.size() > 1) {

            
            for (int i = 0; i < this.neurons.size(); i++) {
                for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                    this.neurons.get(i).inputs.get(j).setWeight(matrix[i][j]);
                    // this.matrix[i][j] = this.neurons.get(i).inputs.get(j).weight;
                }
            }
        } else {
            if (this.hasPreviousLayer()) {
                for (int j = 0; j < this.neurons.get(0).inputs.size(); j++) {
                    this.neurons.get(0).inputs.get(j).setWeight(matrix[j][0]);
                    // this.matrix[i][j] = this.neurons.get(i).inputs.get(j).weight;
                }
            }
            
        }
        //}
    }

    /**
     * creates a 2d matrix from the weights
     * of edges leading to neuron.
     */
    public void createWeightsMatrix() {
        this.matrix = new double[this.neurons.size()][this.neurons.get(0).inputs.size()];
        
        if (this.hasPreviousLayer()) {
            for (int i = 0; i < this.neurons.size(); i++) {
                for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                    this.matrix[i][j] = this.neurons.get(i).inputs.get(j).weight;
                }
            }
        }
    }

    public Vector getOutputVector() {
        double[] v = new double[this.neurons.size()];

        for (int i = 0; i < this.neurons.size(); i++) {
            v[i] = this.neurons.get(i).getOutput();
        }
        return new Vector(v);
    }

    public Matrix getOutputMatrix() {
        double[][] m = new double[this.neurons.size()][1];

        for (int i = 0; i < this.neurons.size(); i++) {
            m[i][0] = this.neurons.get(i).getOutput();
        }

        return new Matrix(m);

    }

    public boolean hasNextLayer() {
       if (this.getNextLayer() == null) {
           return false;
        }
        return true;
    }

    public boolean hasPreviousLayer() {
        if (this.getPrevLayer() == null) {
            return false;
        }
        return true;
    }

    public int getInputSize() {
        int input = 0;
        for (Neuron n: neurons) {
            System.out.println(n.getInputSize());
            input = n.getInputSize();
        }

        return input;
    }

    /**
     * Jätit tarkoituksella pois edellisen lisäämisen,
     * ettei tule sekaannuksia
     * @param layer
     */
    public void setNextLayer(Layer3 layer) {
        this.nextLayer = layer;
        layer.previousLayer = this;
        this.connectNextLayer(layer);
    }

    /**
     * setPrevLayer
     * 
     * Sets the input layer as the previous layer
     * of the current layer.
     * 
     * @param layer
     */
    private void setPrevLayer(Layer3 layer) {
        this.previousLayer = layer;
    }

    /**
     * connectNextLayer
     * 
     * Connects the neurons in this layer to
     * the neurons in the next. This is used
     * via the setNextLayer method.
     * 
     * @param layer
     */
    private void connectNextLayer(Layer3 layer) {
        for (int i = 0; i < this.neurons.size(); i++) {
            for (int j = 0; j < layer.neurons.size(); j++) {
                this.neurons.get(i).addOutput(layer.neurons.get(j));
            }            
        }
    }

    /** uusi */
    public void updateWeightsFromDeltaM() {
        // delta weights * learning rate

        // TODO: Tämä toimenpide ei ole ihan oikeaoppinen.
        this.weights = this.deltaWeights;
        double[][] dWeights = new double[weights.rows][weights.cols];

        for (int i = 0; i < this.weights.rows; i++) {
            for (int j = 0; j < this.weights.cols; j++) {
                dWeights[i][j] = 0.0;
            }
            
        }
        this.deltaWeights = new Matrix(dWeights);
    }

    /**
     * sendOutput()
     * Sends the output of the neurons in this layer to the neurons in the next.
     */
    public void sendOutput() {
        for (Neuron n: this.neurons) {
            n.sendOutput();
        }
        receiveOutput(this.getNextLayer());
        // if (this.hasNextLayer()) {
        //     receiveOutput(this.getNextLayer());
        // }
        
    }

    /**
     * receiveOutput
     * @param layer
     */
    public static void receiveOutput(Layer3 layer) {
        for (Neuron n: layer.neurons) {
            n.evaluate();
            
        }
        // if (layer.hasNextLayer()) {
            
        // }
    }

    /**
     * propagateInput()
     * 
     * propagates the input received by neurons in this layer to
     * the neurons in the next layer.
     */
    public void propagateInput() {
        for (Neuron n: neurons) {
            n.evaluate();
        }
    }

    public void updateWeights(Matrix weights, Matrix dCdW) {
        dCdW.scalarProd(0.01);
        this.weights = weights.matSubract(dCdW);
        this.setWeightsMatrixNeuron(this.weights);
        this.setWeightsFromMatrix(this.weights.getData());

        double[][] dWeights = new double[weights.rows][weights.cols];

        for (int i = 0; i < this.weights.rows; i++) {
            for (int j = 0; j < this.weights.cols; j++) {
                dWeights[i][j] = 0.0;
            }
        }
        this.deltaWeights = new Matrix(dWeights);
        //deltaWeights.map(x -> 0);

    }

    public void updateFromLearning() {
        this.weights = this.getWeightsMatrix();

        if (deltaWeightsAdded > 0) {
            /// l2
            

            Matrix average_dW = deltaWeights.scalarProd(1.0/deltaWeightsAdded);
            System.out.println("Average dw: " + average_dW.toString());
            this.setWeightsFromMatrix(Matrix.subtract(weights, average_dW).getData());
            Matrix erotus = Matrix.subtract(weights, average_dW);

            System.out.println("Erotus: " + erotus.toString());
            

            deltaWeights.map(x -> 0); // clears
            deltaWeightsAdded = 0;

        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.size;
    }

    /**
     * getInputVector
     * 
     * Creates an input vector from the outputs of the
     * previous layer.
     * 
     * @return Vector inputVector
     */
    public Vector getInputVector() {

        
        double[] inputV = new double[this.getPrevLayer().neurons.size()];

        for (int i = 0; i < this.getPrevLayer().neurons.size(); i++) {
            inputV[i] = this.getPrevLayer().neurons.get(i).output; 
        }
            
        // }
        // for (int i = 0; i < this.neurons.size(); i++) {
        //     for (Edge ed: this.neurons.get(i).inputs) {
        //         inputV[i] = ed.fromNeuron.getOutput();
        //     }
        // }
        return new Vector(inputV);
    }


    public Matrix getWeightsMatrix() {
        this.matrix = new double[this.neurons.size()][this.neurons.get(0).inputs.size()];
        
        if (this.hasPreviousLayer()) {
            for (int i = 0; i < this.neurons.size(); i++) {
                for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                    this.matrix[i][j] = this.neurons.get(i).inputs.get(j).weight;
                }
            }
        }
        return new Matrix(this.matrix);
    }

    /**
     * Neuronien tasolla
     * @return
     */
    public void setWeightsMatrixNeuron(Matrix m) {
        
        
        if (this.hasPreviousLayer()) {
            for (int i = 0; i < this.neurons.size(); i++) {
                for (int j = 0; j < this.neurons.get(i).inputs.size(); j++) {
                    
                    this.neurons.get(i).inputs.get(j).setWeight(m.getData()[i][j]);
                }
            }
        }
        this.weights = m.copy();
    }
}
