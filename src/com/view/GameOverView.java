package com.view;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.minesweeper.Player;
import com.util.upgrade.PlayerReg;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameOverView extends StackPane{
	private final Button back;
	private final Button playAgain;
	private final Button restart;
	private final Button saveReplay;
	private final HBox hbox;
	private final BorderPane borderPane;
    TableView<PlayerReg> table;
    protected ObservableList<PlayerReg> info;
	@SuppressWarnings("unchecked")
	public GameOverView() {
		super();
		back = new JFXButton("Back to menu");
		back.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: purple;");
		back.setPrefSize(300, 50);
		back.setTextFill(Color.GREEN);
		back.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		back.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));

		restart = new JFXButton("Restart");
		restart.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: purple;");
		restart.setPrefSize(200, 50);
		restart.setTextFill(Color.GREEN);
		restart.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		restart.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));

		playAgain = new JFXButton("Play again");
		playAgain.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: purple;");
		playAgain.setPrefSize(200, 50);
		playAgain.setTextFill(Color.GREEN);
		playAgain.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		playAgain.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));

		saveReplay = new JFXButton("Save Replay");
		saveReplay.setStyle("-fx-background-radius: 5em; " + "-fx-background-color: purple;");
		saveReplay.setPrefSize(200, 50);
		saveReplay.setTextFill(Color.GREEN);
		saveReplay.setFont(Font.font("Aharoni", FontWeight.BOLD, 26));
		saveReplay.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		table = new TableView<PlayerReg>();


		table.setPrefSize(200 ,160);
		table.setMinWidth(800);
		table.setMaxWidth(800);
        TableColumn<PlayerReg, String> playerNameCol
                = new TableColumn<PlayerReg, String>("Player Name");
        
        playerNameCol.setMinWidth(400);
        playerNameCol.setMaxWidth(400);
        playerNameCol.setResizable(false);
        TableColumn<PlayerReg, String> score
                = new TableColumn<PlayerReg, String>("Score");
        //score.setPrefWidth(200);
        score.setMinWidth(400);
        score.setMaxWidth(400);
        score.setResizable(false);
        playerNameCol.prefWidthProperty().bind(table.widthProperty().divide(2));
        score.prefWidthProperty().bind(table.widthProperty().divide(2));

        playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        score.setSortType(TableColumn.SortType.DESCENDING);
        playerNameCol.setSortType(TableColumn.SortType.ASCENDING);
        
        table.getColumns().addAll(playerNameCol, score);
        table.setPadding(new Insets(10, 50, 10, 90));
		
		
		hbox = new HBox(3);
		hbox.setPadding(new Insets(10, 20, 30, 20));
		hbox.getChildren().addAll(playAgain, restart, back ,saveReplay);

		borderPane = new BorderPane();
		borderPane.setBottom(hbox);
		borderPane.setTop(table);

		getChildren().add(borderPane);
				
		setStyle(getStyle() + "-fx-background-image: url('gameOverBackground.png');"
				+ "-fx-background-repeat: no-repeat;"
				+ "-fx-background-position: center;"
				+ "-fx-background-size: cover;");
		
        getStylesheets().add(ScoreboardLayout.class.getResource("tableCSS.css").toExternalForm());
	}
	
	public void fill(ArrayList<Player> players) {
		ObservableList<PlayerReg> list = FXCollections.observableArrayList();
		for (Player player : players)
			list.add(new PlayerReg(player.toString() ,"" ,player.getScore().getScore()));
        table.setItems(list);
	}

	public final Button getBack() {
		return back;
	}
	public final Button getPlayAgain() {
		return playAgain;
	}
	public final Button getRestart() {
		return restart;
	}

	public final Button getSaveReplay() {
		return saveReplay;
	}

	
}
