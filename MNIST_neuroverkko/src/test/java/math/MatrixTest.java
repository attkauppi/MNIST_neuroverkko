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
        matriisi.summa(2.0);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                double arvo = matriisi.arvot[i][j];
                Assert.assertTrue(arvo == 3.0);
            }
        }
    }
    
    public Matrix fill(Matrix m) {
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
