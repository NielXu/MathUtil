package com.mathutil.linearalgebra;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.mathutil.ExactMatrix;
import com.mathutil.Matrix;
import com.mathutil.exceptions.LinearException;
import com.mathutil.exceptions.MatrixCalculationException;

/**
 * Linear class contains few functions that can help to solve the linear equations, it uses Matrix or ExactMatrix to solve the equations. 
 * It also contains functions that can print out the equations on the console. Please notice that linear equations usually contains one 
 * <code>Cofficient Matrix</code>, one <code>Result Matrix</code> and one <code>Variables array</code>
 * @author danielxu
 *
 */
public class Linear {

	private Linear(){}
	
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
			return homo(coff , res);
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
	private static Solution homo(Matrix coff , Matrix res){
		Solution sol = null;
		return sol;
	}
	
	//Solve nonhomogeneous linear equations(the results are not all 0)
	private static Solution nonHomo(Matrix coff , Matrix res , String...vars){
		Solution sol = null;
		Matrix extendMatrix = augmented(coff , res);
		
		int extendRank = extendMatrix.rank();
		int coffRank = coff.rank();
		
		//Check their rank
		if(extendRank != coffRank)						   //rank != extendRank, it is unsolvable
			sol = new Solution(SolutionCase.UNSOLVABLE);
		else{
			//is full rank, there's only one solution
			if(coff.fullRank()){
				//If the determinant is not 0, use matrix multiplication to solve
				if(coff.det() != 0){
					Matrix m = coff.invert().multiply(res);
					sol = new Solution(SolutionCase.ONLY , m, vars);
				}
				//If it's 0, use Gauss-Jordan elimination
				else{
					sol = new Solution(SolutionCase.ONLY);
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
	//                          For ExactMatrix
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
			return homo();
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
	private static Solution homo(){
		return null;
	}
	
	//Solve nonhomogeneous linear equations(the results are not all 0)
	private static Solution nonHomo(ExactMatrix coff , ExactMatrix res , String...vars){
		Solution sol = null;
		ExactMatrix extendMatrix = augmented(coff , res);
		
		int extendRank = extendMatrix.rank();
		int coffRank = coff.rank();
		
		//Check their rank
		if(extendRank != coffRank)						   //rank != extendRank, it is unsolvable
			sol = new Solution(SolutionCase.UNSOLVABLE);
		else{
			//is full rank, there's only one solution
			if(coff.fullRank()){
				//If the determinant is not 0, use matrix multiplication to solve
				if(coff.det().compareTo(BigDecimal.ZERO) != 0){
					ExactMatrix m = coff.invert().multiply(res);
					sol = new Solution(SolutionCase.ONLY , m, vars);
				}
				//If it's 0, use Gauss-Jordan elimination
				else{
					sol = new Solution(SolutionCase.ONLY);
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
