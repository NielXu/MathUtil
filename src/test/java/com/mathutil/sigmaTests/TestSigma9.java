package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 9, combination of sin, cos and tan test
 * @author danielxu
 *
 */
public class TestSigma9 {

	@Test
	public void test() {
		double expect = Math.sin(Math.cos(Math.tan(1+1))) + Math.sin(Math.cos(Math.tan(2+1)));
		String exp = "sin(cos(tan(x+1)))";
		double test = Sigma.sum(1, 2, exp);
		
		assertEquals(expect , test , 0);
	}

}
