package com.minesweeper;

import java.io.Serializable;

public class MoveType implements Serializable{

	private static final long serialVersionUID = -5024669247158981812L;
	public static final MoveType Reveal = new MoveType("Reveal");
    public static final MoveType Mark = new MoveType("Mark");
    public static final MoveType Unmark = new MoveType("Unmark");
    
    protected final String type;
    protected MoveType(String type) {
    	this.type = type;
    }
	@Override
	public String toString() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoveType other = (MoveType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
