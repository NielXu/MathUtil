package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 3, test the square root and priority of ^ and *
 * @author danielxu
 *
 */
public class TestSigma3 {

	@Test
	public void test() {
		double expect = (1.0*Math.sqrt(1.0+2.0)) + (2.0*Math.sqrt(2.0+2.0)) + (3.0*Math.sqrt(3.0+2.0));
		String exp = "a*(a+2)^(1/2)";
		double test = Sigma.sum(1, 3, exp, "a");
		
		assertEquals(expect , test , 0);
	}

}
