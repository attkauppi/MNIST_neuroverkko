/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork.math.CostFunctions;

import neuralnetwork.neuralnetwork.math.CostFunctions.CostFunction;

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
public class MSETest {

    Vector expected;
    Vector actual;
    MSE mse = new MSE();
    
    public MSETest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        expected = new Vector(1, 2, 3);
        actual =  new Vector(4, -3, 7);
        MSE mse = new MSE();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCost method, of class MSE.
     */
    @Test
    public void testGetCost() {
        Vector expected = new Vector(1, 2, 3);
        Vector actual = new Vector(4, -3, 7);
        double expectedResult = 16.6666;
        double actualResult = this.mse.getCost(expected, actual);
        assertEquals(expectedResult, actualResult, 0.001);
    }

    /**
     * Test of getDerivative method, of class MSE.
     */
    @Test
    public void testGetDerivative() {
        System.out.println("getDerivative");
        Vector expected = this.expected;
        Vector actual = this.actual;
        MSE instance = this.mse;
        Vector expResult = new Vector(2.0, -3.333, 2.666);
        Vector result = instance.getDerivative(expected, actual);
        System.out.println("Result: " + result.toString());
        assertArrayEquals(expResult.getData(), result.getData(), 0.01);
    }
    
}
