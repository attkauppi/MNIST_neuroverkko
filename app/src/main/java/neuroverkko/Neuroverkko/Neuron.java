package neuroverkko.Neuroverkko;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;
import java.util.Random;
import neuroverkko.Math.*;

public class Neuron {

    ArrayList<Edge> inputs = new ArrayList<>();
    ArrayList<Edge> outputs = new ArrayList<>();
    HashMap<Integer, Double> outputWeight = new HashMap<>();

    public double input;
    public double output;
    public double bias;
    public double deltaBias;
    public double inputAfterActivation;
    public int name;

    public Neuron() {
        this.input = 0.0;
        this.output = 0.0;
        this.inputAfterActivation = 0.0;
    }

    public void setName(int value) {
        this.name = value;
    }

    public double getOutput() {
        return this.output;
    }

    public double getInput() {
        return this.input;
    }

    public int getInputSize() {
        return this.inputs.size();
    }

    public int getOutputsSize() {
        return this.outputs.size();
    }

    public void receiveOutput(double input) {
        this.input += input;

        if (this.inputs.stream().filter(i -> i.received != true).count() == 0) {
            evaluate();
        }
        
    }

    public void setInput(double input) {
        this.input = input;
    }

    public void evaluate() {
        // do something;
        this.setOutput(input + 0.3);
    }

    public void setOutput(double output) {
        this.output = output;
        for (Edge ed: this.outputs) {

        }

    }

    public void addInput(Neuron neuron) {
        Edge e = new Edge(neuron, this, Math.random());
        this.inputs.add(e);
        neuron.outputs.add(e);
    }

    public void addInput(Neuron neuron, double weight) {
        Edge e = new Edge(neuron, this, weight);
        this.inputs.add(e);
        neuron.outputs.add(e);
    }

    public void addOutput(Neuron neuron) {
        Edge e = new Edge(this, neuron, Math.random());
        this.outputs.add(e);
        neuron.inputs.add(e);
    }

    public void addOutput(Neuron neuron, double weight) {
        Edge e = new Edge(this, neuron, weight);
        this.outputs.add(e);
        neuron.inputs.add(e);
    }

    public void sendOutput() {
        for (Edge ed: outputs) {
            ed.receiveOutput();
        }
    }

    

    



}