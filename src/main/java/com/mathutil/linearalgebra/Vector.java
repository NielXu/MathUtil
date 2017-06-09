package com.mathutil.linearalgebra;

/**
 * A basic vector, with one or more components.
 * @author danielxu
 *
 * @param <T> Any generic type
 */
public interface Vector<T> {

	/**
	 * Set the vector component to a new one.
	 * @param index - The index of the component
	 * @param val - The new component that will replace the old one
	 * @return The old component
	 */
	public T setComponent(int index , T val);
	
	/**
	 * Get all the components of the vector.
	 * @return The components of the vector
	 */
	public T[] getComponents();
	
	/**
	 * Get the dimension of the vector
	 * @return The dimension of the vector
	 */
	public int getDimension();
	
	/**
	 * Get a component of the vector by the index
	 * @param index - The index of the component, start from 0
	 * @return One of the component
	 */
	public T get(int index);
	
}
