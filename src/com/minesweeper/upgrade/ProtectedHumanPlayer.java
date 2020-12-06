package com.minesweeper.upgrade;

public abstract class ProtectedHumanPlayer extends ProtectedPlayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1408824459436101653L;
	String name;

	public ProtectedHumanPlayer(String name ,int idx, int color ,int startingShields ,int protectionValue) {
		super(idx, color ,startingShields ,protectionValue);
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public ProtectedHumanPlayer(String name, int idx) {
		super(idx);
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
