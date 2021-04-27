package neuroverkko.Neuroverkko;

import java.util.ArrayList;

public class Layer {

    public ArrayList<Neuron> neurons;
    Layer nextLayer;
    Layer previousLayer;
    int size;
    String name;
    public double[][] matrix;

    public Layer(int size, String name) {
        this.name = name;
        this.size = size;
        this.neurons = new ArrayList<>();
        createNeurons();
    }

    public void createNeurons() {
        for (int i = 0; i < this.size; i++) {
            Neuron n = new Neuron();
            n.setName(i);
            this.neurons.add(new Neuron());
        }
    }

    public Layer getPrevLayer() {
        return this.previousLayer;
    }

    public Layer getNextLayer() {
        return this.nextLayer;
    }

    public int getSize() {
        return this.size;
    }

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
    public void setNextLayer(Layer layer) {
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
    private void setPrevLayer(Layer layer) {
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
    private void connectNextLayer(Layer layer) {
        for (int i = 0; i < this.neurons.size(); i++) {
            for (int j = 0; j < layer.neurons.size(); j++) {
                this.neurons.get(i).addOutput(layer.neurons.get(j));
            }            
        }
    }

    public void sendOutput() {
        for (Neuron n: this.neurons) {
            n.sendOutput();
        }
        receiveOutput(this.getNextLayer());
        
    }

    public static void receiveOutput(Layer layer) {
        if (layer.hasNextLayer()) {
            for (Neuron n: layer.neurons) {
                n.evaluate();
                if (n.outputs.size() > 0) {
                    n.sendOutput();
                }
            }
        }


    }

    @Override
    public String toString() {
        return this.name + ", " + this.size;
    }
}
