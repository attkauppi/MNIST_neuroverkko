package neuroverkko.Neuroverkko;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import neuroverkko.Math.ActivationFunctions.Identity;
import neuroverkko.Math.ActivationFunctions.Sigmoid;
import neuroverkko.Math.CostFunctions.MSE;
import neuroverkko.Math.CostFunctions.Quadratic;
import neuroverkko.Math.Optimizers.GradientDescent;
import neuroverkko.Math.*;
import neuroverkko.Neuroverkko.NeuralNetwork;

public class NeuralNetworkTest {

	NeuralNetwork n;
	Layer input;
	Layer hidden;
	Layer output;

	private Layer input2;
	private Layer hidden2;
	private Layer output2;
	NeuralNetwork n2;



	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.n = new NeuralNetwork(3, 10, 784);
		ArrayList<Layer> layers = new ArrayList<>();
        this.input = new Layer(784, new Identity(), 0.0);
        this.hidden = new Layer(30, new Sigmoid(), 0.20);
        this.output = new Layer(10, new Sigmoid(), 0.25);
		
		this.input = input;

		hidden.setPrevLayer(input);
        output.setPrevLayer(hidden);
        this.n.addLayer(input);
        this.n.addLayer(hidden);
        this.n.addLayer(output);

		for (int i = 1; i < this.n.layers.size(); i++) {
            this.n.layers.get(i).setInitialWeightsRand();
            this.n.layers.get(i).setInitialBias(0.2);
        }
		this.n.setCostFunction(new MSE());
        this.n.setOptimizer(new GradientDescent(0.01));
        this.n.setL2(0.0002);

		this.input2 = new Layer(2, new Identity(), 0.0);
        // this.hidden2 = new Layer(3, new Sigmoid(), 0.20);
        // this.output2 = new Layer(1, new Sigmoid(), 0.25);
		
        this.hidden2 = new Layer(3, new Sigmoid(), new GradientDescent(0.03), 0.20);
        this.output2 = new Layer(1, new Sigmoid(), new GradientDescent(0.02), 0.25);

		this.hidden2.setPrevLayer(input2);
		this.output2.setPrevLayer(hidden2);

