package com.minesweeper;

import java.util.ArrayList;
import java.util.Random;

import com.util.Pair;
import com.util.TraversingHelper;

public class SmartComPlayer extends NormalComPlayer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8765829498171967485L;
	int A[][];
	protected TraversingHelper helper;
	protected ArrayList<PlayerMove> canReveal, canMark, canUnmark, ambiguous;

	public SmartComPlayer(int idx) {
		super(idx);
	}

	public SmartComPlayer(int idx, int color) {
		super(idx, color);
	}

	protected void copyGrid(Grid playerGrid) {
		for (int i = 0; i < playerGrid.getN(); i++)
			for (int j = 0; j < playerGrid.getM(); j++) {
				Square sq = playerGrid.getSquare(i, j);
				if (sq.getState().equals(SquareState.Revealed)) {
					if (sq instanceof SafeSquare) {
						SafeSquare safe = (SafeSquare) sq;
						A[i][j] = safe.getMineNeighborsCount();
					} else
						A[i][j] = (int) -1e9;
				} else if (isMine[i][j])
					A[i][j] = (int) -1e9;
				else
					A[i][j] = -1;

			}

	}

	@Override
	public void reset() {
		super.reset();
		canReveal = new ArrayList<>();
		canMark = new ArrayList<>();
		canUnmark = new ArrayList<>();
		ambiguous = new ArrayList<>();
	}

	public void updateGrid() {

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (isMine[i][j]) {
					ArrayList<Pair> neighbours = helper.getNeighbours(i, j);
					for (Pair square : neighbours)
						A[square.getX()][square.getY()]--;
				}
	}

	public void solve(Grid playerGrid) {
		canReveal.clear();
		canMark.clear();
		canUnmark.clear();
		ambiguous.clear();
		for (int i = 0; i < playerGrid.getN(); i++)
			for (int j = 0; j < playerGrid.getM(); j++)
				if (A[i][j] == 0) {
					ArrayList<Pair> neighbours = helper.getNeighbours(i, j);
					for (Pair neighbour : neighbours) {
						if (!helper.isVis(neighbour.getX(), neighbour.getY()))
							if (!playerGrid.getSquare(neighbour.getX(), neighbour.getY())
									.getState().equals(SquareState.Revealed)) {
								if (!isMine[neighbour.getX()][neighbour.getY()])
									if (playerGrid.getSquare(neighbour.getX(), neighbour.getY()).getState()
											.equals(SquareState.Locked))
										canReveal.add(new PlayerMove(this,
												new Square(neighbour.getX(), neighbour.getY()), MoveType.Reveal));
									else
										canUnmark.add(new PlayerMove(this,
												new Square(neighbour.getX(), neighbour.getY()), MoveType.Mark));
								helper.markVis(neighbour.getX(), neighbour.getY());

							}

					}
				}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (isMine[i][j])
					if (playerGrid.getSquare(i, j).getState().equals(SquareState.Locked))
						canMark.add(new PlayerMove(this, new Square(i, j), MoveType.Mark));

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (A[i][j] < 0 && !isMine[i][j])
					if (playerGrid.getSquare(i, j).getState().equals(SquareState.Marked))
						canUnmark.add(new PlayerMove(this, new Square(i, j), MoveType.Mark));

		for (int i = 0; i < n; i++)
			for (int j = 0; j < m; j++)
				if (A[i][j] < 0 && !isMine[i][j])
					if (playerGrid.getSquare(i, j).getState().equals(SquareState.Locked))
						ambiguous.add(new PlayerMove(this, new Square(i, j), MoveType.Reveal));

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
		m = playerGrid.getM();

		if (helper == null)
			helper = new TraversingHelper(n, m);
		helper.reset();

		if (A == null)
			A = new int[n][m];

		if (isMine == null)
			isMine = new boolean[n][m];

		fillList(playerGrid);

		guessMines(playerGrid);

		copyGrid(playerGrid);

		updateGrid();

		solve(playerGrid);

		Random rand = new Random();
		if (canReveal.size() > 0)
			return canReveal.get(rand.nextInt(canReveal.size()));

		if (canMark.size() > 0)
			return canMark.get(rand.nextInt(canMark.size()));

		if (canUnmark.size() > 0)
			return canUnmark.get(rand.nextInt(canUnmark.size()));

		return ambiguous.get(rand.nextInt(ambiguous.size()));
	}
}
