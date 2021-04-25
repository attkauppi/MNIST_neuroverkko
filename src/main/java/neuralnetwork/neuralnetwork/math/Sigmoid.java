package neuralnetwork.neuralnetwork.math;

import static java.lang.Math.exp;
import static java.lang.Math.log;

//mport org.graalvm.compiler.debug.DebugContext.Activation;


public class Sigmoid extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "Sigmoid";
    private Function actFunction;
    private Function derActFunction;

    public Sigmoid(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public Sigmoid(String name) {
        super(name);
    }

    public Sigmoid() {
        super(name);
    }

    @Override
    public Vector calcActFunc(Vector input) {
        return input.map(x -> sigmoidFunction(x));
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> sigmoidFunction(x) * (1.0 - sigmoidFunction(x)));
    }
    
}