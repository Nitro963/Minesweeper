package com.minesweeper;

abstract public class GameException extends Exception{

	private static final long serialVersionUID = 2487299506815704257L;

	
	public GameException() {
		super("There were error(s)");
	}


	public GameException(String ex) {
		super(ex);
	}
	
}
