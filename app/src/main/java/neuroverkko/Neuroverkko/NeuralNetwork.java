package neuroverkko.Neuroverkko;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import neuroverkko.Utils.Viz.SimppeliNakyma;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import neuroverkko.Neuroverkko.Layer;
import neuroverkko.Neuroverkko.Initializer.Initializer;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.Optimizers.*;
import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Utils.RandomNumberGenerator;
import neuroverkko.Utils.Data.MNIST_reader.MNISTReader;
import neuroverkko.Utils.DataStructures.Map.*;
import neuroverkko.Utils.*;
import neuroverkko.Utils.MnistReader;

public class NeuralNetwork {//Runnable {

    // @Override
    // public void run() {
    //     try {
    //         readSmallerDatasets();
    //     } catch (Exception e) {

    //     }
    //     learnFromDataset(3, 10, 0.1, 5);
    

    SimppeliNakyma sm;
    public List<Layer> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer inputLayer;
    public Layer outputLayer;
    public double target;
    public Matrix targetM;
    public double l2;
    public Matrix output;
    public Matrix errorM;
    public CostFunctions costFunction;
    public int minibatch_size;
    public Optimizer opt;
    public List<Matrix> deltaGradientWeights;
    public List<Matrix> deltaGradientBiases;
    public List<Double> trainingCost;
    public List<Double> evaluationCost;
    public List<Double> trainingAccuracy;
    public List<Double> evaluationAccuracy;
    public List<Pair> trainingDataset;
    public List<Pair> evaluationDataset;
    public Initializer init;
    List<Pair> results;
    public Initializer initializer;
    double[] labels_d_validation;
    double[][] scaledImages;
    double[][] scaledImages_validation;
    double[] labels_expanded_d;
    boolean dropout;
    public Matrix dropoutM;


