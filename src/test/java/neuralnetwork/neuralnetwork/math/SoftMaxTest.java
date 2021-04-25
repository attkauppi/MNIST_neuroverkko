/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork.math;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ari
 */
public class SoftMaxTest {
    
    SoftMax sm;
    Vector v;
    Vector v2;

    public SoftMaxTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        sm = new SoftMax();
        v = new Vector(new double[] {1.0, 1.0, 1.0});
        v2 = new Vector(new double[] {-1.0, 0.0, 1.5, 2.0});
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcActFunc method, of class SoftMax.
     */
    @Test
    public void testCalcActFunc() {
        System.out.println("calcActFunc");
        Vector input = v;
        SoftMax instance = sm;
        Vector expResult = new Vector(new double[] {0.333333, 0.333333, 0.333333});
        Vector result = instance.calcActFunc(input);
        System.out.println("calcActFunc result: " + result.toString());
        assertArrayEquals(expResult.getData(), result.getData(), 0.0001);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calc_dCostdInput method, of class SoftMax.
     */
    @Test
    public void testCalc_dCostdInput() {
        System.out.println("calc_dCostdInput");
        Vector output = null;
        Vector dCostdOutput = null;
        SoftMax instance = new SoftMax();
        Vector expResult = null;
        Vector result = instance.calc_dCostdInput(output, dCostdOutput);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
