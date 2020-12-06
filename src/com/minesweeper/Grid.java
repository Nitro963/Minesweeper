package com.minesweeper;

import java.io.Serializable;

public class Grid implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1028420178242658768L;
	protected Square[][] grid;
    protected int n ,m;
    
    public Grid(int n ,int m) {
        grid = new Square[n][m];
        this.n = n;
        this.m = m;
    }

    public Square getSquare(int x ,int y) {
        return grid[x][y];
    }

    public void setSquare(Square sq) {
        grid[sq.x][sq.y] = sq;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }	
    
    public void updateConstrains(int n ,int m) {
    	this.n = n;
    	this.m = m;
    	grid = new Square[n][m];
    	for(int i = 0 ; i < n ; i++)
    		for(int j = 0 ; j < m ; j++)
    			grid[i][j] = new Square(i ,j ,SquareState.Locked);
    }

}
