/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 * Linear
 * 
 * Kuvaa lineaarista aktivaatiofunktiota, joka implementoi IActivationFunction
 * -rajanpinnan.
 * @author ari
 */
public class Linear implements IActivationFunction {
    
    /**
     * Kerroin muuttujalle x
     */
    private double a = 1.0;
    
    /**
     * Linear-dummy-konstruktori
     */
    public Linear() {
    }
    
    /**
     * Linear-konstruktori
     * @param value - lineaarisen funktion muuttuja
     */
    public Linear(double arvo) {
        this.setA(arvo);
    }
    
    /**
     * setA
     * Asettaa uuden muuttujan lineaariselle funktiolle.
     * @param arvo lineaarisen funktion uusi muuttuja
     */
    public void setA(double arvo) {
        this.a = arvo;
    }
    
    /** 
     * calc
     * Suorittaa funktion varsinaisen laskennan
     * @param x
     * @return Antaa funktion arvon syötteellä x
     */
    @Override
    public double calc(double x) {
        System.out.println("x linear laskun jälkeen: " + (a*x));
        return a*x;
    }
    
    @Override
    public double derivative(double x) {
        System.out.println("Linear derivative: " + a);
        return a;
    }
    
    
    
    
}
