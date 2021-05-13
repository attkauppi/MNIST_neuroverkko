/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package neuroverkko;
import neuroverkko.Neuroverkko.*;
import neuroverkko.Neuroverkko.NeuralNetwork.NNBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Utils.Data.MNIST_reader.*;
import neuroverkko.Math.ActivationFunctions.Softmax;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
import neuroverkko.Math.ActivationFunctions.IActivationFunction;
import neuroverkko.Math.ActivationFunctions.*;
// import neuroverkko.Math.ActivationFunctions;
import neuroverkko.Math.ActivationFunctions.SigmoidDouble;
import neuroverkko.Math.ActivationFunctions.LeakyReLu;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.Optimizers.*;


// import org.deeplearning4j.datasets.iterator.DataSetIterator;
// import org.deeplearning4j.datasets.iterator.impl.*;
// import org.nd4j.linalg.api.ndarray.INDArray;
// import org.nd4j.linalg.dataset.DataSet;
// import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
// import org.deeplearning4j.datasets.iterator.BaseDatasetIterator;
// import org.deeplearning4j.datasets.iterator.;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


 
import java.util.concurrent.ThreadLocalRandom;

import neuroverkko.Utils.Data.MNIST_reader.MNISTCompressedReader;
import neuroverkko.Utils.Data.MNIST_reader.MNISTEntry;
// import neuroverkko.Utils.Data.MNIST_reader.*;
import neuroverkko.Utils.Data.MNIST_reader.MNISTReader;
// import javafx.animation.AnimationTimer;
// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.chart.CategoryAxis;
// import javafx.scene.chart.LineChart;
// import javafx.scene.chart.NumberAxis;
// import javafx.scene.chart.XYChart;
// import javafx.stage.Stage;
// import javafx.scene.Group; 
import java.util.Random;

import neuroverkko.Utils.ImageUtils;
import neuroverkko.Utils.MnistReader;

// import neuroverkko.Utils.LineChartSample;

public class App {
    //extends Application {


    // @Override
    // public void start(Stage stage) {
    //     // Luokkaa Random käytetään nopan heittojen arpomiseen
    //     Random arpoja = new Random();

    //     NumberAxis xAkseli = new NumberAxis();
    //     // y-akseli kuvaa nopanheittojen keskiarvoa. Keskiarvo on aina välillä [1-6]
    //     NumberAxis yAkseli = new NumberAxis(1, 6, 1);

    //     LineChart<Number, Number> viivakaavio = new LineChart<>(xAkseli, yAkseli);
    //     // kaaviosta poistetaan mm. pisteisiin liittyvät ympyrät
    //     viivakaavio.setLegendVisible(false);
    //     viivakaavio.setAnimated(false);
    //     viivakaavio.setCreateSymbols(false);

    //     // luodaan dataa kuvaava muuttuja ja lisätään se kaavioon
    //     XYChart.Series keskiarvo = new XYChart.Series();
    //     viivakaavio.getData().add(keskiarvo);

    //     new AnimationTimer() {
    //         private long edellinen;
    //         private long summa;
    //         private long lukuja;

    //         @Override
    //         public void handle(long nykyhetki) {
    //             if (nykyhetki - edellinen < 100_000_000L) {
    //                 return;
    //             }

    //             edellinen = nykyhetki;

    //             // heitetään noppaa
    //             int luku = arpoja.nextInt(6) + 1;

    //             // kasvatetaan summaa ja lukujen määrää
    //             summa += luku;
    //             lukuja++;

    //             // lisätään dataan uusi piste
    //             keskiarvo.getData().add(new XYChart.Data(lukuja, 1.0 * summa / lukuja));
    //         }
    //     }.start();

    //     Scene nakyma = new Scene(viivakaavio, 400, 300);
    //     stage.setScene(nakyma);
    //     stage.show();
    // }

    // @Override 
    // public void start(Stage stage) {
    //    //Defining the x axis             
    //    NumberAxis xAxis = new NumberAxis(1960, 2020, 10); 
    //    xAxis.setLabel("Years"); 
         
    //    //Defining the y axis   
    //    NumberAxis yAxis = new NumberAxis   (0, 350, 50); 
    //    yAxis.setLabel("No.of schools"); 
         
    //    //Creating the line chart 
    //    LineChart linechart = new LineChart(xAxis, yAxis);  
         
