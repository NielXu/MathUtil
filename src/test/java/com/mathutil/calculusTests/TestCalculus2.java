package com.mathutil.calculusTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.calculus.Calculus;

/**
 * Test case 2, right riemann sum.<br>
 * Result from website: http://www.emathhelp.net/calculators/calculus-2/riemann-sum-calculator
 * @author danielxu
 *
 */
public class TestCalculus2 {

	@Test
	public void test() {
		double expect = 19.5417661199681;
		double test = Calculus.integ_riemann_right(TestCalculus1.func, 1, 5, 10);
		
		assertEquals(expect , test , TestCalculus1.ACCEPTABLE_ERROR);
	}

}
