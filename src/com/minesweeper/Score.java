package com.minesweeper;

import java.io.Serializable;

public class Score implements Serializable{
	private static final long serialVersionUID = -2246454046768596269L;
	protected int score;
	protected int mineBouns;
	public Score() {
		score = 0;
		mineBouns = 0;
	}

	public Score(int score) {
		this.score = score;
	}

	public void reset() {
		score = 0;
	}

	@Override
	public String toString() {
		return "Score = " + score + " MineBouns = " + mineBouns;
	}

	public int getScore() {
		return score + mineBouns;
	}

	public final void setScore(int score) {
		this.score = score;
	}
	
	public final int getMineBouns() {
		return mineBouns;
	}

	public final void setMineBouns(int mineBouns) {
		this.mineBouns = mineBouns;
	}

	public void calcScore(PlayerMove playerMove) {
		score += playerMove.getRes().getScoreChange();
	}

}
