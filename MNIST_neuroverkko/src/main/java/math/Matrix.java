/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
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
    
    /**
     * skalaariSumma
     * 
     * Kasvattaa matriisin arvoja yhteenlaskettavalla reaaliluvulla
     * 
     * @param skalaari - skalaariluku, joka summataan matriisin
     * arvoihin
     */
    public void skalaariSumma(double skalaari) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.arvot[i][j] += skalaari;
            }
        }
    }
    
    /**
     * matriisiSumma
     * 
     * Laskee parametrina annetun matriisin yhteen käytössä
     * olevan matriisiolion kanssa.
     * 
     * @param m - matriisi
     */
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
    
    /**
     * matriisiErotus
     * 
     * Laskee matriisien a ja b erotuksen
     * 
     * @param a - matriisi
     * @param b - matriisi
     * @return temp - Matriisiolion, joka saaadaan laskemalla matriisien
     * a ja b erotus
     */
    public static Matrix matriisiErotus(Matrix a, Matrix b) {
        Matrix t = new Matrix(a.rows, a.cols);
        
        try {
            for (int i = 0; i < t.rows; i++) {
                for (int j = 0; j < t.cols; j++) {
                    t.arvot[i][j] = (a.arvot[i][j] - b.arvot[i][j]);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("matriisierotuksessa matriiseilla oltava yhtä monta saraketta ja riviä!");
            //System.out.println("matriisierotuksessa ");
        }
        
        return t;
    }
    
    /**
     * matriisiTulo
     * 
     * Laskee parametreina annettujen matriisien tulon
     * @param Matrix a
     * @param Matrix b
     * @return pisteulo
     */
    public static Matrix pistetulo(Matrix a, Matrix b) {
        
        // FIXME: Tuottaa vääränkokoisen matriisin. Pitäisi olla (1,2),
        // mutta on kokoa (2,2)
       Matrix temp=new Matrix(a.rows,b.cols);
		for(int i=0;i<temp.rows;i++)
		{
			for(int j=0;j<temp.cols;j++)
			{
				double sum=0;
				for(int k=0; k < a.cols;k++)
				{
					sum+=a.arvot[i][k]*b.arvot[k][j];
				}
				temp.arvot[i][j]=sum;
                                System.out.print(temp.arvot[i][j] + " ");
			}
                        System.out.println("");
		}
		return temp;
    }
    
    
    /**
     * fromArray
     * 
     * Luo matriisin (oikeastaan sarakevektorin) taulukosta
     * 
     * @param arr - taulukko
     * @return Matrix temp - sarakevektori
     */
    public static Matrix fromArray(double[] arr) {
        Matrix t = new Matrix(arr.length, 1);
        
        for (int i = 0; i < arr.length; i++) {
            t.arvot[i][0] = arr[i];
        }
        return t;
    }
    
    /**
     * toArray
     * 
     * Muuntaa matriisin listaksi
     * 
     * @return List<Double> t - palauttaa matriisin lista-muodossa 
     */
    // TODO: korvaa List omalla implementaatiolla
    public List<Double> toArray() {
        List<Double> t = new ArrayList<Double>();
        
        
        //List<Double> collection = Arrays.stream(this.arvot).flatMap(Arrays::stream).collect(Collectors.toList());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t.add(this.arvot[i][j]);
                
            }
        }
        return t;
    }
    
    /**
     * matriisiErotus
     * 
     * Laskee parametreina annettujen matriisien a ja b erotuksen
     * 
     * @param a - matriisi
     * @param b - matriisi
     * @return 
     */
//    public static Matrix matriisiErotus(Matrix a, Matrix b) {
//        Matrix t = new Matrix(a.rows, a.cols);
//        
//        try {
//            for (int i = 0; i < a.rows; i++) {
//                for (int j = 0; j < a.cols; j++) {
//                    t.arvot[i][j] = a.arvot[i][j] - b.arvot[i][j];
//                }
//            }
//        } catch (IndexOutOfBoundsException e) {
//            throw new IndexOutOfBoundsException("Matriisierotuksessa molempien matriisien on oltava saman kokoisia!");
//        }
//        return t;
//    }
    
    /**
     * transponoi
     * 
     * Transponoi parametrina saadun matriisin
     * 
     * @param a - Matriisi
     * @return a.T - eli a transponoituna.
     */
    public static Matrix transponoi(Matrix a) {
        Matrix aT = new Matrix(a.cols, a.rows);
        
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                // indeksit i ja j eri järjestyksessä, koska
                // transponoidaan
                aT.arvot[j][i] = a.arvot[i][j];
            }
        }
        return aT;
    }
    
    /**
     * matriisiTulo
     * 
     * Laskee parametrina annetun ja olion välisen
     * matriisitulon
     * 
     * @param Matrix k 
     */
    public void matriisiTulo(Matrix m2) {
        this.arvot = IntStream.range(0, rows)
            .mapToObj(i -> IntStream.range(0, m2.cols)
                    .mapToDouble(j -> IntStream.range(0, this.cols)
                            .mapToDouble(k -> this.arvot[i][k] * m2.arvot[k][j])
                            .sum())
                    .toArray()).toArray(double[][]::new);
        
//        for (int i = 0; i < this.rows; i++) {
//            for (int j = 0; j < this.cols; j++) {
//                this.arvot[i][j] *= k.arvot[i][j];
//            }
//        }
    }
    
    /**
     * skalaaritulo
     * 
     * @param skalaari (double) - skaalaa matriisin arvoja 
     */
    public void skalaaritulo(double skalaari) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.arvot[i][j] *= skalaari;
            }
        }
    }
    
    public void sigmoid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.arvot[i][j] *= 1/(1+Math.exp(-this.arvot[i][j]));
            }
        }
    }
    
    public void dSigmoid() {
        Matrix t = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t.arvot[i][j] *= this.arvot[i][j] * (1-this.arvot[i][j]);
            }
        }
    }
    
}
