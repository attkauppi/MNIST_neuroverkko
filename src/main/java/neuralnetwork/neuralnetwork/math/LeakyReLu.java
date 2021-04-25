package neuralnetwork.neuralnetwork.math;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuralnetwork.neuralnetwork.math.*;

public class LeakyReLu extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "LeakyReLu";
    private Function actFunction = x -> x <= 0 ? 0.01 : x;
    private Function derActFunction = x -> x <= 0 ? 0.01 : 1;

    public LeakyReLu(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public LeakyReLu(String name) {
        super(name);
    }

    // TODO: Harkitse pidätkö
    public LeakyReLu() {
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
        return input.map(x -> x <= 0 ? 0.01 * x : x);
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> x <= 0 ? 0.01 : 1);
    }
}