package neuroverkko.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

public class RandomNumberGeneratorTest {

	private RandomNumberGenerator randomNumberGenerator;

	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.randomNumberGenerator = randomNumberGenerator;
    }
    
    @After
    public void tearDown() {

	}

	@Test
	public void testGetRandom() {
		System.out.println("getRandom");
		double actualValue = this.randomNumberGenerator.getRandom();

		System.out.println("Actual value: " + actualValue);

		for (int i = 0; i < 10000; i++) {
			actualValue = this.randomNumberGenerator.getRandom();
			assertTrue(actualValue < 1.1);
		}
		// TODO: assert scenario
	}
}
