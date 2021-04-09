/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;
import math.Matrix;
import math.IActivationFunction;

/**
 *
 * @author ari
 */
public class OutputLayer extends NeuronLayer {
    
    /**
     * OutputLayerin konstruktori
     */
    public OutputLayer(int numberOfNeurons, IActivationFunction iaf, int numberOfInputs) {
        super(numberOfNeurons, iaf);
        numberOfInputs = numberOfInputs;
        nextLayer = null;
        init();
    }
    
    public OutputLayer(int numberOfNeurons, IActivationFunction iaf, NeuronLayer prevLayer) {
        super(numberOfNeurons, iaf);
        setPreviousLayer(prevLayer);
        numberOfInputs = prevLayer.getNumberOfNeuronsInLayer();
    }
    
    /**
     * setNextLayer
     * 
     * Estää uusien layereiden asettamisen output-layerille
     */
    @Override
    public void setNextLayer(NeuronLayer layer) {
        this.nextLayer = null;
    }
    
    /**
     * setPreviousLayer
     * Linkittää tämän kerroksen edelliseen kerrokseen
     * @param NeuronLayer layer - edellinen kerros
     */
    public void setPreviousLayer(NeuronLayer layer) {
        this.prevLayer = layer;
        if (layer.nextLayer != this) {
            layer.setNextLayer((this));
        }
    }
    
}
