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
public class HalfQuadraticTest {

    Vector expected;
    Vector actual;
    HalfQuadratic hq;
    
    public HalfQuadraticTest() {
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
        hq = new HalfQuadratic();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class HalfQuadratic.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        HalfQuadratic instance = new HalfQuadratic();
        String expResult = "HalfQuadratic";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCost method, of class HalfQuadratic.
     */
    @Test
    public void testGetCost() {
        System.out.println("getCost");
        Vector expected = this.expected;
        Vector actual = this.actual;
        HalfQuadratic instance = this.hq;
        double expResult = 1.5;
        double result = instance.getCost(expected, actual);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDerivative method, of class HalfQuadratic.
     */
    @Test
    public void testGetDerivative() {
        System.out.println("getDerivative");
        Vector expected = this.expected;
        Vector actual = this.actual;
        HalfQuadratic instance = this.hq;
        Vector expResult = new Vector(-1.0, -1.0, -1.0);
        Vector result = instance.getDerivative(expected, actual);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
