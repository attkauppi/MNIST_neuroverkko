package neuroverkko.Math.ActivationFunctions;

import static java.lang.Math.exp;
import static java.lang.Math.log;

import neuroverkko.Math.*;
public abstract class ActivationFunction implements IActivationFunction {

    // TODO: oli final, muuta jos tulee ongelmia
    private String name;
    // Activation function
    private Function actFunc;
    // Derivative of the activation function
    private Function dActFunc;

    public ActivationFunction(String name) {
        this.name = name;
    }

    public ActivationFunction(String name, Function actFunc, Function dActFunc) {
        this.name = name;
        this.actFunc = actFunc;
        this.dActFunc = dActFunc;
    }

    /**
     * calcActFunc
     * 
     * Calculates the result of applying the activation
     * function this.actFunc to the vector.
     * 
     * @param input (vector)
     * @return Vector with activation function applied
     */
    public Vector calcActFunc(Vector input) {
        return input.map(actFunc);
    }
    
    /**
     * dActFunc
     * 
     * Calculates the derivative of the activation function
     * @param output (Vector)
     * @return vector before activation function was applied
     */
    public Vector dActFunc(Vector output) {
        return output.map(dActFunc);
    }

    // dCdI
    /**
     * calc_dCostdInput
     * 
     * The method name is a bit... unconventional? I tried to keep
     * them as clear as I could manage. This method calculates the
     * change in error rate for change in input. This is partial
     * derivative, in essence.
     * 
     * @param output
     * @param dCostdOutput
     * @return Vector
     */
    public Vector calc_dCostdInput(Vector output, Vector dCostdOutput) {
        return dCostdOutput.vecElementProduct(dActFunc(output));
    }

    public String getName() {
        return this.name;
    }

    public double sigmoidFunction(double x) {
        return 1.0/(1.0 + exp(-x));
    }

    public double sigmoidDerivative(double x) {
        return sigmoidFunction(x)*(1.0-sigmoidFunction(x));
    }

    
    


}
