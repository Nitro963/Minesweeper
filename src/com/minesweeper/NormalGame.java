package com.minesweeper;

import java.util.ArrayList;
import java.util.Collections;

public class NormalGame extends Game {

	private static final long serialVersionUID = -2628229686241773020L;

	protected class DefaultRules extends GameRules {

		private static final long serialVersionUID = -4619278471363252030L;
		protected boolean isMine[][];
		protected int n, m;
		protected ArrayList<Player> players;
		protected int minesCount;

		protected int markmine = 5;
		protected int unmarkmine = 1;
		protected int marksafe = 2;
		protected int unmarksafe = 1;
		protected int revealempty = 10;
		protected int revealmine = 250;
		protected int autoreveal = 1;

		public DefaultRules(int n, int m, int minesCount, ArrayList<Player> players) {
			super();
			isMine = new boolean[n + 5][m + 5];
			this.n = n;
			this.m = m;
			this.players = players;
			this.minesCount = minesCount;
			ArrayList<Square> indices = new ArrayList<>();
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					indices.add(new Square(i, j));
			Collections.shuffle(indices);
			for (int i = 0; i < minesCount; i++)
				isMine[indices.get(i).getX()][indices.get(i).getY()] = true;
		}

		@Override
		public int markMine() {
			return markmine;
		}

		@Override
		public int markSafe() {
			return -1 * marksafe;
		}

		@Override
		public int unmarkMine() {
			return -1 * unmarkmine;
		}

		@Override
		public int unmarkSafe() {
			return unmarksafe;
		}

		@Override
		public int revealEmpty() {
			return revealempty;
		}

		@Override
		public int autoReveal() {
			return autoreveal;
		}

		@Override
		public int revealMine() {
			return -1 * revealmine;
		}

		@Override
		public int revealDangerous(Square sq) {
			SafeSquare safe = (SafeSquare) sq;
			return safe.getMineNeighborsCount();
		}

		@Override
		public int lockedMineBouns() {
			return 100;
		}

		@Override
		public int mineCount() {
			return minesCount;
		}

		@Override
		public boolean putMine(int i, int j) {
			return isMine[i][j];
		}

		@Override
		public int putN() {
			return n;
		}

		@Override
		public int putM() {
			return m;
		}

		@Override
		public boolean endTurn(PlayerMove move) {
			return true;
		}

		@Override
		public boolean playerLose(PlayerMove playerMove) {
			if (playerMove.getType().equals(MoveType.Reveal) && playerMove.getSq() instanceof MineSquare)
				return true;
			if (playerMove.getPlayer().getScore().getScore() < 0)
				return true;
			return false;
		}

		@Override
		public boolean endGame() {
			int cnt = 0;
			for (Player player : players) {
				if (player.getState().equals(PlayerState.Loser))
					cnt++;

			}
			if (cnt == players.size()) {
				endGameCause = EndGameCause.Alllost;
				return true;
			}

			cnt = 0;
			for (int i = 0; i < mineCount(); i++) {
				if (mineSquares.get(i).getState().equals(SquareState.Revealed)) {
					cnt = cnt + 1;
				}
			}
			if (cnt == mineCount()) {
				endGameCause = EndGameCause.Allmine;
				return true;
			}

			if (revealedCounter == (putN() * putM()) - mineCount()) {
				endGameCause = EndGameCause.Allsafe;
				return true;
			}

			return false;
		}

		@Override
		public ArrayList<Player> determainPlayers() {
			return players;
		}

		@Override
		public Player determainWinner() {

			Player p = currentPlayer;
			for (Player player : currentPlayers) {
				if (p != null)
					if (p.getScore().getScore() < player.getScore().getScore())
						p = player;
			}
			if (p != null) {
				p.setState(PlayerState.Winner);
				for (Player player : players) {
					if (player.getIdx() != p.getIdx())
						player.setState(PlayerState.Loser);
				}
				return p;
			}
			p = players.get(0);
			for (Player player : players) {
				if (p.getScore().getScore() > player.getScore().getScore())
					p = player;
			}
			return p;
		}

	}

	public NormalGame(int n, int m, int minesCount, ArrayList<Player> players) {
		super();
		rules = new DefaultRules(n, m, minesCount, players);
		gameGrid = new Grid(n, m);
		playerGrid = new Grid(n, m);
		firstMove = new boolean[players.size()];
	}

	protected NormalGame() {
		super();
	}

	@Override
	protected boolean acceptMove(PlayerMove move) throws IllegalMoveException {
		if (gameState.equals(GameState.GameOver) || gameState.equals(GameState.TerminatedGame) || gameState.equals(GameState.ReplayingGame)) {
			throw new IllegalMoveException("The game has been Ended, all further moves will be rejected");
		}
		if (move.getPlayer() != currentPlayer)
			throw new IllegalMoveException();
		int x = move.getSq().getX();
		int y = move.getSq().getY();
		SquareState squareState = gameGrid.getSquare(x, y).getState();
		if (move.getType().equals(MoveType.Mark)) {
			if (squareState.equals(SquareState.Revealed)) {
				throw new IllegalMoveException("You Can't Mark a Revealed Square");
			}
		}
		if (move.getType().equals(MoveType.Reveal)) {
			if (squareState.equals(SquareState.Revealed)) {
				// System.out.println(x + "G" + y);
				throw new IllegalMoveException("You Can't Reveal a Square Twice");
			}
			if (squareState.equals(SquareState.Marked)) {
				throw new IllegalMoveException("You Can't Reveal a Marked Square");
			}
		}
		return true;
	}
}
