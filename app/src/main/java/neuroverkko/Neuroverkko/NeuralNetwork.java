package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import java.util.Arrays;

import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;

public class NeuralNetwork {

    public ArrayList<Layer> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer inputLayer;
    public Layer outputLayer;
    public double target;
    public double lastOutputDer;
    public Vector targetV;
    public Matrix targetM;
    public Vector outputV;
    public Matrix outputM;
    public Vector errorV;
    public Matrix errorM;
    public CostFunctions costFunction;


    public NeuralNetwork(int inputSize) {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
        // Adds the input layer
        Identity i = new Identity();
        Layer l = new Layer(inputSize, "in", new Identity());
        l.setBias(1.0);

        addLayer(l);
        this.error = 0.0;
    }

    public NeuralNetwork() {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
    }



    public void addLayer(Layer l) {
        this.layers.add(l);
        this.layerSize = this.layers.size();

        // TODO: laita takaisin päälle
        // if (this.layers.size() >= 2) {
        //     this.layers.get(layerSize-2).setNextLayer(getLastLayer());
        // }
    }

    public void setCostFunction(CostFunctions costFunction) {
        this.costFunction = costFunction;
    }

    public Layer getFirstLayer() {
        return this.layers.get(0);
    }

    public Layer getLastLayer() {
        return this.layers.get(this.layers.size()-1);
        // return this.outputLayer;
    }

    public void addLayer(ActivationFunction iaf, int neurons, double bias) {
        this.layerSize = this.layers.size();
        String lName = String.valueOf(layerSize);
        Layer l = new Layer(neurons, lName);
        l.setBias(bias);
        l.setActivationFunction(iaf);
        addLayer(l);
        //this.outputLayer = this.layers.get(layerSize-1);
    }

    public void feedInput(double[] inputs) {
        System.out.println("inputs: " + Arrays.toString(inputs));

        Layer l = this.layers.get(0);

        // Gives the input to each neuron as a
        // an input.
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("input: " + inputs[i]);

            l.neurons.get(i).setInput(inputs[i]);
            //l.neurons.get(i).evaluate();
            l.neurons.get(i).setOutput(inputs[i]);
            //l.neurons.get(i).sendOutput();
            
            //l.neurons.get(i).setOutput(inputs[i]);
            //l.neurons.get(i).sendOutput();
            //System.out.println("Neuronin input: " + l.neurons.get(i).getInput());
        }

