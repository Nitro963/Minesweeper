package com.minesweeper;

import java.util.ArrayList;
import java.util.Random;

import com.util.Pair;
import com.util.TraversingHelper;

public class NormalComPlayer extends ComPlayer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3898950399254932142L;
	protected boolean isMine[][];
	protected ArrayList<Square> revealedSquares = new ArrayList<>();
	int n , m;
	public NormalComPlayer(int idx) {
		super(idx);
	}
	
	public NormalComPlayer(int idx, int color) {
		super(idx, color);
		// TODO Auto-generated constructor stub
	}

	public void guessMines(Grid playerGrid){
		TraversingHelper helper = new TraversingHelper(n, m);
		for (Square square : revealedSquares) {
			if(square instanceof SafeSquare) {
				ArrayList<Square> lockedList = new ArrayList<>();
				ArrayList<Square> minesList = new ArrayList<>();
				ArrayList<Pair> neighbours = helper.getNeighbours(square.getX(), square.getY());
				for (Pair neighbour : neighbours) {
					SquareState squareState = playerGrid.getSquare(neighbour.getX(), neighbour.getY()).getState();
					if(squareState.equals(SquareState.Locked) || squareState.equals(SquareState.Marked))
						lockedList.add(playerGrid.getSquare(neighbour.getX(), neighbour.getY()));	
					if(playerGrid.getSquare(neighbour.getX(), neighbour.getY()) instanceof MineSquare) {
						isMine[neighbour.getX()][neighbour.getY()] = true;
						minesList.add(playerGrid.getSquare(neighbour.getX(), neighbour.getY()));
					}

				}
				SafeSquare safe = (SafeSquare) square;
				if(safe.getMineNeighborsCount() - minesList.size() == lockedList.size()) {
					for (Square mine : lockedList)
						isMine[mine.getX()][mine.getY()] = true;
				}
			}
		}
	}
	public void setN(int n) {
		this.n = n;
	}
	public void setM(int m) {
		this.m = m;
	}
	public void reset() {
		super.reset();
		revealedSquares.clear();
		for(int i = 0 ; i < n ; i++)
			for(int j = 0; j < m ; j++)
				isMine[i][j] = false;	
	}
	
	public void fillList(Grid playerGrid) {
		for(int i = 0 ; i < playerGrid.getN() ; i++)
			for(int j = 0; j < playerGrid.getM() ; j++)
				if(playerGrid.getSquare(i, j).getState().equals(SquareState.Revealed)) {
					if(playerGrid.getSquare(i, j) instanceof SafeSquare)
						revealedSquares.add(new SafeSquare((SafeSquare)playerGrid.getSquare(i, j)));
					else
						revealedSquares.add(new MineSquare((MineSquare)playerGrid.getSquare(i, j)));					
				}
	}
	
	@Override
	public PlayerMove getMove(Grid playerGrid) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		n = playerGrid.getN();
		m =	playerGrid.getM();
		if(isMine == null) {
			isMine = new boolean[n][m];
			for(int i = 0 ; i < n ; i++)
				for(int j = 0; j < m ; j++)
					isMine[i][j] = false;	
		}
		fillList(playerGrid);
		
		guessMines(playerGrid);
		
		ArrayList<PlayerMove> moves = new ArrayList<>();
		for(int i = 0 ; i < playerGrid.getN() ; i++)
			for(int j = 0 ; j < playerGrid.getM() ;j++)
				if(!isMine[i][j]) {
					if(playerGrid.getSquare(i, j).getState().equals(SquareState.Marked))
						moves.add(new PlayerMove(this ,new Square(playerGrid.getSquare(i, j)) ,MoveType.Mark));
					else
						if(playerGrid.getSquare(i, j).getState().equals(SquareState.Locked))
							moves.add(new PlayerMove(this ,new Square(playerGrid.getSquare(i, j)) ,MoveType.Reveal));
				}
				else
					if(!playerGrid.getSquare(i, j).getState().equals(SquareState.Marked))
						if(!playerGrid.getSquare(i, j).getState().equals(SquareState.Revealed))
							moves.add(new PlayerMove(this ,new Square(playerGrid.getSquare(i, j)) ,MoveType.Mark));
		
		Random rand = new Random();
		return moves.get(rand.nextInt(moves.size()));
	}

}
