package com.util.upgrade;

import java.io.Serializable;

import com.minesweeper.upgrade.ShieldedNormalGame;

public class GameReg implements Serializable{
	
	private static final long serialVersionUID = -1321205986289220573L;
	protected int gameId;
	protected ShieldedNormalGame game;
	
	public GameReg(int gameId, ShieldedNormalGame game) {
		super();
		this.gameId = gameId;
		this.game = game;
	}

	public final int getGameId() {
		return gameId;
	}

	public final ShieldedNormalGame getGame() {
		return game;
	}
	
}
