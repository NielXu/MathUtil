package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 7, cos function test
 * @author danielxu
 *
 */
public class TestSigma7 {

	@Test
	public void test() {
		double expect = Math.cos(2.0*3.0) + Math.cos(3.0*3.0) + Math.cos(4.0*3.0);
		String exp = "cos(x*3)";
		double test = Sigma.sum(2, 4, exp);
		
		assertEquals(expect , test , 0);
	}

}
