package com.mathutil.linearalgebra;

import java.math.BigDecimal;
import java.util.Arrays;

import com.mathutil.ExactMatrix;
import com.mathutil.MathUtil;
import com.mathutil.Matrix;
import com.mathutil.exceptions.SuperVectorException;

/**
 * SuperVector is a class that allows one to create a n-dimensional vector: [p1,p2,p3...pn], and the components can be modified 
 * through <code>setComponent(int,double)</code>. The components are stored using double type for higher accurancy. 
 * And the distance between two vector(They must have the same dimension) can be calculated by {@link MathUtil#dist(SuperVector, SuperVector)} 
 * in {@link MathUtil} class.
 * @author danielxu
 */
public class SuperVector implements MatrixVector{
	
	/**The array that stores the components**/
	private Double[] compo;
	
	/**Dimension, the number of components**/
	private int dimension;
	
	/**
	 * Create an empty SuperVector without any components
	 */
	public SuperVector(){
		dimension = 0;
	}
	
	public SuperVector(ExactMatrix m){
		if(m == null)
			throw new SuperVectorException("Matrix cannot be null");
		if(m.getRows() > 1)
			throw new SuperVectorException("Only matrices that have one row can be converted to SuperVector");
		
		BigDecimal[][] b = m.getMatrix();
		compo = new Double[m.getCols()];
		
		for(int i=0;i<m.getCols();i++){
			compo[i] = b[0][i].doubleValue();
		}
		dimension = compo.length;
	}
	
	/**
	 * Convert a row matrix to the supervector. Only matrices that have one row can be converted to SuperVector.
	 * @param m - The row matrix
	 */
	public SuperVector(Matrix m){
		if(m == null)
			throw new SuperVectorException("Matrix cannot be null");
		if(m.getRows() > 1)
			throw new SuperVectorException("Only matrices that have one row can be converted to SuperVector");
		
		Number[][] n = m.getMatrix();
		compo = new Double[m.getCols()];
		
		for(int i=0;i<n[0].length;i++){
			compo[i] = n[0][i].doubleValue();
		}
		dimension = compo.length;
	}
	
	/**
	 * Create a SuperVector that has the given components: [p1 , p2 , p3 ... pn]
	 * @param compo The components of the SuperVector
	 */
	public SuperVector(double...compo){
		if(compo == null)
			throw new SuperVectorException("The components of the SuperVector cannot be null");
		
		this.compo = new Double[compo.length];
		for(int i=0;i<compo.length;i++)
			this.compo[i] = compo[i];
		
		dimension = compo.length;
	}
	
	/**
	 * Set one of the component of the vector to a new value.
	 * @param index - The index of the component
	 * @param val - The new value that will replace the old one
	 * @return The old component
	 */
	public Double setComponent(int index, Double val) {
		indexCheck(index);
		double old = compo[index];
		compo[index] = val;
		return old;
	}

	/**
	 * Get all the components of the vector. This method will return a copy of the 
	 * array, one can modify the array and it will not affect the array that is stored 
	 * in the SuperVector class.
	 * @return All the components of the vector
	 */
	public Double[] getComponents() {
		return compo == null ? null : Arrays.copyOf(compo, compo.length);
	}

	/**
	 * Get the dimension of the vector, it's the same as saying, the number 
	 * of components of the vector
	 * @return The dimension of the vector
	 */
	public int getDimension() {
		return dimension;
	}
	
	public Matrix convertMatrix() {
		return new Matrix(this);
	}

	public ExactMatrix convertExactMatrix() {
		return new ExactMatrix(this);
	}

	
	/**
	 * Get the String that represents the vector. The String will start with <i>[</i> and then there is 
	 * a <i>,</i> (one space before comma and one space after comma)between each two components. 
	 * And at the end a <i>]</i> will be added
	 */
	@Override
	public String toString(){
		if(compo == null)
			return "Empty";
		
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for(int i=0;i<compo.length;i++){
			sb.append(compo[i]);
			if(i != compo.length-1)
				sb.append(" , ");
		}
		sb.append(" ]");
		return sb.toString();
	}
	
	//Check if index is valid
	private void indexCheck(int index) {
		if(index >= dimension)
			throw new SuperVectorException("Index out of bound "+index);
	}
}
