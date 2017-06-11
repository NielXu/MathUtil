package com.mathutil.linearalgebra.matrix;

import com.mathutil.exceptions.MatrixException;

/**
 * SimpleMatrix can store informations like a two dimensional array, the length of each row should always be the same. 
 * It supports most of the basic functions in matrix, such as <code>swicthRow(int,int)</code> and <code>switchCol(int,int)</code>. 
 * But it does not support calculations. SimpleMatrix is immutable, which means, you cannot expand the matrix or remove the elements
 * in the matrix. However, you can switch rows and columns in the matrix, you can get the element from the matrix or you can replace the 
 * element in the specific location to a new one.<br>
 * If the purpose of the matrix is to do calculations, please use {@link Matrix} or {@link ExactMatrix}instead.
 * 
 * @author danielxu
 * @param <T> Any type of information is allowed.
 * @see Matrix
 * @see ExactMatrix
 * @see Matrixable
 */
public class SimpleMatrix<T> implements Matrixable<T>{
	
	/**The two dimensional array that stores the information**/
	private T[][] matrix;
	
	/**The number of rows and columns in the matrix**/
	private int rows , cols;
	
	/**
	 * Create an empty matrix, without any elements
	 */
	public SimpleMatrix(){
		rows = 0;
		cols = 0;
	}
	
	/**
	 * Create a new SimpleMatrix, stores the elements in a two dimensional array. Please remember that 
	 * the length of each row in the matrix should always be the same.
	 * @param matrix - The two dimensional array that stores all the elements
	 */
	public SimpleMatrix(T[][] matrix){
		if(matrix == null || matrix.length == 0 || matrix[0].length == 0)
			throw new MatrixException("Matrix cannot be null or empty");
		
		checkRows(matrix);
		//Deep clone the array
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}
	
	/**
	 * Set the element in the matrix to a new one
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 */
	public void set(int row, int col, T value) {
		indexCheck(row , col);
		matrix[row][col] = value;
	}

	/**
	 * Get the element that at the specific row and column
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 * @return The element that at the specific row and column
	 */
	public T get(int row, int col) {
		indexCheck(row , col);
		return matrix[row][col];
	}
	
	/**
	 * Get the number of rows in the matrix
	 * @return The number of rows in the matrix
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Get the number of columns in the matrix
	 * @return The number of columns in the matrix
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Get the matrix, in form of a two dimensional array. <strong>Please do not modify the elements in the array, it will 
	 * also affect the matrix!</strong>
	 * @return The matrix, return null if there is no element in the matrix
	 */
	public T[][] getMatrix() {
		return matrix;
	}

	/**
	 * Switch the elements in two different rows in the matrix.
	 * @param row1 - The index of the first row
	 * @param row2 - The index of the second row
	 */
	public void switchRow(int row1, int row2) {
		if(row1 < 0 || row2 < 0)
			throw new MatrixException("The index row cannot smaller than 0");
		if(row1 >= rows || row2 >= rows)
			throw new MatrixException("Index out of bound");
		if(row1 == row2)
			return;
		
		//Siwtch their position
		T[] temp = matrix[row1];
		matrix[row1] = matrix[row2];
		matrix[row2] = temp;
	}

	/**
	 * Switch the elements in two different columns in the matrix.
	 * @param col1 - The index of the first column
	 * @param col2 - The index of the second column
	 */
	public void switchCol(int col1, int col2) {
		if(col1 < 0 || col2 < 0)
			throw new MatrixException("The index row cannot smaller than 0");
		if(col1 >= cols || col2 >= cols)
			throw new MatrixException("Index out of bound");
		if(col1 == col2)
			return;
		
		for(int i=0;i<rows;i++){
			T temp = matrix[i][col1];
			matrix[i][col1] = matrix[i][col2];
			matrix[i][col2] = temp;
		}
		
	}
	
	/**
	 * Get the String that contains all elements in the matrix, in form of a matrix.<br>
	 * There is a tab (\t) between each element
	 * @return The string that contains all elements in the matrix
	 */
	@Override
	public String toString(){
		if(matrix == null)
			return "Empty";
		
		StringBuilder result = new StringBuilder();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				result.append(matrix[i][j]+"\t");
			}
			if(i != matrix.length-1)
				result.append("\n");
		}
		return result.toString();
	}
	
	//Check if rows are have the same length
	private void checkRows(T[][] matrix){
		//Get the first row length
		int length = matrix[0].length;
		
		for(int i=0;i<matrix.length;i++){
			if(length != matrix[i].length)
				throw new MatrixException("Rows in matrix must have the same length");
		}
	}
	
	//Check if index is smaller than 0 or index is out of bound
	private void indexCheck(int row , int col){
		if(row < 0 || col < 0)
			throw new MatrixException("The index row and column cannot smaller than 0");
		if(row >= rows || col >= cols)
			throw new MatrixException("Index out of bound");
	}
}
