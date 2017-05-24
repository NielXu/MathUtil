package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 8, tan function test
 * @author danielxu
 *
 */
public class TestSigma8 {

	@Test
	public void test() {
		double expect = Math.tan(3*4) + Math.tan(4*4) + Math.tan(5*4);
		String exp = "tan(x*4)";
		double test = Sigma.sum(3, 5, exp);
		
		assertEquals(expect , test , 0);
	}

}
