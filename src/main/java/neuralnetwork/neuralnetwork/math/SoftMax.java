package neuralnetwork.neuralnetwork.math;

import java.util.Arrays;

import static java.lang.Math.exp;
import static java.lang.Math.log;

public class SoftMax extends ActivationFunction {

    // public Sigmoid("Linear", super()::sigmoidFunction, Function ) {
    //     super(name, actFunc, dActFunc);
    //     //TODO Auto-generated constructor stub
    // }

    private static String name = "SoftMax";
    private static Function actFunction = x -> x;
    private static Function derActFunction = x -> 1;

    public SoftMax(String name, Function actFunction, Function derActFunction) {
        super(name, actFunction, derActFunction);
    }

    public SoftMax(String name) {
        super(name);
    }

    // TODO: Harkitse pidätkö
    public SoftMax() {
        super(
            name,
            actFunction,
            derActFunction
        );
    }

    /**
     * calcActFunc
     * Calculates the activation function by applying the exponential
     * function to all the vector's elements and dividing them by the
     * sum of the elements with the exponential function applied to the
     * sum as well.
     * @param: input (vector)
     * @return vector - calculated as described above.
     */
    @Override
    public Vector calcActFunc(Vector input) {
        double[] input_array = Arrays.copyOf(input.getData(), input.getDimensions());

        double expSum = 0.0;//Arrays.copyOf(input);
        double[] input_exp = new double[input.getData().length];
        for (int i = 0; i < input.getData().length; i++) {

            expSum += exp(input.getData()[i]);
            input_exp[i] = exp(input.getData()[i]);
        }

        for (int i = 0; i < input.getData().length; i++) {
            if (expSum != 0.0) {
                input_array[i] = (input_exp[i] / expSum);
            }
            
        }

        return new Vector(input_array);

        // TODO: Sama asia toteutettu for-loopilla
        // for (int i = 0; i < input.getData().length; i++) {
        //     double x = input.getData()[i];
        //     if (x <= 0) {
        //         input.getData()[i] = 0.01 * x;
        //     } else {
        //         input.getData()[i] = x;
        //     }
        // }
        //return input.map(x -> x);
    }

    // TODO: implement
    // @Override
    // public Vector dActFunc(Vector output) {
    //     return output.map(x -> 1);
    // }

    // TODO: implement
    @Override
    public Vector calc_dCostdInput(Vector output, Vector dCostdOutput) {
        Vector sum = output.vecElementProduct(dCostdOutput);

        double sum_of_elements = 0.0;
        for (int i = 0; i < sum.getData().length; i++) {
            sum_of_elements += sum.getData()[i];
        }
        
        Vector subtracted = dCostdOutput.scalarSubtr(sum_of_elements);
        return output.vecElementProduct(subtracted);
    }
}