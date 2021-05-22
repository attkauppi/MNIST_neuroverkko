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
import static java.lang.System.arraycopy;

import neuroverkko.Math.ActivationFunctions.Identity;
import neuroverkko.Math.ActivationFunctions.Sigmoid;
import neuroverkko.Math.CostFunctions.MSE;
import neuroverkko.Math.CostFunctions.Quadratic;
import neuroverkko.Math.Optimizers.GradientDescent;
import neuroverkko.Math.*;
import neuroverkko.Neuroverkko.NeuralNetwork;
import neuroverkko.Neuroverkko.NeuralNetwork.NNBuilder;
import neuroverkko.Neuroverkko.NeuralNetwork.NetworkState;
import neuroverkko.Utils.RandomNumberGenerator;
import neuroverkko.Utils.DataStructures.*;
import neuroverkko.Utils.DataStructures.Map.Pair;

public class NeuralNetworkTest {

	NeuralNetwork n;
	Layer input;
	Layer hidden;
	Layer output;

	private Layer input2;
	private Layer hidden2;
	private Layer output2;
	NeuralNetwork n2;

	private Layer input3;
	private Layer hidden3;
	private Layer output3;
	NeuralNetwork n3;



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
		this.n.setCostFunction(new Quadratic());
        this.n.setOptimizer(new GradientDescent(0.01));
        this.n.setL2(0.0002);

		this.input2 = new Layer(2, new Identity(), 0.0);
        // this.hidden2 = new Layer(3, new Sigmoid(), 0.20);
        // this.output2 = new Layer(1, new Sigmoid(), 0.25);
		
        this.hidden2 = new Layer(3, new Sigmoid(), new GradientDescent(1.0), 0.20);
        this.output2 = new Layer(1, new Sigmoid(), new GradientDescent(1.0), 0.25);

		// this.hidden2.setPrevLayer(input2);
		// this.output2.setPrevLayer(hidden2);

		
		
		this.n2 = new NeuralNetwork(2,3,1);
		this.n2.setCostFunction(new Quadratic());
		this.n2.setL2(1.0);

