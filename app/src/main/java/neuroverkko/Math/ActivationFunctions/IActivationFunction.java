package neuroverkko.Math.ActivationFunctions;

public interface IActivationFunction {

    double calculate(double x);

    public enum ActivationFunction {
        STEP, LINEAR, SIGMOID, HYPERTAN
    }

    double derivative(double x);
    
}
