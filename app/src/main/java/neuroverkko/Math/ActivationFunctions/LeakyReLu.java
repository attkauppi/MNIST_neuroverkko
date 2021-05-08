package neuroverkko.Math.ActivationFunctions;

import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.*;

public class LeakyReLu extends ActivationFunction {

    private static String name = "LeakyReLu";
    private Function actFunc = z -> z <= 0 ? 0.01 : z;
    private Function derActFunc = z -> z <= 0 ? 0.01 : 1;

    public LeakyReLu(String name, Function actFunc, Function derActFunc) {
        super(name, actFunc, derActFunc);
    }

    public LeakyReLu(String name) {
        super(name);
    }

    public LeakyReLu() {
        super(name);
    }

    @Override
    public double calculate(double x) {
        return Math.max(0.01, x);
    }

    @Override
    public double derivative(double x) {
        if (x <= 0) {
            return 0.01;
        } else {
            return 1.0;
        }
    }

    @Override
    public Matrix calcActivation(Matrix input) {
        return input.map(z -> z <= 0 ? 0.01 * z : z);
    }

    @Override
    public Matrix dActFunc(Matrix output) {
        return output.map(x -> x <= 0 ? 0.01: 1);
    }






    
}
