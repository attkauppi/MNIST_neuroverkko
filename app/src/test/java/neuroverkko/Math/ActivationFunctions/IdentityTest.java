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

public class IdentityTest {
	
	private Identity identity;
	private Matrix mat;

	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
		this.identity = new Identity();
        this.mat = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
    }
    
    @After
    public void tearDown() {
    }


	@Test
	public void testWorksWithMatrices() {
		System.out.println("worksWithMatrices test");
		
		Matrix mInstance = this.mat.copy();
		Matrix mInstance2 = this.mat.copy();
		Matrix expectedResultActivation = new Matrix(new double[][] {{1,2,3},{4,5,6},{7,8,9}});
		Matrix expectedResultDerivative = new Matrix(new double[][] {{1,1,1},{1,1,1},{1,1,1}});

		Matrix result = this.identity.activation(mInstance);
		Matrix resultDer = this.identity.matDerivative(mInstance2);
		System.out.println("minstance: " + mInstance.toString());
		System.out.println("Result: " + result.toString());
		System.out.println("Derivative result: " + resultDer.toString());
		assertArrayEquals(expectedResultActivation.getData(), result.getData());
		assertArrayEquals(expectedResultDerivative.getData(), resultDer.getData());
	}

	// @Test
	// public void shouldCalculate() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = identity.calculate(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldDerivative() {
	// 	// TODO: initialize args
	// 	double x;

	// 	double actualValue = identity.derivative(x);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldDActFunc() {
	// 	// TODO: initialize args
	// 	Vector output;

	// 	Vector actualValue = identity.dActFunc(output);

	// 	// TODO: assert scenario
	// }
}
