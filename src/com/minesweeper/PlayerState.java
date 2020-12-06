package com.minesweeper;

import java.io.Serializable;

public class PlayerState implements Serializable{

	private static final long serialVersionUID = -8850589796423547637L;
	public static final PlayerState Playing = new PlayerState("Playing"); 
    public static final PlayerState Winner = new PlayerState("Winner"); 
    public static final PlayerState Loser = new PlayerState("Loser");
    public static final PlayerState Waiting = new PlayerState("Waiting");


    protected final String state;
    
    protected PlayerState(String state) {
    	this.state= state;
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
		PlayerState other = (PlayerState) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	
    
}
