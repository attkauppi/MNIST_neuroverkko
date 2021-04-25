package neuralnetwork.neuralnetwork.math;

public interface IActivationFunction {

    public Vector calcActFunc(Vector input);

    public Vector dActFunc(Vector output);
    
    public enum ActivationFunctionENUM {
        STEP, LINEAR, SIGMOID, HYPERTAN
    }


    
}
