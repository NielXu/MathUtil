package com.mathutil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mathutil.exceptions.CalculationException;

/**
 * MathUtil contains few math functinons that are very helpful, such as <code>round(double val , int places)</code> , 
 * <code>log(double b , double a)</code> and so on. Most of the calculations are using double and will return the double type as the result. 
 * One can always use {@link #round(double, int)} method to round up the result to the given decimal places to reduce acurracy loss.
 * 
 * @author danielxu
 *
 */
public class MathUtil{
	
	/**No instance**/
	private MathUtil(){}
	
	/**
	 * Round a double value to the given decimal places. 0 will be ignored, for example round 1.3499 to 
	 * 3 decimals places will be 1.35.
	 * @param val - The double value that will be rounded
	 * @param places - The number of places that the value will be rounded to
	 * @return The double value that be rounded to the given decimal places. If the value is NaN, the result will also be NaN
	 */
	public static double round(double val , int places){
		if(places < 0)
			throw new CalculationException("The decimal places cannot smaller than 0");
		if(val == 0)
			return 0;
		if(places == 0)
			return Math.round(val);
		if(Double.isNaN(val))
			return Double.NaN;
		
		BigDecimal b = new BigDecimal(val).setScale(places, RoundingMode.HALF_UP);
		return b.doubleValue();
	}
	
	/**
	 * Calculate the logarithm. <br><center>logb(a)</center>
	 * <br>Calculated by <center>lnb/lna</center>
	 * @param b - The base of logarithm, must greater than 0
	 * @param a - The antilogarithm, must greater than 0
	 * @return The logarithm. If a or b is NaN, the result will be NaN
	 */
	public static double log(double b , double a){
		if(b <= 0)
			throw new CalculationException("The base of logarithm must be greater than 0");
		if(a <= 0)
			throw new CalculationException("The antilogarithm must be greater than 0");
			
		return Math.log(a) / Math.log(b);
	}
	
	/**
	 * The same as <code>Math.log(a)</code>, but change log to ln for better understanding.
	 * @param a - The double value
	 * @return
	 * <ul> 
	 * <li>If the argument is NaN or less than zero, then the result is NaN.</li>
	 * <li>If the argument is positive infinity, then the result is positive infinity.</li>
	 * <li>If the argument is positive zero or negative zero, then the result is negative infinity.</li>
	 * </ul>
	 * @see Math#log(double)
	 */
	public static double ln(double a){
		return Math.log(a);
	}
	
}
