/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import math.IActivationFunction;

/**
 *
 * @author ari
 */
public class HiddenLayer extends NeuronLayer {
    
    public HiddenLayer(int numberOfNeurons, IActivationFunction iaf, int numberOfInputs) {
        super(numberOfNeurons, iaf);
        numberOfInputs = numberOfInputs;
        init();
    }
    
    /**
     * setPreviousLayer
     * Asettaa kerroksen tätä kerrosta edeltäväksi. Tarkistaa myös, että
     * edellisessä kerroksessa on tämä kerros lisätty seuraavaksi kerrokseksi.
     * Mikäli ei ole, lisää edellisen kerroksen seuraavaksi kerrokseksi tämän.
     * 
     * @param prev - edellinen kerros
     */
    @Override
    public void setPreviousLayer(NeuronLayer prev) {
        this.prevLayer = prev;
        if (prev.nextLayer != this) {
            prev.setNextLayer(this);
        }
    }
    
   /**
    * setNextLayer
    * Asettaa jonkin kerrkosen tätä kerrosta seuraavaksi kerrokseksi. Tarkistaa
    * myös, että tämä kerros asetetaan toista kerrosta edeltäväksi kerrokseksi.
    * 
    * @param next seuraava kerros
    */ 
    @Override
    public void setNextLayer(NeuronLayer next) {
        nextLayer = next;
        if (next.prevLayer != this) {
            next.setPreviousLayer(this);
        }
    }
    
}
