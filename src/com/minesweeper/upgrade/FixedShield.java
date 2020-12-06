package com.minesweeper.upgrade;

public class FixedShield extends Shield{

	/**
	 * 
	 */
	private static final long serialVersionUID = 675564553217270233L;

	public FixedShield(int protectonValue) {
		super(-1 ,protectonValue ,null);
	}
	
	public FixedShield(int id ,int protectionValue ,ShieldedSquare sq) {
		super(id ,protectionValue ,sq);
		sq.setHasShield(true);
	}

}
