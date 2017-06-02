package com.mathutil;

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

	/**
	 * Calculate the Euclidean distance between two vectors. The two vectors must have the same dimensions in order to 
	 * do the calculation. The formula for euclidean distance:<br>
	 * <center>dist(v1,v2) = dist(v2,v1) = √((p1-q1)^2 + (p2-q2)^2 + (p3-q3)^2 + ... + (pn-qn)^2)</center>
	 * @param v1 - Vector1
	 * @param v2 - Vector2
	 * @return The Euclidean distance between this two vectors
	 * @see SuperVector
	 */
	public static double dist(SuperVector v1 , SuperVector v2){
		if(v1 == null || v2 == null)
			throw new CalculationException("The vectos cannot be null");
		if(v1.getDimension() != v2.getDimension())
			throw new CalculationException("The dimension of the vectors must be the same");
		
		double sum = 0;
		double[] c1 = v1.getComponents();
		double[] c2 = v2.getComponents();
		
		for(int i=0;i<c1.length;i++){
			sum += Math.pow(c1[i] - c2[i], 2);
		}
		return Math.sqrt(sum);
	}
	
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
		if(val == 0 || val ==  Double.NaN)
			return 0;
		if(places == 0)
			return Math.round(val);
		if(Double.isNaN(val))
			return Double.NaN;
		
		long factor = (long) Math.pow(10, places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double) tmp / factor;
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
