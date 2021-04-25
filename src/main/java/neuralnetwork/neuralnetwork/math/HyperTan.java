package neuralnetwork.neuralnetwork.math;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuralnetwork.neuralnetwork.math.*;

public class HyperTan extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "HyperTan";
    private Function actFunction;
    private Function derActFunction;
    private double tangentCoefficient = 1.0;

    public HyperTan(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public HyperTan(String name) {
        super(name);
    }

    public HyperTan() {
        super(name);
    }

    public HyperTan(double tangentCoefficient) {
        super(name);
        this.tangentCoefficient = tangentCoefficient;
    }


    @Override
    public Vector calcActFunc(Vector input) {

        // TODO: Sama asia toteutettu for-loopilla
        // for (int i = 0; i < input.getData().length; i++) {
        //     double x = input.getData()[i];
        //     if (x <= 0) {
        //         input.getData()[i] = 0.01 * x;
        //     } else {
        //         input.getData()[i] = x;
        //     }
        // }
        return input.map(x -> (1.0-exp(-tangentCoefficient))*(1.0/+exp(-tangentCoefficient*x)));
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> (1.0)*Math.pow(dActFunc(x),2.0));
    }

    // The following two methods were added because calculating the
    // activation funciton for a vector required it, but also because
    // adding these will make using individual neurons in the future
    // that much easier.

    /**
     * calcActFunc
     * 
     * Calculates the activation function for a single
     * double value.
     * @param x
     * @return double x
     */
    public double calcActFunc(double x) {
        return (1.0-Math.exp(-tangentCoefficient*x))/(1.0+Math.exp(-tangentCoefficient*x));
    }

    public double dActFunc(double x) {
        return (1.0)-Math.pow(calcActFunc(x),2.0);
    }
}