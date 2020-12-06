package com.minesweeper;

import java.io.Serializable;

class Mine implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8882799393751802035L;
	protected int id;

    public Mine(int id) {
        this.id = id;
    }

	public Mine(Mine m) {
		this.id = m.id;
	}
    
}
public class MineSquare extends Square{
    /**
	 * 
	 */
	private static final long serialVersionUID = 370377607393120036L;
	Mine m;
    
    public Mine getM() {
		return m;
	}

	public void setM(Mine m) {
		this.m = m;
	}
	public String toString(){
		return String.valueOf(m.id);
	}
	public MineSquare(Mine m, int x, int y, SquareState squareState) {
        super(x, y, squareState);
        this.m = m;
    }

	public MineSquare(MineSquare sq) {
		super(sq);
		this.m = new Mine(sq.m);
	}
    
}
