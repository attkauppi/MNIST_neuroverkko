package neuroverkko.Neuroverkko.Initializer;
import neuroverkko.Utils.RandomNumberGenerator;
import neuroverkko.Math.*;

public class XavierUniform implements Initializer {

    @Override
    public void initWeights(Matrix weights, int layer) {
        final double factor = 2.0 * Math.sqrt(6.0 / (weights.cols + weights.rows));
        weights.map(value -> (RandomNumberGenerator.getRandom() - 0.5) * factor);
    }
    
}
