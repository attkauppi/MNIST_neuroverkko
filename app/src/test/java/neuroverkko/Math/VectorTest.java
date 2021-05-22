package neuroverkko.Math;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import neuroverkko.Math.*;
import neuroverkko.Math.ActivationFunctions.Sigmoid;
import neuroverkko.Math.CostFunctions.Quadratic;

public class VectorTest {

	private Vector vector1;
	private Vector vector2;
	private Vector vector3;
    
    
    @Before
    public void setUp() {
        this.vector1 = new Vector(1,2,3);
		this.vector2 = new Vector(1);
		this.vector3 = new Vector(1,2);

    }
    
    @After
    public void tearDown() {
    }

	@Test
	public void shouldGetData() {
		double[] actualValue = this.vector1.getData();

		assertEquals(vector1.getDimensions(), actualValue.length);

		for (int i = 0; i < vector1.getDimensions(); i++) {
			assertEquals(actualValue[i], vector1.getData()[i], 0.1);
		}

		// TODO: assert scenario
	}

	@Test
	public void shouldGetDimensions() {
		int actualValue = vector1.getDimensions();

		assertEquals(3, actualValue);

		// TODO: assert scenario
	}

	@Test
	public void shouldDotProduct() {
		// TODO: initialize args
		// this.vector1 = new Vector(1,2,3);
		Vector v1 = this.vector1;
		Vector v2 = new Vector(3,2,1);


		double dot = v1.dotProduct(v2);
		System.out.println("dot 3x1, 3x1: " + dot);
		assertEquals(10, dot, 0.1);

		Vector other;

		// double actualValue = vector.dotProduct(other);

		// TODO: assert scenario
	}

	// @Test
	// public void shouldMap() {
	// 	// TODO: initialize args
	// 	Function f;

	// 	Vector actualValue = vector1.map(f);

	// 	// TODO: assert scenario
	// }

	@Test
	public void shouldScalarAdd() {
		// TODO: initialize args
		double scalar = 3.0d;

		Vector actualValue = vector1.scalarAdd(scalar);

		// TODO: assert scenario
	}

	@Test
	public void shouldScalarSubtr() {
		// TODO: initialize args
		double scalar = 2.0;

		Vector actualValue = vector1.scalarSubtr(scalar);

		// TODO: assert scenario
	}

	@Test
	public void shouldScalarProd() {
		// TODO: initialize args
		double s = 2.0;

		Vector actualValue = vector1.scalarProd(s);

		// TODO: assert scenario
	}

	@Test
	public void shouldVecAdd() {
		// TODO: initialize args
		Vector other = this.vector1;
		other.scalarProd(2.0);
	
		Vector actualValue = vector1.vecAdd(other);

		// TODO: assert scenario
	}

	@Test
	public void shouldVecElementProduct() {
		// TODO: initialize args
		Vector other = this.vector1;

		Vector actualValue = vector1.vecElementProduct(other);

		// TODO: assert scenario
	}

	// @Test
	// public void shouldOuterProductV() {
	// 	// TODO: initialize args
	// 	Vector u;

	// 	Matrix actualValue = vector.outerProductV(u);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldMatProduct() {
	// 	// TODO: initialize args
	// 	Matrix m;

	// 	Vector actualValue = vector.matProduct(m);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldVecSubtraction() {
	// 	// TODO: initialize args
	// 	Vector other;

	// 	Vector actualValue = vector.vecSubtraction(other);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetMax() {
	// 	double actualValue = vector.getMax();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldGetIndexOfMaxElement() {
	// 	int actualValue = vector.getIndexOfMaxElement();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldOuterProdut() {
	// 	// TODO: initialize args
	// 	Vector other;

	// 	Matrix actualValue = vector.outerProdut(other);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldEquals() {
	// 	// TODO: initialize args
	// 	Object other;

	// 	boolean actualValue = vector.equals(other);

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldToString() {
	// 	String actualValue = vector.toString();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldHashCode() {
	// 	int actualValue = vector.hashCode();

	// 	// TODO: assert scenario
	// }

	// @Test
	// public void shouldSumElements() {
	// 	double actualValue = vector.sumElements();

	// 	// TODO: assert scenario
	// }
}
