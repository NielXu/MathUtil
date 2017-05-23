package com.mathutil.operations;

import java.util.Stack;

import com.mathutil.exceptions.BoundException;
import com.mathutil.exceptions.ExpressionException;

/**
 * Calculating the sum using Sigma notation.
 * @author danielxu
 * @version 0.0.1
 */
public class Sigma {

	private Sigma() {}
	
	/**
	 * Calculating the sum using Sigma(∑) notation. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression). This sum method return type double as the result, it has higher accurancy. However, when facing the large number 
	 * calculation, please use <code>longsum()</code> method instead.<br>
	 * The expression must be in the original form, which means, the operators(* + - / ^) must be explicitly included in the expression. 
	 * And the negative number such as (-a) should be written as (0-a), the square root should be written as (a^(1/2)) 
	 * Here is an example of the correct expression:<br>
	 * <center>a*(1+a)^2 </center> <br>
	 * An example of the incorrect expression:<br>
	 * <center> a(1+a)^2 </center> <br>
	 * The expression can contain empty spaces since they will be removed during the calculation process. The variable can be any String except for 
	 * special numbers: pi, e
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @param varName - The variable name in the expression, such as x, a, t and so on, can also be a name that has length greater than one
	 * @return The sum using Sigma notation, as double
	 * @see #longsum(int, int, String, String)
	 */
	public static double sum(int low , int high , String exp , String varName){
		exp = validate(low , high , exp , varName);
		return calculate(low , high , exp , true);
	}
	
	/**
	 * Calculating the sum using Sigma(∑) notation. The sum will be calculated from i to n (i, n are inclusive, n>=i) by the following way: 
	 * n∑i(Expression). This sum method return type long as the result, it has lower accurancy but can do the large number operation. However, 
	 * when higher accurancy is required, please use <code>sum()</code> method instead.<br>
	 * The expression must be in the original form, which means, the operators(* + - / ^) must be explicitly included in the expression. 
	 * And the negative number such as (-a) should be written as (0-a), the square root should be written as (a^(1/2)) 
	 * Here is an example of the correct expression:<br>
	 * <center>a*(1+a)^2 </center> <br>
	 * An example of the incorrect expression:<br>
	 * <center> a(1+a)^2 </center> <br>
	 * The expression can contain empty spaces since they will be removed during the calculation process. The variable can be any String except for 
	 * special numbers: pi, e
	 * @param low - The lower bound of the sigma notation, should not be greater than the higher bound
	 * @param high - The upper bound of the sigma notation, should not be smaller than the lower bound
	 * @param exp - The expression
	 * @param varName - The variable name in the expression, such as x, a, t and so on, can also be a name that has length greater than one
	 * @return The sum using Sigma notation, as long
	 * @see #sum(int, int, String, String)
	 */
	public static long longsum(int low , int high , String exp , String varName){
		exp = validate(low , high , exp , varName);
		return calculate(low , high , exp);
	}
	
	/*
	 * Check if the expression is valid or not
	 */
	private static String validate(int low , int high , String exp , String varName){
		if (low > high) throw new BoundException("Lower bound cannot be greater than upper bound");
		if(exp == null || exp.equals("")) throw new ExpressionException("Expression cannot be null or empty");
		if(varName == null || exp.equals("")) throw new ExpressionException("Variable name cannot be null or empty");
		
		//Replace the variable name to x, in order to deal with the situation that the length of the varName is greater than one.
		exp = exp.replace(varName, "x");
		//Repalce whitespaces and some special notations
		exp = exp.replace(" ", "");
		exp = exp.replace("e", String.valueOf(Math.E)); //Replace 'e' to natural number e
		exp = exp.replace("pi", String.valueOf(Math.PI));//Replace 'pi' to pi
		//Check if the parenthesis is symmetry or not
		if(!check(exp)) throw new ExpressionException("Parenthesis missing in the expression");
		
		return exp;
	}
	
	/*
	 * Do the whole calculation, using long in order to do large number calculation
	 */
	private static long calculate(int low , int high , String exp){
		long result = 0l;
		String expression;
		for(int i = low ;i <= high ;i++){
			expression = exp.replace("x", String.valueOf(i)); //Replace the variable x to a specific value
			result += singleCalculate(expression);
		}
		return result;
	}
	
	/*
	 * Do the whole calculation, but instead of using long, this method using double for higher accurancy
	 */
	private static double calculate(int low , int high , String exp , boolean dummy){
		double result = 0d;
		String expression;
		for(int i = low ;i <= high ;i++){
			expression = exp.replace("x", String.valueOf(i)); //Replace the variable x to a specific value
			result += singleCalculate(expression);
		}
		return result;
	}
	
	/*
	 * Do a single step of the calculation, evaluate the expression once.
	 */
	private static double singleCalculate(String exp){
		char [] tokens = exp.toCharArray();
		Stack<Character> ops = new Stack<Character>(); //Operations stack
		Stack<Double> vals = new Stack<Double>(); //Values stack
		
		for(int i=0;i<tokens.length;i++){
			//If it is a number, push it to the value stack
			if(tokens[i] >= '0' && tokens[i] <= '9'){
				StringBuilder sb = new StringBuilder();
				
				while(i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')){
					sb.append(tokens[i++]);
				}
				i--;
				vals.push(Double.parseDouble(sb.toString()));
			}
			//If it is (, push it onto the operations stack
			else if(tokens[i] == '('){
				ops.push(tokens[i]);
			}
			//If it is ), find the closest ( to solve
			else if(tokens[i] == ')'){
				while(ops.peek() != '('){
					vals.push(operation(ops.pop() , vals.pop() , vals.pop())); //Do the operation, push the result to the value stack
				}
				ops.pop();
			}
			//If it is the operator
			else if(tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^'){
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())){
	                  vals.push(operation(ops.pop(), vals.pop(), vals.pop()));
				}
	            // Push current token to 'ops'.
	            ops.push(tokens[i]);
			}
		}
		//Do the final operation
		while (!ops.empty()){
            vals.push(operation(ops.pop(), vals.pop(), vals.pop()));
		}
		//Return the final value
		return vals.pop();
	}
	
	/*
	 * Returns true if 'op2' has higher or same precedence as 'op1', otherwise returns false.
	 * (^) > (/ *) > (+ -)
	 */
	private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '^') && (op2 == '+' || op2 == '-' || op2 == '*' || op2 == '/'))
        	return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
	
	/*
	 * Apply the operation according to the operator
	 */
	private static double operation(char op , double val1 , double val2){
		switch (op)
        {
        case '+':
            return val2 + val1;
        case '-':
            return val2 - val1;
        case '*':
            return val2 * val1;
        case '/':
            if (val2 == 0)
                throw new
                UnsupportedOperationException("Cannot divide by zero");
            return val2 / val1;
        case '^':
        	return Math.pow(val2, val1);
        }
        return 0;
	}
	
	/*
	 * Check if the parenthesis is symmetry or not. If yes return true, return false otherwise
	 */
	private static boolean check(String exp){
		Stack<Character> stack = new Stack<Character>();
		
		char c;
		for(int i=0;i<exp.length();i++){
			c = exp.charAt(i);
			if(c == '('){
				stack.push(c);
			}
			else if(c == ')'){
				if(stack.isEmpty()) return false;
				else if(stack.peek() == '('){
					stack.pop();
				}
				else return false;
			}
		}
		
		return true;
	}
	
}
