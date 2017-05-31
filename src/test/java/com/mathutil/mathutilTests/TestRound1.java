package com.mathutil.mathutilTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mathutil.MathUtil;

/**
 * Test case 1, basic test
 * @author danielxu
 *
 */
public class TestRound1 {

	@Test
	public void test() {
		double expect = 9.12;
		double test = MathUtil.round(9.124, 2);
		
		assertEquals(expect , test , 0);
	}

}