    //    //Prepare XYChart.Series objects by setting data 
    //    XYChart.Series series = new XYChart.Series(); 
    //    series.setName("No of schools in an year"); 
         
    //    series.getData().add(new XYChart.Data(1970, 15)); 
    //    series.getData().add(new XYChart.Data(1980, 30)); 
    //    series.getData().add(new XYChart.Data(1990, 60)); 
    //    series.getData().add(new XYChart.Data(2000, 120)); 
    //    series.getData().add(new XYChart.Data(2013, 240)); 
    //    series.getData().add(new XYChart.Data(2014, 300)); 
             
    //    //Setting the data to Line chart    
    //    linechart.getData().add(series);        
         
    //    //Creating a Group object  
    //    Group root = new Group(linechart); 
          
    //    //Creating a scene object 
    //    Scene scene = new Scene(root, 600, 400);  
       
    //    //Setting title to the Stage 
    //    stage.setTitle("Line Chart"); 
          
    //    //Adding scene to the stage 
    //    stage.setScene(scene);
        
    //    //Displaying the contents of the stage 
    //    stage.show();         
    // }   
 
    public String getGreeting() {
        return "Hello World!";
    }


    // public void start(Stage stage) {
    //     stage.setTitle("Line Chart Sample");
    //     //defining the axes
    //     final NumberAxis xAxis = new NumberAxis();
    //     final NumberAxis yAxis = new NumberAxis();
    //     xAxis.setLabel("Number of Month");
    //     //creating the chart
    //     final LineChart<Number,Number> lineChart = 
    //             new LineChart<Number,Number>(xAxis,yAxis);
                
    //     lineChart.setTitle("Stock Monitoring, 2010");
    //     //defining a series
    //     XYChart.Series series = new XYChart.Series();
    //     series.setName("My portfolio");
    //     //populating the series with data
    //     series.getData().add(new XYChart.Data(1, 23));
    //     series.getData().add(new XYChart.Data(2, 14));
    //     series.getData().add(new XYChart.Data(3, 15));
    //     series.getData().add(new XYChart.Data(4, 24));
    //     series.getData().add(new XYChart.Data(5, 34));
    //     series.getData().add(new XYChart.Data(6, 36));
    //     series.getData().add(new XYChart.Data(7, 22));
    //     series.getData().add(new XYChart.Data(8, 45));
    //     series.getData().add(new XYChart.Data(9, 43));
    //     series.getData().add(new XYChart.Data(10, 17));
    //     series.getData().add(new XYChart.Data(11, 29));
    //     series.getData().add(new XYChart.Data(12, 25));
        
    //     Scene scene  = new Scene(lineChart,800,600);
    //     lineChart.getData().add(series);
       
    //     stage.setScene(scene);
    //     stage.show();
    // }

