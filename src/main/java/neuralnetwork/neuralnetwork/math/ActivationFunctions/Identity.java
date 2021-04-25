package neuralnetwork.neuralnetwork.math;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuralnetwork.neuralnetwork.math.*;

public class Identity extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "Identity";
    private Function actFunction;
    private Function derActFunction;

    public Identity(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public Identity(String name) {
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
        return input.map(x -> x);
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> 1);
    }
}