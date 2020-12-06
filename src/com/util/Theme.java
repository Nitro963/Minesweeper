package com.util;

import java.io.Serializable;

public class Theme implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8226093791376502817L;
	String numcellDir[] = new String[9];
	String minecellDir;
	String facingDowncellDir;
	String flaggedcellDir;
	public Theme(String[] numcellDir, String minecellDir, String facingDowncellDir, String flaggedcellDir) {
		super();
		this.numcellDir = numcellDir;
		this.minecellDir = minecellDir;
		this.facingDowncellDir = facingDowncellDir;
		this.flaggedcellDir = flaggedcellDir;
	}
	public String[] getNumCellDir() {
		return numcellDir;
	}
	public String getMinecellDir() {
		return minecellDir;
	}
	public String getFacingDowncellDir() {
		return facingDowncellDir;
	}
	public String getFlaggedcellDir() {
		return flaggedcellDir;
	}

}