        for (Layer ll: layers) {
            ll.propagateInput();
        }
    }

    public double calculateError(double target) {
        this.error = (this.getLastLayer().neurons.get(0).getOutput()-target);
        return this.error;
    }

    public Vector calculateError(Vector target) {
        Layer l = this.getLastLayer();
        Vector output = l.getOutputVector();
        Vector error = target.vecSubtraction(output);
        this.errorV = error;
        return error;
    }

    public Matrix calculateError(Matrix target, Matrix output) {
        Matrix error = Matrix.subtract(target, output);
        error.scalarProd(-1.0);
        return error;
    }

    public double getError() {
        return this.error;
    }

    public void backpropagateError() {
        System.out.println("Backpropagate\n");

        Layer l = this.getLastLayer();

        System.out.println(this.getError());
    
        System.out.println("Viimeisen painot: ");
        System.out.println(l.getWeightsMatrix());


        while (l.hasPreviousLayer()) {
            
            
            for (Neuron n: l.neurons) {
                for (Edge ed: n.inputs) {
                    // endOutputSigmoid = 0.0;
                    System.out.println(ed.fromNeuron.name + " ---> " + ed.toNeuron.name);
                    if (!ed.toNeuron.hasOutputs()) {
                        this.lastOutputDer = ed.toNeuron.output*(1.0-ed.toNeuron.output);
                        System.out.println("korjattu paino: " + ed.calculateNewWeight(this.error, ed.toNeuron.output, this.lastOutputDer));
                    } else {
                        System.out.println("korjattu paino: " + ed.calculateNewWeight(this.error, ed.toNeuron.output));
                    }
                    
                    
                    //if (ed.toNeuron.hasOutputs()) {
                    //ed.calculateNewWeight(this.error, ed.toNeuron.output);
                        
                    //}
                    
                }
            }

            l = l.getPrevLayer();
        }
    }

    public void train(double[] inputs, double[] target) {
        this.feedInput(inputs);
        // Hoitaa inputin syöttämisen sekä forward propagoinnin
        this.targetV = new Vector(target);
        this.targetM = Matrix.fromArray(target);
        
        backpropagateLinAlg(target);
        
    }

    public Matrix calculateGradient(Matrix error) {
        Matrix output = this.getLastLayer().getOutputMatrix();

        Matrix gradient = Matrix.dSigmoid(output);
        gradient.matProduct(error);
        return gradient;
    }

    public void updateFromTraining() {
        for (Layer l: this.layers) {
            if (l.hasPreviousLayer()) {
                l.updateFromLearning();
            }
        }
    }

    public void backpropagateLinAlg(double[] target) {
        // Layer l = this.layers.get(this.layers.size()-2);
        Layer l = this.getLastLayer();

        Matrix mTarget = Matrix.fromArray(target);
        Matrix mOutput = l.getOutputMatrix();
        
        this.calculateError(new Vector(target));
        Matrix error = calculateError(mTarget, mOutput);

        Vector outputDerivaatta = l.iaf.dActFunc(l.getOutputVector());

        Vector errorCopy = 

        outputDerivaatta.scalarProd(this.error);

        
        System.out.println("Output derivaatta: " + outputDerivaatta.toString());

        









       

        
        //     l.weights = l.getOutputMatrix();



        //     Vector errorV = calculateError(new Vector(target));
        //     System.out.println("this.error: " + errorV.toString());

        //     Matrix mTarget = Matrix.fromArray(target);
        //     Matrix mOutput = l.getOutputMatrix();

        //     Matrix error = calculateError(mTarget, mOutput);
        //     System.out.println("Error: " + error.toString());
        //     Matrix gradient = calculateGradient(error);
        //     gradient = Matrix.multiply(gradient, error);

        //     Matrix hidden_t = Matrix.transpose(l.getPrevLayer().getWeightsMatrix());
        //     System.out.println("Hidden_t: " + hidden_t.toString());
        //     gradient.elementProduct(hidden_t);
        //     Matrix weights_output_delta =  gradient.copy();

        //     l.updateWeights(weights_output_delta, errorM);
        //     System.out.println("l weights: " + l.weights.toString());



        






        //System.out.println("Gradient: " + gradient.toString());

        // while (l.hasPreviousLayer()) {
        //     if (l.weights == null) {
        //         l.weights = l.getWeightsMatrix();
        //     }
        //     Vector dcdo = this.costFunction.getDerivative(new Vector(target), l.getOutputVector());
        //     Vector dcdi = l.iaf.calc_dCostdInput(l.getOutputVector(), dcdo); //this.costFunction.getDerivative(new Vector(target), l.getOutputVector());
        //     System.out.println("error change: " + dcdo.toString());

        //     Matrix dcdw = dcdi.outerProdut(l.getNextLayer().getOutputVector());
            
        //     //Vector dCdI = l.iaf.dActFunc(l.getOutputVector(), errorChangeVsOutputChange);
        //     //Vector dCdI = l.iaf.calc_dCostdInput(l.getOutputVector(), errorChangeVsOutputChange);
        //     //System.out.println("output derivative: " + dcdi.toString());

        //     System.out.println("dCdI: " + dcdi.toString());
        //     System.out.println("dcwd: " + dcdw.toString());

            

        //     //Matrix dCdW = dCdI.outerProdut(l.getPrevLayer().getOutputVector());
        //     //System.out.println("dcdw: " + dCdW.toString());

        //     l.setDeltaWeightDeltaBias(dcdw, dcdi);

        //     dcdo = l.getWeightsMatrix().elementProduct(dcdi);
        //     //l.deltaWeights = dCdW;
        //     //System.out.println("Hadamarin jälkeen: " + errorChangeVsOutputChange.toString());

        //     l = l.getPrevLayer();
        // }


    
        
        




        //System.out.println("outputDerivative: " + outputDerivative.toString());

        //Lasketaan korjaus ekoihin kaariin
        Vector errorV = calculateError(new Vector(target));
        System.out.println("this.error: " + errorV.toString());

        mTarget = Matrix.fromArray(target);
        mOutput = l.getOutputMatrix();

        error = calculateError(mTarget, mOutput);
        System.out.println("Error: " + error.toString());
        Matrix gradient = calculateGradient(error);
        gradient = Matrix.multiply(gradient, error);

        Matrix hidden_t = Matrix.transpose(l.getPrevLayer().getWeightsMatrix());
        System.out.println("Hidden_t: " + hidden_t.toString());
        gradient.elementProduct(hidden_t);
        Matrix weights_output_delta =  gradient.copy();
        Matrix vikanPainot = l.getWeightsMatrix();
        
        Matrix vikanpainotT = Matrix.transpose(vikanPainot);

        Matrix gradient_vara = gradient.copy();
        gradient_vara.elementProduct(vikanpainotT);
        Matrix who_delta = gradient_vara.copy();

        System.out.println("gradient_vara: " + gradient_vara.toString());

        //l.setWeightsFromMatrix(gradient_vara.getData());


        System.out.println("Deltat: ");
        System.out.println(who_delta.toString());

        System.out.println("Vikandeltat: " + who_delta.rows + ", " + who_delta.cols);
        System.out.println("vikanpainot: " + vikanPainot.rows + ", " + vikanPainot.cols);
        System.out.println("who_delta: " + who_delta.toString());

        //vikanPainot.addMatrix(vikandeltat);
        //l.getWeightsMatrix().addMatrix(Matrix.transpose(who_delta));
        
        //vikanPainot.add(vikandeltat);
        System.out.println("vikanDeltat: " + who_delta.toString());



        // Vikan virheet painojen suhteen
        Matrix vikanPainot2 = l.getWeightsMatrix();
        Matrix vikanPainot2T = Matrix.transpose(vikanPainot2);
        // Hidden errors
        Matrix hidden_errors = Matrix.multiply(vikanPainot2T, error);

        hidden_errors.elementProduct(l.getWeightsMatrix());
        //hidden_errors.add(l.getWeightsMatrix());

        

        System.out.println("Hidden errors: " + hidden_errors.toString());

        Layer prevLayer = l.getPrevLayer();
        Matrix prevOutput = l.getOutputMatrix();
        Matrix h_gradient = Matrix.dSigmoid(prevOutput);
        h_gradient.matProduct(hidden_errors);






    




        //System.out.println("Output der: " + outputDerivative.toString());
    }

    public void backpropagateLinearAlgebra(Vector targetOutput) {
        Layer l = this.getLastLayer();

        Vector error = l.getOutputVector().vecSubtraction(targetOutput);
        System.out.println("error: " + error.toString());

        Vector gradient = l.iaf.dActFunc(l.getOutputVector());

        // Osittaisderivaatat
        gradient.vecElementProduct(error);
        gradient.scalarProd(1.0);

        // Matrix kerroksen painot
        Matrix w_last = l.getWeightsMatrix();
        Matrix w_lastT = Matrix.transpose(w_last);

        Matrix grad = new Matrix(new double[gradient.getDimensions()][1]);

        for (int i = 0; i < gradient.getDimensions(); i++) {
            grad.getData()[0][i] = gradient.getData()[i];
        }

        System.out.println("w_lastT: ");
        System.out.println(w_lastT.toString());

        System.out.println("Grad: ");
        System.out.println(grad.toString());

        //Matrix last_layer_weights_delta = grad.matProduct(w_lastT);

        

        Matrix lastLayerWeightDeltas = Matrix.multiply(grad, Matrix.transpose(w_lastT));

        System.out.println("wDelta:");
        System.out.println(lastLayerWeightDeltas.toString());

        System.out.println("Toisella tavalla: ");
        //System.out.println(lastLayerWeightDeltas.toString());

        Vector h0 = l.getPrevLayer().getOutputVector();
        
        Matrix h0_m = new Matrix(new double[1][h0.getDimensions()]);
        for (int i = 0; i < h0.getDimensions(); i++) {
            h0_m.getData()[0][i] = h0.getData()[i];
        }


        System.out.println("hidden layer output + last layer weight deltas: ");
        h0_m.add(lastLayerWeightDeltas);
        //lastLayerWeightDeltas.multiply(h0_m);

        //h0.sumElements()

        //Matrix w_delta = w_lastT.


        // Gradient
        

        


    }







    
}
