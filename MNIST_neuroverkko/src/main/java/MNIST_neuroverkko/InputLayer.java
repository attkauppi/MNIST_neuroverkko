/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;
import static java.lang.System.in;
import java.util.ArrayList;
import math.Linear;
/**
 *
 * @author ari
 */
public class InputLayer extends NeuronLayer {
    
    /**
     * InputLayer-konstruktori
     * @param numberOfinputs Inputtien lukumäärä tässä kerroksessa ja neuroverkossa
     * 
     */
    public InputLayer(int numberOfInputs) {
        super(numberOfInputs, new Linear(1));
        this.prevLayer = null;
        this.nextLayer = null;
        this.numberOfInputs = numberOfInputs;
        init();
    }
    
    /**
     * setNextLayer
     * Asettaa tämän kerroksen input kerrokseksi seuraavalle kerrokselle
     * 
     * @param layer - kerros, jonka inputtina tämä kerros toimii
     */
    @Override
    public void setNextLayer(NeuronLayer layer) {
        this.nextLayer = layer;
        if (layer.prevLayer != this) {
            layer.setPreviousLayer(this);
        }
    }
    
    /**
     * setPreviousLayer
     * 
     * Metodin tarkoituksena on estää tämän kerroksen linkittäminen jonkin
     * muun kerroksen edelliseksi kerrokseksi, mikäli tämä kerros on ensimmäinen
     * kerros
     * @param layer 
     */
    @Override
    public void setPreviousLayer(NeuronLayer layer) {
        this.prevLayer = null;
    }
    
    /**
     * init
     * Alustaa kaikki tämän kerroksen neuronit
     */
    @Override
    public void init() {
        for (int i = 0; i < numberOfInputs; i++) {
            this.setNeuron(i, new InputNeuron());
            this.getNeuron(i).init();
        }
    }
    
    /**
     * setInputs
     * Asettaa reaalilukuja kerroksen syötteiksi
     * @param ArrayList<Double> inputs - syötteiksi tulevat reaaliluvut
     */
    @Override
    public void setInputs(ArrayList<Double> inputs) {
        if (inputs.size() == numberOfInputs) {
            inputs = inputs;
        }
    }
    
    /**
     * calc
     * 
     */
    @Override
    public void calc() {
        if (input != null && getListOfNeurons() != null) {
            for (int i = 0; i < numberOfNeuronsInLayer; i++) {
                double[] firstInput = {this.input.get(i)};
                getNeuron(i).setInputs(firstInput);
                getNeuron(i).calc();
                
                try {
                    this.output.set(i, getNeuron(i).getOutput());
                    System.out.println("Input calc output: " + output.toString());
                } catch (IndexOutOfBoundsException e) {
                    this.output.add(getNeuron(i).getOutput());
                }
            }
        }
    }
    
}
