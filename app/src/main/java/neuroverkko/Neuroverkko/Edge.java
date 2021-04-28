package neuroverkko.Neuroverkko;

/**
 * Edges connect neurons between layers
 */
public class Edge {

    public Neuron fromNeuron;
    public Neuron toNeuron;
    public double weight;
    public double deltaWeight;
    public boolean received;

    public Edge(Neuron fromNeuron, Neuron toNeuron, double weight) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = weight;
        this.received = false;
    }

    public Edge(Neuron fromNeuron, Neuron toNeuron) {
        this.fromNeuron = fromNeuron;
        this.toNeuron = toNeuron;
        this.weight = Math.random();
        this.received = false;
    }

    public Neuron getFromNeuron() {
        return this.fromNeuron;
    }

    public Neuron getToNeuron() {
        return this.toNeuron;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double w) {
        this.weight = w;
    }

    public double getWeightedInput() {
        return this.fromNeuron.getOutput()*this.weight;
    }

    public void receiveOutput() {
        this.toNeuron.receiveOutput(this.fromNeuron.getOutput()*this.weight);
        this.received = true;
    }

    public boolean getReceived() {
        return this.received;
    }




    
}
