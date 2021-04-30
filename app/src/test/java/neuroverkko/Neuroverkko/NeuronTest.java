package neuroverkko.Neuroverkko;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
public class NeuronTest {

	private Neuron neuron;

	@Before
	public void setup() {
		this.neuron = new Neuron();
		this.neuron.setOutput(5.0);
	}

	@Test
	public void testGetOutput() {
		double expectedValue = 5.0;
		double actualValue = neuron.getOutput();
		Assert.assertEquals(expectedValue, neuron.getOutput(), 0.0);

		// TODO: assert scenario
	}

	// @Test
	// public void test() {
	// 	double actualValue = neuron.getInput();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldReceiveOutput() {
	// 	// TODO: initialize args
	// 	double input;

	// 	neuron.receiveOutput(input);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldAddInput() {
	// 	// TODO: initialize args
	// 	Neuron neuron;

	// 	neuron.addInput(neuron);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldAddInput() {
	// 	// TODO: initialize args
	// 	Neuron neuron;
	// 	double weight;

	// 	neuron.addInput(neuron, weight);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldAddOutput() {
	// 	// TODO: initialize args
	// 	Neuron neuron;

	// 	neuron.addOutput(neuron);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldAddOutput() {
	// 	// TODO: initialize args
	// 	Neuron neuron;
	// 	double weight;

	// 	neuron.addOutput(neuron, weight);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSendOutput() {
	// 	neuron.sendOutput();

	// 	// TODO: assert scenario
	// }
}
