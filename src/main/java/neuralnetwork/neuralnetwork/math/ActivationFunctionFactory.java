package neuralnetwork.neuralnetwork.math;

@FunctionalInterface
public interface ActivationFunctionFactory {
    ActivationFunction newInstance(Object o);
}
