package com.util.upgrade;

import com.jfoenix.controls.JFXButton;
import com.minesweeper.Player;
import com.minesweeper.upgrade.ShieldedNormalGame;

import java.io.Serializable;

import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerReg implements Serializable ,Comparable<PlayerReg>{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4906716995574176684L;
	private String name;
	private Player player;
    private int score;
    private int gameId;
    private int gameDuration;
    private JFXButton replay;
    private String color;

    public PlayerReg(ShieldedNormalGame game ,int gameId) {
    	this.gameId = gameId;
    	player = game.getWinner();
    	score = player.getScore().getScore();
    	name = player.toString();
    	gameDuration = (int)(game.getGameTime()/1000);
    	System.out.println(gameDuration);
    }
    
    
    public PlayerReg(String name, int score, int IdGame, int GameDuration) {
        this.name = name;
        this.gameId = IdGame;
        this.score = score;
        this.gameDuration = GameDuration;
        this.replay = new JFXButton("Replay");
        replay.setFont(Font.font("Aharoni", FontWeight.NORMAL, 15));
        replay.setStyle("-fx-background-radius: 3em; " + "-fx-background-color: orange;");
        replay.setPrefSize(80, 30);
        replay.setAlignment(Pos.CENTER);
    }
    public PlayerReg(String name, String color,int score) {
        this.name = name;
        this.score = score;
        this.color=color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setReplay(JFXButton replay) {
        this.replay = replay;
    }

    public JFXButton getReplay() {
        return replay;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getIdGame() {
        return gameId;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setIdGame(int IdGame) {
        this.gameId = IdGame;
    }

    public void setGameDuration(int GameDuration) {
        this.gameDuration = GameDuration;
    }


	@Override
	public int compareTo(PlayerReg other) {
		return other.score - score;
	}

}
