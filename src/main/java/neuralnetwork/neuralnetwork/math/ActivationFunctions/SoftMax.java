package neuralnetwork.neuralnetwork.math;

public class SoftMax extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "SoftMax";
    private static Function actFunction = x -> x;
    private static Function derActFunction = x -> 1;

    public SoftMax(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public SoftMax(String name) {
        super(name);
    }

    // TODO: Harkitse pidätkö
    public SoftMax() {
        super(
            name,
            actFunction,
            derActFunction
        );
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