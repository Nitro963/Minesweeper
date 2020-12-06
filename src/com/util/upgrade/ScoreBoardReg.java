package com.util.upgrade;

import com.jfoenix.controls.JFXButton;
import com.minesweeper.upgrade.ShieldedNormalGame;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreBoardReg extends GameReg{

	private static final long serialVersionUID = -8741558790886487029L;
	private final JFXButton replayButton;
	
	public ScoreBoardReg(int gameId ,ShieldedNormalGame game) {
		super(gameId, game);
		replayButton = new JFXButton("Replay");
		replayButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
		replayButton.setStyle("-fx-background-radius: 3em; " + "-fx-background-color: orange;");
		replayButton.setPrefSize(80, 30);
		replayButton.setAlignment(Pos.CENTER);
	}

	public final JFXButton getReplayButton() {
		return replayButton;
	}
	
	
}
