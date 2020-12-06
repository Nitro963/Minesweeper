package com.minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class DumpComPlayer extends ComPlayer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3213664319866955149L;

	public DumpComPlayer(int idx) {
		super(idx);
	}

	public DumpComPlayer(int idx, int color) {
		super(idx, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayerMove getMove(Grid playerGrid) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ArrayList<Square> locked = new ArrayList<>();
	    ArrayList<Square> marked = new ArrayList<>();
	    for(int i = 0 ; i < playerGrid.getN(); i ++)
	    	for(int j = 0 ; j < playerGrid.getM() ;j++)
	    		if(playerGrid.getSquare(i, j).getState() == SquareState.Locked)
	    			locked.add(new Square(playerGrid.getSquare(i, j)));
	    		else
	    			if(playerGrid.getSquare(i, j).getState() == SquareState.Marked)
	    				marked.add(new Square(playerGrid.getSquare(i, j)));
        Random rand = new Random();
        MoveType moves[] = new MoveType[2];
        moves[0] = MoveType.Reveal;
        moves[1] = MoveType.Mark;
        if(locked.isEmpty())
        	return new PlayerMove(this, marked.get(rand.nextInt(marked.size())), MoveType.Mark);
        //System.out.println("ababa");
        return new PlayerMove(this, locked.get(rand.nextInt(locked.size())), moves[rand.nextInt(2)]); 
	}
}
