package com.mathutil.linearalgebra;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mathutil.exceptions.LinearException;
import com.mathutil.exceptions.MatrixCalculationException;
import com.mathutil.exceptions.SuperVectorException;
import com.mathutil.linearalgebra.matrix.ExactMatrix;
import com.mathutil.linearalgebra.matrix.Matrix;

/**
 * Linear class contains few functions that can help to solve the linear equations, it uses Matrix or ExactMatrix to solve the equations. 
 * It also contains functions that can print out the equations on the console. Please notice that linear equations usually contains one 
 * <code>Cofficient Matrix</code>, one <code>Result Matrix</code> and one <code>Variables array</code><br>
 * This class also contains some methods for vectors calculations, such as <code>dot(SuperVector,SuperVector)</code> for calculating the 
 * dot product, <code>cross(SuperVector,SuperVector)</code> for calculating the cross product and so on.
 * 
 * @author danielxu
 *
 */
public class Linear {

	private Linear(){}
	
	// ===================================================================
	//                       For Vectors Calculations
	// ===================================================================
	
	/**
	 * Calaulate the magnitude of a vector, if the dimension of the vector is 0, the magnitude is 0. The formula is:
	 * <center>magn(v) = √(x1^2 + x2^2 + x3^2 + ... + xn^2)</center>
	 * 
	 * @param v - The vector
	 * @return The magnitude of the vector
	 */
	public static double magn(SuperVector v){
		if(v.getDimension() == 0){
			return 0;
		}
		
		double magn = 0;
		for(int i=0;i<v.getDimension();i++){
			magn += Math.pow(v.getComponents()[i], 2);
		}
		return Math.sqrt(magn);
	}
	
	/**
	 * Determine the angle between two vectors, they must have the same dimension. The formula is:
	 * <center>θ = acos( (a·b)/(|a|*|b|) )</center>
	 * @param v1 - The first vector
	 * @param v2 - The second vector
	 * @return The angle between the two vectors, in radians
	 */
	public static double angle(SuperVector v1 , SuperVector v2){
		dimensionCheck(v1 , v2);
		
		double d1 = dot(v1 , v2);
		double d2 = magn(v1);
		double d3 = magn(v2);
		
		return Math.acos(d1 / (d2*d3));
	}
	
	/**
	 * Get the magnitude of the cross product of two vectors, they must in three dimensional space, which means the dimension of both vectors 
	 * must be 3. The result of this method is the magnitude of the cross product, therefore it's a scalar but not a vector. The formula is:
	 * <center>|axb| = |a|*|b|*sin(θ)</center>
	 * 
	 * @param v1 - The first vector, the dimension must be 3
	 * @param v2 - The second vector, the dimension must be 3
	 * @param radianAngle - The angle between the two vectors, in radians
	 * @return The magnitude of the cross product of two vectors, a scalar
	 */
	public static double cross(SuperVector v1 , SuperVector v2 , double radianAngle){
		if(v1.getDimension() != 3 || v2.getDimension() != 3)
			throw new SuperVectorException("Cross product only defined in three dimensional space");
		dimensionCheck(v1 , v2);
		
		double r1 = magn(v1);
		double r2 = magn(v2);
		
		return r1*r2*Math.sin(radianAngle);
	}
	
	/**
	 * Get the cross product of two vectors, they must in three dimensional space, which means the dimension of both vectors 
	 * must be 3. The result of the cross product is a vector, the formula for cross product is:
	 * <center>axb = [a2*b3 - a3*b2 , a3*b1 - a1*b3 , a1*b2 - a2*b1]</center>
	 * 
	 * @param v1 - The first vector, the dimension must be 3
	 * @param v2 - The second vector, the dimension must be 3
	 * @return The cross product of two vectors, a vector
	 */
	public static SuperVector cross(SuperVector v1 , SuperVector v2){
		if(v1.getDimension() != 3 || v2.getDimension() != 3)
			throw new SuperVectorException("Cross product only defined in three dimensional space");
		dimensionCheck(v1 , v2);
		
		Double[] compo1 = v1.getComponents();
		Double[] compo2 = v2.getComponents();
		
		double[] newcompo = new double[v1.getDimension()];
		
		newcompo[0] = compo1[1]*compo2[2] - compo2[1]*compo1[2];
		newcompo[1] = compo1[2]*compo2[0] - compo2[2]*compo1[0];
		newcompo[2] = compo1[0]*compo2[1] - compo2[0]*compo1[1];
		
		return new SuperVector(newcompo);
	}
	
