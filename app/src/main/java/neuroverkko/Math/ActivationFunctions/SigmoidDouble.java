package neuroverkko.Math.ActivationFunctions;

public class SigmoidDouble implements IActivationFunction {

    double coefficient;

    public SigmoidDouble() {
    }

    public SigmoidDouble(double coefficient) {
        this.coefficient = coefficient;
    }

    public void setCoefficient(double value) {
        this.coefficient = value;
    }

    @Override
    public double calculate(double x) {
        return 1.0/(1.0*Math.exp(-coefficient*x));
    }

    @Override
    public double derivative(double x) {
        return calculate(x)*(1.0-calculate(x));
    }


    
}