    public static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}

    // @Override public void start(Stage stage) {
    //     stage.setTitle("Line Chart Sample");
    //     final CategoryAxis xAxis = new CategoryAxis();
    //     final NumberAxis yAxis = new NumberAxis();
    //     xAxis.setLabel("Month");       
        
    //     final LineChart<String,Number> lineChart = 
    //             new LineChart<String,Number>(xAxis,yAxis);
                
    //     lineChart.setTitle("Stock Monitoring, 2010");
                                
    //     XYChart.Series series = new XYChart.Series();
    //     series.setName("My portfolio");
        
    //     series.getData().add(new XYChart.Data("Jan", 23));
    //     series.getData().add(new XYChart.Data("Feb", 14));
    //     series.getData().add(new XYChart.Data("Mar", 15));
    //     series.getData().add(new XYChart.Data("Apr", 24));
    //     series.getData().add(new XYChart.Data("May", 34));
    //     series.getData().add(new XYChart.Data("Jun", 36));
    //     series.getData().add(new XYChart.Data("Jul", 22));
    //     series.getData().add(new XYChart.Data("Aug", 45));
    //     series.getData().add(new XYChart.Data("Sep", 43));
    //     series.getData().add(new XYChart.Data("Oct", 17));
    //     series.getData().add(new XYChart.Data("Nov", 29));
    //     series.getData().add(new XYChart.Data("Dec", 25));
        
        
    //     Scene scene  = new Scene(lineChart,800,600);
    //     lineChart.getData().add(series);
       
    //     stage.setScene(scene);
    //     stage.show();
    // }

        //     try {
    //         String url = "http://127.0.0.1:5000/training_data"; //"http://api.ipinfodb.com/v3/ip-city/?key=d64fcfdfacc213c7ddf4ef911dfe97b55e4696be3532bf8302876c09ebd06b&ip=74.125.45.100&format=json";
    //         URL obj = new URL(url);
    //         HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    //         // optional default is GET
    //         //  Gson g = new Gson();
    //         con.setRequestMethod("GET");
    //         //add request header
    //         con.setRequestProperty("User-Agent", "Mozilla/5.0");
    //         int responseCode = con.getResponseCode();
    //         System.out.println("\nSending 'GET' request to URL : " + url);
    //         System.out.println("Response Code : " + responseCode);
    //         BufferedReader in = new BufferedReader(
    //                 new InputStreamReader(con.getInputStream()));
    //         String inputLine;
    //         StringBuffer response = new StringBuffer();
    //         while ((inputLine = in.readLine()) != null) {
    //             response.append(inputLine);
    //     }
    //  in.close();
    //  //print in String
    //  System.out.println("Api response");
    //  System.out.println(response.toString());

    public static int[] flattenImages(int[][] image) {
        int[] result = new int[image.length * image[0].length];
        for (int r = 0; r < image.length; r++) {
            int[] row = image[r];
            System.arraycopy(row, 0, result, r*row.length, row.length);
        }
        return result;
    }

    public static double[] scale(int[] image) {
        double[] result = new double[image.length];
        for (int i = 0; i < image.length; i++) {
            result[i] = image[i] / 255.0 *0.999 + 0.001;
        }
        return result;
    }

    public static int[][] moveUp(int[][] inImage) {
        int[][] image = new int[inImage.length][inImage[0].length];

        int[] firstRow =new int[inImage[0].length];
        firstRow = Arrays.copyOfRange(inImage[0], 0, inImage[0].length);

        // Move rows 1-n up one row
        for (int row = 1; row < inImage.length; row++) {
            image[row] = Arrays.copyOfRange(inImage[row], 0, inImage[row].length);
        }

        // First row jumps as the last row
        image[image.length-1] = Arrays.copyOfRange(firstRow, 0, firstRow.length);
        
        return image;
    }

    public static int[] fillFrom(int[] filledFrom, int[] fillTo) {
        for (int i = 0; i < filledFrom.length; i++) {
            fillTo[i] = filledFrom[i];
        }
        return fillTo;
    }



    public static void main(String[] args) throws IOException {
        int[] labels = MnistReader.getLabels(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/MNIST/train-labels-idx1-ubyte.gz"));
        List<int[][]> images = MnistReader.getImages(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data//MNIST/train-images-idx3-ubyte.gz"));
        int[] labels_expanded = new int[labels.length*2];
        labels_expanded = fillFrom(labels, labels_expanded);// = Arrays.copyOfRange(labels, 0, labels.length);
        System.out.println("Labels expanded size: " + labels_expanded.length);

        // System.out.println("Labels: " + Arrays.toString(labels));


        double[] labels_d = Arrays.stream(labels).mapToDouble(Double::valueOf).toArray();
        double[][] scaledImages = new double[images.size()*2][];
        for (int i = 0; i < images.size(); i++) {
            scaledImages[i] = scale(flattenImages(images.get(i)));
        }

        // Apply one of the operations to each of the images. Which
        // operation is done is "randomly" chosen.
        for (int i = 0; i < images.size(); i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 4 + 1);
            int[][] image = images.get(i);

            switch (randomNum) {
                case 1:
                    scaledImages[images.size()+i] = scale(flattenImages(ImageUtils.moveRight(image)));
                    labels_expanded[labels.length+i] = labels[i];
                case 2:
                    scaledImages[images.size()+i] = scale(flattenImages(ImageUtils.moveDown(image)));
                    labels_expanded[labels.length+i] = labels[i];
                case 3:
                    scaledImages[images.size()+i] = scale(flattenImages(ImageUtils.moveLeft(image)));
                    labels_expanded[labels.length+i] = labels[i];
                    
                case 4:
                    scaledImages[images.size()+i] = scale(flattenImages(ImageUtils.moveUp(image)));
                    labels_expanded[labels.length+i] = labels[i];
            
            }
        }
        images = null;
        labels = null;

        System.out.println("Expanded: " + Arrays.toString(labels_expanded));

        // Testisetin laajennetut labelit
        double[] labels_expanded_d = Arrays.stream(labels_expanded).mapToDouble(Double::valueOf).toArray();

        System.out.println("labels expanded pituus lopussa: " + labels_expanded.length);

        // Test set
        int[] labels_validation = MnistReader.getLabels(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/MNIST/t10k-labels-idx1-ubyte.gz"));
        List<int[][]> images_validation = MnistReader.getImages(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data//MNIST/t10k-images-idx3-ubyte.gz"));
        // int[] labels_expanded = new int[labels.length*2];
        // labels_expanded = Arrays.copyOfRange(labels, 0, labels.length);


        double[] labels_d_validation = Arrays.stream(labels_validation).mapToDouble(Double::valueOf).toArray();
        double[][] scaledImages_validation = new double[images_validation.size()][];
        for (int i = 0; i < images_validation.size(); i++) {
            scaledImages_validation[i] = scale(flattenImages(images_validation.get(i)));
        }
        images_validation = null;
        labels_validation = null;
        


        //////////// ^ TOIMII!






        // System.out.println("Images:");
        // for (int i = 0; i < scaledImages.length; i++) {
        //     System.out.println(Arrays.toString(scaledImages[i]));
        // }

        // br2 =  new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), encoding));


        // launch(args);
        // LineChartSample lcs = new LineChartSample();

        // Path path = Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/t10k-images.idx3-ubyte");
        // byte[] data = Files.readAllBytes(path);

        // int requiredLength = data.length >>> 2;
        // int[] ints = new int[requiredLength];

        // for (int i = 0; i < requiredLength; i++) {
        //     int j = i << 2;
        //     int x = 0;
        //     x += (data[j++] & 0xff) << 0;
        //     x += (data[j++] & 0xff) << 8;
        //     x += (data[j++] & 0xff) << 16;
        //     x += (data[j++] & 0xff) << 24;
        //     ints[i] = x;

        // }
        
        NeuralNetwork nn = new NeuralNetwork(3, 10, 784);
        ArrayList<Layer> layers = new ArrayList<>();
        Layer input = new Layer(784, new Identity(), 0.0);
        
        Layer hidden = new Layer(30, new Sigmoid(), new GradientDescent(0.02), 0.20);
        Layer output = new Layer(10, new Softmax(), new GradientDescent(0.02), 0.25);
        nn.setL2(0.02);



        hidden.setPrevLayer(input);
        output.setPrevLayer(hidden);

        nn.addLayer(input);
        nn.addLayer(hidden);

        nn.addLayer(output);

        nn.setWeightsUniformly();
        
        nn.setCostFunction(new CrossEntropy());
        nn.setOptimizer(new GradientDescent(0.02));
        nn.setL2(0.05);

        // ///// TÄYSIN TOIMIVA VAIHTOEHTO
        // double[][] kuvat = new double[250_000][784];
        // double[] numero = new double[250_000];

        // try (BufferedReader br = new BufferedReader(new FileReader("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/expanded.csv"))) {
        //     String line;
        //     int indeksi = 0;
        //     while ((line = br.readLine()) != null) {
        //         double[] kuva = new double[784];
        //         String[] values = line.split(",");

        //         if (indeksi != 0) {
        //             // Stream to array but skip first, which is label
        //             numero[indeksi] = Double.parseDouble(values[0]);
        //             kuvat[indeksi] = Arrays.stream(values).skip(1).mapToDouble(Double::valueOf).toArray();
        //         }

        //         indeksi++;
        //     }
        // }

        // System.out.println("Kuvat: " + kuvat.length + ", " + kuvat[0].length);
        // System.out.println("Numero: " + numero.length);

        // List<double[]> recordsTestValues = new ArrayList<>();
        // List<Double> validTestValues = new ArrayList<>();

        // double[][] kuvat_test = new double[250_000][784];
        // double[] numero_test = new double[250_000];
        // try (BufferedReader br = new BufferedReader(new FileReader("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/mnist_test.csv"))) {
        //     String line;
        //     int indeksi = 0;
        //     while ((line = br.readLine()) != null) {
        //         String[] values = line.split(",");

        //         double[] kuva = new double[784];

        //         if (indeksi != 0) {
        //             // Stream to array but skip first, which is label
        //             numero_test[indeksi] = Double.parseDouble(values[0]);
        //             kuvat_test[indeksi] = Arrays.stream(values).skip(1).mapToDouble(Double::valueOf).map(a -> a/255.0).toArray();
        //         }
        //         indeksi++;
        //     }
        // }


        System.out.println("RecordsTestValues: ");
        
        // nn.learnFromDataset(kuvat, 30, 10, 0.1, numero, kuvat_test, numero_test, 0.1);
        System.out.println("labels expanded pituus juuri ennen neuroverkolle lähettämistä: " + labels_expanded.length);

        nn.learnFromDataset(scaledImages, 30, 10, 0.1, labels_expanded_d, scaledImages_validation, labels_d_validation, 0.1);

        // labels_d
        // scaledImages
        
        // labels_d_validation
        // scaledImages_validation
        //nn.SGD(recordsValues, 2, 10, 0.002, validValues, recordsTestValues, validTestValues, 5.0);

        ////// ALLA OLEVA OSUUS ON TOIMIVAA
        // System.out.println(new App().getGreeting());

        
        //NeuralNetwork nn = new NeuralNetwork(2);
        NeuralNetwork3 nn2 = new NeuralNetwork3();
        nn2.setCostFunction(new MSE());

        // SigmoidDouble s = new SigmoidDouble(1.0);
        //IActivationFunction s = new Sigmoid();
        // System.out.println(s.calculate(10));

        // nn.addLayer(new Sigmoid(), 3, 0.2);
        Layer3 i = new Layer3(2, "i1", new Identity());

        Layer3 l22 = new Layer3(1, "o22", new Sigmoid());

        Layer3 l21 = new Layer3(3, "l21", new Sigmoid());

        i.setNextLayer(l21);
        l21.setWeightsFromMatrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}});
        l21.setBias(0.2);
        // System.out.println("l21 painot: ");
        l21.printWeights();
        l21.setNextLayer(l22);
        l22.setWeightsFromMatrix(new double[][] {{0.11},{0.12},{0.13}});
        l22.setBias(0.25);
        // System.out.println("l22 painot");
        l22.printWeights();

        nn2.addLayer(i);
        nn2.addLayer(l21);
        nn2.addLayer(l22);

        // //nn.feedInput(new double[]{0.1, 0.2});

        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});

        // nn.updateFromTraining();

        // for (int indeksi = 1; indeksi < nn.layers.size(); indeksi++) {
        //     Layer3 lll = nn.layers.get(indeksi);
    
        //     System.out.println("Kerroksen painot ennen päivitystä: " + lll.getWeightsMatrix().toString());
        //     lll.updateFromLearning();

        //     System.out.println("Kerroksen painot ennen päivitystä: " + lll.getWeightsMatrix().toString());
        //     System.out.println("Kerroksen painot päivityksen jälkeen: " + lll.weights.toString());

        //     //System.out.println("LLL painot "lll.getWeightsMatrix().toString());
        // }

        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});
        // nn.updateFromTraining();
        // System.out.println(nn.getLastLayer().getOutputMatrix());
        // nn.calculateError(new Matrix(new double[][] {{0.8}}), nn.getLastLayer().getOutputMatrix());

        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});
        // nn.updateFromTraining();
        // System.out.println(nn.getLastLayer().getOutputMatrix());
        // nn.calculateError(new Matrix(new double[][] {{0.8}}), nn.getLastLayer().getOutputMatrix());

        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});
        // nn.updateFromTraining();
        // System.out.println(nn.getLastLayer().getOutputMatrix());
        // nn.calculateError(new Matrix(new double[][] {{0.8}}), nn.getLastLayer().getOutputMatrix());


        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});
        // nn.updateFromTraining();
        // System.out.println(nn.getLastLayer().getOutputMatrix());
        // nn.calculateError(new Matrix(new double[][] {{0.8}}), nn.getLastLayer().getOutputMatrix());

        // nn.train(new double[]{0.1, 0.2}, new double[] {0.8});
        // nn.updateFromTraining();
        // System.out.println(nn.getLastLayer().getOutputMatrix());
        // nn.calculateError(new Matrix(new double[][] {{0.8}}), nn.getLastLayer().getOutputMatrix());

    }
}
