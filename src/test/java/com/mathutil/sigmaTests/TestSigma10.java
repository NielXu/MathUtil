package com.mathutil.sigmaTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.operations.Sigma;

/**
 * Test case 10, absolute value test
 * @author danielxu
 *
 */
public class TestSigma10 {

	@Test
	public void test() {
		double expect = (2.0 + 3.0*Math.abs(1.0*5.0)) + (2.0 + 3.0*Math.abs(2.0*5.0)) + (2.0 + 3.0*Math.abs(3.0*5.0));
		String exp = "2+3*abs(x*5)";
		double test = Sigma.sum(1, 3, exp);
		
		assertEquals(expect , test , 0);
	}

}
