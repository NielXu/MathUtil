package com.mathutil.calculusTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.calculus.Calculus;

/**
 * Test case 3, mid point riemann sum.<br>
 * Result from website: http://www.emathhelp.net/calculators/calculus-2/riemann-sum-calculator
 * @author danielxu
 *
 */
public class TestCalculus3 {

	@Test
	public void test() {
		double expect = 18.0542088984543;
		double test = Calculus.integ_riemann_mid(TestCalculus1.func, 1, 5 , 10);
		
		assertEquals(expect , test , TestCalculus1.ACCEPTABLE_ERROR);
	}

}
