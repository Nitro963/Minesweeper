package com.minesweeper;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleNormalGame extends NormalGame{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4477774516795532784L;

	public void printPlayerGrid() {
		int c = 'A';
		System.out.print("  |");
        for(int i = 0 ; i < playerGrid.getM() ; i++)
        	System.out.print((char)c++ + "|");
        System.out.println();
        
        for(int i = 0 ; i < playerGrid.getN() ; i++ ,System.out.print("\n")){
            System.out.print(String.format("%2d|", i + 1));
            for(int j = 0 ; j < playerGrid.getM(); j++){
                Square sq = playerGrid.getSquare(i, j);
                SquareState state = sq.getState();
                
                if(state .equals(SquareState.Marked)) {
                    System.out.print("P|");
                    continue;
                }
                if(state .equals(SquareState.Locked)) {
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
        for(int i = 0 ; i < gameGrid.getM() ; i++)
        	System.out.print((char)c++ + "|");
        System.out.println();
        for(int i = 0 ; i < gameGrid.getN() ; i++ ,System.out.print("\n")){
        	System.out.print(String.format("%2d|", i + 1));
        	for(int j = 0 ; j < gameGrid.getM(); j++){
                Square sq = gameGrid.getSquare(i, j);
                SquareState state = sq.getState();
                if(sq instanceof MineSquare){
                	if (state .equals(SquareState.Locked))
                        System.out.print("M|");		
                	else {
						if (state .equals(SquareState.Marked))
							System.out.print("T|");
						else
							System.out.print("B|");
                	}
                }
                else{
                    SafeSquare ssq = (SafeSquare) sq;
                    if(ssq.getState() .equals(SquareState.Revealed))
                        System.out.print((ssq.getMineNeighborsCount()) == 0 ? " |" : ssq.getMineNeighborsCount() + "|");
                    else
                    	if(ssq.getState() .equals(SquareState.Marked))
                    		System.out.print("F|");
                    	else
                    		System.out.print("O|");
                }
            }
        }

    }
    
    public void printPlayersStates() {
    	for (Player player : currentPlayers)
    		System.out.println(player + " " + player.getState());

    }
	
    public void printPlayersScore() {
    	for (Player player : currentPlayers) 
    		System.out.println(player + " " + player.getState() + " " + player.getScore());
		
    }
       
    public void start() {
    	initGame();
    	Scanner cin = new Scanner(System.in);
    	while(true) {
			printPlayersStates();
			if (currentPlayer instanceof HumanPlayer) {
				printPlayerGrid();
				try {
					applyPlayerMove(currentPlayer.getMove(playerGrid));
				}catch(GameException e) {
					System.out.println(e.getMessage());
				}				
			} else {
				try {
					ComPlayer p = (ComPlayer)currentPlayer;
					Thread.sleep(200);
					PlayerMove move = p.getMove(playerGrid);
					applyPlayerMove(move);
					System.out.println(p +  " has " + move.getType()  + "ed the square at (" + (move.getSq().getX() + 1) + "," +(char)(move.getSq().getY() + 'A') + ")");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(gameState == GameState.GameOver)
				break;
		}
		printPlayersScore();
		printGameGrid();
		cin.close();
		System.exit(0);
	}
	
    public ConsoleNormalGame(int n ,int m ,int mines ,ArrayList<Player> players) {
    	super(n, m, mines, players);
    }
}
