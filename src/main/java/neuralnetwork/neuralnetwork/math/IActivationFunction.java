package neuralnetwork.neuralnetwork.math;

public interface IActivationFunction {

    public Vector calcActFunc(Vector input);

    public Vector dActFunc(Vector output);
    
    public enum ActivationFunctionENUM {
        STEP, LINEAR, SIGMOID, HYPERTAN, LEAKYRELU, IDENTITY, SOFTMAX, SOFTPLUS, RELU
    }

    public Vector calc_dCostdInput(Vector output, Vector dCostdOutput);

    public String getName();

    public double sigmoidFunction(double x);

    
}
