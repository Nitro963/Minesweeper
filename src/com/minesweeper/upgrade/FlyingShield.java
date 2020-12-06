package com.minesweeper.upgrade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.minesweeper.Grid;
import com.minesweeper.SquareState;
import com.util.Pair;
import com.util.TraversingHelper;

public class FlyingShield extends Shield implements Serializable{

	private static final long serialVersionUID = 8515829310708166714L;
	protected Grid gameGrid;
	protected boolean captured;
	public FlyingShield(int id, int protectionValue, ShieldedSquare sq, Grid gameGrid) {
		super(id, protectionValue, sq);
		this.gameGrid = gameGrid;
		captured = false;
		sq.setHasShield(true);
	}

	public void move(ShieldedSquare sq) {
		this.sq.setHasShield(false);
		this.sq.setShieldId(-1);
		this.sq = sq;
		this.sq.setShieldId(this.id);
		this.sq.setHasShield(true);
		System.out.println(sq);
	}

	public void move() {
		int x = sq.getX();
		int y = sq.getY();
		TraversingHelper helper = new TraversingHelper(gameGrid.getN(), gameGrid.getM());
		ArrayList<Pair> neighbours = helper.getNeighbours(x, y);
		ArrayList<ShieldedSquare> shieldedNeighbours = new ArrayList<>(); 
		for (Pair neighbour : neighbours) {
			if(sq.getState() != SquareState.Revealed)
				if (gameGrid.getSquare(neighbour.getX(), neighbour.getY()) instanceof ShieldedSquare) {
					ShieldedSquare safe = (ShieldedSquare) gameGrid.getSquare(neighbour.getX(), neighbour.getY());
					if (safe.isHasShield()) 
						continue;
					shieldedNeighbours.add(safe);					
				}
		}
		if (shieldedNeighbours.isEmpty())
			return;
		Collections.shuffle(shieldedNeighbours);
		move(shieldedNeighbours.get(0));
	}

	public final boolean isCaptured() {
		return captured;
	}

	public final void setCaptured(boolean captured) {
		this.captured = captured;
	}
	
}
