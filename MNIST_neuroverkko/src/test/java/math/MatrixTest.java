/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;
import math.Matrix;
//import org.gradle.testkit.runner.BuildResult;
//import org.gradle.testkit.runner.GradleRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Assert;


//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.Assert.assertEquals;
//import org.junit.jupiter.api.Test;

//package net.petrikainulainen.junit5;

/**
 *
 * @author ari
 */
public class MatrixTest {
    
    Matrix matriisi;
    
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
    public void summaTest() {
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
    public void matriisiSummaTest() {
        Matrix summattava = new Matrix(3, 3);
        summattava = fill(summattava);
        
        matriisi.matriisiSumma(summattava);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                Assert.assertTrue(matriisi.arvot[i][j] == 2.0);
            }
        }
        
    }
    
}
