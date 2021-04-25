package neuralnetwork.neuralnetwork.math;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuralnetwork.neuralnetwork.math.*;

public class ReLu extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "ReLu";
    private Function actFunction;
    private Function derActFunction;

    public ReLu(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public ReLu(String name) {
        super(name);
    }

    public ReLu() {
        super(name);

    }

    @Override
    public Vector calcActFunc(Vector input) {
        return input.map(x -> x <= 0 ? 0 : x);
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> x <= 0 ? 0 : 1);
    }
}
