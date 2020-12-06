package com.minesweeper;

import java.io.Serializable;

public class EndGameCause implements Serializable{

	private static final long serialVersionUID = 4056511447620183512L;
	public static final EndGameCause Alllost = new EndGameCause("Alllost");
	public static final EndGameCause Allsafe = new EndGameCause("Allsafe");
	public static final EndGameCause Allmine = new EndGameCause("Allmine");

	protected String state;
	
	protected EndGameCause(String state){
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
		EndGameCause other = (EndGameCause) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	

}
