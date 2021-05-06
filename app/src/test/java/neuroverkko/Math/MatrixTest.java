package neuroverkko.Math;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neuroverkko.Math.*;

import java.util.Random;

/**
 *
 * @author ari
 */
public class MatrixTest {

    Matrix instance;
    
    public MatrixTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Matrix((new double[][] {{1,2,3},{4,5,6},{7,8,9}}), 3, 3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of map method, of class Matrix.
     */
    // @Test
    // public void testMap() {
    //     System.out.println("map");
    //     //Function fn = Matrix.scalarProd();
    //     // Matrix instance = null;
    //     Matrix expResult = null;
    //     Matrix result = map(instance.scalarProd(5.0));
    //     assertEquals(expResult, result);
    //     // TODO review the generated test code and remove the default call to fail.
    //     fail("The test case is a prototype.");
    // }

    /**
     * Test of getData method, of class Matrix.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        
        double[][] expResult = instance.getData();
        assertTrue(expResult != null);

        double[][] result = instance.getData();

        for (int i = 0; i < result.length; i++) {
            assertArrayEquals(expResult, result);
        }
        
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of matSum method, of class Matrix.
     */
    @Test
    public void testMatSum() {
        System.out.println("matSum");
        //Matrix m = null;
        //Matrix instance = null;
        Matrix expResult = new Matrix(new double[][] {{2, 4, 6}, {8, 10, 12}, {14, 16, 18}}, 3, 3);
        instance.add(instance);
        assertEquals(expResult, instance);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

	@Test
	public void testMultiplyM() {
		Matrix a = new Matrix(new double[][] {{1, 2, 3}, {4,5,6}, {1, 3, 4}});
		Matrix b = new Matrix(new double[][] {{1, 2, 3}, {4,5,6}, {1, 3, 4}});

		Matrix multiplied = Matrix.multiply(a, b);

		System.out.println(multiplied.toString());

		// a.(b);
		// System.out.println(a.toString());

		a = new Matrix(new double[][] {{1, 2, 3}, {4,5,6}, {1, 3, 4}});
		a.matProduct(b);
		System.out.println(a.toString());



		assertEquals(a, multiplied);
	}

    @Test
    public void testHadamardProductRowVectors() {
        System.out.println("Hadamard product on row vectors");
        Matrix m = new Matrix(new double[][] {{1},{2},{3}});
        Matrix o = new Matrix(new double[][] {{1},{2},{3}});

        Matrix expResult = new Matrix(new double[][] {{1}, {4}, {9}});
        Matrix result = Matrix.hadamardProduct(m,o);
        System.out.println("Exp result: " + expResult.toString());
        System.out.println("result: " + result.toString());
        assertEquals(expResult, result);
    }

    /**
     * Test of matSubract method, of class Matrix.
     */
    @Test
    public void testMatSubract() {
        System.out.println("matSubract");

        // (2*instance) - instance == instance
        Matrix other = new Matrix(new double[][] {{2, 4, 6}, {8, 10, 12}, {14, 16, 18}}, 3, 3);
        Matrix other2 = other.copy();
		// Matrix instance = null;
        Matrix expResult = instance.copy();
        Matrix result = other.matSubract(instance);

		//Matrix other2 = other.copy();
		Matrix expResult2 = Matrix.subtract(other2, instance);
        for (int i = 0; i < result.getData().length; i++) {
            assertArrayEquals(expResult.getData()[i], result.getData()[i], 0.0);
        }

		assertEquals(expResult2, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of scalarProd method, of class Matrix.
     */
    @Test
    public void testScalarProd() {
        System.out.println("scalarProd");
        double scalar = 5.0;
        Matrix expResult = new Matrix(new double[][] {{5, 10, 15}, {20, 25, 30}, {35, 40, 45}}, 3, 3);;
        Matrix result = instance.scalarProd(scalar);
        // 5*instance
        for (int i = 0; i < result.getData().length; i++) {
            assertArrayEquals(expResult.getData()[i], result.getData()[i], 0.0);
        }
    }

    /**
     * Test of equals method, of class Matrix.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Matrix mat = new Matrix(new double[][] {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}}, 3, 3);
        Object other = (Object) mat;


        boolean expResult = false;
        boolean result = instance.equals(other);
        assertEquals(expResult, result);
        boolean result2 = instance.equals(instance);
        assertEquals(true, result2);
        assertEquals(true, mat.equals(mat));
    }
        
    @Test
    public void testMatProduct() {
        System.out.println("matProduct");
        Matrix expResult = new Matrix(new double[][] {{30, 36, 42}, {66, 81, 96}, {102, 126, 150}}, 3, 3);
        Matrix result = instance.matProduct(instance);

        for (int i = 0; i < instance.getData().length; i++) {
            assertArrayEquals(expResult.getData()[i], result.getData()[i], 0.0);
        }
    }

    @Test
    public void testVecMultiply() {
        System.out.println("vecMultiply");
        Vector multiplier = new Vector(new double[] {1,2,3});
        Vector expResult = new Vector(new double[] {30, 36, 42});

        Object result = instance.vecMultiply(multiplier);

        // Should result in a vector object, not matrix
        assertTrue(result instanceof Vector);
        Vector vResult = (Vector) result;

        assertArrayEquals(expResult.getData(), vResult.getData(), 0.0);
    }
    
}
