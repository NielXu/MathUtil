package com.mathutil.exceptions;

public class BoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public BoundException(String errMessage){
		super(errMessage);
	}
	
}
