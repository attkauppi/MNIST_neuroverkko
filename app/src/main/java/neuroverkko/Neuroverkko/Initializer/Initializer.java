package neuroverkko.Neuroverkko.Initializer;
import neuroverkko.Math.*;
import neuroverkko.Utils.*;
import neuroverkko.Utils.RandomNumberGenerator;

public interface Initializer {

    void initWeights(Matrix weights, int layer);

    class Random implements Initializer {

        private double min;
        private double max;

        public Random(double min, double max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public void initWeights(Matrix weights, int layer) {
            double delta = max - min;
            weights.map(value -> min + (RandomNumberGenerator.getRandom() - 0.5) * delta);
        }
    }
    
}
