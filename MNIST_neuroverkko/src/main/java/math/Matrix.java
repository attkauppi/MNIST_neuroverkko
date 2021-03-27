/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;
import java.lang.Math;
/**
 * <h1>Matrix-luokka</h1>
 * Matrix-luokka suorittaa matriisilaskennan operaatioita, kuten pistetulon, 
 * yhteenlaskun ja vähennyksen. Näitä tarvitaan myöhemmissä vaiheissa.
 * 
 * @version: 0.001
 * @author ari
 */
public class Matrix {
    
    public double[][] arvot;
    public int rows, cols;
    
    public Matrix(int rows, int cols) {
        this.arvot = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Täytä sattumanvaraisilla arvoilla 1 ja -1 välillä.
                this.arvot[i][j] = Math.random() * (2-1); 
            }
        }
    }
    
    public void summa(double scaler) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.arvot[i][j] += scaler;
            }
        }
    }
    
    public void matriisiSumma(Matrix m) {
        if (this.cols != m.cols || this.rows != m.rows) {
            throw new IllegalArgumentException("Matriisit eri kokoiset!");
        }
        
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.arvot[i][j] += m.arvot[i][j];
            }
        }
    }
    
    
}
