package com.minesweeper.upgrade;

import com.minesweeper.Grid;
import com.minesweeper.PlayerMove;
import com.util.upgrade.ComLogic;

public abstract class ProtectedComPlayer extends ProtectedPlayer{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5925607390460952101L;
	protected ComLogic logic;
	protected int num;
	public ProtectedComPlayer() {
		super();
	}

	public ProtectedComPlayer(int idx, int color ,int startingShields ,int protectionValue) {
		super(idx, color ,startingShields ,protectionValue);
		num = -1;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public ProtectedComPlayer(int idx) {
		super(idx);
		num = -1;
	}
	
	public String toString() {
		return String.valueOf("Computer" + num);
	}

	public void reset() {
		logic.reset();
		super.reset();
	}

	@Override
	public PlayerMove getMove(Grid playerGrid) {
		PlayerMove move = logic.getMove(playerGrid);
		move.setPlayer(this);
		return move;
	}

}
