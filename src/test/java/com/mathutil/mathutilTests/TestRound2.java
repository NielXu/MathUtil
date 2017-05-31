package com.mathutil.mathutilTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.MathUtil;

/**
 * Test case 2, test if decimal 9 will be rounded to 0, if 0 will be ignored
 * @author danielxu
 *
 */
public class TestRound2 {

	@Test
	public void test() {
		double expect = 9;
		double test = MathUtil.round(8.999999, 1);
		assertEquals(expect , test , 0);
	}

}
