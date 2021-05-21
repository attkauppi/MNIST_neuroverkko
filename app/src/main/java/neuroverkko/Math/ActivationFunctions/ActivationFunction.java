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
    private Function derActFunc;

    public ActivationFunction(String name) {
        this.name = name;
    }

    public ActivationFunction(String name, Function actFunc, Function derActFunc) {
        this.name = name;
        this.actFunc = actFunc;
        this.derActFunc = derActFunc;
    }

    /**
     * calcActivation
     * 
     * Calculates the result of applying the activation
     * function this.actFunc to the vector.
     * 
     * @param input (vector)
     * @return Vector with activation function applied
     */
    public Matrix calcActivation(Matrix input) {
        return input.map(actFunc);

    }
    
    /**
     * dActFunc
     * 
     * Calculates the derivative of the activation function
     * @param output (Vector)
     * @return vector before activation function was applied
     */
    public Matrix dActFunc(Matrix output) {
        return output.map(derActFunc);
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
    public Matrix calc_dCostdInput(Matrix output, Matrix dCostdOutput) {
        Matrix out2 = output.copy();
        Matrix derivativeAct = dActFunc(out2);
        return Matrix.hadamardProduct(dCostdOutput, derivativeAct);
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

    public Matrix sigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = 1.0/(1.0+Math.exp(-a.getData()[i][j]));
            }
        }
        return new Matrix(t);
    }

    
    public Matrix dSigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];

        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = a.getData()[i][j] * (1.0-a.getData()[i][j]);
            }
        }

        return new Matrix(t);
    }

    
    


}
