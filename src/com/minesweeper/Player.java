package com.minesweeper;

import java.io.Serializable;

public abstract class Player implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5754329115947251006L;
	protected Score score;
    protected PlayerState playerState;
    protected int idx;
    protected int color;
    public Player() {
           score = new Score();
           playerState = PlayerState.Waiting;
           idx = -1;
    }

	public Player(int idx) {
		super();
		this.idx = idx;
		score = new Score();
		playerState = PlayerState.Waiting;
        color = -1;
	}

	public Player(int idx, int color) {
		super();
		this.idx = idx;
		this.color = color;
		score = new Score();
		playerState = PlayerState.Waiting;

	}
	
	public void reset(){
		playerState = PlayerState.Waiting;
    	score.reset();
    }
   
	public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
 

    public Score getScore() {
        return score;
    }

    public PlayerState getState() {
        return playerState;
    }

    public void setState(PlayerState playerState) {
    	this.playerState = playerState;
    }

    public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

    public abstract PlayerMove getMove(Grid playerGrid);

}
