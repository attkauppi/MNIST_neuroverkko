package neuroverkko.Utils;
import neuroverkko.Utils.RandomNumberGenerator;

public class UniformInitializer extends WeightInitializer {

    private double min;
    private double max;

    public UniformInitializer(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double Generate() {
        return RandomNumberGenerator.generateBetween(min, max);
    }
    
}
