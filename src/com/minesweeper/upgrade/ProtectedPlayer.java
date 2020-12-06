package com.minesweeper.upgrade;

import java.util.PriorityQueue;

import com.minesweeper.Player;

public abstract class ProtectedPlayer extends Player {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1764917861835198234L;
	protected PriorityQueue<Shield> shields;
	protected int startingShields;
	protected int protectionValue;
	public ProtectedPlayer() {
		super();
		shields = new PriorityQueue<>();
		score = new ProtectedScore();
	}

	public ProtectedPlayer(int idx, int color ,int startingShields ,int protectionValue) {
		super(idx, color);
		shields = new PriorityQueue<>();
		score = new ProtectedScore();
		this.startingShields = startingShields;
		this.protectionValue = protectionValue;
	}

	public ProtectedPlayer(int idx) {
		super(idx);
		shields = new PriorityQueue<>();
		score = new ProtectedScore();
	}

	public void addShield(Shield s) {
		System.out.println("Shield added location: " + s.getSquare());
		shields.add(s);
	}
	
	public void removeShield(Shield s) {
		if(s == null)
			return;
		shields.remove(s);
	}

	public final PriorityQueue<Shield> getShields() {
		return shields;
	}

	public Shield useShield() {
		return shields.poll();
	}
	
	@Override
	public void reset() {
		super.reset();
    	shields.clear();
		for(int i = 0 ; i < startingShields ; i++)
			shields.add(new FixedShield(protectionValue));
	}
}
