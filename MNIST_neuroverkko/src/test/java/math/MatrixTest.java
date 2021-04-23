/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import math.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;

/**
 *
 * @author ari
 */
public class MatrixTest {
    
    public MatrixTest() {
    }
    
    Matrix matriisi;
    
//    public MatrixTest() {
//        this.matriisi = new Matrix(3,3);
//    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
    }
    
    @Before
    public void setUp() {
        matriisi = new Matrix(3, 3);
        // Täytetään matriisi, jotta on helpompaa tarkistaa
        // testit
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++ ) {
                matriisi.arvot[i][j] = 1.0;
            }
        }
        
    }
    
    @After
    public void tearDown() {
        
    }
    
    @Test
    public void matrixFilledCorrectly() {
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                boolean minLimit = matriisi.arvot[i][j] >= -1;
                boolean maxLimit = matriisi.arvot[i][j] <= 1;
                Assert.assertTrue(matriisi.arvot[i][j] >= -1 && matriisi.arvot[i][j] <= 1);
            }
        }
    }
    
    @Test
    public void testSumma() {
        matriisi.skalaariSumma(2.0);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                double arvo = matriisi.arvot[i][j];
                Assert.assertTrue(arvo == 3.0);
            }
        }
    }
    
    public static Matrix fill(Matrix m) {
        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                m.arvot[i][j] = 1.0;
            }
        }
        return m;
    }
    
    @Test
    public void testMatriisiSumma() {
        Matrix summattava = new Matrix(3, 3);
        summattava = fill(summattava);
        
        matriisi.matriisiSumma(summattava);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                Assert.assertTrue(matriisi.arvot[i][j] == 2.0);
            }
        }
        
    }
    
    @Test
    public void testFromArray() {
        double[] taulukko = {1,2,3};
        Matrix sarakevektori = Matrix.fromArray(taulukko);
        
        assertEquals(sarakevektori.rows, taulukko.length);
        assertEquals(sarakevektori.cols, 1);
        
        //fails("Tarkista matriisin fromArray-metodin luoman matriisin koko ");
    }
    
    @Test
    public void testToArrayListSizeEqualsMatrixEntries() {
        Matrix t1 = new Matrix(1,1);
        assertEquals(t1.toArray().size(), 1);
        
        Matrix t2 = new Matrix(2,2);
        assertEquals(t2.toArray().size(), 4);
        
        Matrix t3 = new Matrix(3,3);
        assertEquals(t3.toArray().size(), 9);
    }
    
    @Test
    public void testTransponoi() {
        Matrix a = new Matrix(2,2);
        
        Matrix aT = Matrix.transponoi(a);
        
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                assertEquals(aT.arvot[j][i], a.arvot[i][j], 0.0);
            }
        }
    }
    
    @Test
    public void testPistetulo() {
        Matrix t = new Matrix(2,2);
        this.fillIn(t);
        
        Matrix t2 = new Matrix(2,2);
        this.fillIn(t2);
        
        Matrix pt = Matrix.pistetulo(t, t2);
        
        assertEquals(2, pt.rows);
        assertEquals(2, pt.cols);
        
        // dot product of two 2x2 matrices with
        // 1.0 in every element should equal to 
        // row vector: [2.0, 2.0]
        // for example, to check with octave 
        // a = [1.0, 1.0; 1.0, 1.0]
        // b = [1.0, 1.0; 1.0, 1.0]
        // dot(a,b) = [2.0, 2.0]
        
        assertEquals(2.0, pt.arvot[0][0], 0.0);
        assertEquals(2.0, pt.arvot[0][1], 0.0);
    }
    
    public void testMatriisiTulo() {
        Matrix t = new Matrix(2,2);
        Matrix t2 = new Matrix(2,2);
        
        t.matriisiTulo(t2);
        
        assertEquals(2, t.rows);
        assertEquals(2, t.cols);
        
        /* Tavallisessa matriisien kertolaskussa:
        {1, 1} x {1, 1} = {2, 2}
        {1, 1}   {1, 1}   {2, 2}
        */
        
        for (int i = 0; i < t.rows; i++) {
            for (int j = 0; j < t.cols; j++) {
                assertEquals(2.0, t.arvot[i][j], 0.0);
            }
        }
    }
    
    public void testSkalaaritulo() {
        matriisi.skalaaritulo(3.0);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                assertEquals((3.0*3.0), (matriisi.arvot[i][j]*3.0), 0.0);
            }
        }
    }
    
    
    
    public static void fillIn(Matrix m) {

        for (int i = 0; i < m.rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                m.arvot[i][j] = 1.0;
            }
        }
    }

//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of summa method, of class Matrix.
//     */
//    @Test
//    public void testSumma() {
//        System.out.println("summa");
//        double scaler = 0.0;
//        Matrix instance = null;
//        instance.summa(scaler);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of matriisiSumma method, of class Matrix.
//     */
//    @Test
//    public void testMatriisiSumma() {
//        System.out.println("matriisiSumma");
//        Matrix m = null;
//        Matrix instance = null;
//        instance.matriisiSumma(m);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
