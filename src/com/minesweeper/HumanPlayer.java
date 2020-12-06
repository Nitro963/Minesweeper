
package com.minesweeper;

abstract public class HumanPlayer extends Player{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5401274650418807914L;
	String name;

	public HumanPlayer(String name ,int idx) {
		super(idx);
		this.name = name;
	}
	public HumanPlayer(String name, int idx, int c) {
		super(idx ,c);
		this.name = name;
	}
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
    
}