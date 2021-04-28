package neuroverkko.Neuroverkko;

//import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
		Layer l = new Layer(3, "1");
        Layer l2 = new Layer(1, "2");

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
}
