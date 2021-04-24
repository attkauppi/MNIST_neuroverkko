/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import neuralnetwork.neuralnetwork.math.ActivationFunction;

/**
 *
 * @author ari
 */
public class LayerTest {
    
    public LayerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        ActivationFunction af = new ActivationFunction();
        Layer prev = new Layer(3, af);
    }

    /**
     * Test of getSize method, of class Layer.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        Layer instance = null;
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasPreviousLayer method, of class Layer.
     */
    @Test
    public void testHasPreviousLayer() {
        System.out.println("hasPreviousLayer");
        Layer instance = null;
        boolean expResult = false;
        boolean result = instance.hasPreviousLayer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPreviousLayer method, of class Layer.
     */
    @Test
    public void testSetPreviousLayer() {
        System.out.println("setPreviousLayer");
        Layer prev = null;
        Layer instance = null;
        instance.setPreviousLayer(prev);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
