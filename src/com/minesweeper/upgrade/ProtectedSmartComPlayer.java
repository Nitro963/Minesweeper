package com.minesweeper.upgrade;

import com.util.upgrade.SmartComLogic;

public class ProtectedSmartComPlayer extends ProtectedComPlayer {

	private static final long serialVersionUID = 3875833465186235680L;

	public ProtectedSmartComPlayer(int idx, int color,int startingShields ,int protectionValue) {
		super(idx, color ,startingShields ,protectionValue);
		super.logic = new SmartComLogic();
	}

	public ProtectedSmartComPlayer(int idx) {
		super(idx);
		super.logic = new SmartComLogic();
	}

}
