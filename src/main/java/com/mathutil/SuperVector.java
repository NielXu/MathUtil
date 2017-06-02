package com.mathutil;

import java.util.Arrays;

import com.mathutil.exceptions.SuperVectorException;

/**
 * SuperVector is a class that allows one to create a n-dimensional vector: [p1,p2,p3...pn], and the components can be modified 
 * through <code>setComponent(int,double)</code>. The components are stored using double type for higher accurancy. 
 * And the distance between two vector(They must have the same dimension) can be calculated by {@link MathUtil#dist(SuperVector, SuperVector)} 
 * in {@link MathUtil} class.
 * @author danielxu
 */
public class SuperVector{
	
	/**The array that stores the components**/
	private double[] compo;
	
	/**Dimension, the number of components**/
	private int dimensions;
	
	/**
	 * Create an empty SuperVector without any components
	 */
	public SuperVector(){
		dimensions = 0;
	}
	
	/**
	 * Create a SuperVector that has the given components: [p1 , p2 , p3 ... pn]
	 * @param compo The components of the SuperVector
	 */
	public SuperVector(double...compo){
		if(compo == null)
			throw new SuperVectorException("The components of the SuperVector cannot be null");
		this.compo = compo;
		dimensions = compo.length;
	}
	
	/**
	 * Set one of the component of the vector to a new value.
	 * @param index - The index of the component
	 * @param val - The new value that will replace the old one
	 */
	public void setComponent(int index, double val) {
		indexCheck(index);
		compo[index] = val;
	}

	/**
	 * Get all the components of the vector. This method will return a copy of the 
	 * array, one can modify the array and it will not affect the array that is stored 
	 * in the SuperVector class.
	 * @return All the components of the vector
	 */
	public double[] getComponents() {
		return compo == null ? null : Arrays.copyOf(compo, compo.length);
	}

	/**
	 * Get the dimension of the vector, it's the same as saying, the number 
	 * of components of the vector
	 * @return The dimension of the vector
	 */
	public int getDimension() {
		return dimensions;
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
		if(index >= dimensions)
			throw new SuperVectorException("Index out of bound "+index);
	}
	
}
