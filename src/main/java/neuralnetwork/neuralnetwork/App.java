package neuralnetwork.neuralnetwork;

import java.util.Arrays;

//mport org.graalvm.compiler.debug.DebugContext.Activation;

/**
 * Hello world!
 *
 */
import neuralnetwork.neuralnetwork.math.Matrix;
import neuralnetwork.neuralnetwork.math.*;
import neuralnetwork.neuralnetwork.math.ActivationFunction;
import neuralnetwork.neuralnetwork.math.IActivationFunction;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        
        ActivationFunction s = new Sigmoid();
        Layer l = new Layer(10, s);
        Layer l2 = new Layer(10, new Sigmoid());

        // Matrix m = new Matrix(10000, 10000);
        // Matrix m2 = new Matrix(10000, 10000);

        // for (int i = 0; i < m.data.length; i++) {
        //     for (int j = 0; j < m.data[0].length; j++) {
        //         m.data[i][j] = 1.0;
        //         m2.data[i][j] = 1.0;
        //     }
        // }

        // long startTime = System.nanoTime();
        // m.scalarProd(2.0);
        // long endTime = System.nanoTime();


        // long sTime2 = System.nanoTime();
        // m2.scalarProd2(2.0);
        // long eTime2 = System.nanoTime();

        // System.out.println("Striimejä käyttävän kesto: " + (endTime-startTime));
        // System.out.println("Normaalin: " + (eTime2-sTime2));
        // for (int i = 0; i < m.data.length; i++) {
        //     System.out.println(Arrays.toString(m.data[i]));
        // }

    }
}
