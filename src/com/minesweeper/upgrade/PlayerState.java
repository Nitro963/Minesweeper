package com.minesweeper.upgrade;

public class PlayerState extends com.minesweeper.PlayerState {
	private static final long serialVersionUID = 7362227460944814759L;
	
	public static PlayerState Timeout = new PlayerState("Timeout");
	
	protected PlayerState(String state) {
		super(state);
	}
}
