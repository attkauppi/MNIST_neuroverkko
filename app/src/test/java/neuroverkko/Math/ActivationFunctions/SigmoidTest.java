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
import neuroverkko.Neuroverkko.Layer;

public class SigmoidTest {
	
	private Sigmoid sigmoid;

	@Before
	public void setup() {
		this.sigmoid = new Sigmoid();
	}

	@Test
	public void testCalcActivation() {
		System.out.println("calcActivation");
		// TODO: initialize args
		ActivationFunction sig = new Sigmoid();
		Matrix expResult = new Matrix(new double[][] {{0.7310585786300049}, {0.7310585786300049}, {0.7310585786300049}});

		Matrix m = new Matrix(new double[][] {{1.0}, {1.0}, {1.0}});
		Matrix result = sig.calcActivation(m);

		assertEquals(expResult, result);
		// TODO: assert scenario
	}

	@Test
	public void testdActFunc() {
		System.out.println("Sigmoid dActFunc");
		
		ActivationFunction sig = new Sigmoid();
		Matrix output = new Matrix(new double[][] {{1.0}, {1.0}, {1.0}});

		Matrix actualValue = sigmoid.dActFunc(output);
		Matrix expResult = new Matrix(new double[][] {{0.19661193324148185}, {0.19661193324148185}, {0.19661193324148185}});

		assertEquals(expResult, actualValue);
	}

	@Test
	public void testActFunc() {
		Layer l = new Layer(3, new Sigmoid());
		l.setActivation(new Matrix(new double[][] {{1.0}, {1.0}, {1.0}}));
		Matrix derv = l.actFnc.dActFunc(l.getActivation());
		Matrix expResult = new Matrix(new double[][] {{0.19661193324148185}, {0.19661193324148185}, {0.19661193324148185}});

		assertEquals(expResult, derv);
	}

	

	// @Test
	// public void shouldCalculate() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = sigmoid.calculate(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldDerivative() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = sigmoid.derivative(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSigmoid() {
	// 	// TODO: initialize args
	// 	Matrix a;

	// 	Matrix actualValue = sigmoid.sigmoid(a);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldDSigmoid() {
	// 	// TODO: initialize args
	// 	Matrix a;

	// 	Matrix actualValue = sigmoid.dSigmoid(a);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSderivative() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = Sigmoid.sderivative(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldScalculate() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = Sigmoid.scalculate(x);

	// 	// TODO: assert scenario
	// }
}
