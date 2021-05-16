package neuroverkko.Math.ActivationFunctions;

import neuroverkko.Math.*;

public class Softmax extends ActivationFunction {

    private static String name = "Softmax";
    private Function actFunction;
    private Function derActFunction;

    public Softmax(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public Softmax(String name) {
        super(name);
    }

    public Softmax() {
        super(name);
    }

    @Override
    public Matrix calcActivation(Matrix input) {

        // System.out.println("Input: " + input.toString());
        int maxValueIndex = Matrix.getMatrixMax(input);
        double max = input.getData()[maxValueIndex][0];

        Matrix input_copy = input.copy();
        input_copy.map(value -> Math.exp(value-max));

        double jakaja = Matrix.sumOverAxis(input_copy);

        return input.map(value -> Math.exp(value - max)/(jakaja));

    }

    @Override
    public Matrix dActFunc(Matrix output) {
        return output.map(x -> sigmoidFunction(x) * (1.0 - sigmoidFunction(x)));
    }

    @Override
    public double calculate(double x) {
        return 1.0/(1.0+Math.exp(-1.0*x));
    }

    @Override
    public double derivative(double x) {
        return calculate(x)*(1.0-calculate(x));
    }

    @Override
    public Matrix sigmoid(Matrix a) {
        double[][] t = new double[a.rows][a.cols];
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                t[i][j] = 1.0/(1.0+Math.exp(-a.getData()[i][j]));
            }
        }
        return new Matrix(t);
    }



    
}
