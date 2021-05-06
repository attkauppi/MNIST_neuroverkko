package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import java.util.Arrays;

import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.*;
import neuroverkko.Math.CostFunctions.*;

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

        for (Layer3 ll: layers) {
            ll.propagateInput();
        }
    }

    public double calculateError(double target) {
        this.error = (this.getLastLayer().neurons.get(0).getOutput()-target);
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
        error.scalarProd(-1.0);
        return error;
    }

    public double getError() {
        return this.error;
    }

    // public void backpropagation_vika(Matrix targeMatrix) {
        
    // }

    public void backpropagateError() {
        System.out.println("Backpropagate\n");

        Layer3 l = this.getLastLayer();

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

        Matrix error = this.calculateError(target, l.getOutputMatrix());
        Matrix G = Matrix.dSigmoid(l.getOutputMatrix());// this.calculateError(target, l.getOutputMatrix());
        



        //Matrix S = Matrix.dSigmoid(l.getOutputMatrix());
        // Matrix delta = Matrix.multiply(error, G);
        // l.deltaBiasMatrix = delta;
        // l.deltaWeights = Matrix.hadamardProduct(delta, Matrix.transpose(l.getPrevLayer().getOutputMatrix()));
        //l.setDeltaWeightsMatrix(l.deltaWeights.addMatrix(l.get));
        // System.out.println("Delta weights: " + l.deltaWeights.toString());






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


            //// OLI TÄHÄN
            // System.out.println(l.toString());

            // Matrix nextLayerWeights = this.layers.get(layerIndex+1).getWeightsMatrix();
            // Matrix nextLayerWeightsT = Matrix.transpose(nextLayerWeights);

            
            // Matrix nextLayerDelta = this.layers.get(layerIndex+1).deltaWeights;

            // //System.out.println(nextLayerDelta.toString());

            // Matrix nextLayerWxDelta = Matrix.multiply(nextLayerWeightsT, nextLayerDelta);

            // // Matrix nextWeightsTxDelta = Matrix.multiply(nextLayerWeightsT, nextLayerWxDelta);


            // // prevOutput = this.layers.get(layerIndex-1).getOutputMatrix();
            // // prevOutput_forMult = new Matrix(prevOutput.getData());
            // // prevOutput.scalarSum(-1.0);

            // // this layer D
            // Matrix output = l.getOutputMatrix().copy();
            // System.out.println("output l: " + output.toString());

            // Matrix s_new = Matrix.dSigmoid(output).copy();

            // Matrix deltaWh = Matrix.hadamardProduct(nextLayerWxDelta, s_new);

            // l.setDeltaWeightsMatrix(deltaWh);
            /// OLI TÄHÄN

            
            layerIndex--;

            l = this.layers.get(layerIndex);
            System.out.println(l.name);


            if (!l.hasPreviousLayer()) {
                break;
            }

            
            // prevOutput = l.getPrevLayer().getOutputMatrix();

            // prevOutput_forMult = new Matrix(prevOutput.getData());

            // // dZ / dOutputOfPreviousLayer = dOutputOfPreviousLayer * (1.0 - dOutputOfPreviousLayer)
            // prevOutput.scalarSum(-1.0);

            // System.out.println("prevOutput: " + prevOutput_forMult.toString());
            // System.out.println("prevOut: " + prevOutput.toString());
            
            // S = Matrix.multiply(prevOutput_forMult, prevOutput);
            // //System.out.println("nextWeightsTxDelta: " + nextWeightsTxDelta.toString());

            // System.out.println("S: " + S.toString());

            // Matrix deltaw = Matrix.hadamardProduct(nextLayerWxDelta, S);

            //S = Matrix.multiply(prevOutput_forMult, prevOutput);



            //Matrix nextLayerError = 



            

            // Delta for the first hidden layer


            



            

            // l = l.getPrevLayer();

            // // layer l+1
            // Matrix nextWeights = l.getNextLayer().getWeightsMatrix();
            // System.out.println("nextweights: " + nextWeights.toString());
            
            // Matrix s = l.getNextLayer().
            // Vector s = nextWeights.elementProduct(l.getNextLayer().getDeltaWeights());
            // System.out.println("nextweights kertolaskun jälkeen " + nextWeights.toString());
            

            // // Layer l
            // dInputOutput = l.iaf.dActFunc(l.getOutputVector());//.getInputVector(), l.getOutputVector());

            // // layer l's dinputoutput (hadamard product) prevlayers vector s
            // Vector dinputoutputCopy = new Vector(dInputOutput.getData());
            
            // Vector Dl = s.vecElementProduct(dinputoutputCopy);
            
            // l.setDeltaWeights(Dl);
            // System.out.println(Dl.toString());
        }

    }

    public void backpropagateLinAlg(double[] target) {
        // Layer l = this.layers.get(this.layers.size()-2);
        // Layer l = this.getLastLayer();

        // Matrix mTarget = Matrix.fromArray(target);
        // Matrix mOutput = l.getOutputMatrix();
        
        // this.calculateError(new Vector(target));
        // Matrix error = calculateError(mTarget, mOutput);

        // Vector outputDerivaatta = l.iaf.dActFunc(l.getOutputVector());

        // Vector errorCopy = 

        // outputDerivaatta.scalarProd(this.error);

        
        // System.out.println("Output derivaatta: " + outputDerivaatta.toString());

        









       

        
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
        // Vector errorV = calculateError(new Vector(target));
        // System.out.println("this.error: " + errorV.toString());

        // mTarget = Matrix.fromArray(target);
        // mOutput = l.getOutputMatrix();

        // error = calculateError(mTarget, mOutput);
        // System.out.println("Error: " + error.toString());
        // Matrix gradient = calculateGradient(error);
        // gradient = Matrix.multiply(gradient, error);

        // Matrix hidden_t = Matrix.transpose(l.getPrevLayer().getWeightsMatrix());
        // System.out.println("Hidden_t: " + hidden_t.toString());
        // gradient.elementProduct(hidden_t);
        // Matrix weights_output_delta =  gradient.copy();
        // Matrix vikanPainot = l.getWeightsMatrix();
        
        // Matrix vikanpainotT = Matrix.transpose(vikanPainot);

        // Matrix gradient_vara = gradient.copy();
        // gradient_vara.elementProduct(vikanpainotT);
        // Matrix who_delta = gradient_vara.copy();

        // System.out.println("gradient_vara: " + gradient_vara.toString());

        //l.setWeightsFromMatrix(gradient_vara.getData());


        // System.out.println("Deltat: ");
        // System.out.println(who_delta.toString());

        // System.out.println("Vikandeltat: " + who_delta.rows + ", " + who_delta.cols);
        // System.out.println("vikanpainot: " + vikanPainot.rows + ", " + vikanPainot.cols);
        // System.out.println("who_delta: " + who_delta.toString());

        // //vikanPainot.addMatrix(vikandeltat);
        // //l.getWeightsMatrix().addMatrix(Matrix.transpose(who_delta));
        
        // //vikanPainot.add(vikandeltat);
        // System.out.println("vikanDeltat: " + who_delta.toString());



        // // Vikan virheet painojen suhteen
        // Matrix vikanPainot2 = l.getWeightsMatrix();
        // Matrix vikanPainot2T = Matrix.transpose(vikanPainot2);
        // // Hidden errors
        // Matrix hidden_errors = Matrix.multiply(vikanPainot2T, error);

        // hidden_errors.elementProduct(l.getWeightsMatrix());
        // //hidden_errors.add(l.getWeightsMatrix());

        

        // System.out.println("Hidden errors: " + hidden_errors.toString());

        // Layer prevLayer = l.getPrevLayer();
        // Matrix prevOutput = l.getOutputMatrix();
        // Matrix h_gradient = Matrix.dSigmoid(prevOutput);
        // h_gradient.matProduct(hidden_errors);






    




        //System.out.println("Output der: " + outputDerivative.toString());
    }

    public void backpropagateLinearAlgebra(Vector targetOutput) {
        // Layer l = this.getLastLayer();

        // Vector error = l.getOutputVector().vecSubtraction(targetOutput);
        // System.out.println("error: " + error.toString());

        // Vector gradient = l.iaf.dActFunc(l.getOutputVector());

        // // Osittaisderivaatat
        // gradient.vecElementProduct(error);
        // gradient.scalarProd(1.0);

        // // Matrix kerroksen painot
        // Matrix w_last = l.getWeightsMatrix();
        // Matrix w_lastT = Matrix.transpose(w_last);

        // Matrix grad = new Matrix(new double[gradient.getDimensions()][1]);

        // for (int i = 0; i < gradient.getDimensions(); i++) {
        //     grad.getData()[0][i] = gradient.getData()[i];
        // }

        // System.out.println("w_lastT: ");
        // System.out.println(w_lastT.toString());

        // System.out.println("Grad: ");
        // System.out.println(grad.toString());

        // //Matrix last_layer_weights_delta = grad.matProduct(w_lastT);

        

        // Matrix lastLayerWeightDeltas = Matrix.multiply(grad, Matrix.transpose(w_lastT));

        // System.out.println("wDelta:");
        // System.out.println(lastLayerWeightDeltas.toString());

        // System.out.println("Toisella tavalla: ");
        // //System.out.println(lastLayerWeightDeltas.toString());

        // Vector h0 = l.getPrevLayer().getOutputVector();
        
        // Matrix h0_m = new Matrix(new double[1][h0.getDimensions()]);
        // for (int i = 0; i < h0.getDimensions(); i++) {
        //     h0_m.getData()[0][i] = h0.getData()[i];
        // }


        // System.out.println("hidden layer output + last layer weight deltas: ");
        // h0_m.add(lastLayerWeightDeltas);
        // //lastLayerWeightDeltas.multiply(h0_m);

        // //h0.sumElements()

        // //Matrix w_delta = w_lastT.


        // // Gradient
        

        


    }







    
}
