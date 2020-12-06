package com.util.upgrade;

import com.minesweeper.DumpComPlayer;

public class DumpComLogic extends ComLogic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112639355052180955L;

	public DumpComLogic() {
		super();
		super.p = new DumpComPlayer(-1);
	}
	
}
