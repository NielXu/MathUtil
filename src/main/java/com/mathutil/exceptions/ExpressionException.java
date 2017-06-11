package com.mathutil.exceptions;

public class ExpressionException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExpressionException(String errMessage){
		super(errMessage);
	}

}
