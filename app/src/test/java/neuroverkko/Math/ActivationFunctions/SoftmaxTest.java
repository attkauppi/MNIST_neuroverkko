package neuroverkko.Math.ActivationFunctions;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neuroverkko.Math.*;


public class SoftmaxTest {

	private Softmax softmax;

	@Before
	public void setup() {
		this.softmax = new Softmax();
	}

	@Test
	public void shouldCalcActivation() {
		// TODO: initialize args
		Matrix input = new Matrix(new double[][] {{-1}, {0}, {1.5}, {2}});

		Matrix result = softmax.calcActivation(input);
		Matrix expResult = new Matrix(new double[][] {{0.02778834297343303}, {0.0755365477476706}, {0.3385313204518047}, {0.5581437888270917}});

		assertEquals(expResult, result);
	}

	// @Test
	// public void shouldDActFunc() {
	// 	// TODO: initialize args
	// 	Matrix output;

	// 	Matrix actualValue = softmax.dActFunc(output);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldCalculate() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = softmax.calculate(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldDerivative() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = softmax.derivative(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSigmoid() {
	// 	// TODO: initialize args
	// 	Matrix a;

	// 	Matrix actualValue = softmax.sigmoid(a);

	// 	// TODO: assert scenario
	// }
}
