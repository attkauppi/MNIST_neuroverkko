/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import java.util.ArrayList;
import math.IActivationFunction;
import math.RandomNumberGenerator;

/**
 *
 * @author ari
 */
public class Neuron {
    
    /**
     * Neuronin painot
     */
    protected ArrayList<Double> weight;
    
    /**
     * Neuronin syötteet
     */
    private ArrayList<Double> input;
    
    /**
     * Neuronin output seuraavalle kerrokselle
     */
    private Double output;
    
    /**
     * Arvo, jonka neuronin aktivaatiofunktio saa
     */
    private double valueBeforeActivation;
    
    /**
     * Neuronin inputtien lukumäärä. 0 tarkoittaa, että neuronia ei ole vielä
     * alustettu
     */
    private int numberOfInputs = 0;
    
    /**
     * Neuronin bias
     */
    protected Double bias = 1.0;
    
    /**
     * Neuronin aktivaatiofunktio
     */
    private IActivationFunction activationFunction;
    
    public Neuron() { 
    }
    
    public Neuron(int numberOfInputs, IActivationFunction iaf) {
        this.numberOfInputs = numberOfInputs;
        this.weight = new ArrayList<>(numberOfInputs-1);
        this.input = new ArrayList<>(numberOfInputs);
        this.activationFunction = iaf;
    }
    
    public Neuron(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
        this.weight = new ArrayList<>(numberOfInputs+1);
        this.input = new ArrayList<>(numberOfInputs);
    }
    
    public void init() {
        for (int i = 0; i < numberOfInputs; i++) {
            double newWeight = RandomNumberGenerator.generateNext();
            try {
                this.weight.set(i, newWeight);
            } catch (IndexOutOfBoundsException e) {
                this.weight.add(newWeight);
            }
        }
    }
    
    /**
     * Asettaa reaalilukuvektorin arvot neuronin syötteeksi
     * @param values 
     */
    public void setInputs(double[] values) {
        if (values.length == numberOfInputs) {
            for (int i = 0 ; i < numberOfInputs; i++) {
                try {
                    input.set(i, values[i]);
                } catch (IndexOutOfBoundsException e) {
                    input.add(values[i]);
                }
            }
        }
    }
    
    public void setInputs(ArrayList<Double> values) {
        if (values.size() == numberOfInputs) {
            input = values;
        }
    }
    
    /**
     * getArrayinputs
     * @return Palauttaa neuronin syötteet ArrayList-muodossa
     */
    public ArrayList<Double> getArrayInputs() {
        return input;
    }
    
    /**
     * getInputs
     * @return Palauttaa neuronin inputit taulukkona
     */
    public double[] getInputs() {
        double[] inputs = new double[numberOfInputs];
        for (int i = 0; i < numberOfInputs; i++) {
            inputs[i] = this.input.get(i);
        }
        return inputs;
    }
    
    /**
     * setInput
     * Asettaa reaalilukuarvon tietyksi neuronin syötteeksi.
     * @param i kyseisen neuronin indeksi input listassa
     * @param value inputin arvoksi asetettava luku
     */
    public void setInput(int i, double value) {
        if (i >= 0 && i < numberOfInputs) {
            try {
                input.set(i, value);
            } catch (IndexOutOfBoundsException e) {
                input.add(value);
            }
        }
    }
    
    /**
     * getInput
     * @param i inputin indeksi input-listassa
     * @return inputin i arvo.
     */
    public double getInput(int i) {
        return input.get(i);
    }
    
    /**
     * getWeights
     * @return palautata neuronin painot taulukko muodossa
     */
    public double[] getWeights() {
        double[] weights = new double[numberOfInputs+1];
        for (int i = 0; i <= numberOfInputs; i++) {
            weights[i] = weight.get(i);
        }
        return weights;
    }
    
    public Double getWeight(int i) {
        return weight.get(i);
    }
    
    /**
     * getArrayWeights
     * @return Palauttaa neuronin painto ArrayList-muodossa
     */
    public ArrayList<Double> getArrayWeights() {
        return weight;
    }
    
    /**
     * updateWeight
     * Päivittää painot oppimisen aikana
     * @param i päivitettävän painon indeksi
     * @param value arvo, joka painoksi päivitetään
     */
    public void updateWeight(int i, double value) {
        if (i >= 0 && i <= numberOfInputs) {
            weight.set(i, value);
        }
    }
    
    public int getNumberOfInputs() {
        return this.numberOfInputs;
    }
    
    /**
     * setWeight
     * asettaa painolle uuden arvon indeksissä i
     * @param i päivitettävän painon indeksi
     * @param value painoksi asetettava arvo
     * @throws NeuronException
     */
    public void setWeight(int i, double value) throws NeuronException {
        if (i >= 0 && i < numberOfInputs) {
            this.weight.set(i, value);
        } else {
            throw new NeuronException("Painolla virheellinen indeksi");
        }
    }
    
    public double getOutput() {
        return output;
    }
    
    public void calc() {
        valueBeforeActivation = 0.0;
        if (numberOfInputs > 0) {
            if (input != null && weight != null) {
                for (int i = 0; i <= numberOfInputs; i++) {
                    // The products of all inputs and weights are summed
                    // bias multiplies the last weight - i == numberOfInputs, which
                    // is saved as the valueBeforeActivation's value.
                    valueBeforeActivation += (i==numberOfInputs?bias:input.get(i))*weight.get(i);
                }
            }
        }
        output = activationFunction.calc(valueBeforeActivation);
    }
    
    public void setActivationFunction(IActivationFunction iaf) {
        this.activationFunction = iaf;
    }
    
    /**
     * getValueBeforeActivation
     * @return palauttaa valueBeforeActivation muuttujan arvon
     */
    public double getValueBeforeActivation() {
        return valueBeforeActivation;
    }
    

    
    
    /**
     * - listOfWeightIn: lista reaalilukuja, jotka kuvaavat neuronien input
     * painoja
     * - listOfWeightOut: lista reaalilukuja, jotka kuvaavat neuronien output
     * painoja.
     */
    
    // TODO: Korvaa ArrayListit omalla implementaatiolla.
    private ArrayList<Double> listOfWeightIn;
    private ArrayList<Double> listOfWeightOut;
    
    /**
     * Alustaa listOfWeightIn ja listOfWeightOut -arvot sattumanvaraisilla
     * reaaliluvuilla
    */
    public double initNeuron() {
        // TODO: Implementoi
        return 0.0;
    }
    
    /**
     * Asettaa arvot listOfWeightIn-listaan.
     * @param listOfWeightIn 
     */
    public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
        // TODO: Implementoi
    }
    
    /**
     * Asettaa arvot listOfWeightOut-listaan.
     * @param listOfWeightOut 
     */
    public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
        // TODO: Implementoi
    }
    
    /**
     * Palauttaa listan neuronien input-painoja
     * @return listOfWeightIn
     */
    public ArrayList<Double> getListOfWeightIn() {
        return listOfWeightIn;
    }
    
    /**
     * Palauttaa listan listan neuronien output-painoja
     * @return listOfWeightOut
     */
    public ArrayList<Double> getListOfWeightOut() {
        return listOfWeightOut;
    }
    
    
}
