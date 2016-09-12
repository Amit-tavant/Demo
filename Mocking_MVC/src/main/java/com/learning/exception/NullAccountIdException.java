package com.learning.exception;

public class NullAccountIdException extends Exception{
	private static final long serialVersionUID = 1L;

	
	 public NullAccountIdException(String message){
		 super(message);
	 }
}
