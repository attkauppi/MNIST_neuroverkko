package neuroverkko.Math.ActivationFunctions;

import neuroverkko.Math.*;

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
    public Matrix calcActivation(Matrix input) {
        return input.map(x -> sigmoidFunction(x));
    }

    @Override
    public Matrix dActFunc(Matrix output) {
        return output.map(x -> sigmoidFunction(x) * (1.0 - sigmoidFunction(x)));
    }

    @Override
    public double calculate(double x) {
        return 1.0/(1.0+Math.exp(-1.0*x));
    }

    @Override
    public double derivative(double x) {
        return calculate(x)*(1.0-calculate(x));
    }

    @Override
    public Matrix sigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = 1.0/(1.0+Math.exp(-a.getData()[i][j]));
            }
        }
        return new Matrix(t);
    }

    @Override
    public Matrix dSigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = a.getData()[i][j] * (1.0-a.getData()[i][j]);
            }
        }

        return new Matrix(t);
    }

    public static double sderivative(double x) {
        return scalculate(x)*(1.0-scalculate(x));
    }

    public static double scalculate(double x) {
        return 1.0/(1.0+Math.exp(-1.0*x));
    }
    
}
