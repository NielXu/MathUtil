package com.mathutil.exceptions;

public class CalculationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public CalculationException(String errMessage){
		super(errMessage);
	}
	
}
