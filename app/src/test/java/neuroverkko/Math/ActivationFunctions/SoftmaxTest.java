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
}
