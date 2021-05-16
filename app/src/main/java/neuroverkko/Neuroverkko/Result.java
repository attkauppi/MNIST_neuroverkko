package neuroverkko.Neuroverkko;

import neuroverkko.Math.*;

public class Result {

    public final Matrix output;
    public final Double cost;

    public Result(Matrix output) {
        this.output = output;
        this.cost = null;
    }

    public Result(Matrix output, double cost) {
        this.output = output;
        this.cost = cost;
    }

    public Matrix getOutput() {
        return this.output;
    }

    public Double getCost() {
        return this.cost;
    }

    @Override
    public String toString() {
        return "Result [cost=" + cost + ", output=" + output + "]";
    }
}