		this.hidden2.setInitialWeights(new Matrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}}));
		this.hidden2.setInitialBias(0.2);
		this.output2.setInitialWeights(new Matrix(new double[][] {{0.11, 0.12, 0.13}}));
		this.output2.setInitialBias(0.25);
		
		this.n2 = new NeuralNetwork(2,3,1);
		this.n2.setCostFunction(new Quadratic());
		this.n2.addLayer(input2);
		this.n2.addLayer(hidden2);
		this.n2.addLayer(output2);
    }

	@After
    public void tearDown() {

	}

	@Test
	public void testFeedHomework() {
		this.n2.insertHomework(new Matrix(new double[][] {{0.1}, {0.2}}), new Matrix(new double[][] {{0.8}}));
		assertEquals(true, true);
	}

	@Test
	public void testFeedInput() {

		/**
		 * adapted from example in Igor Livishin 2019, p. 23-41
		 */

		Matrix inputMat = new Matrix(new double[][] {{0.01}, {0.02}});
		
		this.n2.feedInput(inputMat);

		Matrix expInput2Activations = new Matrix(new double[][] {{0.01}, {0.02}});
		assertEquals(expInput2Activations, this.n2.layers.get(0).getActivation());

		Matrix expInputHidden2 = new Matrix(new double[][] {{0.2017}, {0.2023}, {0.2029}});
		Matrix expActivationHidden2 = new Matrix(new double[][] {{0.5502}, {0.5504}, {0.5505}});

		for (int i = 0; i < hidden2.getActivation().rows; i++) {
			assertArrayEquals(expActivationHidden2.getData()[i], this.n2.layers.get(1).getActivation().getData()[i], 0.001);
			assertArrayEquals(expInputHidden2.getData()[i], this.n2.layers.get(1).getInput().getData()[i], 0.001);
		}

		Matrix expInputOutput2 = new Matrix(new double[][] {{0.4481}});
		Matrix expOutputOutput2 = new Matrix(new double[][] {{0.6101}});

		assertArrayEquals(expOutputOutput2.getData()[0], this.n2.layers.get(2).getActivation().getData()[0], 0.001);
		assertArrayEquals(expInputOutput2.getData()[0], this.n2.layers.get(2).getInput().getData()[0], 0.001);
	}

	@Test
	public void testFormatTargetOutput() {
		System.out.println("formatTargetOutput");
		double output = 9.0;
		// output[0] = 1;

		Matrix result = this.n.formatOutput(output);
		Matrix result2 = this.n.formatOutput(2.0);

		// TODO: Näissä voi olla häikkää
		double[][] outputMat = new double[][] {{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}};
		double[][] outputMat2 = new double[][] {{0}, {1}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}};
		
		Matrix expResult = new Matrix(outputMat);
		Matrix expResult2 = new Matrix(outputMat2);


		System.out.println("ExpResult: " + expResult.toString());
		System.out.println("Result: " + result.toString());

		assertEquals(expResult, result);
	}

	@Test
	public void testGetFirstLayer() {
		System.out.println("getFirstLayer");
		Layer result = this.n.getFirstLayer();
		Layer expResult = this.input;

		assertEquals(expResult, result);
	}

	@Test
	public void testWeightSizes() {
		System.out.println("Test weight sizes");
		Matrix weightsHidden = this.n.layers.get(1).getWeights();
		Matrix inputOutput = this.n.getFirstLayer().output;

		System.out.println("Weights hidden rows: " + weightsHidden.rows);
		System.out.println("Weights hidden cols: " + weightsHidden.cols);

		System.out.println("Inputoutput rows: " + inputOutput.rows);
		System.out.println("Inputoutput cols: " + inputOutput.cols);

		assertEquals(inputOutput.rows, weightsHidden.cols);
	}

	@Test
	public void testCalculateActivation() {
		double[][] input = new double[784][1];

		for (int i = 0; i < input.length; i++) {
			input[i][0] = (double) Math.random()*2.0;
			System.out.println(input[i][0]);
		}

		this.input.setActivation(new Matrix(input));

		Matrix z = Matrix.multiply(this.hidden.getWeights(), this.hidden.getPrevLayer().getActivation());
		z = z.addMatrix(this.hidden.getBias());

		System.out.println("z rows: " + z.rows);
		System.out.println("z cols: " + z.cols);

		assertEquals(z.rows, hidden.weights.rows);

	}

	// @Test
	// public void testGetMinibatch() {
	// 	this.n.get_minibatch()
	// }

	@Test
	public void testLearn() {
		System.out.println("Learn");
		this.n.learn();

		System.out.println("Testi");
		assertEquals(true, true);
	}

	@Test
	public void testToJson() {
		System.out.println("toJson");
		String response = this.n.toJson(true);
		System.out.println(response);
		assertEquals(true, true);
	}

	// @Test
	// public void testBuilder() {
	// 	NeuralNetwork.NNBuilder nnb = new NeuralNetwork.NNBuilder(2);
	// 	nnb.addLayer(new Layer(2, new Sigmoid(), 0.25));
	// 	nnb.addLayer(new Layer(2, new Sigmoid(), 0.3));
	// 	nnb.setOptimizer(new GradientDescent(0.1));
	// 	nnb.setInitialWeights(new double[][] {{0.25}, {0.21}});
	// 	NeuralNetwork uusi = nnb.create();
	// 	System.out.println(uusi.layers.get(0).toString());
	// 	assertEquals(true, true);
	// }
    

    

	// @Test
	// public void testSGD() {
	// 	this.n.SGD(recordsValues, 3, 10, 0.002, validValues);
	// }

	// @Test
	// public void shouldSetInitialWeights() {
	// 	neuralNetwork.setInitialWeights();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldAddLayer() {
	// 	// TODO: initialize args
	// 	Layer l;

	// 	neuralNetwork.addLayer(l);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGet_minibatch() {
	// 	// TODO: initialize args
	// 	int previousIndex;
	// 	int minibatch_size;

	// 	Map<Integer,double[]> actualValue = neuralNetwork.get_minibatch(previousIndex, minibatch_size);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldLearn() {
	// 	neuralNetwork.learn();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSGD() {
	// 	// TODO: initialize args
	// 	List<double[]> training_data;
	// 	int epochs;
	// 	int mini_batch_size;
	// 	double learning_rate;
	// 	List<Double> test_data;

	// 	neuralNetwork.SGD(training_data, epochs, mini_batch_size, learning_rate, test_data);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldTargetOutputToMatrix() {
	// 	// TODO: initialize args
	// 	double targetoutput;

	// 	Matrix actualValue = neuralNetwork.targetOutputToMatrix(targetoutput);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldUpdate_mini_batch() {
	// 	// TODO: initialize args
	// 	List<double[]> minibatch;
	// 	double learning_rate;

	// 	neuralNetwork.update_mini_batch(minibatch, learning_rate);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldBackpropagate() {
	// 	List<double[][]> actualValue = neuralNetwork.backpropagate();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetLastLayer() {
	// 	Layer actualValue = neuralNetwork.getLastLayer();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetFirstLayer() {
	// 	Layer actualValue = this.n.getFirstLayer();


	// 	// TODO: assert scenario
	// }
}
