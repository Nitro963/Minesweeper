package com.view;

import com.minesweeper.Player;
import com.minesweeper.upgrade.ProtectedPlayer;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayersState extends HBox {
	private final VBox vbox;
	private final HBox hbox;
	private final Label playerState ,number;
	
	public PlayersState(Player p) {
		ImageView image = new ImageView("shield.png");
		image.setFitWidth(50);
		image.setFitHeight(50);
		vbox = new VBox(10);
		playerState = new Label(p.toString() + " " + p.getState());
		number = new Label("0");
		hbox = new HBox(10);
		
		if (p instanceof ProtectedPlayer) {
			ProtectedPlayer protectedPlayer = (ProtectedPlayer) p;
			number.setText(String.valueOf(protectedPlayer.getShields().size()));
		}
		playerState.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
		playerState.setTextFill(Color.web("0x" + Integer.toHexString(p.getColor())));
		number.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
		number.setTextFill(Color.web("0x" + Integer.toHexString(p.getColor())));
		
		
		hbox.getChildren().addAll(image ,number);
		vbox.getChildren().addAll(playerState ,hbox);
		getChildren().add(vbox);
	}

	public void update(Player p) {
		playerState.setText(p.toString() + " " + p.getState());
		if (p instanceof ProtectedPlayer) {
			ProtectedPlayer protectedPlayer = (ProtectedPlayer) p;
			number.setText(String.valueOf(protectedPlayer.getShields().size()));
		}
	}
}
