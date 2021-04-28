package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import neuroverkko.Math.ActivationFunctions.*;

public class NeuralNetwork {

    public ArrayList<Layer> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer inputLayer;
    public Layer outputLayer;

    public NeuralNetwork(int inputSize) {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
        // Adds the input layer
        Identity i = new Identity();
        Layer l = new Layer(inputSize, "in", new Identity());

        addLayer(l);
        this.error = 0.0;
    }

    public void addLayer(Layer l) {
        this.layers.add(l);
        this.layerSize = this.layers.size();

        if (this.layers.size() > 2) {
            this.layers.get(layerSize-2).setNextLayer(getLastLayer());
        }
    }

    public Layer getLastLayer() {
        return this.layers.get(this.layers.size()-1);
        // return this.outputLayer;
    }

    public void addLayer(IActivationFunction iaf, int neurons, double bias) {
        this.layerSize = this.layers.size();
        String lName = String.valueOf(layerSize);
        Layer l = new Layer(neurons, lName);
        l.setBias(bias);
        l.setActivationFunction(iaf);
        addLayer(l);
        //this.outputLayer = this.layers.get(layerSize-1);
    }

    public void feedInput(double[] inputs) {
        Layer l = this.layers.get(0);

        // Gives the input to each neuron as a
        // an input.
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("input: " + inputs[i]);

            l.neurons.get(i).setInput(inputs[i]);
            l.neurons.get(i).setOutput(inputs[i]);
            l.neurons.get(i).sendOutput();
            System.out.println("Neuronin input: " + l.neurons.get(i).getInput());
        }

        //l.sendOutput();
    }





    
}
