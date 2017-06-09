package com.mathutil.mathutilTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.MathUtil;
import com.mathutil.linearalgebra.Linear;
import com.mathutil.linearalgebra.SuperVector;

/**
 * Test case 1, basic test
 * @author danielxu
 *
 */
public class TestDist1 {

	@Test
	public void test() {
		double expect = 13.09;
		SuperVector v1 = new SuperVector(1.2, 10, 1.4);
		SuperVector v2 = new SuperVector(10.5 ,0.8 ,0.9);
		double test = MathUtil.round(Linear.dist(v1, v2), 2); //rounded to 2 decimal places
		
		assertEquals(expect , test , 0);
	}

}
