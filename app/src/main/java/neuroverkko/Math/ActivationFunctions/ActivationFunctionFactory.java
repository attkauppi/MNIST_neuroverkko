package neuroverkko.Math.ActivationFunctions;

@FunctionalInterface
public interface ActivationFunctionFactory {
    ActivationFunction newInstance(Object o);
}

