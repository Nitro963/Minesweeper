package com.view;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuView extends StackPane {
	private final VBox mainButtons;
	private final JFXButton singlePlayerButton;
	private final JFXButton multiPlayerButton;
	private final JFXButton scoreBoardButton;
	private final JFXButton optionButton;
	private final JFXButton gameRuleButton;
	private final JFXButton exitButton;
	private final JFXButton loadButton;
	private final JFXButton replayButton;
	public MainMenuView() {
		mainButtons = new VBox(20);
		loadButton = new JFXButton("Load Game");
		loadButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		loadButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		loadButton.setPrefSize(200, 50);
		loadButton.setTextFill(Color.BLUE);
		loadButton.setFocusTraversable(false);
		
		
		replayButton = new JFXButton("Replay Game");
		replayButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		replayButton.setPrefSize(200, 50);
		replayButton.setTextFill(Color.BLUE);
		replayButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		replayButton.setFocusTraversable(false);

		
		
		
		singlePlayerButton = new JFXButton("Single Player");
		singlePlayerButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		singlePlayerButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		singlePlayerButton.setPrefSize(200, 50);
		singlePlayerButton.setTextFill(Color.BLUE);
		singlePlayerButton.setFocusTraversable(false);
		
		
		multiPlayerButton = new JFXButton("Multi Player");
		multiPlayerButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		multiPlayerButton.setPrefSize(200, 50);
		multiPlayerButton.setTextFill(Color.BLUE);
		multiPlayerButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		multiPlayerButton.setFocusTraversable(false);
		
		scoreBoardButton = new JFXButton("ScoreBoard");
		scoreBoardButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		scoreBoardButton.setPrefSize(200, 50);
		scoreBoardButton.setTextFill(Color.BLUE);
		scoreBoardButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		scoreBoardButton.setFocusTraversable(false);
		
		optionButton = new JFXButton("Options");
		optionButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		optionButton.setPrefSize(200, 50);
		optionButton.setTextFill(Color.BLUE);
		optionButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		optionButton.setFocusTraversable(false);


		gameRuleButton = new JFXButton("Game Rules");
		gameRuleButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		gameRuleButton.setPrefSize(200, 50);
		gameRuleButton.setTextFill(Color.BLUE);
		gameRuleButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		gameRuleButton.setFocusTraversable(false);
		
		// Exit Button
		exitButton = new JFXButton("Exit");
		exitButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
		exitButton.setPrefSize(200, 50);
		exitButton.setTextFill(Color.BLUE);
		exitButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		exitButton.setFocusTraversable(false);
		
		exitButton.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
		exitButton.setOnAction(Event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("are you sure to exit -_- ?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				System.exit(0);
			}
		});

		mainButtons.getChildren().addAll(singlePlayerButton, multiPlayerButton ,loadButton ,replayButton, optionButton, scoreBoardButton, gameRuleButton,
				exitButton);
		mainButtons.setAlignment(Pos.TOP_LEFT);
		//setBackground(new Background(new BackgroundImage(new Image("mainMenuBackground.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		setStyle(getStyle() + "-fx-background-image: url('mainMenuBackground.png');"
				+ "-fx-background-repeat: no-repeat;"
				+ "-fx-background-position: center;"
				+ "-fx-background-size: cover;");
		getChildren().addAll(mainButtons);
		setPadding(new Insets(10, 10, 20, 20));
	}

	public final VBox getMainButtons() {
		return mainButtons;
	}

	public final JFXButton getSinglePlayerButton() {
		return singlePlayerButton;
	}

	public final JFXButton getMultiPlayerButton() {
		return multiPlayerButton;
	}

	public final JFXButton getScoreBoardButton() {
		return scoreBoardButton;
	}

	public final JFXButton getOptionButton() {
		return optionButton;
	}

	public final JFXButton getGameRuleButton() {
		return gameRuleButton;
	}

	public final JFXButton getExitButton() {
		return exitButton;
	}

	public final JFXButton getLoadButton() {
		return loadButton;
	}

	public final JFXButton getReplayButton() {
		return replayButton;
	}
	
}
