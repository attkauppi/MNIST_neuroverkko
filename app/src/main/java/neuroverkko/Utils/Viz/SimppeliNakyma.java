package neuroverkko.Utils.Viz;

import neuroverkko.App;
import neuroverkko.Math.ActivationFunctions.ActivationFunction;
import neuroverkko.Math.ActivationFunctions.Identity;
import neuroverkko.Math.ActivationFunctions.LeakyReLu;
import neuroverkko.Math.ActivationFunctions.Sigmoid;
import neuroverkko.Math.ActivationFunctions.Softplus;
import neuroverkko.Math.CostFunctions.CrossEntropy;
import neuroverkko.Math.Optimizers.GradientDescent;
import neuroverkko.Math.Optimizers.Momentum;
import neuroverkko.Neuroverkko.Layer;
import neuroverkko.Neuroverkko.NeuralNetwork;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class SimppeliNakyma implements Runnable {

    String name;
    Thread t;

    XYChart.Series evaluationAccuracy;
    XYChart.Series trainingAccuracy;
    XYChart.Series trainingCost;
    XYChart.Series otherCost;
    LineChart<Number, Number> viivakaavio;
    NumberAxis xAkseli;
    NumberAxis yAkseli;
    int addedAccuracies;
    boolean added;
    Queue<Double> q;

    public SimppeliNakyma() {
        this.evaluationAccuracy = new XYChart.Series<>();
        this.trainingAccuracy = new XYChart.Series<>();
        this.trainingCost = new XYChart.Series<>();
        this.otherCost = new XYChart.Series<>();
        this.xAkseli = new NumberAxis();
        this.yAkseli = new NumberAxis();
        this.viivakaavio = new LineChart<>(xAkseli, yAkseli);
        this.addedAccuracies = 0;
        this.added = false;
        this.q = new PriorityQueue<>();
    }

    public void addAccuracy(double value) {
        q.add(value);
        this.added = true;
    }

    public void setAddedState(Boolean state) {
        this.added = added;
    }

    public double getFromQueue() {
        double value = -1;
        if (!this.q.isEmpty()) {
            this.addedAccuracies++;
            value = this.q.poll();
        }

        setAddedState(this.q.isEmpty());

        return -1;
    }

    public void addToQueue(double accuracy) {
        this.q.add(accuracy);
    }

    public int getAddedAccuracies() {
        return this.addedAccuracies;
    }

    public boolean getAdded() {
        return this.added;
    }

    public void run() {
        try {
            System.out.println("Running");
            if (getAdded()) {
                while (!q.isEmpty()) {
                    evaluationAccuracy.getData().add(new XYChart.Data(getFromQueue(), getAddedAccuracies()));
                }
            }
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        
    }

    public Parent getChart() {
        NeuralNetwork nn = new NeuralNetwork(3, 10, 784);
        nn.setSm(this);

        ArrayList<Layer> layers = new ArrayList<>();
        Layer input = new Layer(784, new Identity(), 0.0);
        Layer hidden = new Layer(50, new LeakyReLu(), new GradientDescent(0.03), 0.20);
        Layer hidden2 = new Layer(40, new Sigmoid(), new GradientDescent(0.03), 0.20);
        Layer hidden3 = new Layer(30, new Sigmoid(), new GradientDescent(0.03), 0.20);
        Layer output = new Layer(10, new Softplus(), new GradientDescent(0.03), 0.25);

        nn.addLayer(input);
        nn.addLayer(hidden);
        nn.addLayer(hidden2);
        nn.addLayer(hidden3);
        nn.addLayer(output);

        nn.setWeightsUniformly();

        nn.setCostFunction(new CrossEntropy());
        nn.setOptimizer(new Momentum(0.15));
        nn.setL2(0.01);

        Random arpoja = new Random();

        NumberAxis xAkseli = new NumberAxis();
        // y-akseli kuvaa nopanheittojen keskiarvoa. Keskiarvo on aina välillä [1-6]
        NumberAxis yAkseli = new NumberAxis(1, 6, 1);

        LineChart<Number, Number> viivakaavio = new LineChart<>(xAkseli, yAkseli);
        // kaaviosta poistetaan mm. pisteisiin liittyvät ympyrät
        viivakaavio.setLegendVisible(false);
        viivakaavio.setAnimated(false);
        viivakaavio.setCreateSymbols(false);

        // luodaan dataa kuvaava muuttuja ja lisätään se kaavioon
        XYChart.Series keskiarvo = new XYChart.Series();
        viivakaavio.getData().add(keskiarvo);

        new AnimationTimer() {
            private long edellinen;
            private long summa;
            private long lukuja;

            @Override
            public void handle(long nykyhetki) {
                if (nykyhetki - edellinen < 100_000_000L) {
                    return;
                }

                edellinen = nykyhetki;

                // heitetään noppaa
                int luku = arpoja.nextInt(6) + 1;

                // kasvatetaan summaa ja lukujen määrää
                summa += luku;
                lukuja++;

                // lisätään dataan uusi piste

                // run();
                
                
            }
        }.start();

        return viivakaavio;
    }

    public Parent getView() {

        // Stage window = new Stage();
        BorderPane layout = new BorderPane();
        layout.setTop(new Label("Ylälaita"));
        layout.setRight(new Label("Oikea laita"));
        layout.setLeft(new Label("Vasen laita"));
        layout.setBottom(new Label("Alalaita"));
        layout.setCenter(new Label("Keskikohta"));

        Button lisaanappi = new Button("Tarkista");
        layout.setCenter(lisaanappi);

        


        // Scene view = new Scene(layout);

        // window.setScene(view);
        // window.show();
        return layout;
    }
    
}
