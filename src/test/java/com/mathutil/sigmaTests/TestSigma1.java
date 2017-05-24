package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 1, basic operations
 * @author danielxu
 *
 */
public class TestSigma1 {

	@Test
	public void test() {
		double expect = (1.0*(1.0+1.0)) + (2.0*(2.0+1.0)) + (3.0*(3.0+1.0));
		String exp = "x*(x+1)";
		double test = Sigma.sum(1, 3, exp);
		
		assertEquals(expect , test , 0);
	}

}
