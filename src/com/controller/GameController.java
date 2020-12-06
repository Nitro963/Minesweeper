package com.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EventListener;

import com.minesweeper.GameState;
import com.minesweeper.GuiHumanPlayer;
import com.minesweeper.IllegalArgumentGameException;
import com.minesweeper.IllegalMoveException;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;

import com.minesweeper.upgrade.PlayerState;
import com.minesweeper.upgrade.ProtectedGuiHumanPlayer;
import com.minesweeper.upgrade.ShieldedCustomGame;
import com.minesweeper.upgrade.ShieldedNormalGame;
import com.util.upgrade.FileManger;
import com.util.upgrade.ScoreBoardReg;
import com.view.GameOverView;
import com.view.GameView;
import com.view.OptionsView;
import com.view.ScoreboardLayout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController extends Application implements EventListener {
	protected ShieldedNormalGame gameModel;
	protected GameView gameView;
	protected GameOverView gameOverView;
	protected ScoreboardLayout scoreBoardLayout;
	protected Stage mainStage, gameStage;
	protected OptionsView options;
	protected ArrayList<Player> players;
	protected Scene gameOverScene;

	protected class ButtonClickEventHandler implements EventHandler<MouseEvent> {
		protected int x, y;

		public ButtonClickEventHandler(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void handle(MouseEvent event) {
			if (gameModel.getCurrentPlayer() instanceof GuiHumanPlayer) {
				GuiHumanPlayer guip = (GuiHumanPlayer) gameModel.getCurrentPlayer();
				guip.setR(x);
				guip.setC(y);
				guip.setType(event.getButton() == MouseButton.PRIMARY ? true : false);
				synchronized (guip) {
					guip.notify();
				}
			}
			if (gameModel.getCurrentPlayer() instanceof ProtectedGuiHumanPlayer) {
				ProtectedGuiHumanPlayer guip = (ProtectedGuiHumanPlayer) gameModel.getCurrentPlayer();
				guip.setR(x);
				guip.setC(y);
				guip.setType(event.getButton() == MouseButton.PRIMARY ? true : false);
				synchronized (guip) {
					guip.notify();
				}
			}

		}

	}

	protected class NewGameEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			gameModel.terminate();
			playAgain();
		}

	}

	protected class RestartEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			gameModel.terminate();
			restart();
		}

	}

	protected class ScorebordEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	protected class SaveEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			new Thread(() -> {
				gameView.getIndicator().setVisible(true);
				try {
					FileManger.save(gameModel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gameView.getIndicator().setVisible(false);
			}).start();
		}

	}

	protected class QuickSaveEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			new Thread(() -> {
				gameView.getIndicator().setVisible(true);
				try {
					FileManger.quickSave(gameModel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gameView.getIndicator().setVisible(false);
			}).start();
		}

	}

	protected class QuickLoadEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			new Thread(() -> {
				try {
					gameView.getIndicator().setVisible(true);
					System.out.println("loading");
					ShieldedNormalGame loadedGame = FileManger.quickLoad();
					if (loadedGame == null)
						return;
					gameModel.terminate();
					gameModel = loadedGame;
					players = gameModel.getPlayers();
					Platform.runLater(new UpdateGridView());
					for (Player player : players)
						Platform.runLater(new UpdatePlayerState(player));
					gameModel.loadGame();
					new TimeChangeListener().start();
					runPlayersThreads();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gameView.getIndicator().setVisible(false);
			}).start();
			;
		}

	}

	protected class SaveAsEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			gameView.getIndicator().setVisible(false);
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save As");
			fileChooser.setInitialDirectory(Paths.get(FileManger.savePath).toFile());
			fileChooser.setInitialFileName("game");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save", "*.sav"));
			File s = fileChooser.showSaveDialog(new Stage());
			if (s != null) {
				new Thread(() -> {
					gameView.getIndicator().setVisible(true);
					try {
						FileManger.saveAs(gameModel, s.getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					gameView.getIndicator().setVisible(false);
				}).start();
			}

		}

	}

	protected class BackToMainEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			backToMain();
		}

	}

	protected class ExitEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			System.exit(0);
		}

	}

	protected class GameOver implements Runnable {

		@Override
		public void run() {
			Stage gameOverStage = new Stage();
			try {
				FileManger.addToScoreBoard(gameModel);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scoreBoardLayout.updateScoreBoard();
			for (ScoreBoardReg scoreBoardReg : scoreBoardLayout.getInfo()) {
				scoreBoardReg.getReplayButton().setOnAction(e -> {
					ReplayController controller = new ReplayController(scoreBoardReg.getGame(), mainStage);
					controller.start(new Stage());
					mainStage.close();
				});
			}

			gameOverStage.setScene(gameOverScene);
			gameOverStage.setTitle("Game Over");
			gameOverStage.initModality(Modality.APPLICATION_MODAL);
			gameOverView.fill(gameModel.getPlayers());
			gameOverView.getBack().setOnAction(Event -> {
				gameOverStage.close();
				gameStage.close();
				mainStage.show();
			});
			gameOverView.getPlayAgain().setOnAction(Event -> {
				gameOverStage.close();
				playAgain();
			});
			gameOverView.getRestart().setOnAction(Event -> {
				gameOverStage.close();
				restart();
			});
			gameOverView.getSaveReplay().setOnAction(Event -> {
				new Thread(() -> {
					try {
						FileManger.saveReplay(gameModel);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}).start();

			});
			gameOverStage.setOnCloseRequest(Event -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setHeaderText("Are you sure to exit?");
				alert.showAndWait();
				if (alert.getResult() == ButtonType.OK) {
					System.exit(0);
				} else {
					Event.consume();
				}
			});
			gameOverStage.show();
		}

	}

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
			gameView.updateShieldsCount(gameModel.getAvailableShields());
		}

	}

	protected class TimeChangeListener extends Thread {
		public void run() {
			while (true) {
				synchronized (ShieldedNormalGame.class) {
					if (gameModel.getCurrentPlayers().isEmpty()) {
						gameView.getTimeIndicator().makeIndeterminate();
						return;
					}
					try {
						ShieldedNormalGame.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (gameModel.getState().equals(GameState.TerminatedGame)
						|| gameModel.getState().equals(GameState.GameOver))
					return;

				if (gameModel.getPlayerTime() % 1000 == 0) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							gameView.getTimeIndicator().setProgress(
									gameView.getTimeIndicator().getProgress() - 100 / gameModel.getTimeLimit());
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
		}
	}

	public GameController(OptionsView options, ScoreboardLayout scoreBoardLayout, ArrayList<Player> players,
			Stage prvStage) {
		super();
		this.scoreBoardLayout = scoreBoardLayout;
		if (options.getCustomCheckBox().isSelected())
			if (options.getNormalCheckBox().isSelected())
				gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(), 0,
						players, options.getScoresOptions().getScores(), options.getScoresOptions().getTimeOut());
			else
				gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(),
						options.getShields(), players, options.getScoresOptions().getScores(),
						options.getScoresOptions().getTimeOut());
		else if (options.getShieldedCheckBox().isSelected())
			gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(),
					options.getShields(), players, null, null);
		else
			gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(), 0, players,
					null, null);

		this.mainStage = prvStage;
		this.players = players;
		this.options = options;

		gameModel.initGame();

		this.gameView = new GameView(options.getRows(), options.getColumns(), players, gameModel.getAvailableShields());

		new TimeChangeListener().start();

		this.gameOverView = new GameOverView();
		gameOverScene = new Scene(gameOverView, 900, 400);

		for (int i = 0; i < options.getRows(); i++)
			for (int j = 0; j < options.getColumns(); j++)
				gameView.addGridListener(new ButtonClickEventHandler(i, j), i, j);

		gameView.addNewGameListener(new NewGameEventHandler());
		gameView.addRestartListener(new RestartEventHandler());
		gameView.addScoreBordListener(new ScorebordEventHandler());
		gameView.addSaveListener(new SaveEventHandler());
		gameView.addQuickSaveListener(new QuickSaveEventHandler());
		gameView.addQuickLoadListener(new QuickLoadEventHandler());
		gameView.addSaveAsListener(new SaveAsEventHandler());
		gameView.addBackListener(new BackToMainEventHandler());
		gameView.addExitListener(new ExitEventHandler());

		runPlayersThreads();

	}

	public GameController(ShieldedNormalGame game, OptionsView options, ScoreboardLayout scoreBoardLayout,
			Stage prvStage) {
		this.scoreBoardLayout = scoreBoardLayout;
		gameModel = game;
		this.mainStage = prvStage;
		this.players = gameModel.getPlayers();
		this.options = options;
		this.gameView = new GameView(gameModel.getPlayerGrid().getN(), gameModel.getPlayerGrid().getM(), players,
				gameModel.getAvailableShields());
		this.gameOverView = new GameOverView();
		gameOverScene = new Scene(gameOverView, 900, 400);

		for (int i = 0; i < options.getRows(); i++)
			for (int j = 0; j < options.getColumns(); j++)
				gameView.addGridListener(new ButtonClickEventHandler(i, j), i, j);

		gameView.addNewGameListener(new NewGameEventHandler());
		gameView.addRestartListener(new RestartEventHandler());
		gameView.addScoreBordListener(new ScorebordEventHandler());
		gameView.addSaveListener(new SaveEventHandler());
		gameView.addQuickSaveListener(new QuickSaveEventHandler());
		gameView.addSaveAsListener(new SaveAsEventHandler());
		gameView.addBackListener(new BackToMainEventHandler());
		gameView.addExitListener(new ExitEventHandler());
		Platform.runLater(new UpdateGridView());
		for (Player player : players)
			Platform.runLater(new UpdatePlayerState(player));
		gameModel.loadGame();
		new TimeChangeListener().start();
		runPlayersThreads();

	}

	protected void runPlayersThreads() {
		for (int i = 0; i < players.size(); i++)
			new PlayerThread(players.get(i), gameModel).start();
	}

	protected void restart() {
		gameModel.initGame();
		new TimeChangeListener().start();
		Platform.runLater(new UpdateGridView());
		for (Player player : players)
			Platform.runLater(new UpdatePlayerState(player));
		runPlayersThreads();
	}

	protected void playAgain() {
		if (options.getCustomCheckBox().isSelected())
			if (options.getNormalCheckBox().isSelected())
				gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(), 0,
						players, null, null);
			else
				gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(),
						options.getShields(), players, options.getScoresOptions().getScores(), null);
		else if (options.getShieldedCheckBox().isSelected())
			gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(),
					options.getShields(), players, null, null);
		else
			gameModel = new ShieldedCustomGame(options.getRows(), options.getColumns(), options.getMines(), 0, players,
					null, null);
		gameModel.initGame();
		new TimeChangeListener().start();

		Platform.runLater(new UpdateGridView());
		for (Player player : players)
			Platform.runLater(new UpdatePlayerState(player));
		runPlayersThreads();
	}

	protected void backToMain() {
		gameStage.close();
		gameModel.terminate();
		mainStage.show();
	}

	protected class PlayerThread extends Thread {
		Player player;
		ShieldedNormalGame game;

		public PlayerThread(Player player, ShieldedNormalGame gameModule) {
			super(player.toString());
			this.player = player;
			this.game = gameModule;
		}

		public void run() {
			while (true) {
				synchronized (player) {
					if (!player.getState().equals(PlayerState.Playing)) {
						Platform.runLater(new UpdatePlayerState(player));
						if (game.getState().equals(GameState.GameOver)) {
							Platform.runLater(new GameOver());
							return;
						}
						if (player.getState().equals(PlayerState.Loser))
							return;
						try {
							player.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				if (this.game != gameModel)
					return;
				if (game.getState().equals(GameState.GameOver) || game.getState().equals(GameState.TerminatedGame))
					return;
				Platform.runLater(new UpdatePlayerState(player));
				try {
					PlayerMove playerMove = player.getMove(game.getPlayerGrid());
					game.applyPlayerMove(playerMove);
					if (!game.getCurrentPlayers().isEmpty())
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								gameView.getTimeIndicator().setProgress(100);
							}
						});
					Platform.runLater(new UpdateGridView());
				} catch (IllegalMoveException e) {

				} catch (IllegalArgumentGameException e) {
					if (gameModel != game)
						return;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error");
					System.exit(0);
				}
			}
		}
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(gameView, 1280, 720);
		 //stage.setFullScreen(true);
		stage.setScene(scene);
		stage.show();
		gameStage = stage;
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

}
