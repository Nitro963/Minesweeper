package com.view;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.minesweeper.SafeSquare;
import com.minesweeper.Square;
import com.minesweeper.SquareState;
import com.minesweeper.upgrade.ProtectedPlayer;
import com.util.Theme;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import com.minesweeper.Grid;
import com.minesweeper.Player;
import javafx.scene.control.ProgressIndicator;

public class GameView extends StackPane {

    protected final Button speed;
    protected final Button slow;
	protected final Button buttons[][];
	protected final GridPane gridPane;
	protected final Theme theme;
	protected final BorderPane borderPane;
	protected final RingProgressIndicator timeIndicator;
	protected final ArrayList<Label> playersState;
	protected final ArrayList<Label> playersShieldsCount;
	protected final Label shieldCountlabel;
	protected final Label shieldCountlabel1;
	protected final HBox playerStateHbox;
	protected final HBox hboxindicator;
	protected final VBox vbox;
	
	protected final ArrayList<Label> scores;
	protected final VBox helpvbox;
	protected final MenuBar menuBar;
	protected final Menu gameMenu;
	protected final Menu helpMenu;
	protected final MenuItem newGameButton;
	protected final MenuItem restartButton;
	protected final MenuItem scoreBoardButton;
	protected final MenuItem saveButton;
	protected final MenuItem quickSaveButton;
	protected final MenuItem saveAsButton;
	protected final MenuItem backButton;
	protected final MenuItem exitButton;
	protected final MenuItem aboutGameButton;
	protected final MenuItem quickload;
	protected final ProgressIndicator indicator;

	public GameView(int n, int m, ArrayList<Player> players, int shieldsCount) {
		buttons = new JFXButton[n][m];
		gridPane = new GridPane();	
		borderPane = new BorderPane();
		String nums[] = new String[9];
		for (int i = 0; i < 9; i++)
			nums[i] = String.valueOf("0" + i + ".png");
		theme = new Theme(nums, "Bomb.png", "facingDown.png", "flagged.png");

		menuBar = new MenuBar();
		gameMenu = new Menu("Game");
		helpMenu = new Menu("Help");

		ImageView inew_game = new ImageView("new.png");
		ImageView isave = new ImageView("save.png");
		ImageView isave_as = new ImageView("save as.png");
		ImageView iquick_save = new ImageView("quick save.png");
		ImageView iback = new ImageView("back.png");
		ImageView iexit = new ImageView("exit.png");
		ImageView irestart = new ImageView("restart.png");
		ImageView iscore = new ImageView("score.png");
		ImageView iload = new ImageView("quick load.png");

		newGameButton = new MenuItem("New Game", inew_game);
		restartButton = new MenuItem("Restart", irestart);
		scoreBoardButton = new MenuItem("Score Bord", iscore);
		saveButton = new MenuItem("Save", isave);
		quickload = new MenuItem("Quick Load ", iload);
		quickSaveButton = new MenuItem("Quick Save", iquick_save);
		saveAsButton = new MenuItem("Save as", isave_as);
		backButton = new MenuItem("Back", iback);
		exitButton = new MenuItem("Exit", iexit);
		aboutGameButton = new MenuItem("About");

		timeIndicator = new RingProgressIndicator();
		indicator=new ProgressIndicator();
		indicator.setVisible(false);
		shieldCountlabel = new Label("shields in grid" );
		shieldCountlabel.setTextFill(Color.BLUE);
		
		shieldCountlabel1=new Label(String.valueOf(shieldsCount));
		HBox hboxcountshield=new HBox(5);
		ImageView iShieldGrid = new ImageView("grid shields.png");
		hboxcountshield.getChildren().addAll(iShieldGrid,shieldCountlabel1);
		
		
		playersState = new ArrayList<>();
		playersShieldsCount = new ArrayList<>();
		playerStateHbox = new HBox(100);
		vbox = new VBox(20);
		hboxindicator=new HBox();
		scores = new ArrayList<>();
		players.stream().forEach((player) -> {
			VBox vbox = new VBox();
			Label score=new Label("Score: " + String.valueOf(player.getScore().getScore()));
			
			score.setVisible(false);
			ImageView iShield = new ImageView("player shield.png");
			StringBuilder stb = new StringBuilder();
			Label playerLabel = new Label(player.toString() + " is " + player.getState());
			HBox hbox = new HBox(5);
			Label count = new Label();
			playersShieldsCount.add(count);
			String s = Integer.toHexString(player.getColor());
			while (s.length() + stb.length() < 8)
				stb.append("0");

			stb.append(s);
			s = "#" + stb.toString();
			playerLabel.setTextFill(Color.web(s));
			score.setTextFill(Color.web(s));
			playersState.add(playerLabel);
			if (player instanceof ProtectedPlayer)
				count.setText(String.valueOf(((ProtectedPlayer) player).getShields().size()));
			else
				count.setText(String.valueOf(0));

			count.setTextFill(Color.web(s));
			hbox.getChildren().addAll(iShield, count);
			vbox.getChildren().addAll(playerLabel, hbox ,score);
			scores.add(score);
			this.playerStateHbox.getChildren().add(vbox);
		});
		helpvbox=new VBox();
		helpvbox.getChildren().addAll(shieldCountlabel,hboxcountshield);
		this.playerStateHbox.getChildren().addAll( helpvbox);
		this.playerStateHbox.setPadding(new Insets(10));
		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++) {
				buttons[i][j] = new JFXButton();
				buttons[i][j].setPrefSize(40, 35);
				buttons[i][j].setFocusTraversable(false);

				gridPane.add(buttons[i][j], j, i);
				buttons[i][j].setStyle("-fx-background-color: white;" + "-fx-background-size: 40px 35px;"
						+ "-fx-background-image: url('" + theme.getFacingDowncellDir() + "');");
			}

