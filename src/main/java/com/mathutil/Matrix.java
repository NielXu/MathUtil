package com.mathutil;

import com.mathutil.exceptions.MatrixCalculationException;

/**
 * Matrix is the class that implements the interface <code>CalculableMatrix</code>, which means that this matrix can 
 * be used to do calcultions such as <code>substract(CalculableMatrix m)</code> or <code>add(CalculableMatrix m)</code>.<br>
 * Please keep in mind that the default type that being used to do the calculations is double, an optional type is 
 * using long, which can be set by the method <code>uselong(boolean uselong)</code> <br>
 * CalculableMatrix is immutable, which means, you cannot expand the matrix or remove the elements in the matrix. 
 * However, you can switch rows and columns in the matrix, you can get the element from the matrix or you can replace the 
 * element in the specific location to a new one.<br>
 * If the purpose of the matrix is to store informatoins only, please use <code>SimpleMatrix</code> instead.
 *
 * @author danielxu
 * @see CalculableMatrix
 * @see SimpleMatrix
 */
public class Matrix extends SimpleMatrix<Number> implements CalculableMatrix{
	
	/**The matrix that stores the informations**/
	private Number[][] matrix;
	
	/**Should use long in the calculations or not**/
	private boolean uselong;
	
	/**
	 * Create an empty matrix that has no elements in it
	 */
	public Matrix(){
		super();
	}
	
	/**
	 * Create a new matrix that is calculable, it stores the elements in a two dimensional array. 
	 * Please remember that the length of each row in the matrix should always be the same. 
	 * If the purpose of the matrix is just to store informations, please use <code>SimpleMatrix</code> instead.
	 * @param matrix - The two dimensional array that stores all the elements
	 * @see SimpleMatrix
	 */
	public Matrix(Number[][] matrix){
		super(matrix);
		this.matrix = matrix;
	}
	
	/**
	 * Allow to set if the numbers will be converted to long type or not. The default type 
	 * that being used in the calculation is double. If uselong is set to be true, the following 
	 * operations will use long. This can be changed during the porgram.
	 * @param uselong - Set if the following operations should use long or not. True to use long, false(default) will use double
	 */
	public void userLong(boolean uselong){
		this.uselong = uselong;
	}

	/**
	 * Do addition between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A+B = B+A </center><br>
	 * Which means, <code>matrix1.addition(matrix2)</code> and <code>matrix2.addition(matrix1)</code> will get the same result.
	 * @param m - Another matrix that have the same dimensions
	 * @return A new CalculableMatrix that has the sum of the two matrices
	 */
	public CalculableMatrix add(CalculableMatrix m) {
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
				if(uselong){
					long num1 = convertToLong(m2[i][j]);
					long num2 = convertToLong(matrix[i][j]);
					result[i][j] = num1 + num2;
				}
				else{
					double num1 = convertToDouble(m2[i][j]);
					double num2 = convertToDouble(matrix[i][j]);
					result[i][j] = num1 + num2;
				}
			}
		}
		return new Matrix(result);
	}
	
	/**
	 * Do subtraction between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A-B ≠ B-A </center><br>
	 * Which means, <code>matrix1.substract(matrix2)</code> and <code>matrix2.substract(matrix1)</code> will 
	 * <strong>NOT</strong> get the same result.
	 * @param m - Another matrix that have the same dimensions
	 * @return A new CalculableMatrix that has the difference of the two matrices
	 */
	public CalculableMatrix substract(CalculableMatrix m) {
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
				if(uselong){
					//Matrix1 - Matrix2
					long num1 = convertToLong(matrix[i][j]);
					long num2 = convertToLong(m2[i][j]);
					result[i][j] = num1 - num2;
				}
				else{
					double num1 = convertToDouble(matrix[i][j]);
					double num2 = convertToDouble(m2[i][j]);
					result[i][j] = num1 - num2;
				}
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
	 * @param m - Another CalculableMatrix 
	 * @return The new Matrix that contains the result of the multiplication
	 */
	public CalculableMatrix multiply(CalculableMatrix m) {
		matrixCheck(m);
		//Check if two matrices have the same dimensions
		if(getCols() != m.getRows())
			throw new MatrixCalculationException("Multiplication can only be applied on two matrices that,\n"
					+ "left side matrix's columns number equals to right side matrix's rows number");
		
		//Matrix1: m*n , Matrix2: p*q , M1*M2 = m*q
		int row = getRows();
		int col = m.getCols();
		
		Number[][] result = new Number[row][col];
		Number[][] m2 = m.getMatrix();
		//Initialize the number to 0
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[0].length;j++){
				result[i][j] = 0;
			}
		}
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<m2[0].length;j++){
				for(int k=0;k<matrix[0].length;k++){
					if(uselong){
						long num1 = convertToLong(matrix[i][k]);
						long num2 = convertToLong(m2[k][j]);
						long temp = convertToLong(result[i][j]);
						result[i][j] = temp+(num1*num2);
					}
					else{
						double num1 = convertToDouble(matrix[i][k]);
						double num2 = convertToDouble(m2[k][j]);
						double temp = convertToDouble(result[i][j]);
						result[i][j] = temp+(num1*num2);
					}
				}
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Multiply the whole matrix by a factor. if the original matrix is A, the factor is k, the new matrix will be:<br>
	 * <center>k*A</center><br>
	 * Please notice that this method is different from <code>multiply(CalculableMatrix)</code>, k here means a factor but not a matrix
	 * @param factor - The factor
	 * @return The new matrix that contains the result
	 */
	public CalculableMatrix factor(Number factor) {
		Number[][] result = new Number[getRows()][getCols()];
		
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				if(uselong){
					long fac = convertToLong(factor);
					long origin = convertToLong(matrix[i][j]);
					result[i][j] = origin*fac;
				}
				else{
					double fac = convertToDouble(factor);
					double origin = convertToDouble(matrix[i][j]);
					result[i][j] = origin*fac;
				}
			}
		}
		return new Matrix(result);
	}
	
	//Check if the matrix is null, if the array is empty or if the array contain elements
	private void matrixCheck(CalculableMatrix m){
		if(m == null || m.getMatrix() == null || m.getRows() == 0 || m.getCols() == 0)
			throw new MatrixCalculationException("The matrix can not be null");
	}
	
	//Cast a number to long in order to do the calculation
	private long convertToLong(Number num){
		return new Long(num.longValue());
	}
	
	//Cast a number to double in order to do the calculation
	private double convertToDouble(Number num){
		return new Double(num.doubleValue());
	}
	
}
