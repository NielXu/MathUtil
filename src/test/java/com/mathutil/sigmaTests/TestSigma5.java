package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 5, test if (a) is allowed
 * @author danielxu
 *
 */
public class TestSigma5 {

	@Test
	public void test() {
		double expect = (Math.pow(1.0, 2.0)+1.0) + (Math.pow(2.0, 2.0)+2.0) + (Math.pow(3.0, 2.0)+3.0);
		String exp = "(x)^2+(x)";
		double test = Sigma.sum(1, 3, exp);
		
		assertEquals(expect , test , 0);
	}

}
