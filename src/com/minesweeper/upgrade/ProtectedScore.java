package com.minesweeper.upgrade;

import com.minesweeper.Score;

public class ProtectedScore extends Score {


	private static final long serialVersionUID = 5346263507346969771L;
	protected int shieldsBouns;
	public ProtectedScore() {
		super();
		this.shieldsBouns = 0;
	}
	public final int getShieldsBouns() {
		return shieldsBouns;
	}
	public final void setShieldsBouns(int shieldsBouns) {
		this.shieldsBouns = shieldsBouns;
	}
	
	public int getScore() {
		return super.getScore() + shieldsBouns;
	}
	
	@Override
	public String toString() {
		return super.toString() + " shieldsBouns = " + shieldsBouns;
	}
	

}
