/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.math;
import main.java.math.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

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
                assertTrue(matriisi.arvot[i][j] >= -1 && matriisi.arvot[i][j] <= 1);
            }
        }
    }
    
    @Test
    public void summaTest() {
        matriisi.summa(2.0);
        
        for (int i = 0; i < matriisi.rows; i++) {
            for (int j = 0; j < matriisi.cols; j++) {
                assertTrue(matriisi.arvot[i][j] == 3.0);
            }
        }
    }
    
}
