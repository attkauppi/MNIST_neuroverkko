package neuroverkko.Neuroverkko;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;

import neuroverkko.Neuroverkko.Layer;
import neuroverkko.Neuroverkko.Initializer.Initializer;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;
import neuroverkko.Math.Optimizers.*;
import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Utils.RandomNumberGenerator;
import neuroverkko.Utils.Data.MNIST_reader.MNISTReader;
import neuroverkko.Utils.DataStructures.Map.*;

public class NeuralNetwork {

    public List<Layer> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer inputLayer;
    public Layer outputLayer;
    public double target;
    //public double lastOutputDer;
    // public Vector targetV;
    public Matrix targetM;
    public double l2;
    // public Vector outputV;
    public Matrix output;
    // public Vector errorV;
    public Matrix errorM;
    public CostFunctions costFunction;
    public int minibatch_size;
    public Optimizer opt;
    // public List<Double> test_data;
    // public List<double[]> training_data;
    public List<Matrix> deltaGradientWeights;
    public List<Matrix> deltaGradientBiases;
    public List<Double> trainingCost;
    public List<Double> evaluationCost;
    public List<Double> trainingAccuracy;
    public List<Double> evaluationAccuracy;

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
    }

    public void setLayers(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    public NeuralNetwork(NNBuilder nnb) {
        this.costFunction = nnb.costFunction;
        this.l2 = nnb.l2;
        this.opt = nnb.opt;
        this.layers = new ArrayList<>();
        this.inputSize = nnb.inputSize;

        for (int i = 0; i < nnb.layers.size(); i++) {
            if (i == 0) {
                Layer nnb_l = nnb.layers.get(i);
                // Layer l2 = new Layer(nnb_l.getSize());
                Layer l = new Layer(nnb_l.getSize());
                layers.add(l);
                this.inputLayer = l;
                // System.out.println("L: "+ l.toString());
            } else {
                Layer nnb_l = nnb.layers.get(i);
                Layer l = new Layer(nnb_l.getSize());
                l.setPrevLayer(this.layers.get(i-1));
                l.setInitialWeightsRand();
                l.setInitialBias(0.2);
                layers.add(l);
                
                if (i == nnb.layers.size()-1) {
                    this.outputLayer = l;
                }
                // System.out.println("L: "+ l.toString());
            }
        }



    }

    public void initializeLayers(List<Layer> stateLayers) {

        ArrayList<Layer> layers = new ArrayList<>();

        for (int i = 0; i < stateLayers.size(); i++) {
            //Layer l = new Layer(stateLayers.get(i).getSize(), stateLayers.get(i).getActivationFunction(), stateLayers.get(i).opt);
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
        // return layers;
    }

    public void setInitialWeights() {
        for (Layer l: this.layers) {
            l.setInitialWeightsRand();
        }
        // return this;
    }

    public void setCostFunction(CostFunctions c) {
        this.costFunction = c;
        // return this;
    }

    public void setL2(double l2) {
        this.l2 = l2;
        // return this;
    }

    public void setOptimizer(Optimizer opt) {
        this.opt = opt;
        // return this;
    }

    public void addLayer(Layer l) {

        //this.layers.add(l);
        if (this.layers.isEmpty()) {
            //this.layers.add(l);
            this.inputLayer = l;
            this.inputLayer.actFnc = l.getActivationFunction();
            this.layers.add(l);
            // TODO: periaatteessa myös output layer, jollei
            // muita tule, mutta ei taida viitsiä implementoida
        } else {
            l.actFnc = l.getActivationFunction();
            l.setPrevLayer(this.layers.get(this.layers.size()-1));
            this.layers.add(l);
            this.outputLayer = this.layers.get(this.layers.size()-1);
            l.setInitialWeightsRand();
        }

        // return this;
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

        // List<double[]> batch_images = new ArrayList<>();
        for (int i = 0; i < last_index; i++) {
            double[] image = new double[784];

            for (int j = 0; j < 784; j++) {
                image[j] = training_data.get(i)[j];
            }

            System.out.println("image: " + image.length);

            for (int j = 0; j < image.length; j++) {
                // System.out.println(image[j]);
            }
            int test_value = (int) Math.round(test_data.get(i));
            // for (int j = 0; j < 784; j++) {
            //     image[j] = images.get(i)[i][j];
            // }
            // Hashmapilla saat molemmat, tarkistusarvot sekä 
            // batch.put(labels[i], image);

            batch.put(i, image);
            double[] t_value = new double[1];
            t_value[0] = test_value;
            batch.put(-i, t_value);
            // batch_images.add(image);
        }

        // batch_images = null;

        //System.out.println("size: " + batch_images.size());
        //System.out.println("length: " + batch_images.get(0).length);
        
        return batch;
    }

    public void learn() {
        String LABEL_FILE = "/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/t10k-labels.idx1-ubyte";
		String IMAGE_FILE = "/home/ari/ohjelmointi/tiralabraa/uusi/app/src/main/java/neuroverkko/data/t10k-images.idx3-ubyte";

		int[] labels = MNISTReader.getLabels(LABEL_FILE);

        // System.out.println("labels: " + Arrays.toString(labels));
		List<int[][]> images = MNISTReader.getImages(IMAGE_FILE);
        for (int i = 0; i < images.size(); i++) {
            for (int j = 0; j < images.get(i).length; j++) {
                System.out.println(Arrays.toString(images.get(i)[j]));
            }
        }
        
        Map<Integer, int[]> batch = new HashMap<>();

    }

    // public void feed_input(List<double[]> training_data, int epochs, int mini_batch_size, double learning_rate, List<Double> test_data) {
    //     for (int i = 0; i < training_data.size(); i++) {
    //         List<Double> trainingData = 
    //     }
    // }

    public Pair getInputAndTargetMatrices(double[] mbatch, double targetOutput) {
        Matrix target = formatOutput(targetOutput);
        // System.out.println("Mbatch length");
        // System.out.println(mbatch.length);
        Matrix input = formatInput(mbatch);
        Pair p = new Pair(input, target);
        return p;
    }

    public List<Pair> getDataset(List<double[]> inputs, List<Double> outputs, int mini_batch_size) {
        int n = outputs.size();

        List<Pair> minibatches = new ArrayList<>(); 

        int k = 0;
        for (int i = 0; i < n; i++) {

            int index = 0;
            k += mini_batch_size;
            
            Pair<Matrix, Matrix> p = getInputAndTargetMatrices(inputs.get(i), outputs.get(i));
            minibatches.add(p);
        }

        System.out.println("Kaikki minibatchit haettu!");

        for (int i = 0; i < minibatches.size(); i++) {
            Matrix input = (Matrix) minibatches.get(i).getKey();
            Matrix output = (Matrix) minibatches.get(i).getValue();

            // System.out.println("Input koko: " + input.rows + ", " + input.cols);
            // System.out.println("ouput koko: " + output.rows + ", " + output.cols);
        }
        return minibatches;

    }

    // public void SGD(List<double[]> training_data, int epochs, int mini_batch_size, double learning_rate, List<Double> test_data) {
    public void SGD(
        List<double[]> input,
        int epochs,
        int mini_batch_size,
        double learning_rate,
        List<Double> output,
        List<double[]> testInput,
        List<Double> testOutput,
        double lambda
    ) {
        
        //System.out.println("SGD:n training_data: " + test_data.toString());
        // training_data = training_data;
        int n = input.size();

        // learn();

        int n_test = 0;
        if (!output.isEmpty()) {
            n_test = output.size();
        }

        // minibatches training
        List<Pair> training_data = getDataset(input, output, mini_batch_size);
        List<Pair> test_data = getDataset(testInput, testOutput, mini_batch_size);
       
        
        for (int i = 0; i < epochs; i++) {
            // Randomize the order of the data
            Collections.shuffle(training_data);

            System.out.println("Training data size: " + training_data.size());
            int loops = training_data.size()/mini_batch_size;
            System.out.println("loops: " + loops);
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
            
            double training_accuracy = getAccuracy(training_data);
            this.trainingAccuracy.add(training_accuracy);
            System.out.println("Training accuracy on data: " + training_accuracy);

            double evaluation_cost = getTotalCost(test_data, lambda);
            System.out.println("Evaluation cost: " + evaluation_cost);
            this.evaluationCost.add(evaluation_cost);
            double evaluation_accuracy = getAccuracy(test_data);
            this.evaluationAccuracy.add(evaluation_accuracy);
            System.out.println("evaluation accuracy");
            System.out.println("Evaluation accuracy: " + evaluation_accuracy);
            System.out.println("Evaluation accuracy");
            System.out.println("Training cost: ");
            this.trainingCost.stream().forEach(a -> System.out.print(a + ", "));
            System.out.println("");
            System.out.println("evaluation cost: ");
            this.evaluationCost.stream().forEach(a -> System.out.print(a + ", "));
            System.out.println("");
            System.out.println("training accuracy: ");
            this.trainingAccuracy.stream().forEach(a -> System.out.print(a + ", "));
            System.out.println("");
            System.out.println("Evaluation accuracy");
            this.evaluationAccuracy.stream().forEach(a -> System.out.print(a + ", "));

            // if (test_data != null) {
            //     System.out.println("Do something");
            // }
        }
            
    }

            // Go through each training example in batch


    /**
     * palauttaa (nabla_b, nabla_w):n double[][] listana.
     * @param minibatch
     * @param learning_rate
     * @return
     */
    public void update_mini_batch(List<Matrix> input, double learningRate, List<Matrix> targetOutputs, double lambda) {
        // represent gradients
        
        // List<Matrix> targets = targetOutputs;// getTargetOutputsList(targetOutput);
        // List<Matrix> inputs = input;// getInputMatrixList(minibatch);
        // System.out.println("targets: " + targets.toString());
        // System.out.println("Inputs: " + inputs.toString());
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
            // List<Matrix> delta_gradient_bias = this.deltaGradientBiases;
            // List<Matrix> delta_gradient_weight = this.deltaGradientWeights;
            // System.out.println("backpropagate loopissa");

            for (int j = 0; j < nabla_w.size(); j++) {
                Matrix nabla_wPlusDeltaGradientWeights = nabla_w.get(j).addMatrix(deltaGradientWeights.get(j));
                nabla_w.set(j, nabla_wPlusDeltaGradientWeights);
                nabla_b.set(j, nabla_b.get(j).addMatrix(deltaGradientBiases.get(j)));
                //nabla_b.set(j, deltabPlusDeltaGradientBias);
                // nabla_w.set(j, nabla_w.get(j).addMatrix(this.deltaGradientWeights.get(j))); 
                // nabla_b.set(j, nabla_b.get(j).addMatrix(this.deltaGradientBiases.get(j)));
            }
        }

        for (int i = 0; i < nabla_w.size(); i++) {
            // System.out.println("Päivittää painoja");
            // double scalars = (1.0-learningRate*(lambda/input.size()));
            // Matrix weightsScaled = Matrix.scalarProduct(this.layers.get(i+1).getWeights(), scalars);
            // Matrix nablaWScaled = Matrix.scalarProduct(nabla_w.get(i), (learningRate/input.size()));
            // Matrix difference = Matrix.subtract(weightsScaled, nablaWScaled);
            
            //Matrix weights = 
            // double division = (1.0-learningRate) * (lambda/nabla_w.size());
            // Matrix gradientMdivision = Matrix.scalarProduct(this.deltaGradientWeights.get(i), division);
            // Matrix weight = Matrix.subtract(nabla_w.get(i), gradientMdivision);

            
            // nabla_w.set(i, difference);
            
            // Bias
            // for (int i = 0; i < nabla_w.size(); i++) {
            double division = learningRate / nabla_w.size();
            Matrix gradientMdivision = Matrix.scalarProduct(this.deltaGradientWeights.get(i), division);
            Matrix weight = Matrix.subtract(nabla_w.get(i), gradientMdivision);
            
            nabla_w.set(i, weight);
            
            // Bias
            division = learningRate / nabla_b.size();
            // gradientMdivision = Matrix.scalarProduct(this.deltaGradientBiases.get(i), division);
            // Matrix bias = Matrix.subtract(nabla_b.get(i), gradientMdivision);
            // nabla_b.set(i, bias);
            // double division = learningRate / nabla_b.size();
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

            // 10.0 == learning rate
            Matrix nabla_b_scaled = nabla_b.get(i-1).scalarProd(learningRate/10.0);
            Matrix bias_nabla_b_difference = Matrix.subtract(layers.get(i).getBias(), nabla_b_scaled);
            this.layers.get(i).setBias(bias_nabla_b_difference);

            // this.layers.get(i).setWeights(nabla_w.get(i-1));
            // this.layers.get(i).setBias(nabla_b.get(i-1));

            // this.layers.get(i).setDeltaBias(this.layers.get(i).getBias());
            // this.layers.get(i).setDeltaWeights(this.layers.get(i).getWeights());
        }
        // System.out.println("Olisi pitänyt poistua update_mini_batchista");
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
        return new Matrix(mat);
    }

    public Matrix formatInput(double[] target) {
        double[][] mat = new double[target.length][1];

        for (int i = 0; i < 784; i++) {
            mat[i][0] = target[i];
        }
        return new Matrix(mat);
    }

    public Matrix feedforward(Matrix input) {
        this.getFirstLayer().setActivation(input);
        this.getFirstLayer().setInput(input);

        for (int i = 1; i < this.layers.size(); i++) {
            
            Matrix z = Matrix.multiply(this.layers.get(i).getWeights(), this.layers.get(i).getPrevLayer().getActivation());
            z = z.addMatrix(this.layers.get(i).getBias());
            System.out.println("z: " + z.toString());
            this.layers.get(i).setInput(z);
            this.layers.get(i).setActivation(this.layers.get(i).actFnc.sigmoid(z));

        }
        // this.getFirstLayer().setActivation(input);
        // this.getFirstLayer().setInput(input);

        // for (int i = 1; i < this.layers.size(); i++) {
        //     Matrix z = Matrix.multiply(this.layers.get(i).getWeights(), this.layers.get(i).getPrevLayer().getActivation());
        //     z = z.addMatrix(this.layers.get(i).getBias());
        //     this.layers.get(i).setInput(z);
        //     this.layers.get(i).setActivation(this.layers.get(i).actFnc.sigmoid(z));
        // }
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

        // Feed forward
        // System.out.println("Inputs: " + inputs.rows + ", " + inputs.cols);
        // System.out.println("Inputs: " + inputs.rows + ", " + inputs.cols);
        this.getFirstLayer().setActivation(inputs);
        this.getFirstLayer().setInput(inputs);

        for (int i = 1; i < this.layers.size(); i++) {
            Matrix z = Matrix.multiply(this.layers.get(i).getWeights(), this.layers.get(i).getPrevLayer().getActivation());
            z = z.addMatrix(this.layers.get(i).getBias());
            this.layers.get(i).setInput(z);
            this.layers.get(i).setActivation(this.layers.get(i).actFnc.sigmoid(z));
        }
        // Backward pass
        Matrix delta = this.costFunction.getDerivative(this.getLastLayer().getActivation(), outputs);
        delta = Matrix.dSigmoid(this.getLastLayer().getInput());

        dGradientBiases.set(dGradientBiases.size()-1, delta);
        this.getLastLayer().setDeltaBias(delta);

        Matrix prevLayerAct = this.getLastLayer().getPrevLayer().getActivation();
        Matrix prevLayerActT = Matrix.transpose(prevLayerAct);

        // System.out.println("Delta: " + delta.toString());
        // System.out.println("Delta rows: " + delta.rows + " cols: " + delta.cols);
        // System.out.println("prevLayerActT: " + prevLayerActT.rows + " cols " + prevLayerActT.cols);

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
            // System.out.println("SP: " + sp.rows + " cols: " + sp.cols);
            // System.out.println("Delta now: " + delta.rows + " cols: " + delta.cols);
            delta = Matrix.hadamardProduct(delta, sp);

            l.setDeltaBias(delta);

            prevLayerAct = l.getPrevLayer().getActivation();
            prevLayerActT = Matrix.transpose(prevLayerAct);
            Matrix dw = Matrix.multiply(delta, prevLayerActT);

            // System.out.println("dw: " + dw.rows + ", " + dw.cols);
            // System.out.println("l weights: " + l.weights.rows + ", " + l.weights.cols);
            
            l.setDeltaWeights(dw);

            l = l.getPrevLayer();

            if (l.equals(this.getFirstLayer())) {
                break;
            }
        }
        // System.out.println("Onnistui ajamaan backpropagaten kerran");

        List<Matrix> dWeights = new ArrayList<>();
        List<Matrix> dBiases = new ArrayList<>();

        for (int i = 1; i < this.layers.size(); i++) {
            dWeights.add(this.layers.get(i).getDeltaWeights());
            dBiases.add(this.layers.get(i).getDeltaBias());
        }

        this.deltaGradientBiases = dBiases;
        this.deltaGradientWeights = dWeights;
        // System.out.println("Sai suoritettua backpropagaation tähän mennessä");

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
                // System.out.println("Frobenius: " + this.layers.get(j).getWeights());
                sum_of_weights += Math.pow(Matrix.frobeniusNorm(this.layers.get(j).getWeights()), 2);
            }
            cost+= 0.5*(lambda/data.size())*sum_of_weights;
        }
        return cost;

    }

    public double getAccuracy(List<Pair> data) {
        List<Pair<Double, Double>> results = new ArrayList<>();
        int correctResults = 0;

        for (int i = 0; i < data.size(); i++) {
            Matrix input = (Matrix) data.get(i).getKey();
            //System.out.println("input: " + input.toString());
            Matrix output = (Matrix) data.get(i).getValue();

            System.out.println("Output" + output.toString());
            Matrix result = feedforward(input);
            System.out.println("Result: " + result.toString());
            int resultAsDigit = (int) Matrix.getMatrixMax(result);
            double correctResult = (int) Matrix.getMatrixMax(output);

            System.out.println("------------");
            System.out.println("results as digit: " + resultAsDigit);
            System.out.println("Correct digit: " + correctResult);

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

    public String toJson(boolean pretty) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (pretty) gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create().toJson(new NetworkState(this));
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
        //private Initializer initializer = new Initializer.Random(-0.5, 0.5);
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
                        //otherL.getInitialBias() != null ? RandomNumberGenerator.getRandom() : otherL.getInitialBias()
                    );

                    l.setPrevLayer(this.layers.get(i-1));

                    l.setWeights(otherL.getWeights());
                    
                    this.layers.add(l);


                }
            }
        }

        public NNBuilder initializeLayers() {

            //ArrayList<Layer> layers = new ArrayList<>();

            for (int i = 0; i < this.layers.size(); i++) {
                //Layer l = new Layer(stateLayers.get(i).getSize(), stateLayers.get(i).getActivationFunction(), stateLayers.get(i).opt);
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

                    //double learningRate = stateLayer.getOpt().getLearningRate();
                    l.setWeights(weights);

                    layers.add(l);

                }


            }
            return this;
        }


        public NNBuilder initializeLayers(List<Layer> stateLayers) {

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
            return this;
        }

        public NNBuilder initWeights(Initializer initializer) {
            this.setInitialWeights();
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
                //this.layers.add(l);
                this.inputLayer = l;
                // TODO: periaatteessa myös output layer, jollei
                // muita tule, mutta ei taida viitsiä implementoida
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
