package neuralnetwork.neuralnetwork.math.ActivationFunctions;


import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuralnetwork.neuralnetwork.math.*;

public class Step extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "Step";
    private Function actFunction;
    private Function derActFunction;

    public Step(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public Step(String name) {
        super(name);
    }

    public Step() {
        super(name);
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
        return input.map(x -> x < 0 ? 0.0: 1.0);
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> x == 0 ? Double.MAX_VALUE : 0.0);
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
        x = x < 0 ? 0.0 : 1.0;
        return x;
    }

    public double dActFunc(double x) {
       x = x == 0 ? Double.MAX_VALUE : 0.0;
       return x;
    }
}