package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 2, test the priority
 * @author danielxu
 *
 */
public class TestSigma2 {

	@Test
	public void test() {
		double expect = (2.0+Math.pow(1.0, 1.0)) + (2.0+Math.pow(2.0, 2.0)) + (2.0+Math.pow(3.0, 3.0));
		String exp = "2+a^a";
		double test = Sigma.sum(1, 3, exp, "a");
		
		assertEquals(expect , test , 0);
	}

}
