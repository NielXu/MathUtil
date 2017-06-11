package com.mathutil.linearalgebra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.mathutil.linearalgebra.matrix.ExactMatrix;
import com.mathutil.linearalgebra.matrix.Matrix;

/**
 * A solution, contains the {@link SolutionCase} and the results. Please notice that not every system can be solved, 
 * it might also be infinite solutions or no solutions.
 * 
 * @author danielxu
 *
 */
public class Solution {
	
	/**The solution case**/
	private SolutionCase cas;
	
	/**The matrix map that stores the results and variables**/
	private Map<String , Number> matrixMap;
	
	/**The ExactMatrix that stores the results and variables**/
	private Map<String , BigDecimal> exactMap;
	
	/**The variables**/
	private String[] vars;
	
	/**Return INFINITE if there are infinite solutinos, this is for Matrix **/
	public static final Number INFINITE = Double.POSITIVE_INFINITY;
	
	/**Return UNSOLVABLE if the equations are unsolvable, this is for Matrix**/
	public static final Number UNSOLVABLE = Double.NaN;
	
	/**Return EXACT_INFINITE if there are infinite solutions, this is for ExactMatrix, the value is 371797(Prime number)**/
	public static final BigDecimal EXACT_INFINITE = new BigDecimal("371797"){
		private static final long serialVersionUID = 1L;
		public String toString(){
			return "Infinity";
		}
	};
	
	/**Return EXACT_UNSOLVABLE if the equations are unsolvable, this is for ExactMatrix, the value is -371797(Prime number)**/
	public static final BigDecimal EXACT_UNSOLVABLE = new BigDecimal("-371797"){
		private static final long serialVersionUID = 1L;
		public String toString(){
			return "NaN";
		}
	};
	
	/**If using ExactMatrix or not**/
	private boolean exactMatrix = false;
	
	/**
	 * Only visible for package. Pass only a SolutionCase.
	 * @param cas - The SolutionCase
	 */
	Solution(SolutionCase cas){
		init();
		this.cas = cas;
	}
	
	/**
	 * Only visible for package. Pass a SolutionCase and double results.
	 * @param cas - The SolutionCase
	 * @param ExactMatrix - The ExactMatrix, only has a column
	 */
    Solution(SolutionCase cas , ExactMatrix m , String...vars){
    	exactMatrix = true;
    	this.cas = cas;
    	this.vars = vars;
    	
    	init();
    	//Put the results into a map
    	for(int i=0;i<vars.length;i++){
    		exactMap.put(vars[i], m.getMatrix()[i][0]);
    	}
	}
    
    /**
     * Only visible for package. Pass a SolutionCase and bigdecimal results
     * @param cas - The SolutionCase
     * @param Matrix - The matrix, only has a column
     */
    Solution(SolutionCase cas, Matrix m , String...vars){
    	this.cas = cas;
    	this.vars = vars;
    	
    	init();
    	//Put the result into a map
    	for(int i=0;i<vars.length;i++){
    		matrixMap.put(vars[i], m.getMatrix()[i][0]);
    	}
    }
    
	//Initialize
	private void init(){
		if(exactMatrix){
			exactMap = new HashMap<String , BigDecimal>();
		}
		else{
			matrixMap = new HashMap<String , Number>();
		}
	}
	
    /**
     * Get the solution case.
     * @return The solution case
     * @see SolutionCase
     */
	public SolutionCase getSolutionCase(){
		return cas;
	}
	
	/**
	 * Get the result with the correponding varibable name. This method is for <strong>Matrix</strong>, 
	 * call {@link #getExactResult(String)} if is using ExactMatrix
	 * @param var - The varibale name
	 * @return
	 * <ul>
	 * <li>If the system is unsolvable, return Double.NaN</li>
	 * <li>If the system has infinite solutions, return Double.INFINITE</li>
	 * </ul>
	 * @see #INFINITE
	 * @see #UNSOLVABLE
	 */
	public Number getSolution(String var){
		if(cas == SolutionCase.INFINITE)
			return INFINITE;
		if(cas == SolutionCase.UNSOLVABLE)
			return UNSOLVABLE;
		
		return matrixMap.get(var);
	}
	
	/**
	 * Get the result with the correponding varibable name. This method is for <strong>ExactMatrix</strong>, 
	 * call {@link #getResult(String)} if is using Matrix
	 * @param var - The varibale name
	 * @return
	 * <ul>
	 * <li>If the system is unsolvable, return BigDecimal with the value 371797(Prime number)</li>
	 * <li>If the system has infinite solutions, return BigDecimal with the value -371797(Prime number)</li>
	 * </ul>
	 * @see #EXACT_INFINITE
	 * @see #EXACT_UNSOLVABLE
	 */
	public BigDecimal getExactSolution(String var){
		if(cas == SolutionCase.INFINITE)
			return EXACT_INFINITE;
		if(cas == SolutionCase.UNSOLVABLE)
			return EXACT_UNSOLVABLE;
		
		return exactMap.get(var);
	}
	
	/**
	 * Represent the solutions in form of: <center>x = ...</center> <center>y = ...</center>
	 * @return
	 * <ul>
	 * <li><i>"Unsolvable System"</i> if the therer are no solutions</li>
	 * <li><i>"Infinite Solutions"</i> if there are infinite solutions</li>
	 * <li>The results</li>
	 * </ul>
	 */
	@Override
	public String toString(){
		if(cas == SolutionCase.UNSOLVABLE)
			return "Unsolvable System";
		if(cas == SolutionCase.INFINITE)
			return "Infinite Solutions";
		
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<vars.length;i++){
			if(exactMatrix)
				sb.append(vars[i]+" = "+exactMap.get(vars[i]));
			else
				sb.append(vars[i]+" = "+matrixMap.get(vars[i]));
			if(i != vars.length-1)
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
