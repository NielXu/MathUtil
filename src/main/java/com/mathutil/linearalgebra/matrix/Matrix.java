package com.mathutil.linearalgebra.matrix;

import java.math.BigDecimal;
import java.util.Random;

import com.mathutil.MathUtil;
import com.mathutil.exceptions.MatrixCalculationException;
import com.mathutil.exceptions.MatrixException;
import com.mathutil.linearalgebra.SuperVector;

/**
 * Matrix is the class that can be used to do calcultions such as <code>substract(Matrix m)</code> 
 * or <code>add(Matrix m)</code>. It also implements the interface Matrixable, it means some basic functions such 
 * as <code>switchRows(int row1 , int row2)</code> and <code>switchCols(int col1 , int col2)</code> can also be used. 
 * Please keep in mind that no matter what number type as the input, the calculation and the result will use double. 
 * Matrix is immutable, which means, you cannot expand the matrix or remove the elements in the matrix. 
 * However, you can switch rows and columns in the matrix, you can get the element from the matrix or you can replace the 
 * element in the specific location to a new one.<br>
 * If the purpose of the matrix is to store informatoins only, please use <code>SimpleMatrix</code> instead.<br>
 * If the calculations are long and complex, please consider using {@link ExactMatrix}
 *
 * @author danielxu
 * @see ExactMatrix
 * @see SimpleMatrix
 * @see Matrixable
 */
public class Matrix implements Matrixable<Number>{
	
	/**The matrix that stores the informations**/
	private double[][] matrix;
	
	/**The number of rows and columns**/
	private int rows , cols;
	
	/**
	 * Create an empty matrix that has no elements in it
	 */
	public Matrix(){
		rows = 0;
		cols = 0;
	}
	
	/**
	 * Convert vectors to a matrix, they must have the same dimension in order to be converted. The vectors can be converted as row or column in the matrix.
	 * @param asRow - The vectors as row or not, true to be row, false to be column.
	 * @param vectors - The vectors
	 * @see #Matrix(SuperVector...)
	 */
	public Matrix(boolean asRow , SuperVector...vectors){
		if(vectors == null || vectors[0] == null)
			throw new MatrixException("The vectors cannot be null");
		
		int dimension = vectors[0].getDimension();
		//Check dimensions
		for(int i=0;i<vectors.length;i++){
			if(dimension != vectors[i].getDimension())
				throw new MatrixException("The vectors must have the same dimension in order to be converted to a matrix");
		}
		
		//Vectors as row
		if(asRow){
			matrix = new double[vectors.length][dimension];
			
			for(int i=0;i<vectors.length;i++){
				if(vectors[i] == null)
					throw new MatrixException("The vectors cannot be null");
				
				for(int j=0;j<dimension;j++){
					matrix[i][j] = vectors[i].getComponents()[j];
				}
			}
		}
		//Vectors as columns
		else{
			matrix = new double[dimension][vectors.length];
			for(int i=0;i<dimension;i++){
				for(int j=0;j<vectors.length;j++){
					if(vectors[j] == null)
						throw new MatrixException("The vectors cannot be null");
					matrix[i][j] = vectors[j].getComponents()[i];
				}
			}
		}
		
		rows = matrix.length;
		cols = matrix[0].length;
	}
	
	/**
	 * Convert vectors to a matrix, they must have the same dimension in order to be converted. The vectors are as row in the matrix.
	 * @param vectors - The vectors
	 * @see #Matrix(boolean, SuperVector...)
	 */
	public Matrix(SuperVector...vectors){
		this(true , vectors);
	}
	
