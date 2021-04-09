/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import java.util.ArrayList;
import math.IActivationFunction;

/**
 * Yleisimplementaatio NeuralLayerista. Kaikki näille kerroksille yhteiset
 * ominaisuudet ja toiminnot määritellään tässä luokassa.
 * @author ari
 */
public abstract class NeuronLayer {
    
    /**
     * Kyseisen kerroksen neuronien lukumäärä.
     */
    protected int numberOfNeuronsInLayer;
    /**
     * ArrayLista, johon kyseisen kerroksen neuronit ovat tallennettuna
     */
    // TODO: Korvaa ArrayList omalla implementaatiolla.
    private ArrayList<Neuron> neuron;
    
    /**
     * Kerroksen käyttämän aktivaatiofunktion määrittely.
     */
    // TODO: Implementoi aktivaatiofunktiot.
    protected IActivationFunction activationFunction;
    
    /**
     * Edellinen kerros, jonka output tulee tähän kerrokseen. Auttaa pitämään
     * lukua kerrosten järjestyksestä.
     */
    protected NeuronLayer prevLayer;
    
    /**
     * Seuraava kerros, johon tämän kerroksen arvot menevät inputteina.
     */
    protected NeuronLayer nextLayer;
    
    /**
     * Lista input arvoja, jotka tämä kerros saa syötteinä.
     */
    // TODO: Korvaa ArrayList omalla implementaatiolla.
    protected ArrayList<Double> input;
    
    /**
     * lista output-arvoja, jotka tämä kerros tuottaa
     */
    // TODO: Korvaa ArrayList omalla implementaatiolla.
    protected ArrayList<Double> output;
    
    /**
     * Inputtien lukumäärä, jonka tämä keros voi ottaa vastaan
     */
    protected int numberOfInputs;
    
    /**
     * NeuronLayer-konstruktori
     * @param numberOfNeurons Neuronien lukumäärä tässä kerroksessa
     */
    public NeuronLayer(int numberOfNeurons) {
        this.numberOfNeuronsInLayer = numberOfNeurons;
        // TODO: Korvaa arraylist omalla implementaatiolla
        neuron = new ArrayList<>(numberOfNeurons);
        output = new ArrayList<>(numberOfNeurons);
    }
    
    /**
     * NeuronLayer-konstruktori.
     * @param numberOfneurons - Määrä neuroneita tässä kerroksessa.
     */
    public NeuronLayer(int numberOfNeurons, IActivationFunction iaf) {
        this.numberOfNeuronsInLayer = numberOfNeurons;
        this.activationFunction = iaf;
        // TODO: Korvaa ArrayList omalla implementaatiolla
        neuron = new ArrayList<>(numberOfNeurons);
        output = new ArrayList<>(numberOfNeurons);
    }
    
    /**
     * getNumberOfNeuronsInLayer
     * @return neuronien lukumäärä kerroksessa
     */
    public int getNumberOfNeuronsInLayer() {
        return numberOfNeuronsInLayer;
    }
    
    /**
     * getListOfNeurons
     * @return Palauttaa listan neuroneita tässä kerroksessa
     */
    // TODO: korvaa ArrayList omalla implementaatiolla
    public ArrayList<Neuron> getListOfNeurons() {
        return neuron;
    }
    
    /**
     * getPreviousLayer
     * @return Palauttaa edellisen kerrokseen
     */
    protected NeuronLayer getPreviousLayer() {
        return prevLayer;
    }
    
    /**
     * getNextlayer
     * @return palauttaa seuraavan kerroksen
     */
    protected NeuronLayer getNextLayer() {
        return nextLayer;
    }
    
    /**
     * setPreviousLayer
     * @param layer Edelliseksi kerrokseksi asetettava kerros
     */
    protected void setPreviousLayer(NeuronLayer layer) {
        prevLayer = layer;
    }
    
    /**
     * setNextlayer
     * @param layer asettaa seuraavan kerroksen
     */
    protected void setNextLayer(NeuronLayer layer) {
        nextLayer = layer;
    }
    
    /**
     * init
     * Asettaa aktivaatiofunktion kaikille kerroksen neuroneille sekä alustaa
     * kerroksen neuronit
     */
    protected void init() {
        if (numberOfNeuronsInLayer > 0) {
            for (int i = 0; i < numberOfNeuronsInLayer; i++) {
                try {
                    neuron.get(i).setActivationFunction(activationFunction);
                    neuron.get(i).init();
                } catch(IndexOutOfBoundsException e) {
                    neuron.add(new Neuron(numberOfInputs, activationFunction));
                    neuron.get(i).init();
                }
            }
        }
    }
    
    /**
     * setInputs
     */
    protected void setInputs(ArrayList<Double> inputs) {
        this.numberOfInputs = inputs.size();
        this.input = inputs;
    }
    
    protected void calc() {
        for (int i = 0; i < numberOfNeuronsInLayer; i++) {
            neuron.get(i).setInputs(this.input);
            neuron.get(i).calc();
            
            try {
                output.set(i, neuron.get(i).getOutput());
            } catch (IndexOutOfBoundsException e) {
                output.add(neuron.get(i).getOutput());
            }
        }
    }
    
    /**
     * getOutputs
     * @return Palauttaa arraylistan tämän kerroksen outputeista
     */
    protected ArrayList<Double> getOutputs() {
        return output;
    }
    
    /**
     * getNeuron
     * @param i neuronin indeksi
     * @return neuroni i:nnessä indeksissä
     */
    public Neuron getNeuron(int i) {
        return neuron.get(i);
    }
    
    /**
     * setNeuron
     * Asettaa luodun neuronin syötteenä saatuun kerrokseen
     * @param i kerroksen indeksi, johon neuroni sijoitetaan
     * @param neuron neuroni, joka kyseiseen indeksiin sijoitetaan
     */
    protected void setNeuron(int i, Neuron neuron) {
        try {
            this.neuron.set(i, neuron);
        } catch (IndexOutOfBoundsException e) {
            this.neuron.add(neuron);
        }
    }
    
    public double getWeight(int i, int j) {
        return this.neuron.get(j).getWeight(i);
    }
    
    
    
    

}
