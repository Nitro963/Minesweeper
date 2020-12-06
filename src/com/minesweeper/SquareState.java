package com.minesweeper;

import java.io.Serializable;

public class SquareState implements Serializable {

	private static final long serialVersionUID = -713687683723022022L;
	public static final SquareState Revealed = new SquareState("Revealed");
	public static final SquareState Locked = new SquareState("Locked");
	public static final SquareState Marked = new SquareState("Marked");
	
	protected final String state;

	protected SquareState(String state) {
		this.state = state;
	}

	public String toString() {
		return state;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SquareState other = (SquareState) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

}
