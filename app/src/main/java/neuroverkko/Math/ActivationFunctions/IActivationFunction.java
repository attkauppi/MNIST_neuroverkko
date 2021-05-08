package neuroverkko.Math.ActivationFunctions;

public interface IActivationFunction {

    double calculate(double x);

    public enum ActivationFunction {
        STEP, LINEAR, SIGMOID, HYPERTAN, LEAKYRELU
    }

    double derivative(double x);
    
}