    public NeuralNetwork(int layerSize, int minibatch_size, int input_size) {
        this.layerSize = layerSize;
        this.minibatch_size = minibatch_size;
        this.inputSize = input_size;
        this.layers = new ArrayList<>();
        this.deltaGradientWeights = new ArrayList<>();
        this.deltaGradientBiases = new ArrayList<>();
        this.trainingCost = new ArrayList<>();
        this.evaluationCost = new ArrayList<>();
        this.trainingAccuracy = new ArrayList<>();
        this.evaluationAccuracy = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public NeuralNetwork(int inputSize, int minibatchSize, Optimizer opt) {
        this.layerSize = inputSize;
        this.inputSize = inputSize;
        this.layers = new ArrayList<>();
        this.minibatch_size = minibatchSize;
        this.opt = opt;
        this.deltaGradientWeights = new ArrayList<>();
        this.deltaGradientBiases = new ArrayList<>();
        this.trainingCost = new ArrayList<>();
        this.evaluationCost = new ArrayList<>();
        this.trainingAccuracy = new ArrayList<>();
        this.evaluationAccuracy = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public void setLayers(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    public void addAccuraciesToView(double value) {
        this.sm.addAccuracy(value);
    }

    public NeuralNetwork(NeuralNetwork nn) {
        this.costFunction = nn.costFunction;
        this.l2 = nn.l2;
        this.opt = nn.opt;
        this.layers = new ArrayList<>();

        Layer prevLayer = nn.layers.get(0);

        for (int i = 1; i < nn.layers.size(); i++) {
            Layer l = nn.layers.get(i);
            l.setPrevLayer(prevLayer);
            l.setWeights(nn.layers.get(i).getWeights());
            if (nn.layers.get(i).getDeltaWeights() != null) {
                l.setDeltaWeights(nn.layers.get(i).getDeltaWeights());
            }
            if (nn.layers.get(i).getDeltaBias() != null) {
                l.setDeltaBias(nn.layers.get(i).getDeltaBias());
            }
            l.actFnc = nn.layers.get(i).actFnc;
            l.setOptimizer(nn.layers.get(i).getOpt());
            l.setL2(nn.layers.get(i).l2);
            //nnb.init.initWeights(weights, i);
            // System.out.println("nnb initializerin tuottama weights: " + weights.toString());
            // l.setWeights(weights);
            // l.setOptimizer(opt);
            // l.setL2(l2);
            layers.add(l);

            if (i == nn.layers.size()-1) {
                this.outputLayer = l;
            }

            prevLayer = l;
            // Layer nnb_l = nnb.layers.get(i);
            // Layer l = new Layer(nnb_l.getSize());
            // l.setPrevLayer(this.layers.get(i-1));
            // l.setInitialWeightsRand();
            // l.setInitialBias(0.2);
            // layers.add(l);
        }

    }

    public NeuralNetwork(NNBuilder nnb) {
        this.costFunction = nnb.costFunction;
        this.l2 = nnb.l2;
        this.opt = nnb.opt;
        this.layers = new ArrayList<>();
        this.inputSize = nnb.inputSize;
        this.opt = nnb.opt;


        Layer input = new Layer(inputSize, new Identity());
        this.inputLayer = input;
        layers.add(input);

        Layer prevLayer = inputLayer;

        for (int i = 0; i < nnb.layers.size(); i++) {
            Layer l = nnb.layers.get(i);
            l.setPrevLayer(prevLayer);
            Matrix weights = new Matrix(prevLayer.getSize(), l.getSize());
            nnb.init.initWeights(weights, i);
            System.out.println("nnb initializerin tuottama weights: " + weights.toString());
            l.setWeights(weights);
            l.setOptimizer(opt);
            l.setL2(l2);
            layers.add(l);

            if (i == nnb.layers.size()-1) {
                this.outputLayer = l;
            }

            prevLayer = l;
            this.inputLayer = l;        
            // Layer nnb_l = nnb.layers.get(i);
            // Layer l = new Layer(nnb_l.getSize());
            // l.setPrevLayer(this.layers.get(i-1));
            // l.setInitialWeightsRand();
            // l.setInitialBias(0.2);
            // layers.add(l);
        }
    }
    public void setDropout() {
        this.dropout = true;

        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).dropout = true;
        }
    }

    public void setInitializer(Initializer init) {
        this.init = init;
    }

    public void setWeightsUniformly() {
        UniformInitializer u = new UniformInitializer(-0.5, 0.5);
        for (Layer l: layers) {
            if (!l.hasPrevLayer()) {
                continue;
            }

            l.initializeWeights();
            Matrix weights = l.getWeights();

            double[][] w = new double[weights.rows][weights.cols];
            for (int i = 0; i < weights.rows; i++) {
                for (int j =0 ; j< weights.cols; j++) {
                    w[i][j] = u.Generate();
                }
            }
            l.setWeights(new Matrix(w));

        }
    }

    public void initializeLayers(List<Layer> stateLayers) {

        ArrayList<Layer> layers = new ArrayList<>();

        for (int i = 0; i < stateLayers.size(); i++) {
            Layer stateLayer = stateLayers.get(i);

            if (i == 0) {
                Layer l = new Layer(
                    stateLayer.getSize(),
                    new Identity(),
                    0.0
                );
                l.setZeroBias();
                layers.add(l);
            } else {
                // TODO: mahdollista myös muiden aktivaatiofunktioiden käyttö
                //Object actFnc = stateLayer.getActivationFunction();
                //double learningRate = stateLayer.getOpt().getLearningRate();
                
                Layer l = new Layer(
                    stateLayer.getSize(),
                    new Sigmoid(),
                    new GradientDescent(0.002),
                    stateLayer.getInitialBias()
                );
                l.setPrevLayer(layers.get(i-1));

                //Matrix weights = stateLayer.getWeights();
                l.setInitialWeightsRand();

                layers.add(l);

            }

        }
    }

    public void setInitialWeights() {
        for (Layer l: this.layers) {
            l.setInitialWeightsRand();
        }
    }

    public void setCostFunction(CostFunctions c) {
        this.costFunction = c;
    }

    public void setL2(double l2) {
        this.l2 = l2;

        for (Layer l: this.layers) {
            l.setL2(l2);
        }
    }

    public Optimizer getOpt() {
        return this.opt;
    }

    public void setOptimizer(Optimizer opt) {
        this.opt = opt;

        for (Layer l: layers) {
            l.opt = opt;
        }
    }

    public void addLayer(Layer l) {
        if (this.layers.isEmpty()) {
            this.inputLayer = l;
            this.inputLayer.actFnc = l.getActivationFunction();
            this.layers.add(l);

        } else {
            l.actFnc = l.getActivationFunction();
            l.setPrevLayer(this.layers.get(this.layers.size()-1));
            this.layers.add(l);
            this.outputLayer = l;

            l.setInitialWeightsRand();
        }
    }

    public void setSm(SimppeliNakyma sm) {
        this.sm = sm;
    }

    public void backpropagationG(Matrix targetOutput, Matrix output) {

        Layer l = this.getLastLayer();
        //Matrix error = Matrix.subtract(l.getActivation(), targetOutput);
        Matrix error = costFunction.getDerivative(targetOutput, l.getActivation());
        Matrix dAct = l.actFnc.dActFunc(l.getInput());
        // delta
        Matrix gradient = Matrix.hadamardProduct(error, dAct);

        Matrix dw = Matrix.multiply(gradient, Matrix.transpose(l.getPrevLayer().getActivation()));
        
        // Turhia
        // Matrix adj = Matrix.multiply(gradient, Matrix.transpose(l.getPrevLayer().getActivation()));
        
        // Matrix newWeightsOutput = Matrix.add(l.getWeights(), adj.scalarProd(this.l2));
        // l.setDeltaWeights(newWeightsOutput);
        // Matrix dB = dw.scalarProd(this.l2);
        // l.setDeltaBias(dB);


        l.addDeltaWeightsBiases(dw, gradient);

        l = l.getPrevLayer();

        while (l.hasPrevLayer()) {
            
            Matrix prevErrorW = Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeights()), gradient);
            dAct = l.actFnc.dActFunc(l.getInput());

            gradient = Matrix.hadamardProduct(prevErrorW, dAct);

            dw = Matrix.multiply(gradient, Matrix.transpose(l.getPrevLayer().getActivation()));

            l.addDeltaWeightsBiases(dw, gradient);

            l = l.getPrevLayer();
            
            if (!l.hasPrevLayer()) {
                break;
            }
        }
        
    }

    public void backpropagateVika(Matrix targetOutput, Matrix output) {
        Layer l = this.getLastLayer();


        Matrix dCostdOutput = costFunction.getDerivative(targetOutput, output);
        System.out.println("dCostdOutput shape: " + dCostdOutput.getShape());

       
        //Matrix.multiply(Matrix.transpose(l.getPrevLayer().getActivation()), dCostdInput);

        //System.out.println("dCostdInput: " + dCostdInput.getShape());

        while (l.hasPrevLayer()) {
            
            //dCostdInput = l.actFnc.calc_dCostdInput(l.getActivation(), dCostdOutput);

            Matrix dCostdInput = l.actFnc.calc_dCostdInput(l.getInput(), dCostdOutput);
            Matrix dCostdWeights = dCostdInput.outerProduct(l.getPrevLayer().getActivation());

            System.out.println("l.getWeights: " + l.getWeights().getShape()); 

            System.out.println("dCostdInput: " + dCostdInput.getShape());
            System.out.println("dCostWeights: " + dCostdWeights.getShape());

            l.addDeltaWeightsBiases(dCostdWeights, dCostdInput);


            //Matrix dCostdWeights = Matrix.dotProduct(Matrix.transpose(l.getPrevLayer().getActivation()), dCostdInput);

            // dCostdWeights = Matrix.multiply(Matrix.transpose(l.getPrevLayer().getActivation()), dCostdInput);
            // Matrix dcdw = Matrix.multiply()

            System.out.println("dCostdWeights: " + dCostdWeights.getShape());//CostdInput.outerProduct(l.getPrevLayer().getActivation());


            dCostdOutput = Matrix.multiply(l.getWeights(), dCostdInput);
            System.out.println("dCostdOutput: " + dCostdOutput.getShape());

            l = l.getPrevLayer();

            if (!l.hasPrevLayer()) {
                break;
            }

        }


    }


    public void backpropagateIm(Matrix targetOutput, Matrix output) {

        Layer l = this.getLastLayer();

        // Gradientti
        Matrix dCostdOutput = costFunction.getDerivative(targetOutput, output);
        


        // https://jermwatt.github.io/machine_learning_refined/notes/6_Linear_twoclass_classification/6_2_Cross_entropy.html
        // https://sudeepraja.github.io/Neural/#:~:text=Backpropagation%20is%20an%20algorithm%20used,routine%20such%20as%20gradient%20descent.&text=Any%20layer%20of%20a%20neural,of%20a%20non%20linear%20function.
        // https://eng.libretexts.org/Bookshelves/Computer_Science/Book%3A_Neural_Networks_and_Deep_Learning_(Nielsen)/03%3A_Improving_the_way_neural_networks_learn/3.01%3A_The_cross-entropy_cost_function

        Matrix z = l.getActivation();
        Matrix sp = l.actFnc.dActFunc(z);

        //Matrix costD = costFunction.getDerivative(targetOutput, l.getActivation());
        Matrix delta = Matrix.hadamardProduct(dCostdOutput, sp);
        // System.out.println("Delta: " + delta.getShape());


        //delta = Matrix.multiply(delta, l.actFnc.dActFunc(l.getInput()));

        Matrix dw = Matrix.multiply(delta, Matrix.transpose(l.getPrevLayer().getActivation()));


        // System.out.println("uusi delta: " + delta.rows + " " + delta.cols);
        // System.out.println("dw: " + dw.rows + " " + dw.cols);
        


        int latter = 0;
        l = l.getPrevLayer();
        while (l.hasPrevLayer()) {

            z = l.getActivation();
            sp = l.actFnc.dActFunc(z);

            delta = Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeights()), delta);
            delta = Matrix.hadamardProduct(delta, sp);
            // System.out.println("edellisen painot * delta: " + delta.getShape());

            dw = Matrix.multiply(delta, Matrix.transpose(l.getPrevLayer().getActivation()));
            // System.out.println("uusi dw: " + dw.getShape());

            l.addDeltaWeightsBiases(dw, delta);


            // Matrix derivaatanMuoto = l.actFnc.dActFunc(l.getActivation());
            // System.out.println("derivaatan muoto : " + derivaatanMuoto.rows + " " + derivaatanMuoto.cols);
            // // delta

            // System.out.println(l.getActivation().rows + " " + l.getActivation().cols);

            // Matrix dCostdInput = l.actFnc.calc_dCostdInput(l.getActivation(), dCostdOutput);
            // if (latter > 0) {

            // } else {

            // }
            

            // Matrix dCostdWeights = dCostdInput.outerProduct(l.getPrevLayer().getActivation());
            // // System.out.println("w: " + w.rows + " " + w.cols);




            // // System.out.println("dCostdOutput: " + dCostdOutput.rows + ", " + dCostdOutput.cols);

            // System.out.println("dCostdInput: " + dCostdInput.rows + ", " + dCostdInput.cols);
            
            // // Matrix outProdTesti = dCostdOutput.outerProduct(l.getPrevLayer().getActivation());
            // // System.out.println("Out prod testi: " + outProdTesti.rows + " " + outProdTesti.cols);


            // // Matrix dCostdWeights = dCostdInput.outerProduct(l.getActivation());
            // // System.out.println("dCostdWeights: " + dCostdWeights.rows + ", " + dCostdWeights.cols);
            // System.out.println("weights " + l.getWeights().rows + ", " + l.getWeights().cols);

            // l.addDeltaWeightsBiases(Matrix.transpose(dCostdWeights), dCostdInput);

            // dCostdOutput = Matrix.dotProduct(dCostdInput, l.getWeights());

            // // Matrix dCostOutput2 = Matrix.dotProduct(dCostdInput, l.getPrevLayer().getActivation());
            // // System.out.println("uusi dCostdOutput2: " + dCostOutput2.rows +" "+ dCostOutput2.cols);

            // System.out.println("uusi dCostdOutput: " + dCostdOutput.rows +" "+ dCostdOutput.cols);
            // // dCostdOutput = Matrix.multiply(l.getWeights(), dCostdInput);

            // System.out.println("dCostInput: " + dCostdInput.rows + ", " + dCostdInput.cols);
            // System.out.println("Edellisen aktivaatio: " + l.getPrevLayer().getActivation().rows + " " + l.getPrevLayer().getActivation().cols);


            l = l.getPrevLayer();
            // System.out.println(l.getSize());

            // System.out.println("Tulevan koko + " + l.getWeights().rows + " " + l.getWeights().cols);


            
            // Matrix dCostdWeights = Matrix.hadamardProduct(dCostdInput, l.getPrevLayer().getActivation());
            // System.out.println("dCostWeights tuottaman koko : " + dCostdWeights.rows + " " + dCostdWeights.cols);
            // Matrix erotus = Matrix.subtract(l.getActivation(), targetOutput);


            // if (this.costFunction.getClass() == CrossEntropy.class) {
            //     Matrix ownAct = l.getActivation();
               
            //     //Matrix prodToisinpain = Matrix.transpose(l.getPrevLayer().getActivation()).outerProduct(l.getActivation());

            //     // Taisi tuottaa samat tulokset kuin allaoleva!
            //     Matrix outProdTesti = l.getActivation().outerProduct(l.getPrevLayer().getActivation());
            //     System.out.println("Out prod testi: " + outProdTesti.rows + " " + outProdTesti.cols);

            //     Matrix oikeaProd = erotus.outerProduct(l.getPrevLayer().getActivation());
            //     System.out.println("oikeaProd (erotus): " + oikeaProd.rows + " " + oikeaProd.cols);

            //     // jaettuna datasetin koolla
            //     // oikeaProd.scalarProd(1/ datasetin koko)

            //     Matrix toinenKokeilu = dCostdInput.outerProduct(l.getPrevLayer().getActivation());
            //     System.out.println("Toisen kokeilun koko: " + toinenKokeilu.rows + ", " + toinenKokeilu.cols);
            //     dCostdWeights = Matrix.transpose(toinenKokeilu);


            //     double[] vec1d = new double[l.prevLayer.getActivation().rows];
            //     for (int i = 0; i < l.getPrevLayer().getActivation().rows; i++) {
            //         vec1d[i] = l.getPrevLayer().getActivation().getData()[i][0];
            //     }
            //     Vector vec = new Vector(vec1d);

            //     double[] vec2d = new double[l.getActivation().rows];
            //     for (int i = 0; i < l.getActivation().rows; i++) {
            //         vec2d[i] = l.getActivation().getData()[i][0];
            //     }
            //     Vector vec2 = new Vector(vec2d);

            //     Matrix outerprod = vec2.outerProductV(vec);
            //     System.out.println("Vektorilaskennan tulos: " + outerprod.rows + ", " + outerprod.cols);
            //     // Voisi kelvata transponoituna?
            //     dCostdWeights = Matrix.transpose(outProdTesti);

            //     // Matrix outProdTesti = l.getActivation().outerProduct(Matrix.transpose(l.getPrevLayer().getActivation()));
            //     // System.out.println("Out prod testi: " + outProdTesti.rows + " " + outProdTesti.cols);

            //     //dCostdWeights = Matrix.transpose(l.getPrevLayer().getActivation()).outerProduct(Matrix.subtract(l.getPrevLayer().getActivation(), targetOutput));
            //     System.out.println("dCostWeights: " + dCostdWeights.rows + " " + dCostdWeights.cols);
            //     //dCostdWeights = Matrix.outerProduct(Matrix.transpose(l.getPrevLayer().getActivation()), Matrix.transpose(Matrix.subtract(l.getActivation(), targetOutput)));
            //     //dCostdWeights = Matrix.hadamardProduct(l.getPrevLayer().getActivation(), Matrix.subtract(l.getActivation(), targetOutput)).scalarProd(1/l.getSize());
            // }            
            // System.out.println("dCostWeights shape: " + dCostdWeights.rows + " " + dCostdWeights.cols);

            // l.addDeltaWeightsBiases(dCostdWeights, dCostdInput);
            // // l.addDeltaWeights();

            // // Error forward propagated to earlier layers
            // dCostdOutput = Matrix.multiply(Matrix.transpose(l.getWeights()), dCostdInput);
            // System.out.println("dcostdoutput: " + dCostdOutput.rows + " " + dCostdOutput.cols);

            // l = l.getPrevLayer();

            if (!l.hasPrevLayer()) {
                break;
            }
        }

    }

    public Map<Integer, double[]> get_minibatch(int previousIndex, int minibatch_size, List<double[]> training_data, List<Double> test_data) {
        // String LABEL_FILE = "/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/t10k-labels.idx1-ubyte";
		// String IMAGE_FILE = "/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/t10k-images.idx3-ubyte";

		// int[] labels = MNISTReader.getLabels(LABEL_FILE);
		// List<int[][]> images = MNISTReader.getImages(IMAGE_FILE);
        // System.out.println("images[0].length: " + images[0].length);
        
        Map<Integer, double[]> batch = new HashMap<>();
        int last_index = 0;
        if (previousIndex+minibatch_size < training_data.size()) {
            last_index = previousIndex+minibatch_size;
        } 
        if (previousIndex + minibatch_size > training_data.size()) {
            last_index = training_data.size();
        }

        for (int i = 0; i < last_index; i++) {
            double[] image = new double[784];

            for (int j = 0; j < 784; j++) {
                image[j] = training_data.get(i)[j];
            }

            // System.out.println("image: " + image.length);

            for (int j = 0; j < image.length; j++) {
            }
            int test_value = (int) Math.round(test_data.get(i));

            batch.put(i, image);
            double[] t_value = new double[1];
            t_value[0] = test_value;
            batch.put(-i, t_value);
        }
        return batch;
    }

    public Pair getInputAndTargetMatrices(double[] mbatch, double targetOutput) {
        Matrix target = formatOutput(targetOutput);
        Matrix input = formatInput(mbatch);
        Pair p = new Pair(input, target);
        return p;
    }

    public List<Pair> getDataset(double[][] inputs, double[] outputs, int mini_batch_size) {
        int n = outputs.length;
        List<Pair> minibatches = new ArrayList<>(); 

        int k = 0;
        for (int i = 0; i < n; i++) {

            int index = 0;
            k += mini_batch_size;
            Pair<Matrix, Matrix> p = getInputAndTargetMatrices(inputs[i], outputs[i]);
            minibatches.add(p);
        }
        return minibatches;
    }

    public List<Pair> getValidationDataset(double[][] inputs, double[] outputs, int mini_batch_size) {
        int n = outputs.length;
        List<Pair> minibatches = new ArrayList<>(); 

        int k = 0;
        for (int i = 0; i < n; i++) {

            int index = 0;
            k += mini_batch_size;
            Pair<Matrix, Matrix> p = getInputAndTargetMatrices(inputs[i], outputs[i]);
            minibatches.add(p);
        }
        return minibatches;
    }

    public Pair<Double, Matrix> evaluate(Matrix input) {
        return evaluate(input, null);
    }

    public Result feed(Matrix input) {
        return feed(input, null);
    }

    public Result feed(Matrix input, Matrix output) {
        Matrix receivedInput = input;

        for (Layer l: this.layers) {
            l.evaluate(input);
            receivedInput = l.getActivation();
        }

        if (output != null) {
            backpropagate(output);
            double cost = costFunction.getCost(receivedInput, output, 1);
            return new Result(receivedInput, cost);
        }
        return new Result(receivedInput);
    }



    public Pair<Double, Matrix> evaluate(Matrix input, Matrix expResult) {
        Matrix nextInput = input;
        for (Layer l: this.layers) {
            nextInput = l.evaluateInput(nextInput);
        }

        Object result = new Object();
        if (expResult != null) {
            backpropagate(expResult);
            double cost = this.costFunction.getCost(expResult, nextInput, minibatch_size);
            result = new Pair(cost, nextInput);
        }
        
        return (Pair) result;
    }


    // Takes individual piece of data with the input and
    // desired output
    public void feedData(Matrix input, Matrix targetOutput) {
        Matrix in = input;
        
        for (Layer l: layers) {
            feedInput(in);
        }

        if (targetOutput != null) {
            backpropagate(targetOutput);
            double cost = costFunction.getCost(targetOutput, in, 10);
        }
    }


    /**
     * feedInput
     * 
     * Does the forward propagation, i.e., feeding input data
     * and turning it into an output part of training/evaluating
     * a neural network.
     * @param input (Matrix)
     */
    public void feedInput(Matrix input, boolean validation) {
        // System.out.println("Input: " + input);
        Matrix nextInput = input;
    
        int indeksi = 0;
        for (Layer l: layers) {
            l.evaluate(nextInput);
            nextInput = l.getActivation();
            indeksi++;
        }
    }

    public void feedInput(Matrix input) {
        feedInput(input, false);
    }



    public Matrix getError(Matrix target, Matrix output) {
        return Matrix.subtract(target, output);
    }

    public void backpropagate(Matrix target) {
        
        Layer l = this.getLastLayer();
        Matrix deltaCost = this.costFunction.getDerivative(target, l.getActivation());

        Matrix error = Matrix.multiply(l.actFnc.dActFunc(l.getInput()), deltaCost);
        Matrix gradient = l.actFnc.dActFunc(l.getActivation());
        gradient = Matrix.multiply(gradient, error);
            
        Matrix prevLayerActT = Matrix.transpose(l.getPrevLayer().getActivation());
        Matrix weights_delta = Matrix.multiply(gradient, prevLayerActT);


        l.addDeltaWeightsBiases(weights_delta, gradient);

        error = Matrix.multiply(Matrix.transpose(l.getWeights()), gradient);

        l = l.getPrevLayer();

        while (l.hasPrevLayer()) {

            gradient = l.actFnc.dActFunc(l.getActivation());
            gradient = Matrix.multiply(gradient, error);
            prevLayerActT = Matrix.transpose(l.getPrevLayer().getActivation());
            weights_delta = Matrix.multiply(gradient, prevLayerActT);
            
            l.addDeltaWeightsBiases(weights_delta, gradient);

            // Error is propagated through the network
            error = Matrix.multiply(Matrix.transpose(l.getWeights()), gradient);
            // System.out.println("Error: " + error);

            l = l.getPrevLayer();

            if (l.equals(this.getFirstLayer())) {
                break;
            }

            if (!l.hasPrevLayer()) {
                break;
            }

        }
    }

    public void feed_minibatch(List<Matrix> datasetInputs, List<Matrix> datasetOutputs) {
        for (int i = 0; i < datasetInputs.size(); i++) {
            Matrix in = (Matrix) datasetInputs.get(i);
            feedInput(in);
            backpropagate(datasetOutputs.get(i));
            learn();
        }
        
    }

    public void insertHomework(Matrix input, Matrix target) {
        this.feedInput(input);

        Matrix networkOutput = this.getLastLayer().getActivation();

        if (target != null) {
            backpropagate(target);
            double cost = this.costFunction.getCost(target, this.getLastLayer().getActivation(), this.minibatch_size);
            System.out.println("Cost: " + cost);
        }

    }

    public void learn() {
        for (Layer l: this.layers) {

            // if (!l.hasPrevLayer()) {
            //     continue;
            // } else {
            l.updateWeightsBiases();
            // }
        }

    }

    public void doLearn() {
        for (Layer l: this.layers) {
            if (!l.hasPrevLayer()) {
                continue;
            }

            if (l.deltaWeightsAdded > 0) {
                Matrix weight = l.getWeights();
                l.setWeights(l.opt.updateWeights(l.weights, l.deltaWeights, l.deltaWeightsAdded, l2, this.minibatch_size));

                
                l.resetDeltaWeights();
            }

            if (l.deltaBiasesAdded > 0) {
                l.setBias(l.opt.updateBias(l.bias, l.deltaBias, this.minibatch_size));
                l.resetDeltaBias();
            }
        }
    }

    public void learnFromDataset(int epochs, int mini_batch_size, double learning_rate, double lambda) {
        if (this.scaledImages == null) {
            try {
                this.readDatasets();
            } catch(Exception e) {
            }
        }
        this.learnFromDataset(this.scaledImages, epochs, mini_batch_size, learning_rate, this.labels_expanded_d, this.scaledImages_validation, this.labels_d_validation, lambda);

    }

    /**
     * isCorrectOutput
     * 
     * If the highest activation on the output layer is the same
     * as on the targetOutput, the nn has produced a correct result.
     * Returns true, if output == targetOutput, and otherwise false.
     * 
     * @param lastLayerActivation (Matrix)
     * @param targetOutput (Matrix)
     * @return boolean, true if output == targetoutput
     */
    public boolean isCorrectOutput(Matrix lastLayerActivation, Matrix targetOutput) {
        // Matrix lastAct = this.getLastLayer().getActivation();
        int resultAsDigit = (int) Matrix.getMatrixMax(lastLayerActivation);
        int correctResult = (int) Matrix.getMatrixMax(targetOutput);

        if (resultAsDigit == correctResult) {
            return true;
        }
        return false;
    }



    public void learnFromDataset(
        //List<double[]> input,
        double[][] input,
        int epochs,
        int mini_batch_size,
        double learning_rate,
        //List<Double> output,
        double[] output,
        double[][] testInput,
        double[] testOutput,
        double lambda
    ) {
        int n = input.length;

        // learn();

        int n_test = 0;
        if (output != null) {
            n_test = output.length;
        }

        List<Pair> training_data = getDataset(input, output, mini_batch_size);

        List<Pair> test_data = getDataset(testInput, testOutput, mini_batch_size);
       
        
        for (int i = 0; i < epochs; i++) {
           
            Collections.shuffle(training_data);
            int loops = training_data.size()/mini_batch_size;

            for (int j = 0; j+mini_batch_size < training_data.size()-1; j+=mini_batch_size) {
                List<Matrix> inputs = new ArrayList<>();
                List<Matrix> targetOutputs = new ArrayList<>();
                for (int minibatch = j; minibatch < j+mini_batch_size; minibatch++) {                    
                    inputs.add((Matrix) training_data.get(minibatch).getKey());
                    targetOutputs.add((Matrix) training_data.get(minibatch).getValue());
                }

                for (int k = 0; k < inputs.size(); k++) {
                    feedInput(inputs.get(k));

                    // Adjust weights, if incorrect
                    // if (!isCorrectOutput(this.getLastLayer().getActivation(), targetOutputs.get(k))) {
                    
                    backpropagationG(targetOutputs.get(k), this.getLastLayer().getActivation());

                        //System.out.println("Ei ollut oikein");
                    // backpropagate(targetOutputs.get(k));
                    // backpropagateVika(targetOutputs.get(k), this.getLastLayer().getActivation());
                    // this.backpropagateIm(targetOutputs.get(k), this.getLastLayer().getActivation());
                    // }
                    // this.backpropagate(targetOutputs.get(k));
                    //this.backpropagate(targetOutputs.get(k));
                    // backpropagateVika(targetOutputs.get(k), this.getLastLayer().getActivation());
                    double accuracy = this.getAccuracy(training_data);
                    if (this.trainingAccuracy.isEmpty()) {
                        this.trainingAccuracy.add(accuracy);
                    }
                    
                    if (accuracy < Collections.max(this.trainingAccuracy)-20) {
                        NeuralNetwork nn = loadNNFromJson();

                        this.layers = nn.getLayers();
                        this.costFunction = nn.costFunction;
                        this.l2 = nn.l2;
                        this.costFunction = nn.costFunction;
                        System.out.println(nn.layers.get(0).getWeights());
                        System.out.println("Tiedostosta luetun nn:n painot ^");
                        System.out.println("Yritti korvata!");
                    }

                    if (accuracy > Collections.max(this.trainingAccuracy)) {
                        System.out.println("Kirjoitti talteen");
                        toJsonNetwork(true);
                    }

                    this.trainingAccuracy.add(accuracy);
                }

                this.learn();

                System.out.println("------------------------");
                double evaluation_accuracy = getAccuracy2(testOutput, testInput);
                if (this.evaluationAccuracy.isEmpty()) {
                    this.evaluationAccuracy.add(0d);
                }

                if (evaluation_accuracy > Collections.max(this.evaluationAccuracy)) {
                    // System.out.println("jsoniksi?");
                    this.toJson(true);

                }

                if (evaluation_accuracy < Collections.max(this.evaluationAccuracy)-20) {
                    NeuralNetwork nn = loadNNFromJson();

                    this.layers = nn.getLayers();
                    this.costFunction = nn.costFunction;
                    this.l2 = nn.l2;
                    this.costFunction = nn.costFunction;
                    System.out.println(nn.layers.get(0).getWeights());
                    System.out.println("Tiedostosta luetun nn:n painot ^");
                    System.out.println("Yritti korvata!");

                }

           
            
                System.out.println("Evaluation accuracy: " + evaluation_accuracy + " / " + testOutput.length);
                System.out.println("------------------------");

                if (j + mini_batch_size > training_data.size()) {
                    break;
                }
            }
            double evaluation_accuracy = getAccuracy2(testOutput, testInput);//getAccuracy(test_data);
            System.out.println("Evaluation accuracy: " + evaluation_accuracy + " / " + testOutput.length);

            
            double training_cost = getTotalCost(training_data, lambda);
            this.trainingCost.add(training_cost);
            System.out.println("Cost on training data: " + training_cost + " epoch: " + i);

            System.out.println("Accuracy vanhalla tavalla");

            if (!this.evaluationAccuracy.isEmpty() && evaluation_accuracy > Collections.max(this.evaluationAccuracy)) {
                this.toJson(true);
            }
            this.evaluationAccuracy.add(evaluation_accuracy);
            System.out.println("Evaluation accuracy: " + evaluation_accuracy + " / " + test_data.size());
            
            // System.out.println("Evaluation accuracy");
            // System.out.println("Training cost: ");
            // this.trainingCost.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("evaluation cost: ");
            // this.evaluationCost.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("training accuracy: ");
            // this.trainingAccuracy.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("Evaluation accuracy");
            // this.evaluationAccuracy.stream().forEach(a -> System.out.print(a + ", "));
            ////////////////////// Olivat käytössä

            // if (test_data != null) {
            //     System.out.println("Do something");
            // }
        }       
    }

    public static NeuralNetwork fromFile() {
        return loadNNFromJson();
    }

    public void SGD(
        double[][] input,
        int epochs,
        int mini_batch_size,
        double learning_rate,
        //List<Double> output,
        double[] output,
        double[][] testInput,
        double[] testOutput,
        double lambda
    ) {
        
        int n = input.length;

        int n_test = 0;
        if (output != null) {
            n_test = output.length;
        }

        // minibatches training
        List<Pair> training_data = getDataset(input, output, mini_batch_size);

        List<Pair> test_data = getDataset(testInput, testOutput, mini_batch_size);
       
        
        for (int i = 0; i < epochs; i++) {
            // Randomize the order of the data
            Collections.shuffle(training_data);

            int loops = training_data.size()/mini_batch_size;
            for (int j = 0; j+mini_batch_size < training_data.size()-1; j+=mini_batch_size) {
                List<Matrix> inputs = new ArrayList<>();
                List<Matrix> targetOutputs = new ArrayList<>();
                for (int minibatch = j; minibatch < j+mini_batch_size; minibatch++) {
                    inputs.add((Matrix) training_data.get(minibatch).getKey());
                    targetOutputs.add((Matrix) training_data.get(minibatch).getValue());
                }
                update_mini_batch(inputs, 0.002, targetOutputs, lambda);
                if (j + mini_batch_size > training_data.size()) {
                    break;
                }
            }

            double training_cost = getTotalCost(training_data, lambda);
            this.trainingCost.add(training_cost);
            System.out.println("Cost on training data: " + training_cost + " epoch: " + i);
            
            System.out.println("Training accuracyn hakemista ennen");
            // System.out.println(training_data.get(0).getValue().toString());

            double training_accuracy = getAccuracy(training_data);
            this.trainingAccuracy.add(training_accuracy);
            System.out.println("Training accuracy on data: " + training_accuracy + " / " + training_data.size());

            double evaluationAccuracy = getAccuracy(test_data);
            System.out.println("Evaluation accuracy: " + evaluationAccuracy + " / " + test_data.size());

            double evaluation_cost = getTotalCost(test_data, lambda);
            // System.out.println("Evaluation cost: " + evaluation_cost);
            // this.evaluationCost.add(evaluation_cost);
            double evaluation_accuracy = getAccuracy(test_data);
            this.evaluationAccuracy.add(evaluation_accuracy);
            System.out.println("evaluation accuracy");
            System.out.println("Evaluation accuracy: " + evaluation_accuracy);
            // System.out.println("Evaluation accuracy");
            // System.out.println("Training cost: ");
            // this.trainingCost.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("evaluation cost: ");
            // this.evaluationCost.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("training accuracy: ");
            // this.trainingAccuracy.stream().forEach(a -> System.out.print(a + ", "));
            // System.out.println("");
            // System.out.println("Evaluation accuracy");
            // this.evaluationAccuracy.stream().forEach(a -> System.out.print(a + ", "));

            // if (test_data != null) {
            //     System.out.println("Do something");
            // }
        }
            
    }

    /**
     * palauttaa (nabla_b, nabla_w):n double[][] listana.
     * @param minibatch
     * @param learning_rate
     * @return
     */
    public void update_mini_batch(List<Matrix> input, double learningRate, List<Matrix> targetOutputs, double lambda) {

        List<Matrix> nabla_w = new ArrayList<>();
        List<Matrix> nabla_b = new ArrayList<>();

        for (int i = 1; i < this.layers.size(); i++) {
            Layer l = this.layers.get(i);

            if (l.equals(this.getFirstLayer())) {
                continue;
            }
            Matrix nabla_b_mat = l.getBias().copy();
            nabla_b_mat.fillWithZeros();

            Matrix nabla_w_mat = l.getWeights().copy();
            nabla_w_mat.fillWithZeros();


            nabla_b.add(nabla_b_mat);
            nabla_w.add(nabla_w_mat);
        }

        // for x,y in minibatch
        for (int i = 0; i < targetOutputs.size(); i++) {
            backpropagate(input.get(i), targetOutputs.get(i));

            for (int j = 0; j < nabla_w.size(); j++) {
                Matrix nabla_wPlusDeltaGradientWeights = nabla_w.get(j).addMatrix(deltaGradientWeights.get(j));
                nabla_w.set(j, nabla_wPlusDeltaGradientWeights);
                nabla_b.set(j, nabla_b.get(j).addMatrix(deltaGradientBiases.get(j)));
            }
        }

        for (int i = 0; i < nabla_w.size(); i++) {
            
            double division = learningRate / nabla_w.size();
            Matrix gradientMdivision = Matrix.scalarProduct(this.deltaGradientWeights.get(i), division);
            Matrix weight = Matrix.subtract(nabla_w.get(i), gradientMdivision);
            
            nabla_w.set(i, weight);
            
            // Bias
            division = learningRate / nabla_b.size();

            Matrix nabla_bMultiplied = Matrix.scalarProduct(nabla_b.get(i), division);
            Matrix bias_subtracted = Matrix.subtract(this.layers.get(i+1).getBias(), nabla_bMultiplied);
            Matrix.scalarProduct(this.deltaGradientBiases.get(i), division);
            Matrix bias = Matrix.subtract(nabla_b.get(i), gradientMdivision);
            nabla_b.set(i, bias_subtracted);
        }

        for (int i = 1; i < this.layers.size(); i++) {

            double learningRateLambda = (1.0-learningRate)*(lambda/(input.size()));
            Matrix weightsScaled = this.layers.get(i).getWeights().scalarProd(learningRateLambda);

            Matrix nabla_w_scaled = nabla_w.get(i-1).scalarProd(learningRate/10.0);

            Matrix weightsNablaDifference = Matrix.subtract(weightsScaled, nabla_w_scaled);
            this.layers.get(i).setWeights(weightsNablaDifference);
            Matrix nabla_b_scaled = nabla_b.get(i-1).scalarProd(learningRate/10.0);
            Matrix bias_nabla_b_difference = Matrix.subtract(layers.get(i).getBias(), nabla_b_scaled);
            this.layers.get(i).setBias(bias_nabla_b_difference);
        }
    }

    public List<Matrix> getInputMatrixList(List<double[]> inputs) {
        List<Matrix> inputMatrices = new ArrayList<>();
        
        for (int i = 0; i < inputs.size(); i++) {
            double[][] m = new double[784][1];

            double[] input_i = inputs.get(i);
            for (int j = 0; j < input_i.length; j++) {
                m[j][0] = input_i[j];
            }
            Matrix mMatrix = new Matrix(m);
            inputMatrices.add(mMatrix);
        }

        return inputMatrices;
    }

    public List<Matrix> getTargetOutputsList(List<double[]> targetOuts) {
        List<Matrix> targetOutputMatrixList = new ArrayList<>();
        for (int i = 0; i < targetOuts.size(); i++) {
            double[] targetOut = targetOuts.get(i);
            Matrix target = formatInput(targetOut);
            targetOutputMatrixList.add(target);
        }
        return targetOutputMatrixList;
    }

    public Matrix formatOutput(double value) {
        double[][] mat = new double[10][1];
        for (int i = 0; i < 10; i++) {
            if (value == i) {
                mat[i][0] = 1.0;
            } else {
                mat[i][0] = 0;
            }
        }
        Matrix m = new Matrix(mat);

        return m;
    }

    /**
     * formatInput
     * 
     * Formats the input array into a matrix, which
     * can be used in calculations.
     * @param input (double[] array)
     * @return Matrix, (784, 1)
     */
    public Matrix formatInput(double[] input) {
        double[][] mat = new double[input.length][1];

        for (int i = 0; i < 784; i++) {
            mat[i][0] = input[i];
        }
        return new Matrix(mat);
    }

    /**
     * feedForward
     * 
     * Feedforward only input
     */
    public void feedForward(Matrix input) {
        Matrix result = feedforward(input);
    }

    /**
     * feedForward
     * 
     * @param input list of Matrices
     * @return (Matrix) the output of the last layer
     */
    public Matrix feedforward(Matrix input) {
        this.getFirstLayer().setActivation(input);
        this.getFirstLayer().setInput(input);
        this.getFirstLayer().setActivation(input);
        for (int i = 1; i < this.layers.size(); i++) {
            
            Matrix z = Matrix.multiply(this.layers.get(i).getWeights(), this.layers.get(i).getPrevLayer().getActivation());
            z = z.addMatrix(this.layers.get(i).getBias());
            this.layers.get(i).setInput(z);
            this.layers.get(i).setActivation(this.layers.get(i).actFnc.calcActivation(z));
        }
        return this.getLastLayer().getActivation();
    }

    public void backpropagate(Matrix inputs, Matrix outputs) {
        List<Matrix> dGradientBiases = new ArrayList<>();
        List<Matrix> dGradientWeights = new ArrayList<>();

        // Create a list of matrices that match the
        // size of each layers weights and biases.
        // Filled with zeros.
        for (int i = 1; i < this.layers.size(); i++) {
            Matrix weightsM = this.layers.get(i).getWeights().copy();
            weightsM.fillWithZeros();

            Matrix biasesM = this.layers.get(i).getBias().copy();
            biasesM.fillWithZeros();

            dGradientWeights.add(weightsM);
            dGradientBiases.add(biasesM);
        }

        this.getFirstLayer().setActivation(inputs);
        this.getFirstLayer().setInput(inputs);

        for (int i = 1; i < this.layers.size(); i++) {
            Matrix z = Matrix.multiply(this.layers.get(i).getWeights(), this.layers.get(i).getPrevLayer().getActivation());
            z = z.addMatrix(this.layers.get(i).getBias());
            this.layers.get(i).setInput(z);
            this.layers.get(i).setActivation(this.layers.get(i).actFnc.sigmoid(z));
        }

        Matrix delta = this.costFunction.getDerivative(this.getLastLayer().getActivation(), outputs);
        delta = Matrix.dSigmoid(this.getLastLayer().getInput());

        dGradientBiases.set(dGradientBiases.size()-1, delta);
        this.getLastLayer().setDeltaBias(delta);

        Matrix ownAct = this.getLastLayer().getPrevLayer().getActivation();
        Matrix prevLayerActT = Matrix.transpose(ownAct);

        Matrix deltaWeight = Matrix.multiply(delta, prevLayerActT);

        this.getLastLayer().setDeltaWeights(deltaWeight);
        dGradientWeights.set(dGradientWeights.size()-1, deltaWeight);

        Layer l = this.getLastLayer().getPrevLayer();

        while (l.hasPrevLayer()) {
            Matrix z = l.getActivation();

            Matrix sp = l.actFnc.dActFunc(z);

            Matrix nextLayerWeights = l.getNextLayer().getWeights();
            Matrix nextLayerWeightsT = Matrix.transpose(nextLayerWeights);

            delta = Matrix.multiply(nextLayerWeightsT, delta);
            delta = Matrix.hadamardProduct(delta, sp);

            l.setDeltaBias(delta);

            ownAct = l.getPrevLayer().getActivation();
            prevLayerActT = Matrix.transpose(ownAct);
            Matrix dw = Matrix.multiply(delta, prevLayerActT);
            
            l.setDeltaWeights(dw);

            l = l.getPrevLayer();

            if (l.equals(this.getFirstLayer())) {
                break;
            }
        }

        List<Matrix> dWeights = new ArrayList<>();
        List<Matrix> dBiases = new ArrayList<>();

        for (int i = 1; i < this.layers.size(); i++) {
            dWeights.add(this.layers.get(i).getDeltaWeights());
            dBiases.add(this.layers.get(i).getDeltaBias());
        }

        this.deltaGradientBiases = dBiases;
        this.deltaGradientWeights = dWeights;
    }

    public double getTotalCost(List<Pair> data, double lambda) {
        double cost = 0.0;

        for (int i = 0; i < data.size(); i++) {
            Matrix input = (Matrix) data.get(i).getKey();
            Matrix output = (Matrix) data.get(i).getValue();

            Matrix result = feedforward(input);
            cost += this.costFunction.getCost(output, result, minibatch_size)/data.size();
            
            double sum_of_weights = 0.0;
            for (int j = 1; j < this.layers.size(); j++) {
                sum_of_weights += Math.pow(Matrix.frobeniusNorm(this.layers.get(j).getWeights()), 2);
            }
            cost+= 0.5*(lambda/data.size())*sum_of_weights;
        }
        return cost;

    }

    public double getAccuracy2(double[] output, double[][] input) {
        List<Pair<Double, Double>> results = new ArrayList<>();
        int correctResults = 0;

        for (int i = 0; i < output.length; i++) {
            Matrix tavoiteMatriisi = formatOutput(output[i]);
            Matrix syoteMatriisi = formatInput(input[i]);
            this.feedInput(syoteMatriisi);
            Matrix lastAct = this.getLastLayer().getActivation();
            int resultAsDigit = (int) Matrix.getMatrixMax(lastAct);
            int correctResult = (int) Matrix.getMatrixMax(tavoiteMatriisi);

            if (resultAsDigit == correctResult) {
                correctResults++;
            }
        }
        return correctResults;
    }

    public double getAccuracy(List<Pair> data) {
        List<Pair<Double, Double>> results = new ArrayList<>();
        int correctResults = 0;

        for (int i = 0; i < data.size(); i++) {
            Matrix input = (Matrix) data.get(i).getKey();

            Matrix expected = (Matrix) data.get(i).getValue();

            feedforward(input);

            Matrix result = this.getLastLayer().getActivation();

            int resultAsDigit = (int) Matrix.getMatrixMax(result);
            int correctResult = (int) Matrix.getMatrixMax(expected);

            if (resultAsDigit == correctResult) {
                correctResults++;
            }

            results.add(new Pair(resultAsDigit, correctResult));
            
        }
        return correctResults;

    }

    public Layer getLastLayer() {
        return this.layers.get(this.layers.size()-1);
    }

    public Layer getFirstLayer() {
        return this.layers.get(0);
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public static NeuralNetwork loadNNFromJson() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        try (FileReader reader = new FileReader("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network_state.json")) {
            Object obj = parser.parse(reader);
            NeuralNetwork nn = gson.fromJson(reader, NeuralNetwork.class);
            return nn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public NetworkState loadFromJson() {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        try (FileReader reader = new FileReader("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network_state.json")) {
            Object obj = parser.parse(reader);
            NetworkState n = gson.fromJson(reader, NeuralNetwork.NetworkState.class);
            return n;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public void toJsonNetwork(boolean pretty) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        if (pretty) gsonBuilder.setPrettyPrinting();

        try (FileWriter writer = new FileWriter("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network.json")) {
            gson.toJson(new NetworkState(this));
            gson.toJson(new NetworkState(this), new FileWriter("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network.json.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String toJson(boolean pretty) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        if (pretty) gsonBuilder.setPrettyPrinting();
        try (FileWriter writer = new FileWriter("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network_state.json")) {
            gson.toJson(new NetworkState(this));
            gson.toJson(new NetworkState(this), new FileWriter("/home/ari/ohjelmointi/tiralabraa/mnist_neuroneista/app/network_state.json"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gsonBuilder.create().toJson(new NetworkState(this));
    }

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

    public void readSmallerDatasets() throws Exception {
        double[][] kuvat = new double[60_000][784];
        double[] numero = new double[60_000];

        try (BufferedReader br = new BufferedReader(new FileReader("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/mnist_train.csv"))) {
            String line;
            int indeksi = 0;
            while ((line = br.readLine()) != null) {
                double[] kuva = new double[784];
                String[] values = line.split(",");

                if (indeksi != 0) {
                    // Stream to array but skip first, which is label
                    numero[indeksi] = Double.parseDouble(values[0]);
                    kuvat[indeksi] = Arrays.stream(values).skip(1).mapToDouble(Double::valueOf).map(a -> a/255.0).toArray();
                }

                indeksi++;
            }
        }

        this.scaledImages = kuvat;
        this.labels_expanded_d = numero;

        this.scaledImages_validation = new double[10_000][784];
        this.labels_d_validation = new double[10_000];

        double[][] kuvatTest = new double[10_000][784];
        double[] numeroTest = new double[10_000];

        try (BufferedReader br = new BufferedReader(new FileReader("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/mnist_test.csv"))) {
            String line;
            int indeksi = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                double[] kuva = new double[784];

                if (indeksi != 0) {
                    // Stream to array but skip first, which is label
                    numeroTest[indeksi] = Double.parseDouble(values[0]);
                    kuvatTest[indeksi] = Arrays.stream(values).skip(1).mapToDouble(Double::valueOf).map(a -> a/255.0).toArray();
                }
                indeksi++;
            }
        }

        this.scaledImages_validation = kuvatTest;
        this.labels_d_validation = numeroTest;
    }

    public void readDatasets() throws Exception {
        int[] labels = MnistReader.getLabels(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/MNIST/train-labels-idx1-ubyte.gz"));
        List<int[][]> images = MnistReader.getImages(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data//MNIST/train-images-idx3-ubyte.gz"));
        int[] labels_expanded = new int[labels.length*2];
        labels_expanded = fillFrom(labels, labels_expanded);
        
        double[] labels_d = Arrays.stream(labels).mapToDouble(Double::valueOf).toArray();
        this.scaledImages = new double[images.size()*2][];
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
        this.labels_expanded_d = Arrays.stream(labels_expanded).mapToDouble(Double::valueOf).toArray();

        System.out.println("labels expanded pituus lopussa: " + labels_expanded.length);

        // Test set
        int[] labels_validation = MnistReader.getLabels(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/MNIST/t10k-labels-idx1-ubyte.gz"));
        List<int[][]> images_validation = MnistReader.getImages(Paths.get("/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data//MNIST/t10k-images-idx3-ubyte.gz"));

        this.labels_d_validation = Arrays.stream(labels_validation).mapToDouble(Double::valueOf).toArray();
        this.scaledImages_validation = new double[images_validation.size()][];
        for (int i = 0; i < images_validation.size(); i++) {
            scaledImages_validation[i] = scale(flattenImages(images_validation.get(i)));
        }
        images_validation = null;
        labels_validation = null;
    }

    public static class NNBuilder {

        public final ArrayList<Layer> layers = new ArrayList<>();
        double l2;
        CostFunctions costFunction;
        private final int inputSize;
        Layer inputLayer;
        private final int layerSize;
        Layer outputLayer;
        Optimizer opt = new GradientDescent(0.003);
        Initializer init = new Initializer.Random(-0.5, 0.5);
        int minibatch_size;
        List<Matrix> deltaGradientWeights;
        List<Matrix> deltaGradientBiases;

        public NNBuilder(int inputSize) {
            this.inputSize = inputSize;
            this.layerSize = inputSize;
        }

        public NNBuilder(int minibatch_size, int input_size) {
            this.layerSize = input_size;
            this.minibatch_size = minibatch_size;
            this.inputSize = input_size;
        }

        public NNBuilder(int layerSize, int minibatch_size, int input_size) {
            this.layerSize = layerSize;
            this.minibatch_size = minibatch_size;
            this.inputSize = input_size;
            this.deltaGradientWeights = new ArrayList<>();
            this.deltaGradientBiases = new ArrayList<>();
        }


        public NNBuilder(NeuralNetwork state) {
            this.inputSize = state.inputSize;
            this.l2 = state.l2;
            this.layerSize = state.layerSize;
            this.opt = state.opt;
            this.minibatch_size = state.minibatch_size;
            this.costFunction = state.costFunction;

            List<Layer> stateLayers = state.getLayers();

            for (int i = 0; i < stateLayers.size(); i++) {
                Layer otherL = stateLayers.get(i);
                if (i == 0) {
                    Layer l = new Layer(
                        otherL.getSize(),
                        otherL.getActivationFunction(),
                        otherL.getInitialBias());
                    this.layers.add(l);
                } else {
                    Layer l = new Layer(
                        otherL.getSize(),
                        otherL.getActivationFunction(),
                        0.25
                    );

                    l.setPrevLayer(this.layers.get(i-1));

                    l.setWeights(otherL.getWeights());
                    
                    this.layers.add(l);


                }
            }
            init = (weights, layer) -> {
                Layer o = state.getLayers().get(layer+1);
                Matrix oWeights = o.getWeights();
                weights.fillFrom(oWeights);
            };
        }

        public NNBuilder initializeLayers() {
            for (int i = 0; i < this.layers.size(); i++) {
                Layer stateLayer = this.layers.get(i);

                if (i == 0) {
                    Layer l = new Layer(
                        stateLayer.getSize(),
                        new Identity(),
                        0.0
                    );
                    l.setZeroBias();
                    layers.add(l);
                } else {
                    // TODO: mahdollista myös muiden aktivaatiofunktioiden käyttö
                    //Object actFnc = stateLayer.getActivationFunction();
                    
                    
                    Layer l = new Layer(
                        stateLayer.getSize(),
                        new Sigmoid(),
                        new GradientDescent(0.002),
                        stateLayer.getInitialBias()
                    );
                    l.setPrevLayer(this.layers.get(i-1));
                    l.setInitialWeightsRand();

                    Matrix weights = stateLayer.getWeights();
                    l.setWeights(weights);

                    layers.add(l);

                }


            }
            return this;
        }

        public NNBuilder initWeights(Initializer initializer) {
            this.init = initializer;
            return this;
        }

        public NNBuilder setInitialWeights() {
            for (Layer l: this.layers) {
                l.setInitialWeightsRand();
            }
            return this;
        }

        public NNBuilder setInitialWeights(List<Layer> layers) {
            for (int i = 0; i < layers.size(); i++){
                this.layers.get(i).setWeights(layers.get(i).getWeights());
            }
            return this;
        }

        public NNBuilder setInitialWeights(double[][] layerWeights) {
            for (Layer l: this.layers) {
                l.setInitialWeightsRand();
            }
            return this;
        }

        public NNBuilder setInitialWeights(double[][][] layerWeights) {
            for (Layer l: this.layers) {
                for (int i = 0; i < layerWeights.length; i++) {
                    l.setWeights(new Matrix(layerWeights[i]));
                }
            }
            return this;
        }

        public NNBuilder setCostFunction(CostFunctions c) {
            this.costFunction = c;
            return this;
        }

        public NNBuilder setL2(double l2) {
            this.l2 = l2;
            return this;
        }

        public NNBuilder setOptimizer(Optimizer opt) {
            this.opt = opt;
            return this;
        }

        public NNBuilder addLayer(Layer l) {
            this.layers.add(l);
            if (this.layers.isEmpty()) {
                this.inputLayer = l;
            } else {
                this.layers.add(l);
                this.layers.get(this.layers.size()-1).setPrevLayer(this.layers.get(this.layers.size()-2));
                this.outputLayer = this.layers.get(this.layers.size()-1);
                l.setInitialWeightsRand();
            }

            return this;
        }

        public NeuralNetwork create() {
            return new NeuralNetwork(this);
        }

    }

    public static class NetworkState {
        String costFunction;
        Layer.LayerState[] layers;

        public NetworkState(NeuralNetwork network) {
            costFunction = network.costFunction.getName();
            layers = new Layer.LayerState[network.layers.size()];

            for (int i = 0; i < network.layers.size(); i++) {
                layers[i] = network.layers.get(i).getState();
            }
        }

        public Layer.LayerState[] getLayers() {
            return layers;
        }
    }

}
