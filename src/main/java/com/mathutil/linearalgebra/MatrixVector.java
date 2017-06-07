package com.mathutil.linearalgebra;

import com.mathutil.ExactMatrix;
import com.mathutil.Matrix;

/**
 * The vector that can be converted to a row matrix. Can be converted to Matrix or ExactMatrix.
 * @author danielxu
 *
 */
public interface MatrixVector extends Vector<Double>{

	/**
	 * Convert the vector to a row matrix, which only has one row. The number of the columns is the same as the 
	 * vector's dimension.
	 * @return One row Matrix
	 */
	public Matrix convertMatrix();
	
	/**
	 * Convert the vector to a row matrix, which only has one row. The number of the columns is the same as the 
	 * vector's dimension.
	 * @return One row ExactMatrix
	 */
	public ExactMatrix convertExactMatrix();
	
}
