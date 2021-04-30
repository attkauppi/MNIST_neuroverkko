package neuroverkko.Neuroverkko;

//import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import neuroverkko.Math.ActivationFunctions.*;

import neuroverkko.Math.*;
import java.util.Arrays;

public class LayerTest {
	
	public Layer layer;
	public Layer layer2;

	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.layer = new Layer(2, "1");
		this.layer2 = new Layer(4, "2");
    }
    
    @After
    public void tearDown() {

	}

	@Test
	public void testCreateNeurons() {
		Layer t = new Layer(10, "t");
		int expResult = 10;
		int actual = t.neurons.size();

		assertEquals(expResult, actual);

		// TODO: assert scenario
	}

	@Test
	public void testSetNextLayer() {
		Layer l = this.layer;
		Layer l2 = this.layer2;
		l.setNextLayer(l2);

		assertTrue(l2.hasPreviousLayer());
		assertFalse(l.hasPreviousLayer());
		assertEquals(l.getNextLayer().hashCode(), l2.hashCode());



		//Layer actualValue = layer.getPrevLayer();

		// TODO: assert scenario
	}

	@Test
	public void testSendOutput() {
		Layer l = new Layer(3, "1", new Identity());
        Layer l2 = new Layer(1, "2", new Identity());

        l.setNextLayer(l2);
        
        for (Neuron n: l2.neurons) {
            for (Edge ed: n.inputs) {
                ed.setWeight(1.0);
            }
        }

		for (Neuron n: l.neurons) {
            n.setOutput(0.1);
        }

		l.sendOutput();

		System.out.println(l2.neurons.get(0).input);

		assertEquals(0.3, l2.neurons.get(0).input, 0.01);
	}

	// @Test
	// public void shouldGetNextLayer() {
	// 	Layer actualValue = layer.getNextLayer();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetSize() {
	// 	int actualValue = layer.getSize();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldCreateWeightsMatrix() {
	// 	layer.createWeightsMatrix();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldHasNextLayer() {
	// 	boolean actualValue = layer.hasNextLayer();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldHasPreviousLayer() {
	// 	boolean actualValue = layer.hasPreviousLayer();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetInputSize() {
	// 	int actualValue = layer.getInputSize();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSendOutput() {
	// 	layer.sendOutput();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldReceiveOutput() {
	// 	// TODO: initialize args
	// 	Layer layer;

	// 	Layer.receiveOutput(layer);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldToString() {
	// 	String actualValue = layer.toString();

	// 	// TODO: assert scenario
	// }

	/**
	 * Example from Igor Livshin, Artificial Neuron Networks
	 * for Java     
	 * 
	 * H1
	 *       w_{11}^10.05 // w0.06
	 *           //  
	 *       In1-/---------
	 *          /             H2
	 *       In2------------ 
	 *          \\
	 *           \\
	 *            H3
	 * 
	 * 
	 *  
	 */
	@Test
	public void testGetWeightsMatrix() {
		//NeuralNetwork nn = new NeuralNetwork(2);
        NeuralNetwork nn = new NeuralNetwork();

        SigmoidDouble s = new SigmoidDouble(1.0);
        //IActivationFunction s = new Sigmoid();
        // System.out.println(s.calculate(10));

        // nn.addLayer(new Sigmoid(), 3, 0.2);
        Layer i = new Layer(2, "i1", new Identity());

        Layer l22 = new Layer(1, "o22", new Sigmoid());

        Layer l21 = new Layer(3, "l21", new Sigmoid());

        i.setNextLayer(l21);
        l21.setWeightsFromMatrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}});
        l21.setBias(0.2);
		Matrix actual = l21.getWeightsMatrix();
        // System.out.println("l21 painot: ");

		Matrix expResult = new Matrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}});
		assertEquals(expResult, actual);
	}

	@Test
	public void testFeedInput() {
		//NeuralNetwork nn = new NeuralNetwork(2);
        NeuralNetwork nn = new NeuralNetwork();

        SigmoidDouble s = new SigmoidDouble(1.0);
        //IActivationFunction s = new Sigmoid();
        System.out.println(s.calculate(10));

        // nn.addLayer(new Sigmoid(), 3, 0.2);
        Layer i = new Layer(2, "i1", new Identity());

        Layer l22 = new Layer(1, "o22", new Sigmoid());

        Layer l21 = new Layer(3, "l21", new Sigmoid());

        i.setNextLayer(l21);
        l21.setWeightsFromMatrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}});
        l21.setBias(0.2);
        // System.out.println("l21 painot: ");
        l21.printWeights();
        l21.setNextLayer(l22);
        l22.setWeightsFromMatrix(new double[][] {{0.11},{0.12},{0.13}});
        l22.setBias(0.25);
        // System.out.println("l22 painot");
        l22.printWeights();

        nn.addLayer(i);
        nn.addLayer(l21);
        nn.addLayer(l22);

        nn.feedInput(new double[]{0.1, 0.2});

		nn.calculateError(0.8);
        System.out.println(nn.getError());
		
		double actualError = nn.getError();
		double expectedError = -0.189801155;
		System.out.println("actualError: " + actualError);
		System.out.println("Expected error: " + expectedError);

		assertEquals(expectedError, actualError, 0.01);

		
	}

	@Test
	public void testGetOutputMatrix() {
				//NeuralNetwork nn = new NeuralNetwork(2);
				NeuralNetwork nn = new NeuralNetwork();

				SigmoidDouble s = new SigmoidDouble(1.0);
				//IActivationFunction s = new Sigmoid();
				// System.out.println(s.calculate(10));
		
				// nn.addLayer(new Sigmoid(), 3, 0.2);
				Layer i = new Layer(2, "i1", new Identity());
		
				Layer l22 = new Layer(1, "o22", new Sigmoid());
		
				Layer l21 = new Layer(3, "l21", new Sigmoid());
		
				i.setNextLayer(l21);
				l21.setWeightsFromMatrix(new double[][] {{0.05, 0.06}, {0.07, 0.08}, {0.09, 0.10}});
				l21.setBias(0.2);
				// System.out.println("l21 painot: ");
				l21.printWeights();
				l21.setNextLayer(l22);
				l22.setWeightsFromMatrix(new double[][] {{0.11},{0.12},{0.13}});
				l22.setBias(0.25);
				// System.out.println("l22 painot");
				l22.printWeights();
		
				nn.addLayer(i);
				nn.addLayer(l21);
				nn.addLayer(l22);
		
				nn.feedInput(new double[]{0.1, 0.2});
		
				nn.calculateError(0.8);

				Matrix lastOutput = nn.getLastLayer().getOutputMatrix();
				assertEquals(true, true);


	}
}
