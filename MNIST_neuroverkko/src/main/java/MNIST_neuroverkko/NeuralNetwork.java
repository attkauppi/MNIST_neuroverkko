/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import java.util.ArrayList;
import math.IActivationFunction;
/**
 * Kuvaa koko neuroverkkoa
 * 
 * @author ari
 */
public class NeuralNetwork {
    
    /**
     * input layer - koko verkon input layer
     */
    private InputLayer inputLayer;
    
    /**
     * ArrayLista piilotetuista kerroksista
     */
    public ArrayList<HiddenLayer> hiddenLayer;
    
    /**
     * OutputLayer - koko verkon output layer
     */
    public OutputLayer outputLayer;
    
    /**
     * Piilotettujen kerrosten lukumäärä
     */
    private int numberOfHiddenLayers;
    
    /**
     * syötteiden lukumäärä
     */
    private int numberOfInputs;
    
    /**
     * Outputtien lukumäärä
     */
    private int numberOfOutputs;
    
    /**
     * Inputit arraylist-muodossa
     */
    public ArrayList<Double> input;
    
    /**
     * outputit ArrayList muodossa
     */
    public ArrayList<Double> output;
    
    private boolean activeBias = true;
    
    /**
     * Neuroverkon konstruktori
     */
    public NeuralNetwork(int numberOfInputs, int numberOfOutputs, int[] numberOfHiddenNeurons,
            IActivationFunction[] hiddenActivationFunction, IActivationFunction outputActivationFunction) {
        
        this.numberOfHiddenLayers = numberOfHiddenNeurons.length;
        this.numberOfInputs = numberOfInputs;
        this.numberOfOutputs = numberOfOutputs;
        
        if (numberOfHiddenLayers == hiddenActivationFunction.length) {
            input = new ArrayList<>(numberOfInputs);
            inputLayer = new InputLayer(numberOfInputs);
            
            if (numberOfHiddenLayers > 0) {
                hiddenLayer = new ArrayList<>(numberOfHiddenLayers);
            }
            
            for (int i = 0; i < numberOfHiddenLayers; i++) {
                if (i == 0) {
                    try {
                        hiddenLayer.set(i, new HiddenLayer(numberOfHiddenNeurons[i], hiddenActivationFunction[i],
                        inputLayer.getNumberOfNeuronsInLayer()));
                    } catch (IndexOutOfBoundsException e) {
                        hiddenLayer.add(new HiddenLayer(numberOfHiddenNeurons[i], hiddenActivationFunction[i],
                        inputLayer.getNumberOfNeuronsInLayer()));
                    }

                    inputLayer.setNextLayer(hiddenLayer.get(i));
                    //System.out.println("Input layer: " + inputLayer.getNextLayer());
                } else {
                    try {
                        hiddenLayer.set(i, new HiddenLayer(numberOfHiddenNeurons[i], hiddenActivationFunction[i],
                        hiddenLayer.get(i-1).getNumberOfNeuronsInLayer()));
                    } catch (IndexOutOfBoundsException e) {
                        hiddenLayer.add(new HiddenLayer(numberOfHiddenNeurons[i], hiddenActivationFunction[i],
                        hiddenLayer.get(i-1).getNumberOfNeuronsInLayer()));
                    }
                    hiddenLayer.get(i-1).setNextLayer(hiddenLayer.get(i));
                }
            }
            
            if (numberOfHiddenLayers > 0) {
                outputLayer = new OutputLayer(numberOfInputs, outputActivationFunction, 
                        hiddenLayer.get(numberOfHiddenLayers-1).getNumberOfNeuronsInLayer());
                hiddenLayer.get(numberOfHiddenLayers-1).setNextLayer(outputLayer);
            } else {
                outputLayer = new OutputLayer(numberOfInputs, outputActivationFunction, numberOfOutputs);
                inputLayer.setNextLayer(outputLayer);
            }
        }
    }
    
    public NeuralNetwork(int numberOfInputs, int numberOfOutputs, IActivationFunction outputActivationFunction) {
        this(numberOfInputs, numberOfOutputs, new int[0], new IActivationFunction[0], outputActivationFunction);
    }
    
    public void setInputs(ArrayList<Double> inputs) {
        if (inputs.size() == numberOfInputs) {
            this.input = inputs;
        }
    }
    
    public void setInputs(double[] inputs) {
        if (inputs.length == numberOfInputs) {
            for (int i = 0; i < numberOfInputs; i++) {
                try {
                    input.set(i, inputs[i]);
                } catch (IndexOutOfBoundsException e) {
                    input.add(inputs[i]);
                }
            }
        }
    }
    
    public ArrayList<Double> getArrayInputs() {
        return input;
    }
    
    public Double getInput(int i) {
        return input.get(i);
    }
    
    public Double[] getInputs() {
        Double[] results = new Double[numberOfInputs];
        for (int i = 0; i < numberOfInputs; i++) {
            results[i] = input.get(i);
        }
        return results;
    }
    
    /**
     * calc
     * Laskee outputin kustakin kerroksesta ja välittää sen seuraavalle kerrokselle
     * 
     */
    public void calc() {
        this.inputLayer.setInputs(input);
        this.inputLayer.calc();
        if (numberOfHiddenLayers > 0) {
            for (int i = 0; i < numberOfHiddenLayers; i++) {
                HiddenLayer h = hiddenLayer.get(i);
                System.out.println("h.getPreviousLayer().getOutputs: " + h.getPreviousLayer().getOutputs());
                h.setInputs(h.getPreviousLayer().getOutputs());
                h.calc();
            }
        }
        
        this.outputLayer.setInputs(outputLayer.getPreviousLayer().getOutputs());
        this.outputLayer.calc();
        this.output = outputLayer.getOutputs();
    }
    
    /**
     * getArrayOutputs
     * @return neuroverkon outputit ArrayList-muodossa
     */
    public ArrayList<Double> getArrayOutputs() {
        return output;
    }
    
    /**
     * getOutputs
     * @return Palauttaa neuroverkon outputit taulukkomuodossa
     */
    public double[] getOutputs() {
        double[] outputs = new double[numberOfInputs];
        for (int i = 0; i < numberOfOutputs; i++) {
            outputs[i] = output.get(i);
        }
        return outputs;
    }
    
    public double getOutput(int i) {
        return output.get(i);
    }
    
    public void print() {
        System.out.println("NN: " + this.toString());
        System.out.println("\tInputs: "  + String.valueOf(this.numberOfInputs));
        System.out.println("\tOutputs: " + String.valueOf(this.numberOfOutputs));
        System.out.println("\tHidden Layers" + String.valueOf(this.numberOfHiddenLayers));
        
        for (int i = 0; i < numberOfHiddenLayers; i++) {
            System.out.println("\t\tHidden Layer " + String.valueOf(i) + ": " + 
                    String.valueOf(this.hiddenLayer.get(i).numberOfNeuronsInLayer) + " neurons");
        }
    }

    
}
