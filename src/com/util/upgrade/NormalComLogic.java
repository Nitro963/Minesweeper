package com.util.upgrade;

import com.minesweeper.NormalComPlayer;

public class NormalComLogic extends ComLogic {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2913808740517775316L;

	public NormalComLogic() {
		super();
		super.p = new NormalComPlayer(-1);
	}

}
