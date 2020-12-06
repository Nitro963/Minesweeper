package com.controller;

import java.util.ArrayList;
import java.util.Timer;

import com.minesweeper.ForbiddenServesRequest;
import com.minesweeper.GameState;
import com.minesweeper.Game.GamePlayer;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;
import com.minesweeper.upgrade.ShieldedNormalGame;
import com.minesweeper.upgrade.ShieldedNormalGame.ShieldedGamePlayer;
import com.view.GameView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReplayController extends Application {
	protected class UpdateGridView implements Runnable {
		@Override
		public void run() {
			gameView.updateGridView(gameModel.getPlayerGrid());
		}

	}

	protected class UpdatePlayerState implements Runnable {
		Player player;

		public UpdatePlayerState(Player player) {

			this.player = player;
		}

		@Override
		public void run() {
			gameView.updatePlayerState(player);
			gameView.updateShieldsCount(((ShieldedGamePlayer) gameModel.getGamePlayer()).getCnt());
		}

	}

	protected class BackToMainEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			lock = new Object();
			((ShieldedGamePlayer) gameModel.getGamePlayer()).getTimer().cancel();
			synchronized (gameModel.getGamePlayer()) {
				gameModel.getGamePlayer().notify();
			}
			gameStage.close();
			mainStage.show();
		}

	}

	protected class ExitEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			System.exit(0);
		}

	}

	protected class RestartEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			lock = new Object();
			((ShieldedGamePlayer) gameModel.getGamePlayer()).getTimer().cancel();
			synchronized (gameModel.getGamePlayer()) {
				gameModel.getGamePlayer().notify();
			}
			lock = null;
			new AutoGamePlayer().start();
		}

	}

	ShieldedNormalGame gameModel;
	protected GameView gameView;
	protected Stage mainStage, gameStage;
	protected Object lock;

	public class AutoGamePlayer {
		Timer timer;
		ArrayList<PlayerMove> allMoves;

		public AutoGamePlayer() {
			super();
			timer = new Timer();
			allMoves = new ArrayList<>();
			allMoves.addAll(gameModel.getAllMoves());

		}

		public void start() {
			try {
				gameModel.getGamePlayer().replay();
			} catch (ForbiddenServesRequest e) {
				System.out.println(e.getMessage());
				return;
			}
			for (Player player : gameModel.getPlayers())
				Platform.runLater(new UpdatePlayerState(player));

			new Thread(new Runnable() {
				long time = 0;

				@Override
				public void run() {
					new Thread(() -> {
						while (true) {
							synchronized (GamePlayer.class) {
								try {
									GamePlayer.class.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if (gameModel.getState().equals(GameState.TerminatedGame)
									|| gameModel.getState().equals(GameState.GameOver))
								return;
							if (gameModel.getCurrentPlayers().isEmpty()) {
								gameView.getTimeIndicator().makeIndeterminate();
								return;
							}
							if (((ShieldedGamePlayer) gameModel.getGamePlayer()).getCurrentPlayerTime() % 1000 == 0) {
								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										gameView.getTimeIndicator()
												.setProgress(gameView.getTimeIndicator().getProgress()
														- 100 / gameModel.getTimeLimit());
									}
								});

							}
							if (gameView.getTimeIndicator().getProgress() <= 0)
								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										gameView.getTimeIndicator().setProgress(100);
									}
								});

						}
					}).start();
					while (true) {
						synchronized (gameModel.getGamePlayer()) {
							try {
								gameModel.getGamePlayer().wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if (lock != null)
								return;
							time = ((ShieldedGamePlayer) gameModel.getGamePlayer()).getCurrentTime();
							if (!gameModel.getCurrentPlayers().isEmpty())
								Platform.runLater(new Runnable() {

									@Override
									public void run() {
										gameView.getTimeIndicator().setProgress(100);
									}
								});
							Platform.runLater(new UpdateGridView());
							for (Player player : gameModel.getPlayers())
								Platform.runLater(new UpdatePlayerState(player));
							if (time >= ((ShieldedNormalGame) gameModel).getGameTime()) {
								((ShieldedGamePlayer) gameModel.getGamePlayer()).getTimer().cancel();
								return;
							}
						}
					}
				}
			}).start();

		}

	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(gameView, 1280, 720);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();		
		gameStage = stage;
		new AutoGamePlayer().start();
		stage.setOnCloseRequest(Event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("Are you sure to exit?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				System.exit(0);
			} else {
				Event.consume();
			}
		});
	}

	public ReplayController(ShieldedNormalGame gameModel, Stage mainStage) {
		super();
		this.gameModel = gameModel;
		gameModel.loadGame();
		this.mainStage = mainStage;
		gameView = new GameView(gameModel.getPlayerGrid().getN(), gameModel.getPlayerGrid().getM(),
				gameModel.getPlayers(), gameModel.getAvailableShields());
		gameView.addBackListener(new BackToMainEventHandler());
		gameView.addExitListener(new ExitEventHandler());
		gameView.addRestartListener(new RestartEventHandler());
		gameView.getNewGameButton().setDisable(true);
		gameView.getSaveAsButton().setDisable(true);
		gameView.getSaveButton().setDisable(true);
		gameView.getQuickSaveButton().setDisable(true);
		gameView.getScoreBoardButton().setDisable(true);
		gameView.getQuickload().setDisable(true);
		for (Label score : gameView.getScores())
			score.setVisible(true);
	}

}
