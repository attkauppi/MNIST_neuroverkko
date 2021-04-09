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
    private ArrayList<HiddenLayer> hiddenLayer;
    
    /**
     * OutputLayer - koko verkon output layer
     */
    private OutputLayer outputLayer;
    
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
    private ArrayList<Double> input;
    
    /**
     * outputit ArrayList muodossa
     */
    private ArrayList<Double> output;
    
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
                outputLayer = new OutputLayer(numberOfInputs, outputActivationFunction, numberOfInputs);
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
        
    }
}
