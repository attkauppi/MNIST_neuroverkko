package neuroverkko.Neuroverkko;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import neuroverkko.Neuroverkko.*;
import neuroverkko.Math.ActivationFunctions.*;
import neuroverkko.Math.Optimizers.*;
import neuroverkko.Math.*;

public class LayerTest {
	
	private Layer input;
	private Layer layer;
	private Layer lastLayer;

	private Layer input2;
	private Layer hidden2;
	private Layer output2;


	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
		this.input = new Layer(2, new Identity(), new GradientDescent(0.01), 0.0);
        this.layer = new Layer(10, new Sigmoid(), new GradientDescent(0.01), 0.1);
		this.lastLayer = new Layer(5, new LeakyReLu(), new GradientDescent(0.01), 0.2);

		this.layer.setPrevLayer(input);
		this.lastLayer.setPrevLayer(layer);
		

		this.input2 = new Layer(2, new Identity(), 0.0);
        this.hidden2 = new Layer(3, new Sigmoid(), 0.20);
        this.output2 = new Layer(1, new Sigmoid(), 0.25);

		this.hidden2.setPrevLayer(input2);
		this.output2.setPrevLayer(hidden2);

		// this.hidden2.setInitialWeightsRand();
		this.hidden2.setInitialWeights(new Matrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}}));
		this.hidden2.setInitialBias(0.2);
		this.output2.setInitialWeights(new Matrix(new double[][] {{0.11, 0.12, 0.13}}));
		this.output2.setInitialBias(0.25);
    }
    
    @After
    public void tearDown() {

	}

	@Test
	public void testEvaluate() {
		/**
		 * example from Igor Livishin 2019, p. 23-41
		 */
		Matrix inputMat = new Matrix(new double[][] {{0.01}, {0.02}});

		this.input2.evaluate(inputMat);
		Matrix expInput2Activations = new Matrix(new double[][] {{0.01}, {0.02}});
		assertEquals(expInput2Activations, input2.getActivation());

		this.hidden2.evaluate(this.hidden2.getPrevLayer().getActivation());
		Matrix expInputHidden2 = new Matrix(new double[][] {{0.2017}, {0.2023}, {0.2029}});
		Matrix expActivationHidden2 = new Matrix(new double[][] {{0.5502}, {0.5504}, {0.5505}});

		for (int i = 0; i < hidden2.getActivation().rows; i++) {
			assertArrayEquals(expActivationHidden2.getData()[i], this.hidden2.getActivation().getData()[i], 0.001);
			assertArrayEquals(expInputHidden2.getData()[i], this.hidden2.getInput().getData()[i], 0.001);
		}

		this.output2.evaluate(this.output2.getPrevLayer().getActivation());
		Matrix expInputOutput2 = new Matrix(new double[][] {{0.4481}});
		Matrix expOutputOutput2 = new Matrix(new double[][] {{0.6101}});

		assertArrayEquals(expOutputOutput2.getData()[0], this.output2.getActivation().getData()[0], 0.001);
		assertArrayEquals(expInputOutput2.getData()[0], this.output2.getInput().getData()[0], 0.001);

	}

	@Test
	public void testSetPrevLayer() {
		Layer instanceNext = this.layer;
		Layer instancePrev = this.lastLayer;
		
		instanceNext.setPrevLayer(lastLayer);
		assertEquals(instancePrev, instanceNext.getPrevLayer());
		assertEquals(instanceNext, instancePrev.getNextLayer());
	}

	@Test
	public void testInitializeWeights() {
		Layer instanceNext = this.layer;
		Layer instancePrev = this.lastLayer;

		instanceNext.setPrevLayer(lastLayer);
		instanceNext.initializeWeights();

		assertEquals(10, instanceNext.getWeights().rows);
		assertEquals(5, instanceNext.getWeights().cols);
	}


	// @Test
	// public void shouldGetPrevLayer() {
	// 	Layer actualValue = layer.getPrevLayer();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetActivationFunction() {
	// 	ActivationFunction actualValue = layer.getActivationFunction();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetSize() {
	// 	int actualValue = layer.getSize();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldInitializeWeights() {
	// 	layer.initializeWeights();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldResetDeltaWeights() {
	// 	layer.resetDeltaWeights();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldFillWithZeros() {
	// 	// TODO: initialize args
	// 	double[][] dArray;

	// 	double[][] actualValue = layer.fillWithZeros(dArray);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetInput() {
	// 	Matrix actualValue = layer.getInput();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetActivation() {
	// 	Matrix actualValue = layer.getActivation();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSetInitialWeightsRand() {
	// 	layer.setInitialWeightsRand();

	// 	// TODO: assert scenario
	// }
}
