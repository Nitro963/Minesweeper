package com.controller;

import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.minesweeper.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.minesweeper.GuiHumanPlayer;
import com.minesweeper.upgrade.ProtectedGuiHumanPlayer;
import com.minesweeper.upgrade.ShieldedNormalGame;
import com.util.upgrade.FileManger;
import com.util.upgrade.ScoreBoardReg;
import com.view.GameRulesView;
import com.view.MainMenuView;
import com.view.MultiPlayerView;
import com.view.OptionsView;
import com.view.ScoreboardLayout;

public class MainController extends Application {
	@Override
	public void start(Stage mainStage) {
		MainMenuView menu = new MainMenuView();
		Scene mainScene = new Scene(menu, 1280, 720);

		GameRulesView gameRulesPane = new GameRulesView();
		Scene gameRulesScene = new Scene(gameRulesPane, 1280, 720);
		
		ScoreboardLayout scoreBoard = new ScoreboardLayout();
		Scene scoreBoardScene = new Scene(scoreBoard ,1280 ,720);
		
		ObservableList<ScoreBoardReg> scoreBoardlist = scoreBoard.getInfo();
		for (ScoreBoardReg scoreBoardReg : scoreBoardlist) {
			scoreBoardReg.getReplayButton().setOnAction(e->{
				ReplayController controller = new ReplayController(scoreBoardReg.getGame(), mainStage);
				controller.start(new Stage());
				mainStage.close();
			});
		}
		
		gameRulesPane.getBackButton().setOnAction(Event -> {
			mainStage.setScene(mainScene);
			mainStage.setFullScreen(true);
		});
		menu.getGameRuleButton().setOnAction(Event -> {
			mainStage.setScene(gameRulesScene);
			mainStage.setFullScreen(true);
		});

		OptionsView options = new OptionsView();
		
		
		
		options.getSaveDir().setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("open file");
            File defaultdirectory=new File(FileManger.savePath);
            chooser.setInitialDirectory(defaultdirectory);
            File selectedDirectory= chooser.showDialog(new Stage());
            if(selectedDirectory == null)
            	return;
            FileManger.savePath = selectedDirectory.getPath();
           });

		
		Scene optionsScene = new Scene(options, 1280, 720);

		options.getBackButton().setOnAction(Event -> {
			mainStage.setScene(mainScene);
			mainStage.setFullScreen(true);
		});

		menu.getOptionButton().setOnAction(Event -> {
			if (options.getRows() == 9 && options.getColumns() == 9 && options.getMines() == 10
					&& options.getShields() == 2) {
				options.getEasyButton().setSelected(true);
				options.getCustomBox().setVisible(false);
			} else if (options.getRows() == 16 && options.getColumns() == 16 && options.getMines() == 25
					&& options.getShields() == 5) {
				options.getNormalButton().setSelected(true);
				options.getCustomBox().setVisible(false);
			} else {
				options.getCustomBox().setVisible(true);
				options.getCustomButton().setSelected(true);
			}

			if (options.getScoresOptions().isDefualt()) {
				options.getDefualtCheckBox().setSelected(true);
				options.getCustomCheckBox().setSelected(false);
				options.getScoresOptions().setDisable(true);
				options.getScoresOptions().reset();
			} else {
				options.getDefualtCheckBox().setSelected(false);
				options.getCustomCheckBox().setSelected(true);
				options.getScoresOptions().setDisable(false);
			}
			mainStage.setScene(optionsScene);
			mainStage.setFullScreen(true);
		});

		
		
		menu.getScoreBoardButton().setOnAction(Event -> {
			mainStage.setScene(scoreBoardScene);
			mainStage.setFullScreen(true);
		});
		
		scoreBoard.getBackButton().setOnAction(e->{
			mainStage.setScene(mainScene);
			mainStage.setFullScreen(true);
		});

		MultiPlayerView multiPlayer = new MultiPlayerView(options ,scoreBoard, mainStage);

		multiPlayer.getBack().setOnAction(event -> {
			mainStage.setScene(mainScene);
			mainStage.setFullScreen(true);
		});

		Scene multiPlayerScene = new Scene(multiPlayer, 1280, 720);

		menu.getMultiPlayerButton().setOnAction(Event -> {
			mainStage.setScene(multiPlayerScene);
			mainStage.setFullScreen(true);
		});

		menu.getSinglePlayerButton().setOnAction(e -> {

			ArrayList<Player> playerList = new ArrayList<>();
			if (options.getShieldedCheckBox().isSelected())
				playerList.add(new ProtectedGuiHumanPlayer("You", 0, (int)Long.parseLong(Color.DARKGRAY.toString().substring(2, 10) ,16) ,options.getScoresOptions().getInitShields(),options.getScoresOptions().getScores().get(5)));
			else
				playerList.add(new GuiHumanPlayer("You", 0, (int)Long.parseLong(Color.DARKGRAY.toString().substring(2, 10) ,16)));

			GameController controler = new GameController(options ,scoreBoard, playerList, mainStage);
			controler.start(new Stage());
			
			mainStage.close();
		});

		menu.getLoadButton().setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load Game");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save", "*.sav"));
			fileChooser.setInitialDirectory(Paths.get(FileManger.savePath).toFile());
			File s = fileChooser.showOpenDialog(new Stage());
			if(s != null) {
				try {
					ShieldedNormalGame game = (ShieldedNormalGame) FileManger.loadGame(s.getPath());
					GameController controller = new GameController(game ,options ,scoreBoard ,mainStage);
					controller.start(new Stage());
					mainStage.close();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		menu.getReplayButton().setOnAction(e->{
		    FileChooser fileChooser=new FileChooser();
		    fileChooser.setTitle("Open Replay");
			fileChooser.setInitialDirectory(Paths.get(FileManger.replayPath).toFile());
		    FileChooser.ExtensionFilter rep=new FileChooser.ExtensionFilter ("replay","*.rep");
		    fileChooser.getExtensionFilters().add(rep);
		    File s = fileChooser.showOpenDialog(new Stage());
			if(s != null) {
				ShieldedNormalGame game;
				try {
					game = FileManger.loadReplay(s.getPath());
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
					return;
				}
				ReplayController controller = new ReplayController(game, mainStage);
				controller.start(new Stage());
				mainStage.close();				
			}
		});

		
		
		mainStage.setScene(mainScene);
		mainStage.setTitle("Minesweeper");
		mainStage.setFullScreen(true);
		mainStage.setResizable(true);
		mainStage.setOnCloseRequest(Event -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("Are you sure to exit?");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.OK) {
				System.exit(0);
			} else {
				Event.consume();
			}
		});
		mainStage.show();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileManger.init();
		launch(args);
	}


}
