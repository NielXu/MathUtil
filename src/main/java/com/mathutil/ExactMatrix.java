package com.mathutil;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

import com.mathutil.exceptions.MatrixCalculationException;
import com.mathutil.exceptions.MatrixException;
import com.mathutil.linearalgebra.SuperVector;

/**
 * ExactMatrix is the highest accuracy matrix. It has almost the same functions with <code>Matrix</code>, it can do various kinds of calculations in matrix such as 
 * <code>add(ExactMatrix)</code> or <code>subtract(ExactMatrix)</code>. But the calculations in <code>ExactMatrix</code> 
 * are using BigDecimal, which has the higher accuracy compare with double. Normally, when doing some ordinary calculations, <code>ExactMatrix</code> is not 
 * necessary. But when accuracy it's matter, for example, when one wants the exact result after a long and complex calculation, <code>ExactMatrix</code> is necessary.<br>
 * ExactMatrix is immutable, which means, you cannot expand the matrix or remove the elements in the matrix. However, you can switch rows and columns in the matrix, 
 * you can get the element from the matrix or you can replace the element in the specific location to a new one.<br>
 * If the calculations are small and easy, please consider using {@link Matrix}.<br>
 * If the purpose of the matrix is to store informatoins only, please use {@link SimpleMatrix} instead.
 * 
 * @author danielxu
 * @see Matrix
 * @see Matrixable
 * @see SimpleMatrix
 */
public class ExactMatrix implements Matrixable<BigDecimal>{

	/**The rows and columns of the matrix**/
	private int rows , cols;
	
	/**The two dimensional array that represents the matrix**/
	private BigDecimal[][] matrix;
	
	/**How many decimal places will be displayed**/
	private int string_decimal_places;
	
	/**The precision**/
	private int precision;
	
	/**The default precision**/
	public static final int DEFAULT_PRECISION = 12;
	
	/**The MathContext that will be applied in the calculations**/
	private MathContext context;
	
	/**
	 * Create an empty matrix without any elements in it
	 */
	public ExactMatrix(){
		rows = 0;
		cols = 0;
	}
	
	/**
	 * Convert vectors to a matrix, they must have the same dimension in order to be converted. The vectors can be converted as row or column in the matrix.
	 * 
	 * @param asRow - The vectors as row or not, true to be row, false to be column.
	 * @param precision - The precision of the calculation, the minimum is 8 and the maximum is 25, the default is 12. If the precision given is out 
	 * 					  of the range, it will be set to the default which is 12
	 * @param vectors - The vectors
	 * @see #ExactMatrix(SuperVector...)
	 */
	public ExactMatrix(boolean asRow , int precision , SuperVector...vectors){
		if(vectors == null || vectors[0] == null)
			throw new MatrixException("The vectors cannot be null");
		
		int dimension = vectors[0].getDimension();
		//Check dimensions
		for(int i=0;i<vectors.length;i++){
			if(dimension != vectors[i].getDimension())
				throw new MatrixException("The vectors must have the same dimension in order to be converted to a matrix");
		}
		//Cheeck precision
		if(precision < 8)
			precision = 12;
		if(precision > 25)
			precision = 12;
		this.precision = precision;
		
		//Vectors as row
		if(asRow){
			matrix = new BigDecimal[vectors.length][dimension];
			
			for(int i=0;i<vectors.length;i++){
				if(vectors[i] == null)
					throw new MatrixException("The vectors cannot be null");
				
				for(int j=0;j<dimension;j++){
					matrix[i][j] = new BigDecimal(vectors[i].getComponents()[j]);
				}
			}
		}
		//Vectors as columns
		else{
			matrix = new BigDecimal[dimension][vectors.length];
			for(int i=0;i<dimension;i++){
				for(int j=0;j<vectors.length;j++){
					if(vectors[j] == null)
						throw new MatrixException("The vectors cannot be null");
					matrix[i][j] = new BigDecimal(vectors[j].getComponents()[i]);
				}
			}
		}
		
		init();
	}
	