	/**
	 * Get the dot product of two vectors, they must have the same dimension. The result of the dot product is always a scalar, 
	 * which represents by a double. The formula is:
	 * <center>a·b = |a|*|b|*cos(θ)</center>
	 * Another method to calculate the dot product is {@link #dot(SuperVector, SuperVector)}
	 * 
	 * @param v1 - The first vector
	 * @param v2 - The second vector
	 * @param radianAngle - The angle between two vectors, in radians
	 * @return The dot product of two vectors, a scalar
	 */
	public static double dot(SuperVector v1 , SuperVector v2 , double radianAngle){
		dimensionCheck(v1 , v2);
		
		double mgV1 = magn(v1);
		double mgV2 = magn(v2);
		
		return mgV1 * mgV2 * Math.cos(radianAngle); 
	}
	
	/**
	 * Get the dot product of two vectors, they must have the same dimension. The result of the dot product is always a scalar, 
	 * which represents by a double. The formula is:
	 * <center>a·b = a1*b1 + a2*b2 + a3*b3 + ... + an*bn</center>
	 * Another method to calculate the dot product is {@link #dot(SuperVector, SuperVector , double)}
	 * 
	 * @param v1 - The first vector
	 * @param v2 - The second vector
	 * @return The dot product of two vectors, a scalar
	 */
	public static double dot(SuperVector v1 , SuperVector v2){
		dimensionCheck(v1 , v2);
		
		double sum = 0;
		for(int i=0;i<v1.getDimension();i++){
			sum += v1.get(i)*v2.get(i);
		}
		
		return sum;
	}
	
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
		dimensionCheck(v1 , v2);
		
		double sum = 0;
		Double[] c1 = v1.getComponents();
		Double[] c2 = v2.getComponents();
		
