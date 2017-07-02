package com.mathutil.calculus;

import com.mathutil.operations.ExpReader;
import com.mathutil.operations.Sigma;

/**
 * For some calculations in calculus such as derivative and integral.
 * @author danielxu
 *
 */
public class Calculus {

	/**A very small value, 0.000000001, as h**/
	public static final double MIN = 0.000000001;
	
	//No instance, static class
	private Calculus(){}
	
	/**
	 * Calculate the the derivative of the function on given point x=a, using formula:
	 * <center>lim(h -> 0) f'(a) = (f(a+h) - f(a)) / h</center>
	 * The result is not too accurate since the value of h is not exactly 0, instead, it is {@link #MIN}. If the function is a 
	 * polynomial function, one should use {@link #devPoly(double[], double[], double)} to determine the exact result.<br>
	 * 
	 * @param func - The funciton, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param val - The value, x = a
	 * @return The derivative of the function on point a, which is f'(a)
	 */
	public static double dev(String func , double val){
		double value1 = ExpReader.calculate(func.replace("x", String.valueOf(val+MIN)));
		double value2 = ExpReader.calculate(func.replace("x", String.valueOf(val)));
		return (value1 - value2) / MIN;
	}
	
	/**
	 * Given the degrees and the cofficients of the polynomial function, determine the derivative function. 
	 * This method only works for the polynomial function.
	 * According to the derivative of the polynomial function rule:
	 * <center>f(x) = a1*x^b1 + a2*x^b2 + ... + an*x^bn</center>
	 * <center>f'(x) = (a1*b1)*x^(b1-1) + (a2*b2)*x^(b2-1) + ... + (an*bn)*x^(bn-1)</center>
	 * If the cofficient or the degree is 0 or 1, they have to be written explictly as well.
	 * 
	 * @param coff - The cofficients of the polynomial function, 0 or 1 should not be omitted
	 * @param degree - The degrees of the polynomial function, 0 or 1 should not be omitted
	 * @return The derivative of the polynomial function, as String
	 */
	public static String devPolyExp(double[] coff , double[] degree){
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
				sb.append("("+cof[i]+")"+"*(x)^"+"("+dg[i]+")");
			}
			