	/**
	 * Convert vectors to a matrix, they must have the same dimension in order to be converted. The vectors are as row in the matrix. 
	 * This constructor will use the default precision which is 12.
	 * @param vectors - The vectors
	 * @see #ExactMatrix(boolean, int, SuperVector...)
	 */
	public ExactMatrix(SuperVector...vectors){
		this(true , DEFAULT_PRECISION , vectors);
	}
	
	/**
	 * Create a new matrix, using two dimensional BigDecimal array to represent the numbers. Another recommendatory 
	 * constructor is {@link #ExactMatrix(String[][])}, for higher acurracy. This constructor set the default precision 
	 * to 12, call {@link ExactMatrix#ExactMatrix(BigDecimal[][], int)} to set the precision manually.
	 * @param matrix - The two dimensional BigDecimal array represents the matrix
	 */
	public ExactMatrix(BigDecimal[][] matrix){
		this(matrix ,  DEFAULT_PRECISION);
	}
	
	/**
	 * Create a new matrix, using two dimensional BigDecimal array to represent the numbers. Another recommendatory 
	 * constructor is {@link #ExactMatrix(String[][])}, for higher acurracy.
	 * @param matrix - The two dimensional BigDecimal array represents the matrix
	 * @param precision - The precision of the calculation, the minimum is 8 and the maximum is 25, the default is 12. If the precision given is out 
	 * 					  of the range, it will be set to the default which is 12
	 */
	public ExactMatrix(BigDecimal[][] matrix , int precision){
		if(matrix == null)
			throw new MatrixException("The matrix cannot be null");
		checkRows(matrix);
		
		this.matrix = new BigDecimal[matrix.length][matrix[0].length];
		if(precision < 8)
			precision = 12;
		if(precision > 25)
			precision = 12;
		this.precision = precision;
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				this.matrix[i][j] = matrix[i][j];
			}
		}
		init();
	}
	
	/**
	 * Create a new matrix, using two dimensional String array to represent the numbers. The numbers will be exactly the 
	 * same as the Strings represent. This constructor is more preferable compare with {@link #ExactMatrix(Number[][])}. 
	 * This constructor set the default precision is 12, call {@link #ExactMatrix(String[][], int)} to set the precision manually.
	 * @param matrix - The two dimensional String array represents the matrix
	 */
	public ExactMatrix(String[][] matrix){
		this(matrix ,  DEFAULT_PRECISION);
	}
	
	/**
	 * Create a new matrix, using two dimensional String array to represent the numbers. The numbers will be exactly the 
	 * same as the Strings represent. This constructor is more preferable compare with {@link #ExactMatrix(Number[][])}
	 * @param matrix - The two dimensional String array represents the matrix
	 * @param precision - The precision of the calculation, the minimum is 8 and the maximum is 25, the default is 12. If the precision given is out 
	 * 					  of the range, it will be set to the default which is 12
	 */
	public ExactMatrix(String[][] matrix , int precision){
		if(matrix == null)
			throw new MatrixException("The matrix cannot be null");
		checkRows(matrix);
		
		this.matrix = new BigDecimal[matrix.length][matrix[0].length];
		if(precision < 8)
			precision = 12;
		if(precision > 25)
			precision = 12;
		this.precision = precision;
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				this.matrix[i][j] = new BigDecimal(matrix[i][j]);
			}
		}
		init();
	}
	
	/**
	 * Create a new matrix, using two dimensional Number array to represent the numbers. The numbers might be different and 
	 * lead to accuracy loss. Some extra decimal places will be added at the end of each number, therefore, the numbers might not 
	 * be exactly the same. The more preferable way is to use constructor {@link #ExactMatrix(String[][])} or {@link #ExactMatrix(BigDecimal[][])}. 
	 * This constructor set the default precision to 12, call {@link ExactMatrix#ExactMatrix(Number[][], int)} to set it manually.
	 * @param matrix - The two dimensional Number array represents the matrix
	 */
	public ExactMatrix(Number[][] matrix){
		this(matrix ,  DEFAULT_PRECISION);
	}
	
	/**
	 * Create a new matrix, using two dimensional Number array to represent the numbers. The numbers might be different and 
	 * lead to accuracy loss. Some extra decimal places will be added at the end of each number, therefore, the numbers might not 
	 * be exactly the same. The more preferable way is to use constructor {@link #ExactMatrix(String[][])} or {@link #ExactMatrix(BigDecimal[][])}
	 * @param matrix - The two dimensional Number array represents the matrix
	 * @param precision - The precision of the calculation, the minimum is 8 and the maximum is 25, the default is 12. If the precision given is out 
	 * 					  of the range, it will be set to the default which is 12
	 */
	public ExactMatrix(Number[][] matrix , int precision){
		if(matrix == null)
			throw new MatrixException("The matrix cannot be null");
		checkRows(matrix);
		
		this.matrix = new BigDecimal[matrix.length][matrix[0].length];
		if(precision < 8)
			precision = 12;
		if(precision > 25)
			precision = 12;
		this.precision = precision;
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				this.matrix[i][j] = new BigDecimal(matrix[i][j].doubleValue());
			}
		}
		init();
	}
	
	//Initialize the rows,cols,decimal_places and MathContext
	private void init(){
		rows = matrix.length;
		cols = matrix[0].length;
		string_decimal_places = 3;
		context = new MathContext(precision , RoundingMode.HALF_UP);
	}
	
	/**
	 * Generate a new random matrix. All the elements in the matrix are random numbers within the given range. 
	 * The precision of the new matrix will be set to default which is 12.
	 * @param row - The row of the matrix
	 * @param col - The column of the matrix
	 * @param min - The lower bound of the random numbers, inclusive
	 * @param max - The upper bound of the random numbers, inclusive
	 * @param integer - If the numbers should be integer or double, true to be integer, false to be double
	 * @return The new random matrix
	 */
	public static ExactMatrix randomMatrix(int row , int col , double min , double max , boolean integer){
		if(row <= 0 || col <= 0)
			throw new MatrixException("Illegal dimension of the matrix "+row+" * "+col);
		
		BigDecimal[][] m = new BigDecimal[row][col];
		Random r = new Random();
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(integer) m[i][j] = new BigDecimal(min + r.nextInt((int)max - (int)min + 1));
				else m[i][j] = new BigDecimal(min + (max - min) * r.nextDouble());
			}
		}
		
		return new ExactMatrix(m);
	}
	
	/**
	 * Set how many decimal places will be shown in <code>toString()</code> method. The decimal places will be rounded, for example, 49.5 will be rounded to 50, 
	 * 49.4 will be rounded to 49<br>
	 * @param places - How many decimal places will be displayed
	 * <ul>
	 * <li>If places set to be 0, only the integer part will be displayed</li>
	 * <li>If places set to be greater than 0, it will display the corresponding decimal palces</li>
	 * <li>If places set to be less than 0, it will display in scientific notation</li>
	 * </ul>
	 * @return The ExactMatrix itself, for next operation
	 */
	public ExactMatrix setShowDecimal(int places){
		string_decimal_places = places;
		return this;
	}
	
	/**
	 * Get the precision of the calculations
	 * @return The precision of the calculations
	 */
	public int getPrecision(){
		return precision;
	}
	
	/**
	 * Set the element in the matrix to a new one
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 */
	public void set(int row, int col, BigDecimal value) {
		indexCheck(row , col);
		matrix[row][col] = value;
	}

	/**
	 * Get the element that at the specific row and column
	 * @param row - The row index of the element
	 * @param col - The column index of the element 
	 * @return The element that at the specific row and column
	 */
	public BigDecimal get(int row, int col) { 
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
		BigDecimal[] temp = matrix[row1];
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
			BigDecimal temp = matrix[i][col1];
			matrix[i][col1] = matrix[i][col2];
			matrix[i][col2] = temp;
		}
	}
	
	/**
	 * Multiply a row of the matrix by a given factor.
	 * @param row - The row that will be multiplied
	 * @param factor - The factor, as String
	 */
	public void multiplyRow(int row , String factor){
		multiplyRow(row , new BigDecimal(factor));
	}
	
	/**
	 * Multiply a row of the matrix by a given factor.
	 * @param row - The row that will be multiplied
	 * @param factor - The factor
	 */
	public void multiplyRow(int row , BigDecimal factor){
		if(row >= rows)
			throw new MatrixCalculationException("Index out of bound "+row);
		
		for(int i=0;i<matrix[0].length;i++){
			matrix[row][i] = matrix[row][i].multiply(factor);
		}
	}
	
	/**
	 * Multiply a column of the matrix by a given factor.
	 * @param col - The column that will be multiplied
	 * @param factor - The factor, as String
	 */
	public void multiplyCol(int col , String factor){
		multiplyCol(col , new BigDecimal(factor));
	}
	
	/**
	 * Multiply a column of the matrix by a given factor.
	 * @param col - The column that will be multiplied
	 * @param factor - The factor
	 */
	public void multiplyCol(int col , BigDecimal factor){
		if(col >= cols)
			throw new MatrixCalculationException("Index out of bound "+col);
		
		for(int i=0;i<matrix.length;i++){
			matrix[i][col] = matrix[i][col].multiply(factor);
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
	public ExactMatrix subMatrix(int start_row, int end_row, int start_col, int end_col) {
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
		
		BigDecimal[][] b = new BigDecimal[(end_row - start_row)+1][(end_col - start_col)+1];
		int indexI = 0 , indexJ = 0;
		for(int i=start_row;i<=end_row;i++){
			for(int j=start_col;j<=end_col;j++){
				b[indexI][indexJ] = matrix[i][j];
				indexJ++;
			}
			indexI++;
			indexJ = 0;
		}
		return new ExactMatrix(b);
	}
	
	/**
	 * Get if the matrix is empty.
	 * @return True if it is empty, false otherwise
	 */
	public boolean isEmpty(){
		return matrix == null;
	}
	
	/**
	 * Convert this <code>ExactMatrix</code> to a <code>Matrix</code>, please notice that there might be accuracy loss during the convert process.
	 * @return The Matrix
	 */
	public Matrix toMatrix(){
		Number[][] n = new Number[rows][cols];
		
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				n[i][j] = matrix[i][j].doubleValue();
			}
		}
		return new Matrix(n);
	}
	
	/**
	 * Get how many decimal places will be displayed
	 * @return The number of decimal places that will be displayed
	 */
	public int getDecimal(){
		return string_decimal_places;
	}
	
	/**
	 * Do addition between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A+B = B+A </center><br>
	 * Which means, <code>matrix1.addition(matrix2)</code> and <code>matrix2.addition(matrix1)</code> will get the same result.
	 * 
	 * @param m - Another matrix that have the same dimensions
	 * @return A new ExactMatrix that has the sum of the two matrices
	 */
	public ExactMatrix add(ExactMatrix m){
		matrixCheck(m);
		if(m.getRows() != getRows() || m.getCols() != getCols())
			throw new MatrixCalculationException("Addition can only be applied on two matrices that have the same dimensions");
		
		int row = m.getRows();
		int col = m.getCols();
		
		BigDecimal[][] result = new BigDecimal[row][col];
		BigDecimal[][] m2 = m.getMatrix();
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				result[i][j] = matrix[i][j].add(m2[i][j] , context);
			}
		}
		
		return new ExactMatrix(result);
	}
	
	/**
	 * Do subtraction between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A-B ≠ B-A </center><br>
	 * Which means, <code>matrix1.substract(matrix2)</code> and <code>matrix2.substract(matrix1)</code> will 
	 * <strong>NOT</strong> get the same result.
	 * 
	 * @param m - Another matrix that have the same dimensions
	 * @return A new ExactMatrix that has the difference of the two matrices
	 */
	public ExactMatrix subtract(ExactMatrix m){
		matrixCheck(m);
		if(m.getRows() != getRows() || m.getCols() != getCols())
			throw new MatrixCalculationException("Substraction can only be applied on two matrices that have the same dimensions");
		
		int row = m.getRows();
		int col = m.getCols();
		
		BigDecimal[][] result = new BigDecimal[row][col];
		BigDecimal[][] m2 = m.getMatrix();
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				result[i][j] = matrix[i][j].subtract(m2[i][j] , context);
			}
		}
		
		return new ExactMatrix(result);
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
	public ExactMatrix multiply(ExactMatrix m){
		matrixCheck(m);
		//Check if two matrices have the same dimensions
		if(getCols() != m.getRows())
			throw new MatrixCalculationException("Multiplication can only be applied on two matrices that,\n"
					+ "left side matrix's columns number equals to right side matrix's rows number");
		
		int row = getRows();
		int col = m.getCols();
		BigDecimal[][] result = new BigDecimal[row][col];
		BigDecimal[][] m2 = m.getMatrix();
		//Initialize all the numbers to 0
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[0].length;j++){
				result[i][j] = new BigDecimal("0");
			}
		}
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<m2[0].length;j++){
				for(int k=0;k<matrix[0].length;k++){
					BigDecimal num1 = matrix[i][k];
					BigDecimal num2 = m2[k][j];
					BigDecimal temp = result[i][j];
					result[i][j] = temp.add(num1.multiply(num2) , context);
				}
			}
		}
		
		return new ExactMatrix(result);
	}
	
	/**
	 * Raise the matrix to the power of n. Please notice that if the matrix size is 1*1, it's the same as saying that A^-1 = 1/A0, A0 is the 
	 * only element in the matrix<br>
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
	public ExactMatrix power(int n) {
		//Identity matrix
		if(n == 0){
			BigDecimal[][] result = new BigDecimal[rows][cols];
			int index = 0;
			for(int i=0;i<result.length;i++){
				for(int j=0;j<result[0].length;j++){
					result[i][j] = new BigDecimal("0");
					if(j == index) result[i][j] = new BigDecimal("1");
				}
				index ++;
			}
			return new ExactMatrix(result);
		}
		
		//Raise to the power of n
		else if(n > 0){
			if(n == 1)
				return this;
			if(getRows() != getCols())
				throw new MatrixCalculationException("Only square matrix can be raised to the power of "+n);
			//Keep multiplying itslef
			ExactMatrix m = this;
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
				if(matrix[0][0].compareTo(BigDecimal.ZERO) == 0)
					throw new MatrixCalculationException("The determinant is 0, it is invertible");
				
				BigDecimal result[][] = new BigDecimal[1][1]; 
				result[0][0] = BigDecimal.ONE.divide(matrix[0][0] , precision , RoundingMode.HALF_UP);
				return new ExactMatrix(result);
			}
			//It's not Invertible
			if(determinant(matrix).compareTo(BigDecimal.ZERO) == 0)
				throw new MatrixCalculationException("The determinant of the matrix is 0, it is not invertible");
			
			if(n == -1){
				BigDecimal[][] r = invert(deepClone()); //invert the matrix
				return new ExactMatrix(r);
			}
			else{
				ExactMatrix m = this.power(Math.abs(n)).power(-1);
				return m;
			}
		}
	}
	
	/**
	 * Multiply the whole matrix by a factor. if the original matrix is A, the factor is k, the new matrix will be:<br>
	 * <center>k*A</center><br>
	 * <center>k*A = A*k</center>
	 * Please notice that this method is different from <code>multiply(ExactMatrix)</code>, k here means a factor but not a matrix
	 * 
	 * @param factor - The factor
	 * @return The new matrix that contains the result
	 */
	public ExactMatrix factor(Number factor){
		if(factor == null)
			throw new MatrixCalculationException("The factor cannot be null");
		
		BigDecimal[][] result = new BigDecimal[rows][cols];
		
		BigDecimal fac = new BigDecimal(factor.doubleValue()); //convert to bigdecimal
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				result[i][j] = matrix[i][j].multiply(fac , context);
			}
		}
		return new ExactMatrix(result);
	}
	
	/**
	 * Transpose the matrix, if the size of the original matrix is m*n, after transposed, the size will be n*m.
	 * @return The matrix after transposed.
	 */
	public ExactMatrix transpose(){
		BigDecimal[][] result = new BigDecimal[cols][rows];
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				result[j][i] = matrix[i][j];
			}
		}
		return new ExactMatrix(result);
	}
	
	/**
	 * Find the determinant of the matrix. Please notice that only <strong>square matrices</strong> have determinant, it means only the 
	 * matrices that have dimension n*n have determinant
	 * @return The determinant of the matrix as a number
	 */
	public BigDecimal det(){
		if(getRows() != getCols())
			throw new MatrixCalculationException("Only square matrix has determinant");
		
		return determinant(matrix);
	}
	
	/**
	 * Invert the matrix, it's the same as saying <code>power(-1)</code>. Please notice that not every matrix can be inverted, only square matrices that have 
	 * the non-zero determinant can be inverted. Call <code>det()</code> can get the determinant of the matrix.
	 * @return The inverted matrix
	 * @see {@link #power(int)}
	 */
	public ExactMatrix invert(){
		return power(-1);
	}
	
	/**
	 * Get the Reduced Row Echelon Form(rref) of the matrix, any size of matrix can be transformed to the rref.
	 * @return The rref of the current matrix
	 */
	public ExactMatrix rref(){
		return new ExactMatrix(calculateRREF());
	}
	
	/**
	 * Get the rank of the matrix
	 * @return The rank of the matrix
	 */
	public int rank(){
		BigDecimal[][] m = calculateRREF();
		int rank = 0 , zeros = 0;
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				if(m[i][j].setScale(6, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0)
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
	public BigDecimal[][] getMatrix() {
		return deepClone();
	}
	
	/**
	 * Get the String that contains all elements in the matrix, in form of a matrix.<br>
	 * There is a tab (\t) between each element. There is an empty line(\n) at the end of the matrix.<br> 
	 * The decimals that being displayed will affected by the variable <code>string_decimal_places</code>, which 
	 * can be set by the method {@link #setShowDecimal(int)}. The default decimal places that will be showed is 3.
	 * @return The string that contains all elements in the matrix
	 */
	@Override
	public String toString(){
		if(matrix == null)
			return "Empty";
		
		StringBuilder result = new StringBuilder();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				result.append(matrix[i][j].setScale(string_decimal_places, BigDecimal.ROUND_HALF_UP)+"\t");
			}
			result.append("\n");
		}
		return result.toString();
	}
	
	//Calculate the rref of the matrix
	private BigDecimal[][] calculateRREF() {
		BigDecimal[][] matrix = deepClone();
		int lead = 0;
		int rowCount = matrix.length;
		int colCount = matrix[0].length;
		int i;
		boolean quit = false;
		
		//Only one column, or only 1 element
		if(matrix[0].length == 1){
			matrix[0][0] = new BigDecimal("1");
			for(int k=1;k<matrix.length;k++){
				matrix[k][0] = new BigDecimal("0");
			}
			return matrix;
		}
		
		for (int row = 0; row < rowCount && !quit; row++){
			if (colCount <= lead){
				quit = true;
				break;
			}
			i = row;
			
			while (!quit && matrix[i][lead].setScale(6, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) == 0){
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
				rrefSwap(matrix , i, row);
				if (matrix[row][lead].compareTo(BigDecimal.ZERO) != 0)
					rrefMulti(matrix , row, BigDecimal.ONE.divide(matrix[row][lead] , context));

				for (i = 0; i < rowCount; i++){
					if (i != row)
						rrefSub(matrix ,matrix[i][lead], row, i);
				}
			}
		}
		return matrix;
	}
	
	//For rref calculation, multiply a row
	private void rrefMulti(BigDecimal[][] matrix , int row , BigDecimal factor){
		for(int i=0;i<matrix[0].length;i++){
			matrix[row][i] = matrix[row][i].multiply(factor , context);
		}
	}
	
	//For rref calaulation, swaps two rows
	private void rrefSwap(BigDecimal[][] matrix , int row1, int row2) {
		BigDecimal[] swap = new BigDecimal[matrix[0].length];

		for (int c1 = 0; c1 < matrix[0].length; c1++)
			swap[c1] = matrix[row1][c1];

		for (int c1 = 0; c1 < matrix[0].length; c1++) {
			matrix[row1][c1] = matrix[row2][c1];
			matrix[row2][c1] = swap[c1];
		}
	}
	
	//For rref calculation, subtraction
	private void rrefSub(BigDecimal[][] matrix , BigDecimal scalar, int r1, int r2) {
		for (int c1 = 0; c1 < matrix[0].length; c1++)
			matrix[r2][c1] = matrix[r2][c1].subtract(matrix[r1][c1].multiply(scalar , context) , context);
	}
	
	//Find the determinant recursively
	private BigDecimal determinant(BigDecimal[][] arr) {
		BigDecimal result = new BigDecimal("0");
		if (arr.length == 1) {
			return arr[0][0];
		}
		if (arr.length == 2) {
			BigDecimal num1 = arr[0][0].multiply(arr[1][1] , context);
			BigDecimal num2 = arr[0][1].multiply(arr[1][0] , context);
			result = num1.subtract(num2);
			return result;
		}
		for (int i=0; i<arr[0].length;i++) {
			BigDecimal temp[][] = new BigDecimal[arr.length - 1][arr[0].length - 1];
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
			BigDecimal num1 = arr[0][i];
			BigDecimal num2 = new BigDecimal(Math.pow(-1 , (int)i));
			result = result.add(num1.multiply(num2 , context).multiply(determinant(temp) , context) , context);
		}
		return result;
	}
	
	//Invert
	private BigDecimal[][] invert(BigDecimal a[][]) {
        int n = a.length;
        BigDecimal x[][] = new BigDecimal[n][n];
        BigDecimal b[][] = new BigDecimal[n][n];
        int index[] = new int[n];
        for(int i=0;i<b.length;i++){
        	for(int j=0;j<b[0].length;j++){
        		b[i][j] = new BigDecimal("0");
        	}
        }
        for (int i=0; i<n; ++i) 
            b[i][i] = new BigDecimal("1");
 
        //Transform the matrix into an upper triangle
        gaussian(a, index);
        
        //Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i){
            for (int j=i+1; j<n; ++j){
                for (int k=0; k<n; ++k){
                	BigDecimal num = a[index[j]][i].multiply(b[index[i]][k] , context);
                    b[index[j]][k] = b[index[j]][k].subtract(num , context);
                }
            }
        }
 
        //Perform backward substitutions
        for (int i=0; i<n; ++i) {
        	BigDecimal num = b[index[n-1]][i].divide(a[index[n-1]][n-1] , context);
            x[n-1][i] = num;
            for (int j=n-2; j>=0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) {
                	BigDecimal num2 = a[index[j]][k].multiply(x[k][i] , context);
                    x[j][i] = x[j][i].subtract(num2 , context);
                }
                x[j][i] = x[j][i].divide(a[index[j]][j] , context);
            }
        }
        return x;
    }
	
	
	
	//Method to carry out the partial-pivoting Gaussian
	//elimination.  Here index[] stores pivoting order.
    private void gaussian(BigDecimal a[][], int index[]) {
        int n = index.length;
        BigDecimal c[] = new BigDecimal[n];
 
        // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
        //Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) {
            BigDecimal c1 = new BigDecimal("0");
            for (int j=0; j<n; ++j) {
                BigDecimal c0 = a[i][j].abs();
                if (c0.compareTo(c1) == 1) c1 = c0;
            }
            c[i] = c1;
        }
 
        //Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) {
            BigDecimal pi1 = new BigDecimal("0");
            for (int i=j; i<n; ++i) {
                BigDecimal pi0 = a[index[i]][j].abs();
                pi0 = pi0.divide(c[index[i]] , context);
                if (pi0.compareTo(pi1) == 1) {
                    pi1 = pi0;
                    k = i;
                }
            }
 
            //Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	{
                BigDecimal pj = a[index[i]][j].divide(a[index[j]][j] , context);
 
                //Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
                //Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] = a[index[i]][l].subtract(pj.multiply(a[index[j]][l] , context) , context);
            }
        }
    }
    
	//Check if the matrix is null, if the array is empty or if the array contain elements
	private void matrixCheck(ExactMatrix m){
		if(m == null || m.getMatrix() == null || m.getRows() == 0 || m.getCols() == 0)
			throw new MatrixCalculationException("The matrix can not be null");
	}
	
	//Check if rows are have the same length
	private void checkRows(Object[][] matrix){
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
			throw new MatrixException("Index out of bound" + row +" "+col);
	}

	//Deep clone the matrix
	private BigDecimal[][] deepClone(){
		BigDecimal[][] clone = new BigDecimal[getRows()][getCols()];
		for(int i=0;i<clone.length;i++){
			for(int j=0;j<clone[0].length;j++){
				clone[i][j] = matrix[i][j];
			}
		}
		return clone;
	}
}
