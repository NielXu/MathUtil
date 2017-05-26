package com.mathutil.exceptions;

public class MatrixCalculationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MatrixCalculationException(String errMessage){
		super(errMessage);
	}
	
}
