package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 11, test -x
 * @author danielxu
 *
 */
public class TestSigma11 {

	@Test
	public void test() {
		double expect = -1+Math.pow(2, -1) + -2+Math.pow(2, -2) + -3+Math.pow(2, -3);
		String exp = "-x+2^(-x)";
		double test = Sigma.sum(1, 3, exp);
		
		assertEquals(expect , test , 0);
	}

}
