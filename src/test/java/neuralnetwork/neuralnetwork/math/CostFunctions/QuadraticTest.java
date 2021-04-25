/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork.math.CostFunctions;

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
public class QuadraticTest {

    Vector expected;
    Vector actual;
    Quadratic q;
    
    public QuadraticTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        actual = new Vector(1,2,3);
        expected = new Vector(2,3,4);
        q = new Quadratic();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Quadratic.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Quadratic instance = new Quadratic();
        String expResult = "Quadratic";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class Quadratic.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Vector expected = this.expected;
        Vector actual = this.actual;
        Quadratic instance = this.q;
        double expResult = 3.0;
        double result = instance.getCost(expected, actual);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDerivative method, of class Quadratic.
     */
    @Test
    public void testGetDerivative() {
        System.out.println("getDerivative");
        Vector expected = this.expected;
        Vector actual = this.actual;
        Quadratic instance = this.q;
        Vector expResult = new Vector(-2.0, -2.0, -2.0);
        //Vector expResult = new Vector(-2.0, -, 8.0);
        Vector expected2 = new Vector(2,3,4);
        Vector expResult2 = new Vector(2,2,2);
        Vector result = instance.getDerivative(expected, actual);
        //Vector result2 = instance.getDerivative(expected, actual);
        System.out.println("Result: " + result.toString());
        assertEquals(expResult, result);
        //assertEquals(expResult2, result2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
