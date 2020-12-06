
package com.minesweeper;

public class SafeSquare extends Square{

	private static final long serialVersionUID = 8226392188148940505L;
	protected int mineNeighborsCount;
    public SafeSquare(int x, int y, SquareState squareState) {
        super(x, y, squareState);
        mineNeighborsCount = 0;
    }

    public SafeSquare(SafeSquare sq) {
    	super(sq);
    	this.mineNeighborsCount = sq.mineNeighborsCount;
	}

	public int getMineNeighborsCount() {
        return mineNeighborsCount;
    }

    public void setMineNeighboursCount(int mineNeighborsCount) {
        this.mineNeighborsCount = mineNeighborsCount;
    }
    
    
}
