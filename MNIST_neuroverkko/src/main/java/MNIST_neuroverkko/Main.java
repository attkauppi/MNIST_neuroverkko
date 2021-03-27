/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;
import java.util.Arrays;
import math.Matrix;
/**
 *
 * @author ari
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Matrix m = new Matrix(3,3);
        System.out.println("yes yes yes");
        
        m.summa(3.0);
        System.out.println("m.arvot: "+ Arrays.deepToString(m.arvot));
    }
    
}
