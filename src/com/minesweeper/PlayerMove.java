package com.minesweeper;

import java.io.Serializable;

public class PlayerMove implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5153183397415572892L;
	protected Player player;
	protected Square sq;
	protected MoveType moveType;
	protected MoveResult res;
	protected ApplicationMechanism mechanism; 
	protected int id;
    public PlayerMove(){
        player = null;
        sq = null;
        moveType = null;
        res = null;
        id = -1;
    }

    public void setSq(Square sq) {
		this.sq = sq;
	}

	public PlayerMove(Player player, Square sq, MoveType type) {
        this.player = player;
        this.sq = sq;
        this.moveType = type;
        res = null;
        id = -1;
        mechanism = ApplicationMechanism.Player;
    }
    
    public PlayerMove(int id ,Player player, Square sq, MoveType moveType, MoveResult res ,ApplicationMechanism mechanism) {
        this.player = player;
        this.sq = sq;
        this.moveType = moveType;
        this.res = res;
        this.mechanism = mechanism;
        this.id = id;
    }
    
    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(player);
		builder.append(" has ");
		builder.append(moveType);
		builder.append("ed ");
		builder.append(sq);
		return builder.toString();
	}

	public final Square getSq() {
        return sq;
    }

    public final MoveResult getRes() {
        return res;
    }

    public final Player getPlayer() {
        return player;
    }

    public final void setPlayer(Player player) {
        this.player = player;
    }

    public final MoveType getType() {
        return moveType;
    }

    public final void setType(MoveType moveType) {
        this.moveType = moveType;
    }

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	public final ApplicationMechanism getMechanism() {
		return mechanism;
	}
    
    
}
