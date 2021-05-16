package neuroverkko.Math.ActivationFunctions;

import neuroverkko.Math.*;

public class Softplus extends ActivationFunction {

    private static String name = "Softplus";
    private Function actFunction = x -> Math.log(1.0) + Math.exp(x);
    private Function derAcFunction = x -> sigmoidFunction(x);

    public Softplus(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public Softplus(String name) {
        super(name);
    }

    public Softplus() {
        super(name);
    }


    @Override
    public double calculate(double x) {
        return Math.log(1.0) + Math.exp(x);
    }

    @Override
    public double derivative(double x) {
        if (x <= 0) {
            return 0.01;
        } else {
            return sigmoidFunction(x);
        }
    }

    @Override
    public Matrix calcActivation(Matrix input) {
        return input.map(x -> Math.log(1.0) + Math.exp(x));
    }

    @Override
    public Matrix dActFunc(Matrix output) {
        return output.map(x -> sigmoidFunction(x));
    }
}
