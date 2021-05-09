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

public class CrossEntropyTest {

	private CrossEntropy crossEntropy;

	
	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
		this.crossEntropy = new CrossEntropy();
    }
    
    @After
    public void tearDown() {
    }

	@Test
	public void testGetCost() {
		System.out.println("crossEntropy getCost");
		Matrix output = new Matrix(new double[][] {{0.71225743}, {0.53309757}});
		Matrix target  = new Matrix(new double[][] {{1.0}, {0.2}});

		

		double expResult = 1.0744340165501296;
		double result = this.crossEntropy.getCost(target, output, 0);
		// System.out.println("Result: " + result);

		assertEquals(expResult, result, 0.0001);
	}

// 	@Test
// 	public void shouldGetName() {
// 		String actualValue = crossEntropy.getName();

// 		// TODO: assert scenario
// 	}

// 	@Test
// 	public void shouldGetCost() {
// 		// TODO: initialize args
// 		Matrix target;
// 		Matrix output;
// 		int minibatch_size;

// 		double actualValue = crossEntropy.getCost(target, output, minibatch_size);

// 		// TODO: assert scenario
// 	}

// 	@Test
// 	public void shouldGetDerivative() {
// 		// TODO: initialize args
// 		Matrix target;
// 		Matrix output;

// 		Matrix actualValue = crossEntropy.getDerivative(target, output);

// 		// TODO: assert scenario
// 	}

// 	@Test
// 	public void shouldApplyLogarithm() {
// 		// TODO: initialize args
// 		Matrix a;

// 		Matrix actualValue = CrossEntropy.applyLogarithm(a);

// 		// TODO: assert scenario
// 	}
}
