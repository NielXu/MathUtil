package com.mathutil.calculus;

import com.mathutil.operations.ExpReader;

/**
 * For some calculations in calculus such as derivative and integral.
 * @author danielxu
 *
 */
public class Calculus {

	/**A very small value, 0.000000001, as h**/
	public static double MIN = 0.000000001;
	
	//No instance, static class
	private Calculus(){}
	
	/**
	 * Calculate the the derivative of the function on given point x=a, using formula:
	 * <center>lim(h -> 0) f'(a) = (f(a+h) - f(a)) / h</center>
	 * The result is not too accurate since the value of h is not exactly 0, instead, it is {@link #MIN}. If the function is a 
	 * polynomial function, one should use {@link #devPoly(double[], double[], double)} to determine the exact result.<br>
	 * See {@link ExpReader#calculate(String)} to check out the available operations.
	 * 
	 * @param exp - The expression, must use x as the variable name
	 * @param val - The value, x = a
	 * @return The derivative of the function on point a, which is f'(a)
	 */
	public static double dev(String exp , double val){
		double value1 = ExpReader.calculate(exp.replace("x", String.valueOf(val+MIN)));
		double value2 = ExpReader.calculate(exp.replace("x", String.valueOf(val)));
		return (value1 - value2) / MIN;
	}
	
	/**
	 * Given the degrees and the cofficients of the polynomial function, determine the derivative of the function. 
	 * This method only works for the polynomial function.
	 * According to the derivative of the polynomial function rule:
	 * <center>f(x) = a1*x^b1 + a2*x^b2 + ... + an*x^bn</center>
	 * <center>f'(x) = (a1*b1)*x^(b1-1) + (a2*b2)*x^(b2-1) + ... + (an*bn)*x^(bn-1)</center>
	 * If the cofficient or the degree is 0 or 1, they have to be written explictly as well.
	 * 
	 * @param degree - The degrees of the polynomial function, 0 or 1 should not be omitted
	 * @param coff - The cofficients of the polynomial function, 0 or 1 should not be omitted
	 * @return The derivative of the polynomial function, as String
	 */
	public static String devPolyExp(double[] degree , double[] coff){
		String[] cof = new String[coff.length];
		String[] dg = new String[degree.length];
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<degree.length;i++){
			dg[i] = String.valueOf(degree[i]-1);
			cof[i] = String.valueOf(coff[i]*degree[i]);
		}
		for(int i=0;i<cof.length;i++){
			if(dg[i].equals("0.0")){
				sb.append("("+cof[i]+")");
			}
			else{
				sb.append("("+cof[i]+")"+"*x^"+"("+dg[i]+")");
			}
			
			if(i != cof.length-1)
				sb.append(" + ");
		}
		return sb.toString();
	}
	
	/**
	 * Calculating the derivative of the polynomial function. This method uses the {@link #devPolyExp(double[], double[])} to determine the 
	 * derivative function first and then evaluate the result. This method only works for the polynomial function. 
	 *  
	 * @param degree - The degrees of the polynomial function, 0 or 1 should not be omitted
	 * @param coff - The cofficients of the polynomial function, 0 or 1 should not be omitted
	 * @param val - The value, x = a
	 * @return The derivative of the function on point a, which is f'(a)
	 * @see #devPolyExp(double[], double[])
	 */
	public static double devPoly(double[] degree , double[] coff , double val){
		String exp = devPolyExp(degree , coff).replace("x", String.valueOf(val));
		return ExpReader.calculate(exp);
	}
	
	public static double integ(){
		//TODO integral approximation
		return 0;
	}
	
	public static double integPoly(){
		//TODO integral of polynomial functions
		return 0;
	}
	
}
