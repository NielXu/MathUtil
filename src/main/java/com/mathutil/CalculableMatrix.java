package com.mathutil;

/**
 * For the matrices that are calculable. The basic calculations are: 
 * <code>add(CalculableMatrix m)</code>, <code>substract(CalculableMatrix m)</code>, <code>multiply(CalculableMatrix m)<code> 
 * 
 * @author danielxu
 * @see Matrix
 */
public interface CalculableMatrix extends Matrixable<Number>{

	/**
	 * Do addition between two matrices
	 * @param m - Another CalculableMatrix
	 * @return The matrix that contains the result of the operation
	 */
	public CalculableMatrix add(CalculableMatrix m);
	
	/**
	 * Do substraction between two matrices
	 * @param m - Another CalculableMatrix
	 * @return The matrix that contains the result of the operation
	 */
	public CalculableMatrix substract(CalculableMatrix m);
	
	/**
	 * Do multiplication between two matrices
	 * @param m - Another CalculableMatrix
	 * @return The matrix that contains the result of the operation
	 */
	public CalculableMatrix multiply(CalculableMatrix m);
}
