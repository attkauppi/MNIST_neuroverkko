package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import java.util.Arrays;

import neuroverkko.Math.ActivationFunctions.*;

public class NeuralNetwork {

    public ArrayList<Layer> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer inputLayer;
    public Layer outputLayer;
    public double target;

    public NeuralNetwork(int inputSize) {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
        // Adds the input layer
        Identity i = new Identity();
        Layer l = new Layer(inputSize, "in", new Identity());
        l.setBias(1.0);

        addLayer(l);
        this.error = 0.0;
    }

    public NeuralNetwork() {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
    }



    public void addLayer(Layer l) {
        this.layers.add(l);
        this.layerSize = this.layers.size();

        // TODO: laita takaisin päälle
        // if (this.layers.size() >= 2) {
        //     this.layers.get(layerSize-2).setNextLayer(getLastLayer());
        // }
    }

    public Layer getFirstLayer() {
        return this.layers.get(0);
    }

    public Layer getLastLayer() {
        return this.layers.get(this.layers.size()-1);
        // return this.outputLayer;
    }

    public void addLayer(ActivationFunction iaf, int neurons, double bias) {
        this.layerSize = this.layers.size();
        String lName = String.valueOf(layerSize);
        Layer l = new Layer(neurons, lName);
        l.setBias(bias);
        l.setActivationFunction(iaf);
        addLayer(l);
        //this.outputLayer = this.layers.get(layerSize-1);
    }

    public void feedInput(double[] inputs) {
        System.out.println("inputs: " + Arrays.toString(inputs));

        Layer l = this.layers.get(0);

        // Gives the input to each neuron as a
        // an input.
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("input: " + inputs[i]);

            l.neurons.get(i).setInput(inputs[i]);
            //l.neurons.get(i).evaluate();
            l.neurons.get(i).setOutput(inputs[i]);
            //l.neurons.get(i).sendOutput();
            
            //l.neurons.get(i).setOutput(inputs[i]);
            //l.neurons.get(i).sendOutput();
            //System.out.println("Neuronin input: " + l.neurons.get(i).getInput());
        }

        for (Layer ll: layers) {
            ll.propagateInput();
        }

        //l.propagateInput();
    }

    public double getError(double target) {
        return (target - this.getLastLayer().neurons.get(0).getOutput());
    }

    public void backpropagateError() {

        for (int i = this.layers.size()-1; i > 0; i--) {
            
        }

    }







    
}
