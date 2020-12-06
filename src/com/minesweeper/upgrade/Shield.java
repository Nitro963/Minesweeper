package com.minesweeper.upgrade;

import java.io.Serializable;

public abstract class Shield implements Comparable<Shield> ,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3438232178520987344L;
	protected int protectionValue;
	protected ShieldedSquare sq;
	protected int id;
	public final int getProtectionValue() {
		return protectionValue;
	}

	public final void setProtectionValue(int protectionValue) {
		this.protectionValue = protectionValue;
	}
	
	public Shield(int id, int protectionValue, ShieldedSquare sq) {
		this.protectionValue = protectionValue;
		this.sq = sq;
		this.id = id;
	}
	
	public final ShieldedSquare getSquare() {
		return sq;
	}
	
	public final int getId() {
		return id;
	}
	
	@Override
	public int compareTo(Shield s) {
		return this.getProtectionValue() - s.getProtectionValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shield other = (Shield) obj;
		if (id != other.id)
			return false;
		if (sq == null) {
			if (other.sq != null)
				return false;
		} else if (!sq.equals(other.sq))
			return false;
		return true;
	}
	
	
}
