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

import jdk.internal.jshell.tool.resources.version;

import static org.junit.Assert.*;

/**
 *
 * @author ari
 */
public class SigmoidTest {

    Sigmoid s;
    Vector v;
    
    public SigmoidTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        s = new Sigmoid("Sigmoid");
        v =  new Vector(new double[] {1.0, 1.0, 1.0});
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcActFunc method, of class Sigmoid.
     */
    @Test
    public void testCalcActFunc() {
        System.out.println("calcActFunc");
        Vector input = v;
        Sigmoid instance = s;
        Vector expResult = new Vector(new double[] {0.73106, 0.73106, 0.73106});
        Vector result = instance.calcActFunc(input);
        System.out.println("Result: " + result.toString());
        assertArrayEquals(expResult.getData(), result.getData(), 0.0001);
    }

    /**
     * Test of dActFunc method, of class Sigmoid.
     */
    @Test
    public void testDActFunc() {
        System.out.println("dActFunc");
        Vector output = null;
        Sigmoid instance = null;
        Vector expResult = null;
        Vector result = instance.dActFunc(output);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
