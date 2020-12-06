package com.minesweeper;

public class ForbiddenServesRequest extends GameException{

	private static final long serialVersionUID = -7257199002173935979L;

	public ForbiddenServesRequest(String ex) {
		super(ex);
	}

	public ForbiddenServesRequest() {
		super();
	}
	
	
}
