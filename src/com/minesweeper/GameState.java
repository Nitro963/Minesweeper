package com.minesweeper;

import java.io.Serializable;

public class GameState implements Serializable{

	private static final long serialVersionUID = -320981554550902672L;
	public static final GameState GameOver = new GameState("GameOver");
    public static final GameState RunningGame = new GameState("RunningGame");
    public static final GameState NewGame = new GameState("NewGame");
    public static final GameState TerminatedGame = new GameState("TerminatedGame");
    public static final GameState ReplayingGame = new GameState("ReplayingGame");
    String state;
    protected GameState(String state) {
    	this.state = state;
    }
	@Override
	public String toString() {
		return state;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameState other = (GameState) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	
    
    
}