			if(i != cof.length-1)
				sb.append(" + ");
		}
		return sb.toString();
	}
	
	/**
	 * Calculate the derivative of the polynomial function. This method uses the {@link #devPolyExp(double[], double[])} to determine the 
	 * derivative function first and then evaluate the result. This method only works for the polynomial function. 
	 *  
	 * @param coff - The cofficients of the polynomial function, 0 or 1 should not be omitted
	 * @param degree - The degrees of the polynomial function, 0 or 1 should not be omitted
	 * @param val - The value, x = a
	 * @return The derivative of the function on point a, which is f'(a)
	 */
	public static double devPoly(double[] coff , double[] degree , double val){
		String exp = devPolyExp(coff , degree).replace("x", String.valueOf(val));
		return ExpReader.calculate(exp);
	}
	
	/**
	 * Calculate the integral of the funciton in the given range using left Riemann Sum. The formula of Riemann Sum is:
	 * <center>b∫a f(x)dx = b∑(i=a) ( f(xi)(xi-x(i-1)) ) , xi-1 <= xi* <= xi</center>
	 * The more parts that the area be divided, the more accurate the result will be. 
	 * Please notice that the result is only a approximation and might not be too accurate, it depends on the given parts. 
	 * If the lower bound is greater than the upper bound, the result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param func - The function, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param low - The lower bound, a
	 * @param high - The upper bound, b
	 * @param parts - How many parts the area will be divided, recommend is 100
	 * @return The approximated integral evaluated by Reimann Sum
	 */
	public static double integ_riemann_left(String func , double low , double high , int parts){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		double dx = (high-low)/parts;
		double sum = Sigma.sum(low, high-dx, func, dx)* dx;
		
		return neg? -sum : sum;
	}
	
	/**
	 * Calculate the integral of the funciton in the given range using right Riemann Sum. The formula of Riemann Sum is:
	 * <center>b∫a f(x)dx = b∑(i=a) ( f(xi*)(xi-x(i-1)) ) , x(i-1) <= xi* <= xi</center>
	 * The more parts that the area be divided, the more accurate the result will be. 
	 * Please notice that the result is only a approximation and might not be too accurate, it depends on the given parts. 
	 * If the lower bound is greater than the upper bound, the result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param func - The function, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param low - The lower bound, a
	 * @param high - The upper bound, b
	 * @param parts - How many parts the area will be divided
	 * @return The approximated integral evaluated by Reimann Sum
	 */
	public static double integ_riemann_right(String func , double low , double high , int parts){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		double dx = (high-low)/parts;
		double sum = Sigma.sum(low+dx, high, func, dx)*dx;
		
		return neg? -sum : sum;
	}
	
	/**
	 * Calculate the integral of the funciton in the given range using mid point Riemann Sum. The formula of Riemann Sum is:
	 * <center>b∫a f(x)dx = b∑(i=a) ( f(xi)(xi-x(i-1)) ) , xi-1 <= xi* <= xi</center>
	 * The more parts that the area be divided, the more accurate the result will be. 
	 * Please notice that the result is only a approximation and might not be too accurate, it depends on the given parts. 
	 * If the lower bound is greater than the upper bound, the result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param func - The function, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param low - The lower bound, a
	 * @param high - The upper bound, b
	 * @param parts - How many parts the area will be divided
	 * @return The approximated integral evaluated by Reimann Sum
	 */
	public static double integ_riemann_mid(String func , double low , double high , int parts){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		double dx = (high-low)/parts;
		double sum = Sigma.sum((low+low+dx)/2, high, func, dx)*dx;
		
		return neg? -sum : sum;
	}
	
	/**
	 * Calculate the integral of the funciton in the given range using trapezoid rule. The formula of trapezoid rule is:
	 * <center>b∫a f(x)dx = (dx/2)*(b∑(i=a) f(x1) + 2*f(x2) + ... + 2*f(x(n-1)) + f(xn))</center>
	 * The more parts that the area be divided, the more accurate the result will be. 
	 * Please notice that the result is only a approximation and might not be too accurate, it depends on the given parts. 
	 * If the lower bound is greater than the upper bound, the result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param func - The function, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param low - The lower bound, a
	 * @param high - The upper bound, b
	 * @param parts - How many parts the area will be divided.
	 * @return The approximated integral evaluated by Reimann Sum
	 */
	public static double integ_riemann_trapezoid(String func , double low , double high , int parts){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		double dx = (high-low)/parts;
		double sum = Sigma.sum(low+dx, high-dx, func, dx)*2;
		sum += ExpReader.calculate(func.replace("x", String.valueOf(low)));
		sum += ExpReader.calculate(func.replace("x", String.valueOf(high)));
		sum *= dx/2;
		
		return neg? -sum : sum;
	}
	
	
	/**
	 * Calculate the integral of the function in the given range using Simpson's Rule. The formula of Simpson's Rule is:
	 * <center>b∫a f(x)dx ≈ (dx/3)(f(x0) + 4f(x1) + 2f(x2) ... + 4f(xn-1) + f(xn)), dx = (b-a)/n</center>
	 * Please notice that the result is only a approximation and might not be too accurate, it depends on the given parts.
	 * If the lower bound is greater than the upper bound, the result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param func - The function, see {@link ExpReader#calculate(String)} to check out the available operations.
	 * @param low - The lower bound, a
	 * @param high - The upper bound, b
	 * @param parts - How many parts, must be an even number according to Simpson's Rule. Usually 4 is recommended
	 * @return The approximated integral evaluated by Simpson's Rule
	 */
	public static double integ_simpson(String func , double low , double high , double parts){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		double dx = (high-low)/parts;
		double d1 = dx/3;
		double sum = 0;
		int index = 0;
		for(double i=low;i<=high;i+=dx){
			if(i == low || i == high){
				sum += ExpReader.calculate(func.replace("x", String.valueOf(i)));
			}
			else{
				if(index % 2 ==0){
					sum += 4 * ExpReader.calculate(func.replace("x", String.valueOf(i)));
				}
				else{
					sum += 2 * ExpReader.calculate(func.replace("x", String.valueOf(i)));
				}
				index++;
			}
		}
		return neg? -d1*sum : d1*sum;
	}

	/**
	 * Given the degrees and the cofficients of the polynomial function, determine the infinite integral. 
	 * This method only works for the polynomial function.
	 * According to the integral of the polynomial function rule:
	 * <center>f(x) = a1*x^b1 + a2*x^b2 + ... + an*x^bn</center>
	 * <center>∫f(x)dx = (a1*x^(b1+1))/(b1+1) + (a2*x^(b2+1))/(b2+1) + ... + (an*x^(bn+1))/(bn+1) + C</center>
	 * If the cofficient or the degree is 0 or 1, they have to be written explictly as well. The result will always contains a C 
	 * represents any number.
	 * 
	 * @param coff - The cofficients of the polynomial function, 0 or 1 should not be omitted
	 * @param degree - The degrees of the polynomial function, 0 or 1 should not be omitted
	 * @return The integral of the polynomial function, as String
	 */
	public static String integPolyExp(double[] coff , double[] degree){
		String[] cof = new String[coff.length];
		String[] dg = new String[degree.length];
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<degree.length;i++){
			dg[i] = String.valueOf(degree[i]+1);
			cof[i] = String.valueOf(coff[i]/(degree[i]+1));
		}
		for(int i=0;i<cof.length;i++){
			if(dg[i].equals("0.0")){
				sb.append("("+cof[i]+")");
			}
			else{
				sb.append("("+cof[i]+")"+"*(x)^"+"("+dg[i]+")");
			}
			
			if(i != cof.length-1)
				sb.append(" + ");
		}
		sb.append(" + C");
		return sb.toString();
	}
	
	/**
	 * Calculate the definite integral of the polynomial function between a and b. According to the fundamental theorem of calculus:
	 * <center>F(b) - F(a) = b∫a f(x)dx</center>
	 * Please notice that this method only works for polynomial functions. And also, if the lower bound is greater than the upper bound, the 
	 * result will be opposite according to:
	 * <center>F(b) - F(a) = -a∫b f(x)dx, if a > b</center>
	 * 
	 * @param coff - The cofficients of the polynomial function, 0 and 1 should not be omitted
	 * @param degree - The degrees of the polynomial function, 0 and 1 should not be omitted
	 * @param low - The lower bound a
	 * @param high - The upper bound b
	 * @return The definite intergral
	 */
	public static double integPoly(double[] coff , double[] degree , double low , double high){
		if(low == high)
			return 0;
		
		boolean neg = false;
		if(low > high){
			double temp = low;
			low = high;
			high = temp;
			neg = true;
		}
		
		String func = integPolyExp(coff , degree).replace(" + C", "");
		double v1 = ExpReader.calculate(func.replace("x", String.valueOf(high)));
		double v2 = ExpReader.calculate(func.replace("x", String.valueOf(low)));
		
		return neg ? -v1-v2 : v1-v2;
	}
	
}
