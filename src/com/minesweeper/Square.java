package com.minesweeper;

import java.io.Serializable;

public class Square implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8842571856622001439L;
	protected int x, y;
	protected SquareState squareState;
	protected int color = -1;

	@Override
	public String toString() {
		return ("(" + (x + 1) + "," + (char)(y + 'A') + ")");
	}

	public Square(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		squareState = SquareState.Locked;
	}

	public Square(Square sq) {
		this.x = sq.x;
		this.y = sq.y;
		this.squareState = sq.squareState;
		this.color = sq.color;
	}

	public Square(int x, int y, SquareState squareState) {
		this.x = x;
		this.y = y;
		this.squareState = squareState;
	}

	public Square(int x, int y, SquareState squareState, int color) {
		super();
		this.x = x;
		this.y = y;
		this.squareState = squareState;
		this.color = color;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Square() {
		x = y = 0;
		squareState = SquareState.Locked;
	}

	public SquareState getState() {
		return squareState;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setState(SquareState squareState) {
		this.squareState = squareState;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
