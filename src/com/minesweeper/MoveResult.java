package com.minesweeper;

import java.io.Serializable;

public class MoveResult implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8749660499835689065L;
	private int scoreChange;
    private SquareState newSquareStates;

    public MoveResult(int scoreChange, SquareState newSquareState) {
        this.scoreChange = scoreChange;
        this.newSquareStates = newSquareState;
    }

    public int getScoreChange() {
        return scoreChange;
    }

    public final void setScoreChange(int scoreChange) {
		this.scoreChange = scoreChange;
	}

	public SquareState getNewSquareStates() {
        return newSquareStates;
    }
    
}