		this.n2.addLayer(input2);
		this.n2.addLayer(hidden2);
		this.n2.addLayer(output2);
		this.n2.setWeightsUniformly();
		this.hidden2.setInitialWeights(new Matrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}}));
		this.hidden2.setInitialBias(0.2);
		this.output2.initializeWeights();
		this.output2.setInitialWeights(new Matrix(new double[][] {{0.11, 0.12, 0.13}}));
		this.output2.setInitialWeights(new Matrix(new double[][] {{0.11, 0.12, 0.13}}));
		this.output2.setInitialBias(0.25);

		this.input3 = new Layer(2, new Identity(), 0.0);
        this.hidden3 = new Layer(2, new Sigmoid(), new GradientDescent(0.1), 0.20);
        this.output3 = new Layer(2, new Sigmoid(), new GradientDescent(0.1), 0.25);
		this.hidden3.setPrevLayer(input3);
		this.hidden3.setInitialBias(0.2);

		this.hidden3.setBias(new Matrix(new double[][] {{0.25}, {0.45}}));
		this.output3.setPrevLayer(hidden3);
		this.output3.setInitialBias(0.25);
		this.output3.setBias(new Matrix(new double[][] {{0.15}, {0.35}}));
		this.n3 = new NeuralNetwork(3, 1, 2);
		this.n3.opt = new GradientDescent(0.1);
		this.n3.setCostFunction(new Quadratic());
		this.n3.addLayer(input3);
		this.n3.addLayer(hidden3);
		this.n3.addLayer(output3);

    }

	@After
    public void tearDown() {

	}

	// @Test
	// public void testFeedHomework() {
	// 	this.n2.insertHomework(new Matrix(new double[][] {{0.1}, {0.2}}), new Matrix(new double[][] {{0.8}}));
	// 	assertEquals(true, true);
	// }

	public void testInputEvaluation() {
		double[][] initialWeights = {{0.3, 0.2}, {-0.4, 0.6}, {0.7, -0.3}, {0.5, -0.1}};
		// this.n3.feedforward(input)

	}

	@Test
	public void testIsCorrectOutput() {
		System.out.println("isCorrect");

		Matrix m = new Matrix(new double[][] {{0.1},{0.234},{0.2345},{0.3}});
		Matrix targetOutput = new Matrix(new double[][] {{0},{0},{0},{1}});
		Matrix mWrong = new Matrix(new double[][] {{0.1},{0.234},{0.4},{0.3}});

		int result = m.getMatrixMax(m);
		int wrongResult = mWrong.getMatrixMax(mWrong);
		int expResult = m.getMatrixMax(targetOutput);

		// Checking if the method used by this method
		// works correctly
		assertEquals(expResult, result);
		assertEquals(3, expResult);
		assertNotEquals(expResult, wrongResult);
		NeuralNetwork nn = new NeuralNetwork(3, 10, new GradientDescent(0.3));

		nn.addLayer(new Layer(4));
		nn.getLastLayer().getActivation();


		System.out.println(nn.isCorrectOutput(m, targetOutput));
		System.out.println(nn.isCorrectOutput(mWrong, targetOutput));


		// Check method return value is correct
		assertEquals(true, nn.isCorrectOutput(m, targetOutput));
		assertEquals(false, nn.isCorrectOutput(mWrong, targetOutput));

	}

	@Test
	public void testBackpropagateImproved() {
		// NeuralNetwork nn = new NeuralNetwork.NNBuilder(2, 10, new GradientDescent(0.01)).create();


		// 	nb.setCostFunction(new MSE()),

		// )

		System.out.println("backpropagateImproved");
		NeuralNetwork n = this.n3;

		Layer l = new Layer(1, new Sigmoid());
		Layer hidden = new Layer(3, new Sigmoid());
		l.setPrevLayer(hidden);

		n3.addLayer(hidden);
		n3.addLayer(l);

		n.getLastLayer().setInitialWeights(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));
		// l.setWeights(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));

		hidden.setActivation(new Matrix(new double[][] {{0.73105857863}, {0.78583498304}, {0.68997448112}}));

		// System.out.println(l.calculateInput(hidden.getActivation()).getData());
		// System.out.println(l.getActivation().toString());

		// l.setActivation(new Matrix(new double[][] {{0.77}}));

		


		// Matrix errorDelta = n.backpropagateImproved(new Matrix(new double[][] {{0.77}}));
		// System.out.println("Error delta: " + errorDelta.toString());

		assertEquals(true, true);

	}

	@Test
	public void testLearning() {
		System.out.println("testLearning");

		this.n3.setL2(0.0);
		//this.n3.feedInput();
		// Output: , new Matrix(new double[][] {{1}, {0.2}
		//Matrix output = this.n3.getLastLayer().getActivation();
		//System.out.println("Output: " + output.toString());
		//System.out.println("oikea output: " + this.n3.getLastLayer().output.toString());

		Matrix input = new Matrix(new double[][] {{2},{3}});
		Matrix output = new Matrix(new double[][] {{1},{0.2}});
		
		this.n3.feedInput(input);

		this.n3.doLearn();

		System.out.println("n3 learning rate: " + this.n3.opt.getLearningRate());
		System.out.println("Viimeisen aktivaatio: ");
		System.out.println(this.n3.getLastLayer().getActivation());
		// this.input3.evaluate(input);
		// for (Layer l: this.n3.layers) {
		// 	if (!l.equals(this.input3)) {
		// 		l.evaluate(l.getPrevLayer().getActivation());
		// 	}
		// }
		this.n3.layers.get(this.n3.layers.size()-1).getActivation();

		System.out.println(this.n3.getLastLayer().getActivation().toString());

		assertEquals(true, true);
	}

	@Test
	public void testGetAccuracy2() {
		System.out.println("getAccuracy2");

	}

	@Test
	public void testXORLearn() {

		// Example from: https://stevenmiller888.github.io/mind-how-to-build-a-neural-network/
		System.out.println("XOR learning");

		NeuralNetwork n = this.n2;
		Layer input = this.input2;
		Layer hidden = this.hidden2;
		Layer output = this.output2;

		System.out.println(n.layers.get(2).getWeights().rows + ", " + n.layers.get(2).getWeights().cols);
		n.layers.get(2).setWeights(new Matrix(new double[][] {{0.3, 0.5, 0.9}}));
		System.out.println("uotput: " + n.layers.get(2).getWeights().toString());
		// n.layers.get(2).setInitialWeights(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));
		// n.layers.get(2).initializeWeights();

		for (Layer l: n.layers) {
			// System.out.println(l.getSize());

			if (l.hasPrevLayer()) {
				// System.out.println(l.getWeights());
			}
		}
		System.out.println("Painot listassa outputissa: " +n.layers.get(2).getWeights().toString());


		hidden.setInitialWeights(new Matrix(new double[][] {{0.8, 0.2}, {0.4, 0.9}, {0.3, 0.5}}));
		hidden.setInitialBias(0);
		output.setInitialWeights(new Matrix(new double[][] {{0.3, 0.5, 0.9}}));
		output.setInitialBias(0);
		n.layers.get(2).setInitialBias(0);
		n.getLastLayer().setInitialBias(0.0);
		// n.getLastLayer().setWeigh(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));

		// n.get

		System.out.println("last layer bias: " + n.getLastLayer().getBias().toString());
		System.out.println("last layer weights: " + n.getLastLayer().getWeights());
		



		System.out.println("Output edellinen: " + output.getPrevLayer().getSize());

		n.feedInput(new Matrix(new double[][] {{1.0}, {1.0}, {1.0}}));
		n.layers.get(1).setInitialBias(0.0);

		System.out.println("Outputtia edeltävän aktivaatio: " + output.getPrevLayer().getActivation().toString());
		System.out.println("Outputtin asetetut painto: " + output.getWeights().toString());
		// n.layers.get(2).setInitialWeights(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));
		// output.setInitialWeights(new Matrix(new double[][] {{0.3}, {0.5}, {0.9}}));
		System.out.println("Outputtin asetetut painto: " + output.getWeights().toString());
		System.out.println("outputing painot listassa: " + n.layers.get(2).getWeights().toString());





		Matrix hiddenExpInputBeforeBias = new Matrix(new double[][] {{1.0}, {1.3}, {0.8}});
		Matrix hiddenResultInputBeforeBias = n.layers.get(1).getInput();
		assertEquals(hiddenExpInputBeforeBias, hiddenResultInputBeforeBias);

		Matrix hiddenExpActivationBeforeBias = new Matrix(new double[][] {{0.73105857863}, {0.78583498304}, {0.68997448112}});
		Matrix hiddenResultActivationBeforeBias = n.layers.get(1).getActivation();
		for (int row = 0; row < hiddenResultActivationBeforeBias.rows; row++) {
			assertArrayEquals(
				hiddenExpActivationBeforeBias.getData()[row],
				hiddenResultActivationBeforeBias.getData()[row], 0.00000001
			);
		}

		Matrix outputExpInputBeforeBias = new Matrix(new double[][] {{1.235}});
		Matrix outputResultInputBeforeBias = n.getLastLayer().getInput();

		for (int i = 0; i < outputResultInputBeforeBias.rows; i++) {
			assertArrayEquals(outputExpInputBeforeBias.getData()[i], outputResultInputBeforeBias.getData()[i], 0.01);
		}

		Matrix outputExpActivation = new Matrix(new double[][] {{0.7746924929149283}});
		Matrix outputResultActivation = n.getLastLayer().getActivation();

		for (int i = 0; i < outputResultActivation.rows; i++) {
			assertArrayEquals(outputExpActivation.getData()[i], outputResultActivation.getData()[i], 0.001);
		}

		System.out.println(n.getLastLayer().getActivation().rows +" " + n.getLastLayer().getActivation().cols);
		System.out.println(n.getLastLayer().getActivation().toString());

		Matrix target = new Matrix(new double[][] {{0}});
		System.out.println("Target: " + target.rows + ", " + target.cols);
		Matrix actualResult = n.getLastLayer().getActivation();

		System.out.println("Output layerin aktivaatio: " + actualResult.toString());

		assertEquals(0.77, actualResult.getData()[0][0], 0.01);

		Matrix expResult = new Matrix(new double[][] {{0}});

		System.out.println("Ennen backpropagaatiota: " + n.getLastLayer().getActivation().toString());
		System.out.println("Toiseksi viimeisen aktivaatio: " + n.layers.get(1).getActivation().toString());

		Matrix viimeisenAktivaationDerivaatta = n.getLastLayer().actFnc.dActFunc(Matrix.subtract(n.getLastLayer().getActivation(), n.getLastLayer().getBias()));
		// System.out.println("Viimeisen aktivaatio: " + viimeisenAktivaationDerivaatta.toString());
		System.out.println("Viimeisen aktivaation derivaatta: " + viimeisenAktivaationDerivaatta.toString());
		viimeisenAktivaationDerivaatta.scalarProd(-0.77);
		System.out.println("Viimeisen aktivaation derivaatta kerrottuna virheellä: " + viimeisenAktivaationDerivaatta.toString());

		n.backpropagate(expResult);

		for (Layer l: n.layers) {
			if (l.hasPrevLayer()) {
				System.out.println(l.getDeltaWeights().toString());
				System.out.println("deltaWeights added: " + l.deltaWeightsAdded);
			}
			
		}

		n.learn();

		for (Layer l: n.layers) {
			if (l.hasPrevLayer()) {
				System.out.println(l.getWeights().toString());
				System.out.println("deltaWeights added: " + l.deltaBias.toString());
			}
			
		}
		System.out.println("Toiseksi viimeisen aktivaatio: " + n.layers.get(1).getActivation().toString());
		System.out.println("Lopuksi viimeisen aktivaatiot: " + n.getLastLayer().getActivation().toString());
		System.out.println("Lopuksi viimeisen painot: " + n.getLastLayer().getWeights().toString());




		// n.backpropagate(target);

		// for (Layer l: n.layers) {
		// 	if (l.hasPrevLayer()) {
		// 		System.out.println(l.getDeltaWeights().toString());
		// 	}
		// }

		assertEquals(true, true);
	}




	//FIXME: korjaa, toimii edelleen layer-luokassa.
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
		Matrix expActivationHidden2 = new Matrix(new double[][] {{0.5502017}, {0.550403}, {0.550551}});

		System.out.println(this.n2.layers.get(1).getActivation().toString());
		System.out.println(this.n2.layers.get(1).getInput().toString());

		for (int i = 0; i < hidden2.getActivation().rows; i++) {
			assertArrayEquals(expActivationHidden2.getData()[i], this.n2.layers.get(1).getActivation().getData()[i], 0.0001);
			assertArrayEquals(expInputHidden2.getData()[i], this.n2.layers.get(1).getInput().getData()[i], 0.001);
		}

		Matrix expInputOutput2 = new Matrix(new double[][] {{0.4481481}});
		Matrix expOutputOutput2 = new Matrix(new double[][] {{0.6101988}});

		System.out.println(n2.getLastLayer().getActivation().toString());
		System.out.println(n2.getLastLayer().getInput().toString());

		assertArrayEquals(expOutputOutput2.getData()[0], this.n2.layers.get(2).getActivation().getData()[0], 0.00001);
		assertArrayEquals(expInputOutput2.getData()[0], this.n2.layers.get(2).getInput().getData()[0], 0.00001);

		// Backward
		System.out.println("Vikan painot ennen backpropagaatiota: " + n2.getLastLayer().getWeights().toString());

		System.out.println("Viimeisen layerin aktivaatio: " + n2.getLastLayer().getActivation().toString());

		/**
		 * Tavoite: 0.80
		 * learning_rate = 1
		 * w11 = 0.11
		 * 
		 * a_11 = 61019884459
		 * 
		 * w_11, viimeisessä = 
		 * 
		 * 	dE/dW11 = dE/dO * dO/dZ * dZ/dW
		 * 	dE/dW11 = -0.1898011 * 0.23785621465 * 0.5502547397403884
		 * 		dE/dO = (0.61019884459-0.80) = -0.1898011
		 * 		*
		 * 		dO/dZ = 61019884459*(1-61019884459) =0.23785621465
		 * 		*
		 * 		dZ/dW = H1 = 0.5502547397403884
		 * 
		 * w11 = 0.11 - learning_rate * -0.1898011 = 0.11 -1*(-0.1898011) = .13484145447045810733
		 * 
		 * w12 = 0.12 + 0.024841461722517316 = 0.1448414617225173
		 * 
		 * w13 = 0.13 + 0.024841461722517316 = .15485486045207970089
		 *  */
		Layer l = n2.getLastLayer();

		Matrix target = new Matrix(new double[][] {{0.8}});
		// Matrix dCostdOutput = n2.costFunction.getDerivative(target, l.getActivation());
		// System.out.println("dCostdOutput: " + dCostdOutput.toString());
		// Matrix dCostdInput = l.actFnc.calc_dCostdInput(l.getActivation(), dCostdOutput);
		// System.out.println("dCostdInput: " + dCostdInput.toString());
		// Matrix dCdW = dCostdInput.outerProduct(l.getPrevLayer().getActivation());
		// System.out.println("dCdW: " + dCdW.toString());

		// Outputin painojen korjaus

		Matrix error = Matrix.subtract(l.getActivation(), target);
		Matrix expError = new Matrix(new double[][]{{-0.18980115540874787}});
		System.out.println(l.getActivation().toString());
		System.out.println("error: " + error.toString());
		assertEquals(expError, error);

		// Cost function (quadratic tuottaa saman tuloksen)
		Matrix costFunctionDer = n2.costFunction.getDerivative(target, l.getActivation());
		assertEquals(expError, costFunctionDer);


		Matrix dAct = l.actFnc.dActFunc(l.getInput());
		System.out.println("dAct: " + dAct.toString());
		Matrix dActExp = new Matrix(new double[][] {{0.23785621465075305}});
		assertEquals(dActExp, dAct);
		System.out.println("l.input: " + l.getInput());
		Matrix dCdI = l.actFnc.calc_dCostdInput(l.getInput(), costFunctionDer);
		System.out.println(l.getInput());
		System.out.println("dCdI: " + dCdI.toString());


		// outputin oikea virhe ==> dE/dO * dO/dZ 
		Matrix oGradient = Matrix.hadamardProduct(error, dAct);
		System.out.println("oGradient: " + oGradient.toString());

		// Virhetermi kertaa exellisen aktivaatio, delta
		Matrix adj = Matrix.multiply(oGradient, Matrix.transpose(l.getPrevLayer().getActivation()));
		System.out.println("Adj: " + adj.toString());

		// Uudet painot
		Matrix newWeightsOutput = Matrix.add(l.getWeights(), adj.scalarProd(-1.0));
		Matrix newWeightsOutputExp = new Matrix(new double[][] {{0.13484145447045810733,0.144841461722517316, 0.15485486045207970089}});
		System.out.println("new Weights: " + newWeightsOutput.toString());

		for (int i = 0; i < newWeightsOutput.rows; i++) {
			assertArrayEquals(newWeightsOutputExp.getData()[i], newWeightsOutput.getData()[i], 0.00001);
		}

		// h2
		l = l.getPrevLayer();
		System.out.println("Ekan piilotetun aktivaatiot: " + l.getActivation().toString());


		Matrix l3Delta = Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeights()), oGradient);
		System.out.println("l3Delta: " + l3Delta.toString());

		// oGradientin koko oli jo valmiiksi oikea, ilmeisesti ei tarvitse
		// kertoa painoilla?
		dAct = l.actFnc.dActFunc(l.getInput());
		Matrix dActExpH2 = new Matrix(new double[][]{{0.24747446113362584},{0.24745951542013223},{0.24744452652184917}});
		assertEquals(dActExpH2, dAct);
		System.out.println("h2 dact: " + dAct.toString());

		Matrix gradientL2 = Matrix.hadamardProduct(l3Delta, dAct);
		System.out.println("gradient l2 kerrokselle: " + gradientL2.toString());

		Matrix l2DeltaWeights = Matrix.multiply(gradientL2, Matrix.transpose(l.getPrevLayer().getActivation()));
		System.out.println("L2sen delta painot: " + l2DeltaWeights.toString());

		l2DeltaWeights.scalarProd(-1);
		Matrix adjL2w = Matrix.add(l.getWeights(), l2DeltaWeights);
		System.out.println("L2 uudet painot: " + adjL2w.toString());

		// h2:sen virhe
		System.out.println(l.getNextLayer().getWeights().toString());
		// delta2 = (w3^T * delta3 o dActFunc(z2))
		System.out.println("oGradient.shape: " + oGradient.getShape());
		System.out.println("next weights shape: " + l.getNextLayer().getWeights().getShape());
		Matrix prevLayerComponent = Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeights()), oGradient);
		Matrix prevLayerComp2 = Matrix.pistetulo(Matrix.transpose(l.getNextLayer().getWeights()), oGradient);
		System.out.println("prevLayerComp2: " + prevLayerComp2.toString());
		System.out.println("prevLayerCompoentn: " + prevLayerComponent.toString());
		Matrix gradient =  Matrix.hadamardProduct(Matrix.multiply(Matrix.transpose(l.getNextLayer().getWeights()), oGradient), l.actFnc.dActFunc(l.getInput()));
		// Matrix h2Virhe = Matrix.dotProduct(gradient, Matrix.transpose(l.getPrevLayer().getActivation()));
		// System.out.println("h2virhe: " + h2Virhe.toString());

		System.out.println("gradient h2: " + gradient.toString());
		/**
		 * z1 (input) = 0.2017000
		 * z2 = 0.2023
		 * z3 = 0.20290000
		 * 
		 * h1 = 0.5502547397403884
		 * h2 = 0.5504032199355139
		 * h3 = 0.5505516911502556
		 * 
		 * w1_delta = 
		 */

		Matrix expOutput = new Matrix(new double[][] {{0.80}});
		n2.backpropagate(expOutput);
		n2.learn();

		System.out.println("Vikan uudet painot: " + n2.getLastLayer().getWeights().toString());
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
	public void testFormatInput() {
		System.out.println("formatInput");

		double[] input = new double[784];

		for (int i = 0; i < input.length; i++) {
			input[i] = RandomNumberGenerator.getRandom();
		}

		Matrix result = this.n.formatInput(input);

		int expColumns = 1;
		int expRows = 784;
		assertEquals(expColumns, result.cols);
		assertEquals(expRows, result.rows);
		
		for (int i = 0; i < result.rows; i++) {
			assertEquals(input[i], result.getData()[i][0], 0.01);
		}
	}

	@Test
	public void testGetFirstLayer() {
		System.out.println("getFirstLayer");
		Layer result = this.n.getFirstLayer();
		Layer expResult = this.input;

		assertEquals(expResult, result);
	}

	@Test
	public void testFormatOutput() {
		System.out.println("formatOutput");

		Matrix uusi = this.n.formatOutput(5.0);

		System.out.println("Uusi: " + uusi.toString());

		System.out.println("uusi: " + uusi.toString());

		assertEquals(true, true);


	}

	@Test
	public void testGetInputAndTargetMatrices() {
		System.out.println("getInputAndTargetMatrices");

		double[] input = new double[784];
		for (int i = 0; i < input.length; i++) {
			input[i] = RandomNumberGenerator.getRandom();
		}

		double targetOutput = 5.0;
		
		Matrix outputResult = this.n.formatOutput(targetOutput);
		Matrix inputResult = this.n.formatInput(input);
		System.out.println("Outputresult: " + outputResult.toString());

		Pair<Matrix, Matrix> result = this.n.getInputAndTargetMatrices(input, targetOutput);

		System.out.println(result.getValue().toString());



		assertEquals(inputResult, result.getKey());
		assertEquals(outputResult, result.getValue());
	}

	// @Test
	// public void testGetDataset() {
	// 	System.out.println("getDataset");

	// 	double[][] inputs = new double[250_000][748];
	// 	double[] outputs = new double[250_000];
	// 	int mini_batch_size = 10;

	// 	for (int i = 0; i < inputs.length; i++) {
	// 		for (int j = 0; j < inputs[0].length; j++) {
	// 			inputs[i][j] = RandomNumberGenerator.getRandom();
	// 			outputs[j] = RandomNumberGenerator.getRandom();
	// 		}	
	// 	}

	// 	List<Pair> training_data = (List<Pair>) this.n.getDataset(inputs, outputs, mini_batch_size);

	// 	for (int i = 0; i < training_data.size(); i++) {
	// 		Matrix input = (Matrix) training_data.get(i).getKey();
	// 		Matrix output = (Matrix) training_data.get(i).getValue();

	// 		assertArrayEquals(inputs[i], input.getData(), 0.01);
	// 	}

	// }

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

	// public void testLearn2() {
	// 	this.n.learn();
	// }

	// @Test
	// public void testToJson() {
	// 	System.out.println("toJson");
	// 	String response = this.n.toJson(true);
	// 	System.out.println(response);
	// 	assertEquals(true, true);
	// }

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
