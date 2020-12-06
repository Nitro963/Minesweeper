package com.minesweeper.upgrade;

import com.minesweeper.SafeSquare;
import com.minesweeper.SquareState;

public class ShieldedSquare extends SafeSquare {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8361642411041203191L;
	protected boolean hasShield;
	protected int shieldId;
	public ShieldedSquare(int x, int y, SquareState squareState) {
		super(x, y, squareState);
		hasShield = false;
	}

	public ShieldedSquare(int x, int y, SquareState squareState, boolean hasShield) {
		super(x, y, squareState);
		this.hasShield = hasShield;
	}

	public ShieldedSquare(SafeSquare sq) {
		super(sq);
		hasShield = false;
	}

	public ShieldedSquare(ShieldedSquare sq) {
		super(sq);
		this.hasShield = sq.hasShield;
	}

	
	public final boolean isHasShield() {
		return hasShield;
	}

	
	public final void setHasShield(boolean hasShield) {
		this.hasShield = hasShield;
	}

	
	public final int getShieldId() {
		return shieldId;
	}

	
	public final void setShieldId(int shieldId) {
		this.shieldId = shieldId;
	}
}
