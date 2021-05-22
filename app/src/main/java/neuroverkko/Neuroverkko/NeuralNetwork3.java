package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import java.util.Arrays;

import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;
/**
 * Ylimääräinen luokka, johon toimintoja toteutettu käyttäen yksittäisiä neuroneja.
 */
public class NeuralNetwork3 {

    public ArrayList<Layer3> layers;
    public int layerSize;
    public double error;
    public int inputSize;
    public Layer3 inputLayer;
    public Layer3 outputLayer;
    public double target;
    public double lastOutputDer;
    public Vector targetV;
    public Matrix targetM;
    public Vector outputV;
    public Matrix outputM;
    public Vector errorV;
    public Matrix errorM;
    public CostFunctions costFunction;


    public NeuralNetwork3(int inputSize) {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
        // Adds the input layer
        Identity i = new Identity();
        Layer3 l = new Layer3(inputSize, "in", new Identity());
        l.setBias(1.0);

        addLayer(l);
        this.error = 0.0;
    }

    public NeuralNetwork3() {
        this.layerSize = 1;
        this.layers = new ArrayList<>();
    }



    public void addLayer(Layer3 l) {
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

    public Layer3 getFirstLayer() {
        return this.layers.get(0);
    }

    public Layer3 getLastLayer() {
        return this.layers.get(this.layers.size()-1);
        // return this.outputLayer;
    }

    public void addLayer(ActivationFunction iaf, int neurons, double bias) {
        this.layerSize = this.layers.size();
        String lName = String.valueOf(layerSize);
        Layer3 l = new Layer3(neurons, lName);
        l.setBias(bias);
        l.setActivationFunction(iaf);
        addLayer(l);
        //this.outputLayer = this.layers.get(layerSize-1);
    }

    public void feedInput(double[] inputs) {
        System.out.println("inputs: " + Arrays.toString(inputs));

        Layer3 l = this.layers.get(0);

        // Gives the input to each neuron in the input layer
        // as an input
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

        for (Layer3 ll: layers) {
            ll.propagateInput();
        }
    }

    public double calculateError(double target, int neuronIndex) {
        this.error = (this.getLastLayer().neurons.get(neuronIndex).getOutput()-target);
        return this.error;
    }

    public double calculateError(double target, double output) {
        this.error = (this.getLastLayer().neurons.get(0).getOutput()-target);
        this.error = output - target;
        return this.error;
    }

    public Vector calculateError(Vector target) {
        Layer3 l = this.getLastLayer();
        Vector output = l.getOutputVector();
        Vector error = target.vecSubtraction(output);
        this.errorV = error;
        return error;
    }

    public Matrix calculateError(Matrix target, Matrix output) {
        Matrix error = Matrix.subtract(target, output);
        // error.scalarProd(-1.0);
        return error;
    }

    public double getError() {
        return this.error;
    }

    // public void backpropagation_vika(Matrix targeMatrix) {
        
    // }

    // TODO: siirrä oikeaan paikkaan
    public double getCost(double target, double output) {
        return 0.5*Math.pow((target-output), 2.0);
    }

    public void backpropagateError() {
        System.out.println("Backpropagate\n");

        Layer3 l = this.getLastLayer();

        System.out.println(this.getError());
    
        System.out.println("Viimeisen painot: ");
        System.out.println(l.getWeightsMatrix());


        while (l.hasPreviousLayer()) {
            
            
            for (Neuron n: l.neurons) {
                for (Edge ed: n.inputs) {
                 
                    System.out.println(ed.fromNeuron.name + " ---> " + ed.toNeuron.name);
                    if (!ed.toNeuron.hasOutputs()) {
                        double error = calculateError(target, ed.toNeuron.output);
                        // double error = ed.toNeuron.output*(1-ed.toNeuron.output);



                        // dE/dO
                        this.lastOutputDer = ed.toNeuron.output*(1.0-ed.toNeuron.output);


                        System.out.println("korjattu paino: " + ed.calculateNewWeight(this.error, ed.toNeuron.output, this.lastOutputDer));
                    } else {
                        System.out.println("korjattu paino: " + ed.calculateNewWeight(this.error, ed.toNeuron.output));
                    }        
                }
            }

            l = l.getPrevLayer();
        }
    }

    public void train(double[] inputs, double[] target) {
        this.feedInput(inputs);
        this.targetV = new Vector(target);
        this.targetM = Matrix.fromArray(target);

        backpropagation(targetM, targetV);

        for (int i = 0; i < this.layers.size(); i++) {
            System.out.println(this.layers.get(i).toString());
            System.out.println("Päivitettäessä painot: " + this.layers.get(i).weights.toString());
            System.out.println("Päivitettäessä deltapainot: " + this.layers.get(i).deltaWeights.toString());
            this.layers.get(i).updateWeightsFromDeltaM();
            System.out.println("päivityksen jälkeen painot: " + this.layers.get(i).weights.toString());
            System.out.println("");
        }
        
        // backpropagateLinAlg(target);
        
    }

    public Matrix calculateGradient(Matrix error) {
        Matrix output = this.getLastLayer().getOutputMatrix();

        Matrix gradient = Matrix.dSigmoid(output);
        gradient.matProduct(error);
        return gradient;
    }

    public void updateFromTraining() {
        for (Layer3 l: this.layers) {
            if (l.hasPreviousLayer()) {
                l.updateFromLearning();
                //l.updateWeights(l.getWeightsMatrix(), l.deltaWeights);
                //l.updateWeights(l.getWeightsMatrix(), l.deltaWeights);
            }
        }
    }

    public void backpropagation(Matrix target, Vector targetV) {
        Layer3 l = this.getLastLayer();
        System.out.println(l.toString());

        int layerIndex = this.layers.size()-1;

        Matrix prevLayerOutputs = l.getPrevLayer().getOutputMatrix().copy();
        //Matrix outputs Matrix.transpose(prevLayerOutputs)

        Matrix delta = this.costFunction.getDerivative(target, l.getOutputMatrix());

        System.out.println(delta.toString());
        l.deltaBiasMatrix = delta;
        l.getOutputMatrix();
        Matrix deltaw = Matrix.multiply(delta, Matrix.transpose(l.getPrevLayer().getOutputMatrix()));///.scalarProd(delta[0][0]));
        
        
        System.out.println("Deltaw välissä: " + deltaw.toString());
        Matrix sp = Matrix.dSigmoid(l.getInputsMat());
        deltaw = Matrix.multiply(deltaw, sp);
        l.deltaBiasMatrix = deltaw;

        System.out.println("Deltaw nyt: " + deltaw.toString());

        Matrix deltaw_oikeasti = Matrix.multiply(deltaw, Matrix.transpose(l.getPrevLayer().getOutputMatrix()));
        l.deltaWeights = deltaw_oikeasti;
        
        System.out.println("deltaw oikeasti: " + deltaw_oikeasti.toString());

        System.out.println("prev layer activations: "  + l.getPrevLayer().getOutputMatrix().toString());
        System.out.println("deltaW: " + deltaw.toString());

        System.out.println("---------------");



        l.deltaBiasMatrix = delta;
        // l.setDeltaWeightsMatrix(deltaw.addMatrix(l.getWeightsMatrix()));

        Matrix delta_copy = delta.copy();

        // Matrix deltaWe = delta.scalarProd(l.getPrevLayer().getl .getPrevLayer().getOutputMatrix());
        // System.out.println(deltaWe.toString());

        System.out.println("Delta");
        System.out.println(delta.toString());

        Matrix errorN = this.calculateError(target, l.getOutputMatrix());
        Matrix G = Matrix.dSigmoid(l.getOutputMatrix());// this.calculateError(target, l.getOutputMatrix());
        


        // // Derivative of the cost function for outputs
        // // and target
        // Matrix lWeights = l.getOutputMatrix();
        // Matrix lWeightsT = Matrix.transpose(lWeights);

        // Matrix G = this.costFunction.getDerivative(targetM, lWeightsT);
        // Vector doutputtarget = this.costFunction.getDerivative(targetV, l.getOutputVector());

        // // Derivative of the activation function with respect
        // // inputs -- this is how it's calculated only
        // // in the output layer.
        // //Vector dInputOutput = l.iaf.calc_dCostdInput(l.getOutputVector(), l.getInputVector());

        // Matrix prevOutput = l.getPrevLayer().getOutputMatrix();

        // Matrix prevOutput_forMult = new Matrix(prevOutput.getData());

        // // dZ / dOutputOfPreviousLayer = dOutputOfPreviousLayer * (1.0 - dOutputOfPreviousLayer)
        // prevOutput.scalarSum(-1.0);
        // //Matrix S = Matrix.multiply(prevOutput_forMult, prevOutput);
        // Matrix S = prevOutput_forMult.matProduct(prevOutput).copy();

        // // Delta of output
        // Matrix Dl = Matrix.hadamardProduct(G, S);

        // l.setDeltaWeightsMatrix(Dl);

        layerIndex--; 



        
        

        // Vector dInputOutput = l.iaf.dActFunc(l.getOutputVector());//l.iaf.dSigmoid()  l.iaf.calc_dCostdInput(output, dCostdOutput)

        // // Just to keep the doutputtarget intact.
        // Vector doutputtarget_copy = new Vector(doutputtarget.getData());

        // // Delta of the output neuron:
        // doutputtarget_copy.vecElementProduct(dInputOutput);

        // l.setDeltaWeights(doutputtarget);


        //l = l.getPrevLayer();


        while (l.hasPreviousLayer()) {

           
            
            l = this.layers.get(layerIndex);

            Matrix activation = l.getInputsMat();
            sp = Matrix.dSigmoid(activation);
            System.out.println(sp.toString());
            Matrix previous_layer_weights = l.getNextLayer().getWeightsMatrix();
            System.out.println("previous weights shape: " + previous_layer_weights.toString());
            Matrix delta_before_sp = Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeightsMatrix()), delta);
            delta = Matrix.multiply(delta_before_sp, sp);
            l.deltaBiasMatrix = delta;

            Matrix delta_weight = Matrix.multiply(delta, Matrix.transpose(l.getPrevLayer().getOutputMatrix()));
            System.out.println("Delta weight");
            System.out.println(delta_weight.toString());

            Matrix changed_weights = delta_weight.addMatrix(l.getWeightsMatrix());
            l.setDeltaWeightsMatrix(changed_weights);
            
            layerIndex--;

            l = this.layers.get(layerIndex);
            System.out.println(l.name);


            if (!l.hasPreviousLayer()) {
                break;
            }
        }

    }
}
