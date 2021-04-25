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
public class LeakyReLuTest {
    LeakyReLu LRL;
    Vector v;
    
    public LeakyReLuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        LRL = new LeakyReLu("LeakyReLu");
        v =  new Vector(new double[] {1.0, 0.0, -5.0});
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcActFunc method, of class LeakyReLu.
     */
    @Test
    public void testCalcActFunc() {
        System.out.println("calcActFunc");
        Vector input = v;
        LeakyReLu instance = LRL;
        System.out.println("Instance: " + instance.toString());
        Vector expResult = new Vector(new double[] {1.0, (0.01*0.0), (-5.0*0.01)});
        Vector result = LRL.calcActFunc(input);
        System.out.println("Vector: " + result.toString());
        assertArrayEquals(expResult.getData(), result.getData(), 0.01);
    }

    /**
     * Test of dActFunc method, of class LeakyReLu.
     */
    @Test
    public void testDActFunc() {
        System.out.println("dActFunc");
        Vector output = v;
        LeakyReLu instance = LRL;
        Vector expResult =new Vector(new double[] {1.0, 0.01, 0.01});
        Vector result = instance.dActFunc(output);
        assertArrayEquals(expResult.getData(), result.getData(), 0.01);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
