package com.mathutil.calculusTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.calculus.Calculus;

/**
 * Test case 4, trapezoid rule.<br>
 * Result from website: http://www.emathhelp.net/calculators/calculus-2/riemann-sum-calculator
 * @author danielxu
 *
 */
public class TestCalculus4 {

	@Test
	public void test() {
		double expect = 18.0828628820601;
		double test = Calculus.integ_riemann_trapezoid(TestCalculus1.func, 1, 5, 10);
		
		assertEquals(expect , test , TestCalculus1.ACCEPTABLE_ERROR);
	}

}
