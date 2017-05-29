package com.mathutil;

import com.mathutil.exceptions.MatrixCalculationException;
import com.mathutil.exceptions.MatrixException;

/**
 * Matrix is the class that can be used to do calcultions such as <code>substract(Matrix m)</code> 
 * or <code>add(Matrix m)</code>. It also implements the interface Matrixable, it means some basic functions such 
 * as <code>switchRows(int row1 , int row2)</code> and <code>switchCols(int col1 , int col2)</code> can also be used. 
 * Please keep in mind that no matter what number type as the input, the calculation and the result will use double. 
 * Matrix is immutable, which means, you cannot expand the matrix or remove the elements in the matrix. 
 * However, you can switch rows and columns in the matrix, you can get the element from the matrix or you can replace the 
 * element in the specific location to a new one.<br>
 * If the purpose of the matrix is to store informatoins only, please use <code>SimpleMatrix</code> instead.
 *
 * @author danielxu
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
	 * Create a new matrix that is calculable, it stores the elements in a two dimensional array. 
	 * Please remember that the length of each row in the matrix should always be the same.
	 * If the purpose of the matrix is just to store informations, please use <code>SimpleMatrix</code> instead.
	 * @param matrix - The two dimensional array that stores all the elements
	 * @see SimpleMatrix
	 */
	public Matrix(Number[][] matrix){
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
	 * Do addition between two matrices, only matrices that have the same dimensions can do the operations. 
	 * In matrix:<br><center> A+B = B+A </center><br>
	 * Which means, <code>matrix1.addition(matrix2)</code> and <code>matrix2.addition(matrix1)</code> will get the same result.
	 * 
	 * @param m - Another matrix that have the same dimensions
	 * @return A new CalculableMatrix that has the sum of the two matrices
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
	 * @return A new CalculableMatrix that has the difference of the two matrices
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
	 * Please notice that this method is different from <code>multiply(CalculableMatrix)</code>, k here means a factor but not a matrix
	 * 
	 * @param factor - The factor
	 * @return The new matrix that contains the result
	 */
	public Matrix factor(Number factor) {
		if(factor == null)
			throw new MatrixCalculationException("The factor cannot be null");
		
		Number[][] result = new Number[getRows()][getCols()];
		
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
		Number[][] result = new Number[getRows()][getRows()];
		//Identity matrix
		if(n == 0){
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
			//Invertible
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
			result.append("\n");
		}
		return result.toString();
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
        		x[i][j] = round(x[i][j] , 2);
        	}
        }
        return x;
    }
 
	//Method to carry out the partial-pivoting Gaussian
	//elimination.  Here index[] stores pivoting order.
    private void gaussian(double a[][], int index[]) 
    {
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
    	double[][] r = new double[getRows()][getCols()];
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
    	Number[][] r = new Number[getRows()][getCols()];
    	for(int i=0;i<r.length;i++){
    		for(int j=0;j<r[0].length;j++){
    			r[i][j] = matrix[i][j];
    		}
    	}
    	return r;
    }
    
    //Round the double value to the given places
    private double round(double val , int places){
    	long factor = (long) Math.pow(10, places);
        val = val * factor;
        long tmp = Math.round(val);
        return (double) tmp / factor;
    }

    
	//Check if the matrix is null, if the array is empty or if the array contain elements
	private void matrixCheck(Matrix m){
		if(m == null || m.getMatrix() == null || m.getRows() == 0 || m.getCols() == 0)
			throw new MatrixCalculationException("The matrix can not be null");
	}
	
	//Cast a number to double in order to do the calculation
	private double convertToDouble(Number num){
		return new Double(num.doubleValue());
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
