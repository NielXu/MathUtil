package com.mathutil.operations;

/**
 * Calculating the sum using Sigma notation.
 * @author danielxu
 */
public class Sigma {

	private Sigma() {}
	
	/**
	 * Calculating the sum using Sigma(∑) notation. <strong>The variable name must be x</strong>. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression), and the increment will be 1. This sum method return type double as the result, it has higher accuracy. However, when facing the large number 
	 * calculation, please use <code>longsum()</code> method instead.<br>
	 * See {@link ExpReader#calculate(String)} to check out the available operations.
	 * 
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @return The sum using Sigma notation, as double
	 * @see #longsum(int, int, String)
	 * @see ExpReader#calculate(String)
	 */
	public static double sum(double low , double high , String exp){
		return calculate(low , high , exp , 1);
	}
	
	/**
	 * Calculating the sum using Sigma(∑) notation. <strong>The variable name must be x</strong>. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression), and the increment will be the given increment. This sum method return type double as the result, it has higher accuracy. However, when facing the large number 
	 * calculation, please use <code>longsum()</code> method instead.<br>
	 * See {@link ExpReader#calculate(String)} to check out the available operations.
	 * 
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @param incre - The increment of each step of the calculation
	 * @return The sum using Sigma notation, as double
	 * @see #longsum(int, int, String)
	 * @see ExpReader#calculate(String)
	 */
	public static double sum(double low , double high , String exp , double incre){
		return calculate(low , high , exp , incre);
	}
	
	/**
	 * Calculating the sum using Sigma(∑) notation.<strong>The variable name must be x</strong>. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression), and the increment will be 1. This sum method return type long as the result, it has lower accurancy but can do the large number operation. However, 
	 * when higher accuracy is required, please use <code>sum()</code> method instead.<br>
	 * See {@link ExpReader#calculate(String)} to check out the available operations.
	 * 
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @return The sum using Sigma notation, as long
	 * @see #sum(int, int, String)
	 * @see ExpReader#calculate(String)
	 */
	public static long longsum(double low , double high , String exp){
		return calculate_long(low , high , exp , 1);
	}
	
	/**
	 * Calculating the sum using Sigma(∑) notation.<strong>The variable name must be x</strong>. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression), and the increment will be the given increment. This sum method return type long as the result, it has lower accurancy but can do the large number operation. However, 
	 * when higher accuracy is required, please use <code>sum()</code> method instead.<br>
	 * See {@link ExpReader#calculate(String)} to check out the available operations.
	 * 
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @param incre - The increment of each step of the calculation
	 * @return The sum using Sigma notation, as long
	 * @see #sum(int, int, String)
	 * @see ExpReader#calculate(String)
	 */
	public static long longsum(double low , double high , String exp , double incre){
		return calculate_long(low , high , exp , incre);
	}
	
	/*
	 * Do the whole calculation, but instead of using long, this method using double for higher accurancy
	 */
	private static double calculate(double low , double high , String exp , double incre){
		double result = 0d;
		String expression;
		for(double i = low ;i <= high ;i+=incre){
			expression = exp.replace("x", String.valueOf(i)); //Replace the variable x to a specific value
			result += ExpReader.calculate(expression);
		}
		return result;
	}
	
	/*
	 * Do the whole calculation, using long in order to do large number calculation
	 */
	private static long calculate_long(double low , double high , String exp, double incre){
		long result = 0l;
		String expression;
		for(double i = low ;i <= high ;i+=incre){
			expression = exp.replace("x", String.valueOf(i)); //Replace the variable x to a specific value
			result += ExpReader.calculate(expression);
		}
		return result;
	}
}
