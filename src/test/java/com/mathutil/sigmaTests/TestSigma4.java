package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 4, test the case that the power is a double
 * @author danielxu
 *
 */
public class TestSigma4 {

	@Test
	public void test() {
		double expect = (1.0*Math.pow(1.0+2.0, 2.0/3.0)) + (2.0*Math.pow(2.0+2.0, 2.0/3.0)) + (3.0*Math.pow(3.0+2.0, 2.0/3.0));
		String exp = "a*(a+2)^(2/3)";
		double test = Sigma.sum(1, 3, exp, "a");
		
		assertEquals(expect , test , 0);
	}

}
