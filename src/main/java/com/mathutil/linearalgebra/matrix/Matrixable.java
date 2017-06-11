package com.mathutil.linearalgebra.matrix;

/**
 * All the basic functions that a matrix should have.
 * @author danielxu
 *
 * @param <T> - Any generic type
 */
public interface Matrixable<T> {
	
	/**
	 * Set the element at the location to a new one
	 * @param row - The row index
	 * @param col - The column index
	 * @param value - The new value
	 */
	public void set(int row , int col , T value);
	
	/**
	 * Get the element at the specific location
	 * @param row - The row index
	 * @param col - The column index
	 * @return The element
	 */
	public T get(int row , int col);
	
	/**
	 * Get the number of rows in the matrix
	 * @return The number of rows in the matrix
	 */
	public int getRows();
	
	/**
	 * Get the number of columns in the matrix
	 * @return The number of columns in the matrix
	 */
	public int getCols();
	
	/**
	 * Switch between two rows
	 * @param row1 - row index 1
	 * @param row2 - row index 2
	 */
	public void switchRow(int row1 , int row2);
	
	/**
	 * Switch between two columns
	 * @param col1 - column index 1
	 * @param col2 - column index 2
	 */
	public void switchCol(int col1 , int col2);
	
	/**
	 * Get the two dimensional array that represents the matrix
	 * @return The two dimensional array
	 */
	public T[][] getMatrix();
	
}
