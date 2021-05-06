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

public class LayerTest {
	
	private Layer layer;
	private Layer prevLayer;

	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.layer = new Layer(10, new Sigmoid(), new GradientDescent(0.01), 0.1);
		this.prevLayer = new Layer(5);
    }
    
    @After
    public void tearDown() {

	}


	@Test
	public void testSetPrevLayer() {
		Layer instanceNext = this.layer;
		Layer instancePrev = this.prevLayer;
		
		instanceNext.setPrevLayer(prevLayer);
		assertEquals(instancePrev, instanceNext.getPrevLayer());
		assertEquals(instanceNext, instancePrev.getNextLayer());
	}

	@Test
	public void testInitializeWeights() {
		Layer instanceNext = this.layer;
		Layer instancePrev = this.prevLayer;

		instanceNext.setPrevLayer(prevLayer);
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
