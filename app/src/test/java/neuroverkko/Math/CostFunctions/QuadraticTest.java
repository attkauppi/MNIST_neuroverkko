package neuroverkko.Math.CostFunctions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;

public class QuadraticTest {
	


	private Quadratic quadratic;

	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		this.quadratic = new Quadratic();
	}
	
	@After
	public void tearDown() {
	}

	@Test
	public void testGetCost() {
		System.out.println("Quadratic getCost");
		Matrix output = new Matrix(new double[][] {{0.71225743}, {0.53309757}});
		Matrix target  = new Matrix(new double[][] {{1.0}, {0.2}});


		Matrix output2 = new Matrix(new double[][] {{0.2}, {0.1}});
		Matrix target2  = new Matrix(new double[][] {{0.1}, {0.2}});

		double expResult = 0.0968748888650549;
		double result = this.quadratic.getCost(target, output, 1);
		// System.out.println("Result: " + result);

		assertEquals(expResult, result, 0.0001);
	}

	@Test
	public void testGetDerivative() {
		System.out.println("getDerivative");
		Matrix target  = new Matrix(new double[][] {{1}, {2}, {3}});
		Matrix output = new Matrix(new double[][] {{4}, {-3}, {7}});

		double cost = new Quadratic().getCost(target, output, 1);
		System.out.println("Cost: " + cost);

		// TODO: Remove if cost calculation includes 0.5 as a multiplier,
		// if so, this is half of the wanted result.
		// assertEquals(3*3+5*5+4*4, cost, 0.01);

		// // assertEquals((3*3+5*5+4*4)*0.5, cost, 0.01);

		// Matrix expError = new Matrix(new double[][] {{6.0}, {-10.0}, {8.0}});
		// Matrix error = new Quadratic().getDerivative(target, output);

		// // // If quadratic getCost-formula includes 0.5 the correct value will be
		// // // half of expError here. Otherwise, if the getDerivative
		// // // includes scalar multiplication by 2 and the getCost doesn't have
		// // // scalar multiplication by 0.5, this will be the correct result.
		// System.out.println("Exp error: " + expError.toString());
		// assertEquals(expError, error);

		Matrix expError2 = new Matrix(new double[][] {{3.0}, {-5.0}, {4.0}});
		expError2 = expError2;//.scalarProd(2.0);
		Matrix error2 = new Quadratic().getDerivative(target, output);
		System.out.println("Exp error2: " + expError2.toString());
		assertEquals(expError2, error2);

	}
}
	