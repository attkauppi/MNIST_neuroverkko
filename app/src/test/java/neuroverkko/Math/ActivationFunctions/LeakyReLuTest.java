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

public class LeakyReLuTest {

	private LeakyReLu leakyReLu;
	private Matrix mat;


	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
		this.leakyReLu = new LeakyReLu();
        this.mat = new Matrix(new double[][] {{1,-2, 0},{3,-5,0},{0,0,0}});
    }
    
    @After
    public void tearDown() {
    }

	@Test
	public void testCalcActivation() {
		System.out.print("LeakyReLu calcActivation");
		Matrix instance = this.mat.copy();
		LeakyReLu lrl = this.leakyReLu;

		Matrix expResult = new Matrix(new double[][] {{1.0, 0.01*(-2.0), 0}, {3.0, (-5.0*0.01), 0.0}, {0.0, 0.0, 0.0}});
		Matrix result = lrl.calcActivation(instance);
		assertEquals(expResult, result);
	}

	@Test
	public void testDActFunc() {
		System.out.print("LeakyReLu dActFunc");
		Matrix instance = this.mat.copy();
		LeakyReLu lrl = this.leakyReLu;
		Matrix expResult = new Matrix(new double[][] {{1.0, 0.01, 0.01}, {1.0, 0.01, 0.01}, {0.01, 0.01, 0.01}});
		Matrix result = lrl.dActFunc(instance);
		assertEquals(expResult, result);
	}
}
