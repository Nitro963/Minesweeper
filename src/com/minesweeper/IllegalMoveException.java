package com.minesweeper;

public class IllegalMoveException extends GameException{
	private static final long serialVersionUID = -1429054575138373104L;
	
	public IllegalMoveException() {
		super("IllegalMove");
	}
	public IllegalMoveException(String ex) {
		super(ex);
	}
}
