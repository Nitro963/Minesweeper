package com.minesweeper;

import java.util.ArrayList;

public class CustomGame extends NormalGame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818805559862028561L;

	protected class CustomRules extends DefaultRules {

		/**
		 * 
		 */
		private static final long serialVersionUID = -502476288743215323L;

		public CustomRules(int n ,int m ,int minesCount ,ArrayList<Player> players ,ArrayList<Integer> scores) {
			super(n ,m ,minesCount ,players);
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
	
	protected CustomGame() {
		super();
	}
	
	public CustomGame(int n ,int m ,int minesCount ,ArrayList<Player> players ,ArrayList<Integer> scores) {
		super();
		rules = new CustomRules(n ,m ,minesCount ,players ,scores);
        gameGrid = new Grid(n ,m);
        playerGrid = new Grid(n ,m);
        firstMove = new boolean[players.size()];
	}
}