		for(int i=0;i<c1.length;i++){
			sum += Math.pow(c1[i] - c2[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	//Check if two vectors have the same dimension
	private static void dimensionCheck(SuperVector v1 , SuperVector v2){
		if(v1.getDimension() != v2.getDimension())
			throw new SuperVectorException("The vectors must have the same dimension");
	}
	
	// ===================================================================
	//                       For Matrix Calculations
	// ===================================================================
	
	/**
	 * Display the linear equations, given the cofficient matrix, variables matrix and result matrix. 0 will not be omitted and will be displayed 
	 * as well.
	 * An example display:
	 * <center>2.0*x + 3.0*y + 1.0*z = 3.0</center>
	 * <center>0.0*x + 4.0*y + 2.0*z = 3.0</center>
	 * <center>1.0*x + 3.0*y + 8.0*z = 9.0</center>
	 * <ul>
	 * <li>The cofficient matrix must be a square matrix, which means the dimension of it should be n*n</li>
	 * <li>The variables array must have the same length with the cofficient matrix, which is n</li>
	 * <li>The result matrix must be a column matrix, and the number of rows should be the same with the previous two's, which means the dimension should be n*1</li>
	 * </ul>
	 * 
	 * @param coff - The cofficient matrix, should be a square matrix
	 * @param result - The result matrix, should be a column matrix
	 * @param vars - The variables, should be a column matrix
	 */
	public static void printEqs(Matrix coff , Matrix result , String...vars){
		if(coff == null || result == null || vars == null)
			throw new LinearException("The matrices or the variables cannot be null");
		if(coff.isEmpty() || result.isEmpty())
			throw new LinearException("The matirces cannot be empty");
		if(vars.length != coff.getCols() || vars.length != coff.getRows())
			throw new MatrixCalculationException("The number of variables must be the same as the number of rows and columns of the cofficient matrix");
		if(result.getCols() > 1)
			throw new MatrixCalculationException("The result matrix can only contains one column");
		if(result.getRows() != coff.getRows())
			throw new MatrixCalculationException("The number of rows of the cofficient matrix should be the same with the result matrix's");
		
		for(int i=0;i<coff.getRows();i++){
			for(int j=0;j<coff.getCols();j++){
				if(j != coff.getCols()-1)
					System.out.print(coff.get(i,j)+"*"+vars[j] + " + ");
				else
					System.out.print(coff.get(i, j)+"*"+vars[j] + " = ");
			}
			System.out.println(result.get(i, 0));
		}
	}
	
	/**
	 * Solve the linear equations. The cofficient matrix must be a square matrix, and the result matrix must have the same number of columns 
	 * with the cofficient matrix. And the variables must have the same length with the cofficient matrix's as well.
	 * 
	 * @param coff - The cofficient matrix
	 * @param res - The result matrix
	 * @param vars - The variables
	 * @return The solution, contains the {@link SolutionCase} and all the solutions. See {@link Solution} for more informations.
	 */
	public static Solution solve(Matrix coff , Matrix res , String... vars){
		if(coff == null || res == null)
			throw new LinearException("The matrices cannot be null");
		if(coff.isEmpty() || res.isEmpty())
			throw new LinearException("The matrices cannot be empty");
		if(vars.length != coff.getCols() || vars.length != coff.getRows())
			throw new MatrixCalculationException("The number of variables must be the same as the number of rows and columns of the cofficient matrix");
		if(res.getCols() > 1)
			throw new MatrixCalculationException("The result matrix can only contains one column");
		if(res.getRows() != coff.getRows())
			throw new MatrixCalculationException("The number of rows of the cofficient matrix should be the same with the result matrix's");
		
		if(isHomo(res)){
			return homo(coff , res , vars);
		}
		else{
			return nonHomo(coff , res , vars);
		}
	}
	
	//Check if the system is homogeneous linear equations(the results are all 0) or nonhomogeneous linear equations(are not all 0)
	private static boolean isHomo(Matrix res){
		for(int i=0;i<res.getCols();i++){
			if(res.getMatrix()[0][i].doubleValue() != 0)
				return false;
		}
		return true;
	}
	
	//Create a augmented matrix, add result matrix on the right side of the cofficient matrix
	private static Matrix augmented(Matrix coff , Matrix res){
		Number[][] extend = new Number[coff.getRows()][coff.getCols()+1]; //Extend the matrix, add result column to the right side
		
		Number[][] m1 = coff.getMatrix();
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1[0].length;j++){
				extend[i][j] = m1[i][j];
			}
		}
		for(int i=0;i<extend.length;i++){
			extend[i][extend[0].length-1] = res.getMatrix()[i][0];
		}
		return new Matrix(extend);
	}
	
	//Solve homogeneous linear equations(the results are all 0)
	private static Solution homo(Matrix coff , Matrix res , String...vars){
		Solution sol;
		if(coff.fullRank()){
			Number[][] result = new Number[coff.getRows()][1];
			for(int i=0;i<result.length;i++){
				result[i][0] = 0.0;
			}
			sol = new Solution(SolutionCase.ZEROS , new Matrix(result) , vars);
		}
		else{
			sol = new Solution(SolutionCase.INFINITE);
		}
		return sol;
	}
	
	//Solve nonhomogeneous linear equations(the results are not all 0)
	private static Solution nonHomo(Matrix coff , Matrix res , String...vars){
		Solution sol;
		Matrix extendMatrix = augmented(coff , res);
		
		int extendRank = extendMatrix.rank();
		int coffRank = coff.rank();
		
		//Check their rank
		if(extendRank != coffRank)//rank != extendRank, it is unsolvable
			sol = new Solution(SolutionCase.UNSOLVABLE);
		else{
			//is full rank, there's only one solution
			if(coff.fullRank()){
				//If the determinant is not 0, use matrix multiplication to solve
				if(coff.det() != 0){
					Matrix m = coff.invert().multiply(res);
					sol = new Solution(SolutionCase.ONLY , m, vars);
				}
				//If the determinant is 0
				else{
					sol = new Solution(SolutionCase.UNSOLVABLE);
				}
			}
			//not full rank, infinite solutions
			else{
				sol = new Solution(SolutionCase.INFINITE);
			}
		}
		return sol;
	}
	
	// ===================================================================
	//                    For ExactMatrix Calculations
	// ===================================================================
	
	/**
	 * Display the linear equations, given the cofficient matrix, variables matrix and result matrix. 0 will not be omitted and will be displayed 
	 * as well. The decimal places that will be displayed is according to the cofficient matrix's decimal places setting. Which can be set by 
	 * {@link ExactMatrix#setShowDecimal(int)} Fill the matrix with 0 if it is empty in order to balance the matrix to a square matrix.<br>
	 * An example display:
	 * <center>2.000*x + 3.000*y + 1.000*z = 3.000</center>
	 * <center>0.000*x + 4.000*y + 2.000*z = 3.000</center>
	 * <center>1.000*x + 3.000*y + 8.000*z = 9.000</center>
	 * <ul>
	 * <li>The cofficient matrix must be a square matrix, which means the dimension of it should be n*n</li>
	 * <li>The variables array must have the same length with the cofficient matrix, which is n</li>
	 * <li>The result matrix must be a column matrix, and the number of rows should be the same with the previous two's, which means the dimension should be n*1</li>
	 * </ul>
	 * 
	 * @param coff - The cofficient matrix, should be a square matrix
	 * @param result - The result matrix, should be a column matrix
	 * @param vars - The variables, should be a column matrix
	 */
	public static void printEqs(ExactMatrix coff , ExactMatrix result , String...vars){
		if(coff == null || result == null || vars == null)
			throw new LinearException("The matrices or the variables cannot be null");
		if(coff.isEmpty() || result.isEmpty())
			throw new LinearException("The matirces cannot be empty");
		if(vars.length != coff.getCols() || vars.length != coff.getRows())
			throw new MatrixCalculationException("The number of variables must be the same as the number of rows and columns of the cofficient matrix");
		if(result.getCols() > 1)
			throw new MatrixCalculationException("The result matrix can only contains one column");
		if(result.getRows() != coff.getRows())
			throw new MatrixCalculationException("The number of rows of the cofficient matrix should be the same with the result matrix's");
		
		for(int i=0;i<coff.getRows();i++){
			for(int j=0;j<coff.getCols();j++){
				if(j != coff.getCols()-1)
					System.out.print(coff.get(i , j).setScale(coff.getDecimal(), RoundingMode.HALF_UP)+"*"+vars[j] + " + ");
				else
					System.out.print(coff.get(i , j).setScale(coff.getDecimal(), RoundingMode.HALF_UP)+"*"+vars[j] + " = ");
			}
			System.out.println(result.get(i, 0).setScale(coff.getDecimal(), RoundingMode.HALF_UP));
		}
	}
	
	/**
	 * Solve the linear equations. The cofficient matrix must be a square matrix, and the result matrix must have the same number of columns 
	 * with the cofficient matrix. And the variables must have the same length with the cofficient matrix's as well.
	 * 
	 * @param coff - The cofficient matrix
	 * @param res - The result matrix
	 * @param vars - The variables
	 * @return The solution, contains the {@link SolutionCase} and all the solutions. See {@link Solution} for more informations.
	 */
	public static Solution solve(ExactMatrix coff , ExactMatrix res , String...vars){
		if(coff == null || res == null)
			throw new LinearException("The matrices cannot be null");
		if(coff.isEmpty() || res.isEmpty())
			throw new LinearException("The matrices cannot be empty");
		if(vars.length != coff.getCols() || vars.length != coff.getRows())
			throw new MatrixCalculationException("The number of variables must be the same as the number of rows and columns of the cofficient matrix");
		if(res.getCols() > 1)
			throw new MatrixCalculationException("The result matrix can only contains one column");
		if(res.getRows() != coff.getRows())
			throw new MatrixCalculationException("The number of rows of the cofficient matrix should be the same with the result matrix's");
		
		if(isHomo(res)){
			return homo(coff , res , vars);
		}
		else{
			return nonHomo(coff , res , vars);
		}
	}
	
	//Check if the system is homogeneous linear equations(the results are all 0) or nonhomogeneous linear equations(are not all 0)
	private static boolean isHomo(ExactMatrix res){
		for(int i=0;i<res.getCols();i++){
			if(res.getMatrix()[0][i].compareTo(BigDecimal.ZERO) != 0)
				return false;
		}
		return true;
	}
	
	//Create a augmented matrix, add result matrix on the right side of the cofficient matrix
	private static ExactMatrix augmented(ExactMatrix coff , ExactMatrix res){
		BigDecimal[][] extend = new BigDecimal[coff.getRows()][coff.getCols()+1]; //Extend the matrix, add result column to the right side
		
		BigDecimal[][] m1 = coff.getMatrix();
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1[0].length;j++){
				extend[i][j] = m1[i][j];
			}
		}
		for(int i=0;i<extend.length;i++){
			extend[i][extend[0].length-1] = res.getMatrix()[i][0];
		}
		return new ExactMatrix(extend);
	}
	
	//Solve homogeneous linear equations(the results are all 0)
	private static Solution homo(ExactMatrix coff , ExactMatrix res , String...vars){
		Solution sol;
		if(coff.fullRank()){
			BigDecimal[][] b = new BigDecimal[coff.getRows()][1];
			for(int i=0;i<b.length;i++){
				b[i][0] = new BigDecimal("0");
			}
			sol = new Solution(SolutionCase.ZEROS , new ExactMatrix(b) , vars);
		}
		else{
			sol = new Solution(SolutionCase.INFINITE);
		}
		return sol;
	}
	
	//Solve nonhomogeneous linear equations(the results are not all 0)
	private static Solution nonHomo(ExactMatrix coff , ExactMatrix res , String...vars){
		Solution sol;
		ExactMatrix extendMatrix = augmented(coff , res);
		
		int extendRank = extendMatrix.rank();
		int coffRank = coff.rank();
		
		//Check their rank
		if(extendRank != coffRank)//rank != extendRank, it is unsolvable
			sol = new Solution(SolutionCase.UNSOLVABLE);
		else{
			//is full rank, there's only one solution
			if(coff.fullRank()){
				//If the determinant is not zero
				if(coff.det().compareTo(BigDecimal.ZERO) != 0){
					ExactMatrix m = coff.invert().multiply(res);
					sol = new Solution(SolutionCase.ONLY , m, vars);
				}
				//If the determinant is zero
				else{
					sol = new Solution(SolutionCase.INFINITE);
				}
			}
			//not full rank, infinite solutions
			else{
				sol = new Solution(SolutionCase.INFINITE);
			}
		}
		return sol;
	}
	
}
