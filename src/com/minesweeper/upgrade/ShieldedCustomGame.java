package com.minesweeper.upgrade;

import java.util.ArrayList;
import java.util.Timer;

import com.minesweeper.Grid;
import com.minesweeper.Player;

public class ShieldedCustomGame extends ShieldedNormalGame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4166667089748992471L;

	public class CustomRulesAndShields extends DefaultRulesAndShields{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1717535766729893605L;

		public CustomRulesAndShields(int n, int m, int minesCount,int shieldsCount, ArrayList<Player> players ,ArrayList<Integer> scores ,Integer timeLimit) {
			super(n ,m ,minesCount ,shieldsCount,players);
			if(timeLimit != null)
				super.timeLimit = timeLimit;
			if(scores == null)
				return;
			markmine = scores.get(0);
			unmarkmine = scores.get(1);
			marksafe = scores.get(2);
			unmarksafe = scores.get(3);
			revealempty = scores.get(4);
			revealmine = scores.get(5);
			autoreveal= scores.get(6);
		}
	}
	
	
	public ShieldedCustomGame(int n, int m, int minesCount,int shieldsCount, ArrayList<Player> players ,ArrayList<Integer> scores ,Integer timeLimit) {
		super();
		rules = new CustomRulesAndShields(n ,m ,minesCount,shieldsCount ,players,scores ,timeLimit);
        gameGrid = new Grid(n ,m);
        playerGrid = new Grid(n ,m);
        firstMove = new boolean[players.size()];
		shields = new ArrayList<>();
		shieldsMovementsTimer = new Timer();
		clock = new Timer();
		gamePlayer = new ShieldedGamePlayer();
	}
	
}
