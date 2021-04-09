/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

/**
 *
 * @author ari
 */
public class Sigmoid implements IActivationFunction {
    
    private double a = 1.0;
    
    public Sigmoid(double a) {
        this.a = a;
    }
    
    @Override
    public double calc(double x) {
        return 1.0/(1.0+Math.exp(-a*x));
    }
    
    @Override
    public double derivative(double x) {
        return calc(x)*(1-calc(x));
    }
    
}
