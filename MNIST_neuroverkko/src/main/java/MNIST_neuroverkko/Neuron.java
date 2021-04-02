/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import java.util.ArrayList;

/**
 *
 * @author ari
 */
public class Neuron {
    
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
