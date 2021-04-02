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

}
