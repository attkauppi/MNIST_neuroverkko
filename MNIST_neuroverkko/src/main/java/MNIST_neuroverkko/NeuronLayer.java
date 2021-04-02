/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;

import java.util.ArrayList;

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
    
    
}
