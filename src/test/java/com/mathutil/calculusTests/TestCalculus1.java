package com.mathutil.calculusTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.calculus.Calculus;

/**
 * Test case 1, left riemann sum.<br>
 * Result from website: http://www.emathhelp.net/calculators/calculus-2/riemann-sum-calculator
 * @author danielxu
 *
 */
public class TestCalculus1 {

	/**The test function for calculus**/
	public static final String func = "(1+x^4)^(1/3)";
	
	/**The accpetable the error in the calculation**/
	public static final double ACCEPTABLE_ERROR = 0.0000000001;
	
	@Test
	public void test() {
		double expect = 16.6239596441521;
		double test = Calculus.integ_riemann_left(func, 1, 5, 10);
		
		assertEquals(expect , test , ACCEPTABLE_ERROR);
	}

}
