package neuralnetwork.neuralnetwork.math;
import static java.lang.Math.exp;
import static java.lang.Math.log;

public class SoftPlus extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "SoftPlus";
    private Function actFunction;
    private Function derActFunction;

    public SoftPlus(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    //IActivationFunction.ActivationFunctionENUM("RELU");

    public SoftPlus(String name) {
        super(name);
    }

    public SoftPlus() {
        super(name);
    }

    // public SoftPlus() {
    //     super("SoftPlus", map(x -> log(1.0 + exp(x))), x -> sigmoidFunction(x));
    // }

    @Override
    public Vector calcActFunc(Vector input) {
        return input.map(x -> log(1.0 + exp(x)));
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> sigmoidFunction(x));
    }
    
}
