package neuroverkko.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

public class ImageUtilsTest {

	private ImageUtils imageUtils;
	private int[][] image;
	private int[][] image2;
	private int[][] image3;

	@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
		this.imageUtils = imageUtils;
		this.image = new int[2][3];

		for (int i = 0; i < this.image.length; i++) {
			for (int j = 0; j < this.image[i].length; j++) {
				this.image[i][j] = i;
			}
		}

		this.image2 = new int[3][3];

		for (int i = 0; i < this.image2.length; i++) {
			for (int j = 0; j < this.image2[i].length; j++) {
				this.image2[i][j] = i;
			}
		}

		this.image3 = new int[3][3];

		for (int i = 0; i < this.image3.length; i++) {
			for (int j = 0; j < this.image3[i].length; j++) {
				if (j % 2 == 0) {
					this.image3[j][i] = i;
				} else {
					this.image3[j][i] = j;
				}
				
			}
		}
	}

	@After
    public void tearDown() {
	}

	@Test
	public void testMoveUp() {
		System.out.println("Move up");

		int[][] expResult = new int[][] {{1, 1, 1}, {0, 0, 0}};

		int[][] instance = this.image;
		int[][] result = this.imageUtils.moveUp(instance);

		System.out.println("Result: ");
		
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			assertArrayEquals(expResult, result);
		}

	}
	@Test
	public void testMoveDown() {
		System.out.println("Move down");

		int[][] expResult = new int[][] {{1, 1, 1}, {0, 0, 0}};

		int[][] instance = this.image;
		int[][] result = this.imageUtils.moveDown(instance);

		System.out.println("Result: ");
		
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			assertArrayEquals(expResult, result);
		}

		int[][] expResult2 = new int[][] {{2,2,2},{0,0,0},{1,1,1}};
		int[][] instance2 = this.image2;

		int[][] result2 = this.imageUtils.moveDown(instance2);

		for (int i = 0; i < result2.length; i++) {
			System.out.println(Arrays.toString(result2[i]));
			assertArrayEquals(expResult2, result2);
		}
	}

	@Test
	public void testMoveLeft() {
		System.out.println("Move left");

		int[][] expResult = new int[][] {{1, 2, 0}, {1, 1, 1}, {1, 2, 0}};

		/**
		 *  [0, 1, 2]
			[1, 1, 1]
			[0, 1, 2]

		 */
		int[][] instance = this.image3;
		System.out.println("image: ");
		for (int i = 0; i < image3.length; i++) {
			System.out.println(Arrays.toString(image3[i]));
			// assertArrayEquals(expResult, result);
		}
		int[][] result = this.imageUtils.moveLeft(instance);

		System.out.println("Result: ");

		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			// assertArrayEquals(expResult, result);
		}
		
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			assertArrayEquals(expResult, result);
		}
	}

	@Test
	public void testMoveRight() {
		System.out.println("Move right");

		int[][] expResult = new int[][] {{2, 0, 1}, {1, 1, 1}, {2, 0, 1}};

		/** 
			original         expResult
			[0, 1, 2]		[2, 0, 1]
			[1, 1, 1] -->   [1, 1, 1]
			[0, 1, 2]		[2, 0, 1]

		 */
		int[][] instance = this.image3;
		System.out.println("image: ");
		for (int i = 0; i < image3.length; i++) {
			System.out.println(Arrays.toString(image3[i]));
			// assertArrayEquals(expResult, result);
		}
		int[][] result = this.imageUtils.moveRight(instance);

		System.out.println("Result: ");

		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			// assertArrayEquals(expResult, result);
		}
		
		for (int i = 0; i < result.length; i++) {
			System.out.println(Arrays.toString(result[i]));
			assertArrayEquals(expResult, result);
		}
	}



}
