package com.minesweeper;

public class IllegalArgumentGameException extends GameException {

	
	private static final long serialVersionUID = 5898302479456343222L;

	public IllegalArgumentGameException() {
		super("IllegalArgumentGameException");
	}

	public IllegalArgumentGameException(String ex) {
		super(ex);
	}

}
