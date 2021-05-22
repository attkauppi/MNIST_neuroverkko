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
import neuroverkko.Math.ActivationFunctions.Sigmoid;
import neuroverkko.Math.CostFunctions.Quadratic;
import org.ejml.simple.SimpleMatrix;

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

    @Test
    public void testAddMatrix() {
        System.out.println("add (matrix)");
        Matrix m = new Matrix(new double[][] {{1},{1},{1}});
        Matrix o = new Matrix(new double[][] {{1},{1},{1}});

        m = m.add(o);

        Matrix expResult = new Matrix(new double[][] {{2},{2},{2}});

        Matrix m2 = new Matrix(new double[][] {{1},{1},{1}});
        Matrix o2 = new Matrix(new double[][] {{1},{1},{1}});
        Matrix expResult2 = new Matrix(new double[][] {{2},{2},{2}});

        Matrix m3 = new Matrix(new double[][] {{1},{1},{1}});
        Matrix o3 = new Matrix(new double[][] {{-1},{-1},{-1}});
        Matrix expResult3 = new Matrix(new double[][] {{0},{0},{0}});


        m2 = Matrix.add(m2, o2);

        m3 = Matrix.add(m3, o3);


        assertEquals(expResult2, m2);
        assertEquals(expResult, m);
        assertEquals(expResult3, m3);

        System.out.println("m: " + m.toString());

        System.out.println("o: " + o.toString());

        // Checking results with ejml
        SimpleMatrix ms = new SimpleMatrix(new double[][] {{1},{1},{1}});
        SimpleMatrix os = new SimpleMatrix(o.getData());
        ms = ms.plus(os);
        Matrix msm = new Matrix(Matrix.ejmlMatrixToArrays(ms));

        System.out.println("msm: " + msm.toString());

        assertEquals(msm, m);
        

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
    public void testDotProduct() {
        System.out.println("dotProduct");
        Matrix a = new Matrix(new double[][] {{1,2,3}});
        Matrix b = new Matrix(new double[][] {{1},{2},{3}});
        double result = a.dotProduct(b);
        Matrix mult = Matrix.multiply(a,b);


        assertEquals(14.0, result, 0.01);

        System.out.println("result: " + mult.toString());

        SimpleMatrix am = new SimpleMatrix(new double[][] {{1,2,3}});
        SimpleMatrix bm = new SimpleMatrix(new double[][] {{1},{2},{3}});

        am.dot(bm);

        double resultEjml = am.dot(bm);
        assertEquals(resultEjml, result, 0.1);

    }

    @Test
    public void testDotProductV() {
        System.out.println("dotProductV");
        Matrix a = new Matrix(new double[][] {{1},{2},{3}});
        Matrix b = new Matrix(new double[][] {{8, 6, 0}});

        // b = Matrix.transpose(b);

        System.out.println(Matrix.dotProduct(a, b).toString());

    }

    @Test
    public void testDotProductForMatrices() {
        System.out.println("dotProduct for matrices");

        Matrix a = new Matrix(new double[][] {{1}, {2}, {3}});
        Matrix b = new Matrix(new double[][] {{8, 6, 0}});

        Matrix result = Matrix.dotProduct(a, b);
        System.out.println("Result: " + result.toString());

        Matrix expResult =  new Matrix(new double[][] {{8,6,0}, {16, 12, 0}, {24, 18, 0}});

        
        assertEquals(expResult, result);

        // SimpleMatrix am = new SimpleMatrix(new double[][] {{1}, {2}, {3}});
        // SimpleMatrix bm = new SimpleMatrix(new double[][] {{8, 6, 0}});
        // am.dot(bm);
        // Matrix ejml = new Matrix(Matrix.ejmlMatrixToArrays(am));
        // System.out.println("ejml: " + ejml.toString());
        // assertEquals(ejml, result);

    }

    @Test
    public void testMatrixMultiplyDimensions() {
        System.out.println("multiply dimensions");

        // 3x1
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        // 2x3
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix expResult = new Matrix(new double[][] {{10},{28}});

        Matrix result = Matrix.multiply(w3,x2);

        System.out.println("result: " + result.toString());

        assertEquals(expResult, result);
    }

    @Test
    public void testDotProductNew() {
        System.out.println("dotProduct");

        // delta = self.cost_derivative(self.get_last_layer().get_activation(), y) * \
        //      self.sigmoid_derivative(self.get_last_layer().get_input())

        Sigmoid s = new Sigmoid();
        Quadratic q = new Quadratic();
        // output
        Matrix x3 = new Matrix(new double[][] {{0.5},{0.25}});
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix target= new Matrix(new double[][] {{1.0},{1.0}});

        

        Matrix subResult = Matrix.subtract(x3, target);
        Matrix cFunctionResult = q.getDerivative(target, x3);
        System.out.println("subResult: " + subResult.toString());
        System.out.println(cFunctionResult.toString());

        //Matrix deltaO = q.getDerivative(target, x3);
        
        // 
        Matrix delta = Matrix.multiply(cFunctionResult, Matrix.transpose(s.dActFunc(x3)));

        System.out.println("Delta: " + delta.toString());
        // Matrix delta = 
        // Matrix deltaH2 = new Matrix(new double[][] {{1,2,3},{4,5,6}});
        
        assertEquals(true, true);
    }
    
    @Test
    public void testHadamardProductDimensions() {
        System.out.println("hadamardProduct dimensions");

        // 3x1
        Matrix x2 = new Matrix(new double[][] {{3},{2},{1}});
        // 2x3
        Matrix w3 = new Matrix(new double[][] {{1,2,3},{4,5,6}});

        Matrix expResult = new Matrix(new double[][] {{10},{28}});
        Sigmoid s = new Sigmoid();
        Matrix dSigmoid = s.dActFunc(expResult);

        // 2x1
        Matrix x3 = new Matrix(new double[][] {{0.5},{0.25}});
        // 2x1
        Matrix target= new Matrix(new double[][] {{1.0},{1.0}});

        Matrix subResult = Matrix.subtract(x3, target);
        // 2x1
        Matrix subExpResult = new Matrix(new double[][] {{-0.5},{-0.75}});
        assertEquals(subExpResult, subResult);

        // Outputin virhe, 2x1
        Matrix outputError = Matrix.hadamardProduct(subResult, dSigmoid);
        Matrix expHadResult = new Matrix(new double[][] {{-2.269*Math.pow(10, -5)},{-5.185*Math.pow(10, -13)}});

        for (int i = 0; i < outputError.rows; i++) {
            assertArrayEquals(expHadResult.getData()[i], outputError.getData()[i], 0.001);
        }

        // 2x1 * 3x1^T = 2x1 * 1x3 = 2x3 matriisi
        Matrix errorPrevAct = Matrix.multiply(outputError, Matrix.transpose(x2));

        Matrix expErrorResult = new Matrix(
            new double[][] {
                {-6.809*Math.pow(10, -5),-4.5396*Math.pow(10, -5),-2.269*Math.pow(10, -5)},
                {-1.555*Math.pow(10, -12), -1.0372*Math.pow(10, -12), -5.1859*Math.pow(10,-13)}
            });

        for (int i = 0; i < errorPrevAct.rows; i++) {
            assertArrayEquals(expErrorResult.getData()[i], errorPrevAct.getData()[i], 0.001);
        }

        assertEquals(expErrorResult.rows, errorPrevAct.rows);
        assertEquals(expErrorResult.cols, errorPrevAct.cols);

    }

    @Test
    public void testDotProductWVectors() {
        System.out.println("dotProductWVectors");
        Matrix a = new Matrix(new double[][] {{1},{2},{3}});
        Matrix o = new Matrix(new double[][] {{1,2,3}});
        Matrix expResult = new Matrix(new double[][] {{1,2,3},{2,4,6},{3,6,9}});

        assertEquals(expResult, Matrix.dotProduct(a,o));

        Matrix w = new Matrix(new double[][] {{1,2,3}});
        Matrix output = new Matrix(new double[][] {{1}});

        Matrix result = Matrix.dotProduct(w, output);
        System.out.println(result.toString());




        // Vector av = new Vector(1,2,3);
        // Vector bv = new Vector(1);

        // System.out.println(Matrix.dotProduct(a,o).toString());

        // Matrix d = Matrix.dotProductWVectors(a, o);
        // av.dotProduct(bv);
        // System.out.println(av.toString());


        // for (int i = 0; i < a.rows; i++) {
        //     for (int j = 0; j < o.cols; j++) {
        //         Vector n = new Vector(a.getData()[i]);
        //     }
        // }

        // System.out.println(a.elementProduct(o).toString());

        // Matrix n = Matrix.dotProductWVectors(a, o);

        // assertEquals(true, true);
    }

    @Test
    public void testGetMatrixMax() {
        System.out.println("getMatrixMax");

        Matrix m = new Matrix(new double[][] {{0}, {0}, {1.0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}});
        int result = Matrix.getMatrixMax(m);
        int expResult = 2;
        assertEquals(expResult, result);
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
    public void testHadamardProductMatrices() {
        System.out.println("HadamardProduct for matrices");
        Matrix m = new Matrix(new double[][] {{1, 2, 3}, {2,4,6}, {3,6,9}});
        Matrix o = new Matrix(new double[][] {{4, 5, 6}, {5,7,9}, {6,9,12}});

        Matrix expResult = new Matrix(new double[][] {{4, 10, 18}, {10,28,54}, {18,54,108}});
        Matrix result = Matrix.hadamardProduct(m, o);
        Matrix resultOtherWay = Matrix.hadamardProduct(o,m);
        assertEquals(expResult, result);
        assertEquals(expResult, resultOtherWay);

    }

    @Test
    public void testMatrixSubtractNegative() {
        System.out.println("subtract");
        Matrix m = new Matrix(new double[][] {{1},{2},{3}});
        Matrix o = new Matrix(new double[][] {{-1},{-2},{-3}});

        Matrix expResult = new Matrix(new double[][] {{2},{4},{6}});
        Matrix result = Matrix.subtract(m, o);

        assertEquals(expResult, result);
    }

    @Test
    public void testAdd() {
        System.out.println("add");
        Matrix m = new Matrix(new double[][] {{1},{2},{3}});
        Matrix o = new Matrix(new double[][] {{1},{2},{3}});

        Matrix expResult = new Matrix(new double[][] {{2},{4},{6}});
        Matrix result = Matrix.add(m, o);

        assertEquals(expResult, result);
    }

    @Test
    public void testAddNegative() {
        System.out.println("Add negative");
        System.out.println("add");
        Matrix m = new Matrix(new double[][] {{1},{2},{3}});
        Matrix o = new Matrix(new double[][] {{-1},{-2},{-3}});

        Matrix expResult = new Matrix(new double[][] {{0},{0},{0}});
        Matrix result = Matrix.add(m, o);

        assertEquals(expResult, result);
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
