package com.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;

import com.controller.GameController;
import com.jfoenix.controls.JFXButton;
import com.minesweeper.*;
import com.minesweeper.upgrade.ProtectedComPlayer;
import com.minesweeper.upgrade.ProtectedDumpComPlayer;
import com.minesweeper.upgrade.ProtectedGuiHumanPlayer;
import com.minesweeper.upgrade.ProtectedNormalComPlayer;
import com.minesweeper.upgrade.ProtectedSmartComPlayer;

import java.util.ArrayList;

class DuplicateNameException extends GameException {

    private static final long serialVersionUID = -959252864873705040L;

    public DuplicateNameException() {
        super("Duplicate player name is forbidden");
    }

    public DuplicateNameException(String ex) {
        super(ex);
    }

}

public class MultiPlayerView extends StackPane {

    ArrayList<Player> playerList = new ArrayList<>();
    OptionsView options;
    Stage prvStage;
    ArrayList<Integer> scores;
    JFXButton back;
    public MultiPlayerView(OptionsView options ,ScoreboardLayout scoreBoardLayout, Stage prvStage) {
    	this.options = options;
        this.prvStage = prvStage;
        back = new JFXButton("Back to menu");
        back.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
        back.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: yellow;");
        back.setPrefSize(300, 50);
        back.setTextFill(Color.BLUE);

        JFXButton determineButton = new JFXButton("Determine Genres of players");
        determineButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: orange;");
        determineButton.setPrefSize(300, 40);
        determineButton.setTextFill(Color.BLUE);
        determineButton.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));

        PlayerOptionsView playerSelect[] = new PlayerOptionsView[4];
        for (int i = 0; i < 4; i++) {
            playerSelect[i] = new PlayerOptionsView(i + 1);
            playerSelect[i].setSpacing(10);
        }

        GridPane grid = new GridPane();
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                playerSelect[i].getHumanButton().setSelected(true);
                playerSelect[i].getHumanName().setVisible(true);
                continue;
            }
            playerSelect[i].getComputerType().setVisible(true);
            playerSelect[i].getComButton().setSelected(true);
            playerSelect[i].getDumpButton().setSelected(true);
        }

        grid.add(playerSelect[0], 0, 0);
        grid.add(playerSelect[1], 1, 0);
        grid.add(playerSelect[2], 0, 1);
        grid.add(playerSelect[3], 1, 1);
        grid.setVisible(false);

        TextField textplayerno = new TextField();
        textplayerno.setMaxSize(50, 100);

        grid.setVgap(25);
        grid.setHgap(200);

        // ok button
        JFXButton playButton = new JFXButton("Play");
        playButton.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
        playButton.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: yellow;");
        playButton.setPrefSize(200, 50);
        playButton.setTextFill(Color.BLUE);
        playButton.setVisible(false);

        
        
        Label label1 = new Label("Players Configurations");
        label1.setFont(Font.font("Aharoni", FontWeight.NORMAL, 30));
        label1.setTextFill(Color.BLACK);

        Label label2 = new Label("Enter the number of players:");
        label2.setFont(Font.font("Aharoni", FontWeight.NORMAL, 20));

        determineButton.setOnAction(e -> {
            String s = textplayerno.getText();
            final int numofplayers;
            try {
                numofplayers = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                grid.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Not a valid input");
                alert.setContentText("Number of players should be an integer number");
                alert.showAndWait();
                return;
            }
            try {
                if (numofplayers <= 1 || numofplayers > 4) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                grid.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("Not a valid input");
                alert.setContentText("Number of player should be more than 1 and less than 4");
                alert.showAndWait();
                return;

            }
            grid.setVisible(true);
            playButton.setVisible(true);
            for (int i = 0; i < numofplayers; i++) {
                playerSelect[i].setVisible(true);
            }

            for (int i = numofplayers; i < 4; i++) {
                playerSelect[i].setVisible(false);
            }

            playButton.setOnAction(Event -> {
                playerList.clear();
                ArrayList<String> names = new ArrayList<>();
                try {
                    for (int i = 0; i < numofplayers; i++) {
                        if (playerSelect[i].getHumanButton().isSelected()) {
                            String name1 = playerSelect[i].getTextField().getText();
                            if (name1.length() < 1) {
                                throw new Exception();
                            }
                            for (String name : names) {
                                if (name.equals(name1)) {
                                    throw new DuplicateNameException();
                                }
                            }

                            names.add(name1);
                            if(options.getHumanShield().isSelected())
                            	playerList.add(new GuiHumanPlayer(name1, i ,playerSelect[i].getColor()));
                            else
                            	playerList.add(new ProtectedGuiHumanPlayer(name1, i ,playerSelect[i].getColor(),options.getScoresOptions().getInitShields(),options.getScoresOptions().getScores().get(5)));
                            	
                        } else {
                            if (playerSelect[i].getDumpButton().isSelected()) {
                            	if(options.getDumbComShield().isSelected())
                            		playerList.add(new DumpComPlayer(i ,playerSelect[i].getColor()));
                            	else
                            		playerList.add(new ProtectedDumpComPlayer(i ,playerSelect[i].getColor(),options.getScoresOptions().getInitShields(),options.getScoresOptions().getScores().get(5)));
                            		
                            }
                            if (playerSelect[i].getNormalButton().isSelected()) {
                            	if(options.getNormalComShield().isSelected())
                            		playerList.add(new NormalComPlayer(i ,playerSelect[i].getColor()));
                            	else
                            		playerList.add(new ProtectedNormalComPlayer(i ,playerSelect[i].getColor(),options.getScoresOptions().getInitShields(),options.getScoresOptions().getScores().get(5)));
                        		
                            }
                            if (playerSelect[i].getSmartButton().isSelected()) {
                            	if(options.getSmartComShield().isSelected())
                            		playerList.add(new SmartComPlayer(i ,playerSelect[i].getColor()));
                            	else
                            		playerList.add(new ProtectedSmartComPlayer(i ,playerSelect[i].getColor(),0,options.getScoresOptions().getScores().get(5)));
                            }
                        }
                    }
                    int cnt = 0;
                    for(int i = 0 ;i < numofplayers ; i++) {
                    	if (playerList.get(i) instanceof ComPlayer) {
							ComPlayer new_name = (ComPlayer) playerList.get(i);
							new_name.setNum(++cnt);
						}
                    	else
                    		if (playerList.get(i) instanceof ProtectedComPlayer) {
								ProtectedComPlayer new_name = (ProtectedComPlayer) playerList.get(i);
								new_name.setNum(++cnt);
							}
                    }
                    
					prvStage.close();
					
					GameController controler = new GameController(options ,scoreBoardLayout, playerList, prvStage);
					controler.start(new Stage());

				} catch (DuplicateNameException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Not a valid input");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Not a valid input");
                    alert.setContentText("Human player name can't be empty string");
                    alert.showAndWait();
                }
            });
        });

        VBox l = new VBox(10);
        l.getChildren().addAll(label1, label2);
        l.setPadding(new Insets(20, 20, 20, 20));
        HBox baraa = new HBox(20);
        Label space = new Label("                                ");
        VBox k = new VBox(20);
        k.getChildren().addAll(textplayerno, determineButton);
        k.setPadding(new Insets(20, 20, 20, 20));
        baraa.getChildren().addAll(k, space);
        Image multiplayerBackground = new Image("new_background.jpg");
        setBackground(new Background(new BackgroundImage(multiplayerBackground, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        HBox h = new HBox(100);
        h.setPadding(new Insets(20, 20, 20, 20));
        h.getChildren().addAll(back, playButton);
        BorderPane borderpane = new BorderPane();
        borderpane.setTop(l);
        borderpane.setLeft(baraa);
        borderpane.setCenter(grid);
        borderpane.setBottom(h);
        getChildren().add(borderpane);
    }
    
    public Button getBack() {
    	return back;
    }

}
