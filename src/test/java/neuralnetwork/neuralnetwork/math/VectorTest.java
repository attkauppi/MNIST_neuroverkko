/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork.neuralnetwork.math;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

/**
 *
 * @author ari
 */
public class VectorTest {
    
    public VectorTest() {
    }

    Vector instance;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        int[] a = {1,2,3};
        this.instance = new Vector(a);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetData() {
        System.out.println("getData");
        double[] expResult = {1.0, 2.0, 3.0};
        assertArrayEquals(expResult, instance.getData(), 0.0);
    }

    /**
     * Test of getDimensions method, of class Vector.
     */
    @Test
    public void testGetDimensions() {
        System.out.println("getDimensions");
        int expResult = 3;
        int result = instance.getDimensions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of dotProduct method, of class Vector.
     */
    @Test
    public void testDotProduct() {
        System.out.println("dotProduct");
        double[] b1 = new double[] {1,2,3};
        Vector other = new Vector(b1);
        double expResult = 14.0;
        double result = instance.dotProduct(other);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testScalarProd() {
        System.out.println("scalarMultiplication");
        double scalar = 3.0;

        Vector expResult = new Vector(new int[] {3, 6, 9});
        Vector result = instance.scalarProd(scalar);
        assertArrayEquals(expResult.getData(), result.getData(), 0.0);
    }

    @Test
    public void testScalarSubtr() {
        
        // -1
        Vector expResult = new Vector(new int[] {0, 1, 2});
        Vector apu = new Vector(instance.getData());
        apu = apu.scalarSubtr(1.0);
        
        assertArrayEquals(expResult.getData(), apu.getData(), 0.0);
        
        // -2
        expResult = expResult.scalarSubtr(1.0);
        Vector apu2 = new Vector(instance.getData());
        apu2 = apu2.scalarSubtr(2.0);

        assertArrayEquals(expResult.getData(), apu2.getData(), 0.0);
    }

    @Test
    public void testVecSubtraction() {
        System.out.println("vecSubtraction");
        Vector expResult = new Vector(new int[] {0, 0, 0});
        assertArrayEquals(expResult.getData(), instance.vecSubtraction(instance).getData(), 0.0);
    }

    @Test
    public void testVecAdd() {
        System.out.println("vecAdd");
        Vector expResult = new Vector(new int[] {2,4,6});
        Vector result = instance.vecAdd(instance);

        assertArrayEquals(expResult.getData(), result.getData(), 0.0);
    }

    @Test
    public void testElementProduct() {
        System.out.println("vecProduct");

        Vector expResult = new Vector(new int[] {1, 4, 9});
        Vector result = instance.vecElementProduct(instance);

        assertArrayEquals(expResult.getData(), result.getData(), 0.0);
    }

    @Test
    public void testOuterProduct() {
        System.out.println("outerProdut");
        double[][] expArray = {{1,2,3},{2,4,6},{3,6,9}};
        Matrix expResulMatrix = new Matrix(expArray, expArray.length, expArray[0].length);

        Matrix result = instance.outerProdut(instance);

        for (int i = 0; i < instance.getDimensions(); i++) {
            assertArrayEquals(expResulMatrix.getData()[i], result.getData()[i], 0.0);
        }
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Vector other = new Vector(new int[] {2, 2, 2});

        double[] oArray = new double[] {2.0,2.0,2.0};

        assertNotSame(oArray, instance);
        // TODO: maybe improve
    }

    @Test
    public void testMax() {
        System.out.println("max");
        assertEquals(3.0, instance.getMax(), 0.0);
    }

    @Test
    public void testGetIndexOfMaxElement() {
        System.out.println("getIndexOfMaxElement");

        double expResult = 3.0;
        double result = Arrays.stream(instance.getData()).max().getAsDouble();
        assertEquals(expResult, result, 0.0);
    }

    
    
}
