/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MNIST_neuroverkko;
import java.util.Arrays;
//import math.Matrix;
//import math.RandomNumberGenerator;
//import math.IActivationFunction;
//import math.Sigmoid;
//import math.Linear;
import MNIST_neuroverkko.*;
//import MNIST_neuroverkko.HiddenLayer;
//import MNIST_neuroverkko.*;
import math.*;
import MNIST_neuroverkko.NeuralNetwork;

/**
 *
 * @author ari
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Matrix m = new Matrix(3,3);
//        System.out.println("yes yes yes");
//        
//        m.summa(3.0);
//        System.out.println("m.arvot: "+ Arrays.deepToString(m.arvot));

        RandomNumberGenerator.seed=0;
        
        int numberOfInputs=2;
        int numberOfOutputs=1;
        int[] numberOfHiddenNeurons= { 3 };
        IActivationFunction[] hiddenAcFnc = { new Sigmoid(1.0) } ;
        Linear outputAcFnc = new Linear(1.0);
        System.out.println("Creating Neural Network...");
        NeuralNetwork nn = new NeuralNetwork(numberOfInputs,numberOfOutputs,
                numberOfHiddenNeurons,hiddenAcFnc,outputAcFnc);
        System.out.println("Neural Network created!");
        nn.print();
        
        double [] neuralInput = { 1.5 , 0.5 };
        
        double [] neuralOutput;
        System.out.println("Feeding the values ["+
                String.valueOf(neuralInput[0])+" ; "+
                String.valueOf(neuralInput[1])+"] to the neural network");
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
        System.out.println("Output generated:"+String.valueOf(neuralOutput[0]));
        
        neuralInput[0] = 1.0;
        neuralInput[1] = 2.1;
        
        System.out.println("Feeding the values ["+
                String.valueOf(neuralInput[0])+" ; "+
                String.valueOf(neuralInput[1])+"] to the neural network");
        nn.setInputs(neuralInput);
        nn.calc();
        neuralOutput=nn.getOutputs();
        
        System.out.println("Output generated:"+String.valueOf(neuralOutput[0]));
//        RandomNumberGenerator.seed = 0;
//        System.out.println("SAATANA");
//        
//        int numberOfInputs = 2;
//        int numberOfOutputs = 1;
//        int[] numberOfHiddenNeurons = { 3 };
//        IActivationFunction[] hiddenActivationFunction = { new Sigmoid(1.0) };
//        Linear outputActivationFunction = new Linear(1.0);
//        
//        NeuralNetwork nn = new NeuralNetwork(numberOfInputs, numberOfOutputs,
//        numberOfHiddenNeurons, hiddenActivationFunction, outputActivationFunction);
//        
//        
//        
//        double[] neuralInput = {1.5, 0.5};
//        
//        System.out.println("Feeding the values ["+
//            String.valueOf(neuralInput[0])+" ; "+
//            String.valueOf(neuralInput[1])+"] to the neural network");
//        
//        double[] neuralOutput;
//        nn.setInputs(neuralInput);
//        nn.calc();
//        
//        neuralOutput = nn.getOutputs();
//        System.out.println("output: " + Arrays.toString(neuralOutput));
//        
//        System.out.println("Output generated:"+String.valueOf(neuralOutput[0]));
//        
//        neuralInput[0] = 1.0;
//        neuralInput[1] = 2.1;
//        
//        nn.setInputs(neuralInput);
//        nn.calc();
//        neuralOutput = nn.getOutputs();
//        
//        System.out.println("nn print: ");
//        nn.print();
//        
//        System.out.println("Output: " + String.valueOf(neuralOutput[0]));
//        System.out.println(Arrays.toString(nn.getOutputs()));
    }
    
}
