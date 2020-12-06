package com.minesweeper;

abstract public class ComPlayer extends Player{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1128078151078392167L;
	int num;
	public ComPlayer(int idx) {
    	super(idx);
    	num = -1;
    }
    public ComPlayer(int idx ,int color) {
		super(idx ,color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ComPlayer" + num;
	}

	public int getNum() {
		return num;
	}
    public void setNum(int num) {
    	this.num = num;
    }
}
