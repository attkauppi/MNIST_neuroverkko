/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork.math.ActivationFunctions;

import neuralnetwork.neuralnetwork.math.Vector;
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
public class StepTest {
    
    Vector v;
    Step s;
    double x;
    double y;
    
    public StepTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        v =new Vector(new double[] {-1.0, 0.0, 1.0});
        s = new Step();
        x = -1.0;
        y = 2.0;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcActFunc method, of class Step.
     */
    @Test
    public void testCalcActFunc_Vector() {
        System.out.println("calcActFunc");
        Vector input = v;
        Step instance = s;
        Vector expResult = new Vector(new double[] {0.0, 1.0, 1.0});
        Vector result = instance.calcActFunc(input);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of dActFunc method, of class Step.
     */
    @Test
    public void testDActFunc_Vector() {
        System.out.println("dActFunc");
        Vector output = v;
        Step instance = s;
        Vector expResult = new Vector(new double[] {0.0, Double.MAX_VALUE, 0.0});
        Vector result = instance.dActFunc(output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of calcActFunc method, of class Step.
     */
    @Test
    public void testCalcActFunc_double() {
        System.out.println("calcActFunc");
        Step instance = s;
        double expResult1 = 0.0;
        double expResult2 = 1.0;
        double result = instance.calcActFunc(x);
        double result2 = instance.calcActFunc(y);
        assertEquals(expResult1, result, 0.0);
        assertEquals(expResult2, result2, 1.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of dActFunc method, of class Step.
     */
    @Test
    public void testDActFunc_double() {
        System.out.println("dActFunc");
        double x0 = 0.0;
        Step instance = s;
        double expResult = Double.MAX_VALUE;
        double expResult2 = 0.0;
        double result = instance.dActFunc(x0);
        double result2 = instance.dActFunc(x);
        double result3 = instance.dActFunc(y);
        assertEquals(expResult, result, 0.0);
        assertEquals(expResult2, result2, 0.0);
        assertEquals(expResult2, result3, 0.0);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