	/**
	 * Create a new matrix that is calculable, it stores the elements in a two dimensional array. 
	 * Please remember that the length of each row in the matrix should always be the same.
	 * If the purpose of the matrix is just to store informations, please use <code>SimpleMatrix</code> instead.
	 * @param matrix - The two dimensional array that stores all the elements
	 * @see SimpleMatrix
	 */
	public Matrix(Number[][] matrix){
		if(matrix == null)
			throw new MatrixException("The matrix cannot be null");
		checkRows(matrix);
		this.matrix = new double[matrix.length][matrix[0].length];
		
		rows = matrix.length;
		cols = matrix[0].length;
		
		//Deep clone from the array, modify the input array will not affect the array that stores in matrix
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix[0].length;j++)
				this.matrix[i][j] = convertToDouble(matrix[i][j]);
	}
	
	/**
	 * Generate a new random matrix. All the elements in the matrix are random numbers 
	 * within the given range.
	 * @param row - The row of the matrix
	 * @param col - The column of the matrix
	 * @param min - The lower bound of the random number, inclusive
	 * @param max - The upper bound of the random number, inclusive
	 * @param integer - If the numbers should be integer or double, true to be integer, false to be double
	 * @return The new random matrix
	 */
	public static Matrix randomMatrix(int row , int col , double min , double max , boolean integer){
		if(row <= 0 || col <= 0)
			throw new MatrixException("Illegal dimension of the matrix "+row+" * "+col);
		
		Number[][] m = new Number[row][col];
		Random r = new Random();
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(integer) m[i][j] = min + r.nextInt((int)max - (int)min + 1);
				else m[i][j] = min + (max - min) * r.nextDouble();
			}
		}
		return new Matrix(m);
	}
	
	/**
	 * Get the number of rows in the matrix
	 * @return The number of rows in the matrix
	 */
	public int getRows(){
		return rows;
	}
	
	/**
	 * Get the number of columns in the matrix
	 * @return The number of columns in the matrix
	 */
	public int getCols(){
		return cols;
	}
	
	/**
	 * Set the element in the matrix to a new one
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 */
	public void set(int row, int col, Number value) {
		indexCheck(row , col);
		double val = convertToDouble(value);
		matrix[row][col] = val;
	}

	/**
	 * Get the element that at the specific row and column
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 * @return The element that at the specific row and column
	 */
	public Number get(int row, int col) {
		indexCheck(row , col);
		return matrix[row][col];
	}

	/**
	 * Switch the elements in two different rows in the matrix.
	 * @param row1 - The index of the first row
	 * @param row2 - The index of the second row
	 */
	public void switchRow(int row1, int row2) {
		if(row1 < 0 || row2 < 0)
			throw new MatrixException("The index row cannot smaller than 0");
		if(row1 >= cols || row2 >= cols)
			throw new MatrixException("Index out of bound");
		if(row1 == row2)
			return;
		
		//Siwtch their position
		double[] temp = matrix[row1];
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
			double temp = matrix[i][col1];
			matrix[i][col1] = matrix[i][col2];
			matrix[i][col2] = temp;
		}
	}
	
	/**
	 * Multiply a row of the matrix by a given factor.
	 * @param row - The row that will be multiplied
	 * @param factor - The factor
	 */
	public void multiplyRow(int row , Number factor){
		if(row > rows)
			throw new MatrixCalculationException("Index out of bound "+row);
		
		for(int i=0;i<cols;i++){
			matrix[row][i] *= factor.doubleValue();
		}
	}
	
	/**
	 * Multiply a column of the matrix by a given factor.
	 * @param col - The column that will be multiplied
	 * @param factor - The factor
	 */
	public void multiplyCol(int col , Number factor){
		if(col > cols)
			throw new MatrixCalculationException("Index out of bound "+col);
		
		for(int i=0;i<rows;i++){
			matrix[i][col] *= factor.doubleValue();
		}
	}
	
	/**
	 * Get the subMatrix of this matrix.
	 * @param start_row - (Inclusive)The starting row for the subMatrix, it means where the subMatrix row starts from in the original matrix
	 * @param end_row - (Inclusive)The ending row for the subMatrix, it means where the subMatrix row ends at in the original matrix
	 * @param start_col - (Inclusive)The starting column for the subMatrix, it means where the subMatrix column starts from in the original matrix
	 * @param end_col - (Inclusive)The ending column for the subMatrix, it means where the subMatrix column ends at in the original matrix
	 * @return The subMatrix with size: (end_row - start_row + 1) * (end_col - start_col + 1)
	 */
	public Matrix subMatrix(int start_row, int end_row, int start_col, int end_col) {
		//Swicth the bounds if the lower bound is greater than the upper bound
		if(start_row > end_row){
			int temp = start_row;
			start_row = end_row;
			end_row = temp;
		}
		if(start_col > end_col){
			int temp = start_col;
			start_col = end_col;
			end_col = temp;
		}
		//Out of bound
		if(start_row < 0 || start_row >= rows || end_col < 0 || end_col >= cols)
			throw new MatrixException("Index out of bound");
		
		int indexI = 0 ,indexJ = 0;
		Number[][] b = new Number[(end_row - start_row)+1][(end_col - start_col)+1];
		for(int i=start_row;i<=end_row;i++){
			for(int j=start_col;j<=end_col;j++){
				b[indexI][indexJ] = matrix[i][j];
				indexJ++;
			}
			indexI++;
			indexJ = 0;
		}
		return new Matrix(b);
	}
	
	/**
	 * Get if the matrix is empty.
	 * @return True if it is empty, false otherwise
	 */
	public boolean isEmpty(){
		return matrix == null;
	}
	
	/**
	 * Convert this <code>Matrix</code> to a <code>ExactMatrix</code>, please notice that there might be accuracy loss during the convert process.
	 * @return The ExactMatrix
	 */
	public ExactMatrix toExactMatrix(){
		BigDecimal[][] b = new BigDecimal[rows][cols];
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				b[i][j] = new BigDecimal(matrix[i][j]);
			}
		}
		return new ExactMatrix(b);
	}
	
	/**
	 * Do addition between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A+B = B+A </center><br>
	 * Which means, <code>matrix1.addition(matrix2)</code> and <code>matrix2.addition(matrix1)</code> will get the same result.
	 * 
	 * @param m - Another matrix that have the same dimensions
	 * @return A new Matrix that has the sum of the two matrices
	 */
	public Matrix add(Matrix m) {
		matrixCheck(m);
		//Check if two matrices have the same dimensions
		if(m.getRows() != getRows() || m.getCols() != getCols())
			throw new MatrixCalculationException("Addition can only be applied on two matrices that have the same dimensions");
		
		int row = m.getRows();
		int col = m.getCols();
		
		Number[][] result = new Number[row][col];
		Number[][] m2 = m.getMatrix();
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				double num1 = convertToDouble(m2[i][j]);
				double num2 = matrix[i][j];
				result[i][j] = num1 + num2;
			}
		}
		return new Matrix(result);
	}
	
	/**
	 * Do subtraction between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A-B ≠ B-A </center><br>
	 * Which means, <code>matrix1.substract(matrix2)</code> and <code>matrix2.substract(matrix1)</code> will 
	 * <strong>NOT</strong> get the same result.
	 * 
	 * @param m - Another matrix that have the same dimensions
	 * @return A new Matrix that has the difference of the two matrices
	 */
	public Matrix substract(Matrix m) {
		matrixCheck(m);
		//Check if two matrices have the same dimensions
		if(m.getRows() != getRows() || m.getCols() != getCols())
			throw new MatrixCalculationException("Substraction can only be applied on two matrices that have the same dimensions");
		
		int row = m.getRows();
		int col = m.getCols();
		
		Number[][] result = new Number[row][col];
		Number[][] m2 = m.getMatrix();
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				double num1 = convertToDouble(matrix[i][j]);
				double num2 = convertToDouble(m2[i][j]);
				result[i][j] = num1 - num2;
			}
		}
		return new Matrix(result);
	}
	
	/**
	 * Do multiplication between two matrices. The operation can only be applied when the number of columns of the matrix on the left side 
	 * is equal to the number of rows of the matrix on the right side. It means, if the dimensions of the left matrix is: <i>m*n</i>, the dimensions 
	 * of the right matrix is <i>p*q</i>, the multiplication can only be applied when <i>n=p</i>. And the size of the result matrix will 
	 * be <i>m*q</i>. For example, matrixA <i>3*2</i> and MatrixB <i>2*4</i> matrix will produce a <i>3*4</i> matrix.<br>
	 * And in matrix multiplication:<br>
	 * <center>A*B ≠ B*A</center><br>
	 * Which means, <code>matrix1.multiply(matrix2)</code> will <strong>NOT</strong> get the same result with <code>matrix2.multiply(matrix1)</code>
	 * 
	 * @param m - Another CalculableMatrix 
	 * @return The new Matrix that contains the result of the multiplication
	 */
	public Matrix multiply(Matrix m) {
		matrixCheck(m);
		//Check if two matrices have the same dimensions
		if(getCols() != m.getRows())
			throw new MatrixCalculationException("Multiplication can only be applied on two matrices that,\n"
					+ "left side matrix's columns number equals to right side matrix's rows number");
		
		//Matrix1: m*n , Matrix2: p*q , M1*M2 = m*q
		int row = rows;
		int col = m.getCols();
		
		Number[][] result = new Number[row][col];
		Number[][] m2 = m.getMatrix();
		//Initialize the number to 0
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[0].length;j++){
				result[i][j] = 0d;
			}
		}
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<m2[0].length;j++){
				for(int k=0;k<matrix[0].length;k++){
					double num1 = convertToDouble(matrix[i][k]);
					double num2 = convertToDouble(m2[k][j]);
					double temp = convertToDouble(result[i][j]);
					result[i][j] = temp+(num1*num2);
				}
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Multiply the whole matrix by a factor. if the original matrix is A, the factor is k, the new matrix will be:<br>
	 * <center>k*A</center><br>
	 * <center>k*A = A*k</center>
	 * Please notice that this method is different from <code>multiply(Matrix)</code>, k here means a factor but not a matrix
	 * 
	 * @param factor - The factor
	 * @return The new matrix that contains the result
	 */
	public Matrix factor(Number factor) {
		if(factor == null)
			throw new MatrixCalculationException("The factor cannot be null");
		
		Number[][] result = new Number[rows][cols];
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				double fac = convertToDouble(factor);
				double origin = convertToDouble(matrix[i][j]);
				result[i][j] = origin*fac;
			}
		}
		return new Matrix(result);
	}
	
	/**
	 * Transpose the matrix, if the size of the original matrix is m*n, after transposed, the size will be n*m
	 * @return The matrix after transposed.
	 */
	public Matrix transpose(){
		Number[][] result = new Number[cols][rows];
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				result[j][i] = matrix[i][j];
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Raise the matrix to the power of n. The result will be rounded to two decimal places in order to eliminate the accuracy loss<br>
	 * <ul>
	 * <li>If n = 0, get the identity matrix. There's a property, if I is the identity matrix of matrix A then: <br><center>A*I = I*A = A</center></li>
	 * <li>If n > 0, it's the same as mutiply the matrix for n times</li>
	 * <li>If n < 0, it's the same as multiply the matrix for n times then invert it. There's a property:<br>
	 * 		<center>A^-1 * A = I  &nbsp or  &nbsp  A*A^-1 = I</center><br>
	 * 		if the matrix is the identity matrix I, then<br>
	 * 		<center>I^-1 = I</center><br>
	 * 		
	 * </ul>
	 * @param n - The power, can be positive integer, negative integer, or 0
	 * @return If n = 0, return the identity of the matrix. If n > 0, return the matrix that raise to the power of n. 
	 * If n < 0, return the inverse of the matrix to the power of n.
	 */
	public Matrix power(int n) {
		//Identity matrix
		if(n == 0){
			Number[][] result = new Number[rows][cols];
			int index = 0;
			for(int i=0;i<result.length;i++){
				for(int j=0;j<result[0].length;j++){
					result[i][j] = 0;
					if(j == index) result[i][j] = 1;
				}
				index ++;
			}
			return new Matrix(result);
		}
		
		//Raise to the power of n
		else if(n > 0){
			if(n == 1)
				return this;
			if(getRows() != getCols())
				throw new MatrixCalculationException("Only square matrix can be raised to the power of "+n);
			//Keep multiplying itslef
			Matrix m = this;
			for(int i=1;i<n;i++){
				m = m.multiply(this);
			}
			return m;
		}
		
		//Inverse
		else{
			if(getRows() != getCols())
				throw new MatrixCalculationException("Only square matrix can be raised to the power of "+n);
			//Only one element in the matrix
			if(rows == 1 && cols == 1){
				if(matrix[0][0] == 0)
					throw new MatrixCalculationException("The determinant is 0, it is invertible");
				
				double r = 1 / matrix[0][0];
				Number[][] result = new Number[][]{
					{r}
				};
				return new Matrix(result);
			}
			//It's not Invertible
			if(determinant(matrix) == 0)
				throw new MatrixCalculationException("The determinant of the matrix is 0, it is invertible");
			
			if(n == -1){
				double[][] r = invert(deepClone()); //invert the matrix
				return new Matrix(convertToNumber(r));
			}
			else{
				Matrix m = this.power(Math.abs(n)).power(-1);
				return m;
			}
		}
	}
	
	/**
	 * Invert the matrix, it's the same as saying <code>power(-1)</code>. Please notice that not every matrix can be inverted, only square matrices that have 
	 * the non-zero determinant can be inverted. Call <code>det()</code> can get the determinant of the matrix.
	 * @return The inverted matrix
	 * @see {@link #power(int)}
	 */
	public Matrix invert(){
		return power(-1);
	}
	
	/**
	 * Find the determinant of the matrix. Please notice that only <strong>square matrices</strong> have determinant, it means only the 
	 * matrices that have dimension n*n have determinant
	 * @return The determinant of the matrix as a number
	 */
	public double det() {
		if(getRows() != getCols())
			throw new MatrixCalculationException("Only square matrix has determinant");
		
		return determinant(matrix);
	}
	
	/**
	 * Get the Reduced Row Echelon Form(rref) of the matrix, any size of matrix can be transformed to the rref.
	 * @return The rref of the current matrix
	 */
	public Matrix rref(){
		return new Matrix(convertToNumber(calculateRREF()));
	}
	
	/**
	 * Get the rank of the matrix. The rank of a matrix A is the dimension of the vector space generated (or spanned) by its columns.
	 * @return The rank of the matrix
	 */
	public int rank(){
		double[][] m = calculateRREF();
		int rank = 0 , zeros = 0;
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				if(m[i][j] == 0)
					zeros++;
			}
			if(zeros != cols)
				rank++;
			zeros = 0;
		}
		return rank;
	}
	
	/**
	 * Check if the matrix is full rank. Full rank means the rank of the matrix is equal to the numebr of rows of the matrix
	 * @return True if it is full rank, false otherwise
	 */
	public boolean fullRank(){
		return rank() == rows;
	}
	
	/**
	 * Get the two dimensional array that represents the matrix, this method will return a copy of the 
	 * array but not the real array, therefore, the return array can be modified and it will not affect the 
	 * original array that stores in matrix.
	 * @return The two dimensional array that represents the matrix
	 */
	public Number[][] getMatrix(){
		return convertToNumber(matrix);
	}
	
	/**
	 * Get the String that contains all elements in the matrix, in form of a matrix.<br>
	 * There is a tab (\t) between each element. There is an empty line(\n) at the end of the matrix
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
	
	//Calculate the rref
	private double[][] calculateRREF() {
		double[][] m = deepClone();
		int lead = 0;
		int rowCount = m.length;
		int colCount = m[0].length;
		int i;
		boolean quit = false;

		//Only on column or one element
		if(m[0].length == 1){
			m[0][0] = 1;
			for(int k=1;k<m.length;k++){
				m[k][0] = 0;
			}
			return m;
		}
		
		for (int row = 0; row < rowCount && !quit; row++) {
			if (colCount <= lead){
				quit = true;
				break;
			}
			i = row;

			while (!quit && m[i][lead] == 0){
				i++;
				if (rowCount == i){
					i = row;
					lead++;

					if (colCount == lead){
						quit = true;
						break;
					}
				}
			}

			if (!quit){
				rrefSwap(m, i, row);

				if (m[row][lead] != 0)
					rrefMulti(m, row, 1.0f / m[row][lead]);

				for (i = 0; i < rowCount; i++){
					if (i != row)
						rrefSub(m, m[i][lead], row, i);
				}
			}
		}
		return m;
	}
	
	//rref calculation swap two rows
	private void rrefSwap(double[][] m, int row1, int row2) {
		double[] swap = new double[m[0].length];

		for (int c1 = 0; c1 < m[0].length; c1++)
			swap[c1] = m[row1][c1];

		for (int c1 = 0; c1 < m[0].length; c1++) {
			m[row1][c1] = m[row2][c1];
			m[row2][c1] = swap[c1];
		}
	}

	//rref calculation, multiply row
	private void rrefMulti(double[][] m, int row, double scalar) {
		for (int c1 = 0; c1 < m[0].length; c1++)
			m[row][c1] *= scalar;
	}

	//rref calculation, substraction
	private void rrefSub(double[][] m, double scalar, int subtract_scalar_times_this_row, int from_this_row) {
		for (int c1 = 0; c1 < m[0].length; c1++)
			m[from_this_row][c1] -= scalar * m[subtract_scalar_times_this_row][c1];
	}
	
	//Find the determinant recursively
	private double determinant(double[][] arr) {
		double result = 0;
		if (arr.length == 1) {
			result = convertToDouble(arr[0][0]);
			return result;
		}
		if (arr.length == 2) {
			result = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
			return result;
		}
		for (int i=0; i<arr[0].length;i++) {
			double temp[][] = new double[arr.length - 1][arr[0].length - 1];
			for (int j=1;j<arr.length;j++) {
				for (int k=0;k<arr[0].length;k++) {
					if (k < i) {
						temp[j-1][k] = arr[j][k];
					} 
					else if (k > i) {
						temp[j-1][k-1] = arr[j][k];
					}
				}
			}
			result += arr[0][i] * Math.pow(-1, (int) i) * determinant(temp);
		}
		return result;
	}
	
	//Invert
	private double[][] invert(double a[][]) {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
        //Transform the matrix into an upper triangle
        gaussian(a, index);
        
        //Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]-= a[index[j]][i]*b[index[i]][k];
 
        //Perform backward substitutions
        for (int i=0; i<n; ++i) {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        
        for(int i=0;i<x.length;i++){
        	for(int j=0;j<x[0].length;j++){
        		x[i][j] = MathUtil.round(x[i][j] , 2);
        	}
        }
        return x;
    }
 
	//Method to carry out the partial-pivoting Gaussian
	//elimination.  Here index[] stores pivoting order.
    private void gaussian(double a[][], int index[]) {
        int n = index.length;
        double c[] = new double[n];
 
        // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
        //Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) {
            double c1 = 0;
            for (int j=0; j<n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
        //Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) {
            double pi1 = 0;
            for (int i=j; i<n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }
 
            //Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	{
                double pj = a[index[i]][j]/a[index[j]][j];
 
                //Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
                //Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }
	
    //Get a copy of the matrix
    //deep clone the two dimensional array, prevent from modifying the original values
    private double[][] deepClone(){
    	double[][] r = new double[rows][cols];
    	for(int i=0;i<r.length;i++){
    		for(int j=0;j<r[0].length;j++){
    			r[i][j] = matrix[i][j];
    		}
    	}
    	return r;
    }
    
    //Get a copy of the matrix
    //this method deep clone the two dimensional array and return Number type
    private Number[][] convertToNumber(double[][] matrix){
    	Number[][] r = new Number[rows][cols];
    	for(int i=0;i<r.length;i++){
    		for(int j=0;j<r[0].length;j++){
    			r[i][j] = matrix[i][j];
    		}
    	}
    	return r;
    }
    
	//Check if the matrix is null, if the array is empty or if the array contain elements
	private void matrixCheck(Matrix m){
		if(m == null || m.getMatrix() == null || m.getRows() == 0 || m.getCols() == 0)
			throw new MatrixCalculationException("The matrix can not be null");
	}
	
	//Cast a number to double in order to do the calculation
	private double convertToDouble(Number num){
		return num.doubleValue();
	}
	
	//Check if rows are have the same length
	private void checkRows(Number[][] matrix){
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
