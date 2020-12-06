package com.minesweeper.upgrade;

import com.minesweeper.ApplicationMechanism;
import com.minesweeper.MoveResult;
import com.minesweeper.MoveType;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;
import com.minesweeper.Square;

public class TimedPlayerMove extends PlayerMove{

	private static final long serialVersionUID = -7586606954166182479L;
	protected final Long moveTime;
	protected Shield usedShield;
	public TimedPlayerMove(PlayerMove playerMove,long moveTime ,Shield usedShield) {
		super(playerMove.getId() ,playerMove.getPlayer(), playerMove.getSq(), playerMove.getType(), playerMove.getRes(), playerMove.getMechanism());
		this.moveTime = moveTime;
		this.usedShield = usedShield;
	}
	
	public TimedPlayerMove(int id ,Player player, Square sq, MoveType moveType, MoveResult res, ApplicationMechanism mechanism ,Long moveTime ,Shield usedShield) {
		super(id ,player, sq, moveType, res, mechanism);
		this.moveTime = moveTime;
		this.usedShield = usedShield;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public final long getMoveTime() {
		return moveTime;
	}

	public final Shield getUsedShield() {
		return usedShield;
	}

	public final void setUsedShield(Shield usedShield) {
		this.usedShield = usedShield;
	}
	
	public final void setMoveResult(MoveResult result) {
		this.res = result;
	}
}
