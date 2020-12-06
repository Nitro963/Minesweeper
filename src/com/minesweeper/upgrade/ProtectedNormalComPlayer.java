package com.minesweeper.upgrade;

import com.util.upgrade.NormalComLogic;

public class ProtectedNormalComPlayer extends ProtectedComPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6230864565165627694L;

	public ProtectedNormalComPlayer(int idx, int color,int startingShields ,int protectionValue) {
		super(idx, color ,startingShields ,protectionValue);
		super.logic = new NormalComLogic();
	}

	public ProtectedNormalComPlayer(int idx) {
		super(idx);
		super.logic = new NormalComLogic();
	}
	
}