		newGameButton.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
		restartButton.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN));
		saveButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
		quickSaveButton.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.CONTROL_DOWN));
		saveAsButton.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN));
		backButton.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
		exitButton.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCodeCombination.ALT_DOWN));

		gameMenu.getItems().addAll(newGameButton, restartButton,
				new SeparatorMenuItem(), scoreBoardButton,
				new SeparatorMenuItem(), saveButton, quickSaveButton, saveAsButton,quickload, 
				new SeparatorMenuItem(), backButton,exitButton);
		helpMenu.getItems().addAll(aboutGameButton);
		menuBar.getMenus().addAll(gameMenu, helpMenu);
		vbox.getChildren().addAll(menuBar, playerStateHbox);
		hboxindicator.getChildren().addAll(timeIndicator,indicator);
		
		ImageView ispeed=new ImageView("Forward.png");
		speed=new JFXButton();
		speed.setGraphic(ispeed);
		speed.setStyle("-fx-background-radius: 5em;"+"-fx-background-color: purple; ");
		speed.setPrefSize(100, 50);
		speed.setVisible(false);
		 
		ImageView islow=new ImageView("Backward.png");
		slow=new JFXButton();
		slow.setGraphic(islow);
		slow.setStyle("-fx-background-radius: 5em;"+"-fx-background-color: purple; ");
		slow.setPrefSize(100, 50);
		slow.setVisible(false);
		VBox replay=new VBox(10);
		replay.getChildren().addAll(speed,slow);
		
		borderPane.setTop(vbox);
		borderPane.setRight(replay);
		gridPane.setAlignment(Pos.CENTER);
		borderPane.setCenter(gridPane);
		borderPane.setBottom(hboxindicator);
		getChildren().add(borderPane);
		setStyle(getStyle() + "-fx-background-image: url('new_background.jpg');" + "-fx-background-repeat: no-repeat;"
				+ "-fx-background-position: center;" + "-fx-background-size: cover;");
	}

	public final Button getGridButton(int i, int j) {
		return buttons[i][j];
	}

	public void addGridListener(EventHandler<MouseEvent> buttonClickEventHandler, int i, int j) {
		buttons[i][j].setOnMouseClicked(buttonClickEventHandler);
	}

	public void addNewGameListener(EventHandler<ActionEvent> eventHandler) {
		newGameButton.setOnAction(eventHandler);
	}

	public void addRestartListener(EventHandler<ActionEvent> eventHandler) {
		restartButton.setOnAction(eventHandler);
	}

	public void addScoreBordListener(EventHandler<ActionEvent> eventHandler) {
		scoreBoardButton.setOnAction(eventHandler);
	}

	public void addSaveListener(EventHandler<ActionEvent> eventHandler) {
		saveButton.setOnAction(eventHandler);
	}

	public void addQuickSaveListener(EventHandler<ActionEvent> eventHandler) {
		quickSaveButton.setOnAction(eventHandler);
	}

	public void addSaveAsListener(EventHandler<ActionEvent> eventHandler) {
		saveAsButton.setOnAction(eventHandler);
	}

	public void addBackListener(EventHandler<ActionEvent> eventHandler) {
		backButton.setOnAction(eventHandler);
	}

	public void addExitListener(EventHandler<ActionEvent> eventHandler) {
		exitButton.setOnAction(eventHandler);
	}

	public void addAboutGameListener(EventHandler<ActionEvent> eventHandler) {
		aboutGameButton.setOnAction(eventHandler);
	}
	
	public void addQuickLoadListener(EventHandler<ActionEvent> eventHandler) {
		quickload.setOnAction(eventHandler);
	}

	public synchronized void updateGridView(Grid playerGrid) {
		for (int i = 0; i < playerGrid.getN(); i++)
			for (int j = 0; j < playerGrid.getM(); j++) {
				Square sq = playerGrid.getSquare(i, j);
				StringBuilder stb = new StringBuilder();
				String s = Integer.toHexString(sq.getColor());
				while (s.length() + stb.length() < 8)
					stb.append("0");
				stb.append(s);
				s = stb.toString();
				if (sq.getState().equals(SquareState.Revealed)) {
					if (sq instanceof SafeSquare) {
						SafeSquare safe = (SafeSquare) sq;
						buttons[i][j].setStyle("-fx-background-color: #" + s + ";" + "-fx-background-size: 40px 35px;"
								+ "-fx-background-image: url('" + theme.getNumCellDir()[safe.getMineNeighborsCount()]
								+ "');");
					} else {
						buttons[i][j].setStyle("-fx-background-color: #" + s + ";" + "-fx-background-size: 40px 35px;"
								+ "-fx-background-image: url('" + theme.getMinecellDir() + "');");
					}
				} else if (sq.getState().equals(SquareState.Marked)) {
					buttons[i][j].setStyle("-fx-background-color: #" + s + ";" + "-fx-background-size: 40px 35px;"
							+ "-fx-background-image: url('" + theme.getFlaggedcellDir() + "');");
				} else {
					buttons[i][j].setStyle("-fx-background-color: #" + s + ";" + "-fx-background-size: 40px 35px;"
							+ "-fx-background-image: url('" + theme.getFacingDowncellDir() + "');");
				}
			}
	}

	public synchronized void updatePlayerState(Player player) {
		playersState.get(player.getIdx()).setText(player.toString() + " is " + player.getState());
		if (player instanceof ProtectedPlayer)
			playersShieldsCount.get(player.getIdx())
					.setText(String.valueOf(((ProtectedPlayer) player).getShields().size()));
		scores.get(player.getIdx()).setText("Score: " + String.valueOf(player.getScore().getScore()));

	}

	public synchronized void updateShieldsCount(int count) {
		shieldCountlabel1.setText(String.valueOf(count));
	}


	public final Button getSpeed() {
		return speed;
	}

	public final Button getSlow() {
		return slow;
	}

	public final RingProgressIndicator getTimeIndicator() {
		return timeIndicator;
	}

	public final ArrayList<Label> getScores() {
		return scores;
	}

	public final MenuBar getMenuBar() {
		return menuBar;
	}

	public final MenuItem getNewGameButton() {
		return newGameButton;
	}

	public final MenuItem getRestartButton() {
		return restartButton;
	}

	public final MenuItem getScoreBoardButton() {
		return scoreBoardButton;
	}

	public final MenuItem getSaveButton() {
		return saveButton;
	}

	public final MenuItem getQuickSaveButton() {
		return quickSaveButton;
	}

	public final MenuItem getSaveAsButton() {
		return saveAsButton;
	}

	public final MenuItem getBackButton() {
		return backButton;
	}

	public final MenuItem getExitButton() {
		return exitButton;
	}

	public final MenuItem getAboutGameButton() {
		return aboutGameButton;
	}

	public final MenuItem getQuickload() {
		return quickload;
	}

	public final ProgressIndicator getIndicator() {
		return indicator;
	}
	
}
