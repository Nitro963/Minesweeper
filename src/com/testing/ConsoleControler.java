package com.testing;

import java.util.Scanner;

import com.minesweeper.ComPlayer;
import com.minesweeper.ForbiddenServesRequest;
import com.minesweeper.Game;
import com.minesweeper.GameException;
import com.minesweeper.GameState;
import com.minesweeper.Grid;
import com.minesweeper.HumanPlayer;
import com.minesweeper.IllegalMoveException;
import com.minesweeper.MineSquare;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;
import com.minesweeper.SafeSquare;
import com.minesweeper.Square;
import com.minesweeper.SquareState;
import com.minesweeper.upgrade.ProtectedComPlayer;
import com.minesweeper.upgrade.ProtectedHumanPlayer;
import com.util.upgrade.FileManger;

public class ConsoleControler {
	protected Game gameModel;
	
	public ConsoleControler(Game gameModel) {
		super();
		this.gameModel = gameModel;
	}

	public void printPlayerGrid() {
		int c = 'A';
		System.out.print("  |");
		Grid playerGrid = gameModel.getPlayerGrid();
        for(int i = 0 ; i < playerGrid.getM() ; i++)
        	System.out.print((char)c++ + "|");
        System.out.println();
        
        for(int i = 0 ; i < playerGrid.getN() ; i++ ,System.out.print("\n")){
            System.out.print(String.format("%2d|", i + 1));
            for(int j = 0 ; j < playerGrid.getM(); j++){
                Square sq = playerGrid.getSquare(i, j);
                SquareState squareState = sq.getState();
                
                if(squareState.equals(SquareState.Marked)) {
                    System.out.print("P|");
                    continue;
                }
                if(squareState.equals(SquareState.Locked)) {
                    System.out.print("O|");
                    continue;
                }
                
                if(sq instanceof MineSquare)
                     System.out.print("B|");
                else{
                	 SafeSquare ssq = (SafeSquare) sq;
                	 System.out.print((ssq.getMineNeighborsCount()) == 0 ? " |" : ssq.getMineNeighborsCount() + "|");						
                }
            }
        }
    }
    
    public void printGameGrid() {
		int c = 'A';
        System.out.print("  |");
        Grid gameGrid = gameModel.getGameGrid();
        for(int i = 0 ; i < gameGrid.getM() ; i++)
        	System.out.print((char)c++ + "|");
        System.out.println();
        for(int i = 0 ; i < gameGrid.getN() ; i++ ,System.out.print("\n")){
        	System.out.print(String.format("%2d|", i + 1));
        	for(int j = 0 ; j < gameGrid.getM(); j++){
                Square sq = gameGrid.getSquare(i, j);
                SquareState squareState = sq.getState();
                if(sq instanceof MineSquare){
                	if (squareState.equals(SquareState.Locked))
                        System.out.print("M|");		
                	else {
						if (squareState.equals(SquareState.Marked))
							System.out.print("T|");
						else
							System.out.print("B|");
                	}
                }
                else{
                    SafeSquare ssq = (SafeSquare) sq;
                    if(squareState.equals(SquareState.Revealed))
                        System.out.print((ssq.getMineNeighborsCount()) == 0 ? " |" : ssq.getMineNeighborsCount() + "|");
                    else
                    	if(ssq.getState() == SquareState.Marked)
                    		System.out.print("F|");
                    	else
                    		System.out.print("O|");
                }
            }
        }

    }
    
    public void printPlayersStates() {
    	for (Player player : gameModel.getPlayers())
    		System.out.println(player + " " + player.getState() + " " + player.getScore());

    }
	
    public void printPlayersScore() {
    	for (Player player : gameModel.getPlayers())
    		System.out.println(player.getScore());
		
    }
    public ConsoleControler load() {
    	gameModel.loadGame();
    	return this;
    }
    public ConsoleControler init() {
    	gameModel.initGame();
    	return this;
    }
    public void start() {
		while(true) {
			printPlayersStates();
			Player currentPlayer = null;
			try {
				currentPlayer = gameModel.getCurrentPlayer();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			if (currentPlayer instanceof ProtectedHumanPlayer) {
				printPlayerGrid();
				try {
					gameModel.applyPlayerMove(currentPlayer.getMove(gameModel.getPlayerGrid()));
				}catch(GameException e) {
					System.out.println(e.getMessage());
				}				
			} else {
				try {
					ProtectedComPlayer p = (ProtectedComPlayer)currentPlayer;
					Thread.sleep(200);
					PlayerMove move = p.getMove(gameModel.getPlayerGrid());
					gameModel.applyPlayerMove(move);
					System.out.println(p +  " has " + move.getType()  + "ed the square at (" + (move.getSq().getX() + 1) + "," +(char)(move.getSq().getY() + 'A') + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(gameModel.getState() == GameState.GameOver)
				break;
//			@SuppressWarnings("resource")
//			Scanner cin = new Scanner(System.in);
//			System.out.println("Save? enter 1");
//			if(cin.nextInt() == 1)

		}
		printGameGrid();
		printPlayersScore();
		FileManger.saveGame(gameModel, "game.bin");
		System.exit(0);
	}

}
