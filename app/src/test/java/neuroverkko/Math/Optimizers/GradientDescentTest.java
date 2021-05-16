package neuroverkko.Math.Optimizers;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neuroverkko.Math.*;

import java.util.Random;



public class GradientDescentTest {

	private double learningRate;

	private GradientDescent gradientDescent;

	@Before
	public void setup() {
		this.gradientDescent = new GradientDescent(0.02);
	}

	@Test
	public void shouldToString() {
		String actualValue = gradientDescent.toString();

		// TODO: assert scenario
	}

	// @Test
	// public void shouldCostDerivative() {
	// 	// TODO: initialize args
	// 	Matrix output;
	// 	Matrix target;

	// 	Matrix actualValue = gradientDescent.costDerivative(output, target);

	// 	// TODO: assert scenario
	// }

	@Test
	public void shouldUpdateWeightsFromLayer() {
		// TODO: initialize args
		Matrix weights = new Matrix(new double[][]{{2, 3, 4}, {3, 4, 5}});
		Matrix deltaWeights = new Matrix(new double[][]{{.2, .3, .4}, {.3, .4, .5}});

		Matrix actualValue = gradientDescent.updateWeightsFromLayer(weights, deltaWeights);
		System.out.println(actualValue.toString());

		assertEquals(true, true);

		// TODO: assert scenario
	}

	// @Test
	// public void shouldUpdateWeights() {
	// 	// TODO: initialize args
	// 	Matrix weights;
	// 	Matrix deltaWeights;
	// 	int trainingDatasetSize;
	// 	double l2;
	// 	int minibatch_size;

	// 	Matrix actualValue = gradientDescent.updateWeights(weights, deltaWeights, trainingDatasetSize, l2, minibatch_size);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldUpdateBias() {
	// 	// TODO: initialize args
	// 	Matrix bias;
	// 	Matrix deltaBias;
	// 	int minibatch_size;

	// 	Matrix actualValue = gradientDescent.updateBias(bias, deltaBias, minibatch_size);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetLearningRate() {
	// 	double actualValue = gradientDescent.getLearningRate();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetL2NormSquared() {
	// 	// TODO: initialize args
	// 	Matrix weight;

	// 	double actualValue = gradientDescent.getL2NormSquared(weight);

	// 	// TODO: assert scenario
	// }
}
