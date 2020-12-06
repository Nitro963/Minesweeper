package com.minesweeper.upgrade;

import com.util.upgrade.DumpComLogic;

public class ProtectedDumpComPlayer extends ProtectedComPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1688730504004544492L;

	public ProtectedDumpComPlayer(int idx, int color,int startingShields ,int protectionValue) {
		super(idx, color ,startingShields ,protectionValue);
		super.logic = new DumpComLogic();
	}

	public ProtectedDumpComPlayer(int idx) {
		super(idx);
		super.logic = new DumpComLogic();
	}
	
}
