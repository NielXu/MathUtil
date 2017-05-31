package com.mathutil.mathutilTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.MathUtil;

/**
 * Test case 3, test Double.NaN
 * @author danielxu
 *
 */
public class TestRound3 {

	@Test
	public void test() {
		double expect = Double.NaN;
		double test = MathUtil.round(Double.NaN, 10);
		assertEquals(expect , test , 0);
	}

}
