package com.minesweeper;

public class GuiHumanPlayer extends HumanPlayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5862123912523279519L;
	public Integer r, c;
	public Boolean type;

	public GuiHumanPlayer(String name, int idx) {
		super(name, idx);
	}

	public GuiHumanPlayer(String name, int idx, int c) {
		super(name, idx, c);
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
	public PlayerMove getMove(Grid playerGrid) {
		if (type == null)
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
