package neuroverkko.Math.ActivationFunctions;



import static java.lang.Math.exp;
import static java.lang.Math.log;
import neuroverkko.Math.*;

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

    public Identity() {
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
    public double calculate(double x) {
        return 1.0 * x;
    }
    
    @Override
    public double derivative(double x) {
        return 1.0;
    }

    @Override
    public Vector dActFunc(Vector output) {
        return output.map(x -> 1);
    }

    public static Matrix activation(Matrix m) {
        return m.map(value -> 1.0*value);
    }

    public static Matrix matDerivative(Matrix m) {
        return m.map(x -> 1.0);
    }

//     static class IdentityDouble implements IActivationFunction {

//         private static String name = "Identity";
//         public static double coefficient = 1.0;
    
//         public IdentityDouble() {
//             //this.coefficient = 1.0;
//         }
    
//         @Override
//         public double calculate(double x) {
//             return coefficient * x;
//         }
    
//         @Override
//         public double derivative(double x) {
//             return 1.0;
//         }
// }



    
}
