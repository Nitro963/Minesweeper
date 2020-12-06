package com.minesweeper.upgrade;

import com.minesweeper.upgrade.ProtectedHumanPlayer;

import com.minesweeper.Grid;
import com.minesweeper.MoveType;
import com.minesweeper.PlayerMove;
import com.minesweeper.Square;

public class ProtectedGuiHumanPlayer extends ProtectedHumanPlayer {

	private static final long serialVersionUID = -2682168626592622365L;
	public Integer r, c;
	public Boolean type;

	public ProtectedGuiHumanPlayer(String name, int idx) {
		super(name, idx);
	}

	public ProtectedGuiHumanPlayer(String name, int idx, int c,int startingShields ,int protectionValue) {
		super(name, idx, c ,startingShields ,protectionValue);
	}

	public final Integer getR() {
		return r;
	}

	public final void setR(Integer r) {
		this.r = r;
	}

	public final void setC(Integer c) {
		this.c = c;
	}

	public final void setType(Boolean type) {
		this.type = type;
	}

	@Override
	public synchronized PlayerMove getMove(Grid playerGrid) {
		if (type == null)
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		try {
			if(type == null)
				return null;
			if (type)
				return new PlayerMove(this, new Square(r, c), MoveType.Reveal);
			return new PlayerMove(this, new Square(r, c), MoveType.Mark);
		} finally {
			r = null;
			c = null;
			type = null;
		}
	}

}
