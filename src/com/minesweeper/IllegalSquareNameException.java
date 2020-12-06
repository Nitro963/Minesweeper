package com.minesweeper;

public class IllegalSquareNameException extends GameException{

	private static final long serialVersionUID = 7168575620506710246L;
	public IllegalSquareNameException() {
		super("Illegal Square Coordinates");
	}

	public IllegalSquareNameException(String ex) {
		super(ex);
	}
}
