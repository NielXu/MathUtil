package com.mathutil.mathutilTests;

import org.junit.Test;

import com.mathutil.MathUtil;
import com.mathutil.exceptions.CalculationException;

/**
 * Test case 2, check exception
 * @author danielxu
 *
 */
public class TestDist2 {

	@Test(expected = CalculationException.class) //expect CalculationException in MathUtil class
	public void test() {
		MathUtil.dist(null, null);
	}

}
