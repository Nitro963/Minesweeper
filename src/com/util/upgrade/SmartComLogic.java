package com.util.upgrade;

import com.minesweeper.SmartComPlayer;

public class SmartComLogic extends ComLogic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5002837037571676724L;

	public SmartComLogic() {
		super();
		super.p = new SmartComPlayer(-1);
	}
	
}
