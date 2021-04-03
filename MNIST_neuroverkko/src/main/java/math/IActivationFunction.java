/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * Tällä rajapinnalla hyödynnetään erilaisia aktivaatiofunktioita, joita
 * neuroverkossa käytetään. Kaikki erilaiset aktivaatiofunktiot toteuttavat
 * tämän luokan.
 * @author ari
 */
public interface IActivationFunction {
    // TODO: Implementoi
    /**
     * calc-metodilla lasketaan aktivaatiofunktion arvo
     * @param x
     * @return palauttaa aktivaatiofunktiolla lasketun arvon syöttellä x.
     */
    double calc(double x);
    
    /**
     * ActivationFunctionENUM
     * Listaa joitakin yleisesti hyödynnettyjä aktivaatiofunktioita. 
     */
    public enum ActivationFunctionENUM {
        STEP, LINEAR, SIGMOID, HYPERTAN
    }
    
    double derivative(double x);
    
}
