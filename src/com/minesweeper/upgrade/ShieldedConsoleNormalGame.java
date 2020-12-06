package com.minesweeper.upgrade;


import java.util.ArrayList;

import com.minesweeper.Player;

public class ShieldedConsoleNormalGame extends ShieldedNormalGame {
	
	private static final long serialVersionUID = 8811735218854866771L;

	public void runClock() {}
		
	public ShieldedConsoleNormalGame(int n, int m, int minesCount, int shieldsCount, ArrayList<Player> players) {
		super(n, m, minesCount, shieldsCount, players);
	}
}
