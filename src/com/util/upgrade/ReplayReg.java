package com.util.upgrade;

import java.io.Serializable;

import com.minesweeper.upgrade.ShieldedNormalGame;

public class ReplayReg implements Serializable{

	private static final long serialVersionUID = 4060358837401328416L;
	private final ShieldedNormalGame game;
	private final int replayId;
	public ReplayReg(ShieldedNormalGame game, int replayId) {
		super();
		this.game = game;
		this.replayId = replayId;
	}
	public final ShieldedNormalGame getGame() {
		return game;
	}
	public final int getReplayId() {
		return replayId;
	}
	
	

}
