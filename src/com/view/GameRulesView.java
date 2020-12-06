package com.view;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameRulesView extends StackPane{
	private final Label gameRuleLabel;
	private final Group group;
	private final JFXButton backButton;
	
	public GameRulesView(){
		gameRuleLabel = new Label(
                "\n\nFirst:\n\n"
                + " -Understand the principles behind Minesweeper.\n -Each Minesweeper game starts out with a grid of unmarked squares.\n -After clicking one of these squares, some of the squares will disappear, some will remain blank, and some will have numbers on them.\n -It's your job to use the numbers to figure out which of the blank squares have mines and which are safe to click.\n -Minesweeper is similar to a Sudoku puzzle in that your success is largely contingent on being able to eliminate possible answers until only one answer remains.\n"
                + "\nSecond:\n\n"
                + " -Use the mouse's left and right buttons.\n -The mouse is the only tool that you'll need to play Minesweeper. \n -The left mouse button is used to click squares that don't contain mines,\n -while the right mouse button is used to flag squares that contain mines.\n -On higher difficulties, you'll need to mark squares that you suspect contain mines until you can verify that they do contain mines.\n"
                + "\nThird:\n\n"
                + " -Don't worry about your first click. \n -The first square that you click will never have a mine beneath it;\n -clicking a square will clear off some of the board while numbering other squares.\n"
                + "\nFourth:\n\n"
                + " -Know what the numbers mean.\n -A number on a square refers to the number of mines that are currently touching that square.\n -For example, if there are two squares touching each other and one of the squares has \"1\" on it, you know that the square next to it has a mine beneath it. ");
		gameRuleLabel.setFont(Font.font("Aharoni", FontWeight.NORMAL, 14));
        gameRuleLabel.setTextFill(Color.BLACK);
        backButton = new JFXButton("Back");
        backButton.setLayoutX(1);
        backButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
        backButton.setLayoutY(650);
        backButton.setOnAction(Event -> {
        });
        backButton.setFont(Font.font("Aharoni"));
        backButton.setTextFill(Color.BLUE);
        backButton.setAlignment(Pos.TOP_RIGHT);
        group = new Group();
        group.getChildren().addAll(gameRuleLabel, backButton);
        getChildren().addAll(group);
        
        
        setBackground(new Background(new BackgroundImage(new Image("gameRulesBackground.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		
	}

	
	public final Label getGameRuleLabel() {
		return gameRuleLabel;
	}

	public final Group getGroup() {
		return group;
	}

	public final Button getBackButton() {
		return backButton;
	}

	
}
