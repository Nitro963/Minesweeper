package com.util.upgrade;

import java.io.Serializable;

import com.minesweeper.Grid;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;

public abstract class Logic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6024414928980734812L;
	Player p;
	public void reset() {
		p.reset();
	}
	public PlayerMove getMove(Grid playerGrid) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return p.getMove(playerGrid);
	}
}
