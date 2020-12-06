package com.minesweeper.upgrade;

import java.util.ArrayList;
import java.util.TimerTask;

public class MoveShieldsRandomly extends TimerTask {
	ArrayList<FlyingShield> flyingShields;
	
	public MoveShieldsRandomly(ArrayList<FlyingShield> flyingShields) {
		super();
		this.flyingShields = flyingShields;
	}

	@Override
	public void run() {
		for (FlyingShield flyingShield : flyingShields)
			flyingShield.move();
	}
}
