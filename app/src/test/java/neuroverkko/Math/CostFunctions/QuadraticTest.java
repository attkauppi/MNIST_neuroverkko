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
}
	