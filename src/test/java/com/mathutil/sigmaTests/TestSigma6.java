package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 6, sin function test
 * @author danielxu
 *
 */
public class TestSigma6 {

	@Test
	public void test() {
		double expect = Math.pow(Math.sin(1.0*2.0), 2) + Math.pow(Math.sin(2.0*2.0), 2);
		String exp = "sin(x*2)^2";
		double test = Sigma.sum(1, 2, exp);
		
		assertEquals(expect , test , 0);
	}

}
