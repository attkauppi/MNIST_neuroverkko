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
	public void testSigmoid() {
		System.out.println("Sigmoid function");
		// 3x1
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        // 2x3
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix expResult = new Matrix(new double[][] {{10},{28}});
		Matrix result = Matrix.multiply(w3,x2);
		assertEquals(expResult, result);

		Matrix dSigmoid = this.sigmoid.calcActivation(result);

		Matrix expResultSig = new Matrix(new double[][] {{0.999},{0.999}});
		System.out.println("dSigmoid: " + dSigmoid.toString());

		System.out.println("expResultSig: " + expResultSig.toString());
		for (int i = 0; i < dSigmoid.rows; i++) {
			assertArrayEquals(dSigmoid.getData()[i], expResultSig.getData()[i], 0.001);
		}
	}

	@Test
	public void testSigmoidDerivative() {
		System.out.println("dActFunc");

		// 3x1
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        // 2x3
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix expResult = new Matrix(new double[][] {{10},{28}});
		Matrix result = Matrix.multiply(w3,x2);
		assertEquals(expResult, result);

		Matrix expResultdSigmoid = new Matrix(new double[][] {{4.5396*Math.pow(10, -5)},{6.914*Math.pow(10, -13)}});
		System.out.println("expResultdSigmoid: " + expResultdSigmoid.toString());

		Matrix dSigmoid = this.sigmoid.dActFunc(result);
		System.out.println("dsigmoid: " + dSigmoid.toString());

		for (int i = 0; i < dSigmoid.rows; i++) {
			assertArrayEquals(expResultdSigmoid.getData()[i], dSigmoid.getData()[i], 0.001);
		}
		
		assertEquals(expResultdSigmoid.rows, dSigmoid.rows);
		assertEquals(expResultdSigmoid.cols, dSigmoid.cols);


	}

	@Test
    public void testMatrixMultiplyDimensionsOk() {
        System.out.println("multiply dimensions");

        // 3x1
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        // 2x3
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix expResult = new Matrix(new double[][] {{10},{28}});
		Matrix result = Matrix.multiply(w3,x2);

		assertEquals(expResult, result);

		System.out.println("Result: " + result.toString());

		// Matrix dSigmoid = this.sigmoid.dSigmoid(result);
		Matrix dSigmoid2 = this.sigmoid.dActFunc(result);

		// System.out.println(dSigmoid.toString());
		System.out.println(dSigmoid2.toString());

		// assertEquals(dSigmoid, dSigmoid2);


		System.out.println(dSigmoid2.toString());
		Matrix expResultdSigmoid = new Matrix(new double[][] {{4.5396*Math.pow(10, -5)},{6.914*Math.pow(10, -13)}});

		assertEquals(expResultdSigmoid.rows, dSigmoid2.rows);
		assertEquals(expResultdSigmoid.cols, dSigmoid2.cols);
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
