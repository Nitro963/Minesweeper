package com.minesweeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.util.Pair;
import com.util.TraversingHelper;

public abstract class Game implements Serializable {

	private static final long serialVersionUID = 306490703935936639L;

	protected abstract class GameRules implements Serializable {

		private static final long serialVersionUID = -7414149716398814298L;
		EndGameCause endGameCause;

		public abstract int markMine();

		public abstract int markSafe();

		public abstract int unmarkMine();

		public abstract int unmarkSafe();

		public abstract int revealEmpty();

		public abstract int autoReveal();

		public abstract int revealMine();

		public abstract int revealDangerous(Square sq);

		public abstract int lockedMineBouns();

		public abstract int mineCount();

		public abstract boolean putMine(int i, int j);

		public abstract int putN();

		public abstract int putM();

		public abstract boolean endTurn(PlayerMove move);

		public abstract boolean endGame();

		public final EndGameCause getCause() {
			return endGameCause;
		}

		public final void reset() {
			endGameCause = null;
		}

		public abstract boolean playerLose(PlayerMove move);

		public abstract ArrayList<Player> determainPlayers();

		public abstract Player determainWinner();

	}

	protected class Applyer implements Serializable {

		private static final long serialVersionUID = -2280175189988678208L;
		protected TraversingHelper helper;
		protected ArrayList<PlayerMove> currentMoves;

		public Applyer() {
			helper = new TraversingHelper(gameGrid.getN(), gameGrid.getM());
			currentMoves = new ArrayList<>();
		}

		public void reset() {
			helper.reset();
			currentMoves.clear();
		}

		protected void floodFill(int x, int y) {
			SafeSquare sq = null;
			try {
				sq = (SafeSquare) gameGrid.getSquare(x, y);
			} catch (ClassCastException e) {
				System.out.println(gameGrid.getSquare(x, y));
				System.out.println(x + "Error" + y);
				return;
			}

			helper.markVis(x, y);
			currentMoves.add(new PlayerMove(currentMoves.size() + allMoves.size(), currentPlayer, sq, MoveType.Reveal,
					new MoveResult(rules.autoReveal(), SquareState.Revealed), ApplicationMechanism.Flood));

			if (sq.mineNeighborsCount != 0)
				return;

			ArrayList<Pair> neighbours = helper.getNeighbours(x, y);
			for (Pair neighbour : neighbours) {
				if (gameGrid.getSquare(neighbour.getX(), neighbour.getY()).getState().equals(SquareState.Locked)
						&& !helper.isVis(neighbour.getX(), neighbour.getY()))
					floodFill(neighbour.getX(), neighbour.getY());

			}
		}

		protected void revealMine(int x, int y) {
			currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, gameGrid.getSquare(x, y), MoveType.Reveal,
					new MoveResult(rules.revealMine(), SquareState.Revealed), ApplicationMechanism.Player));
		}

		protected void revealSafe(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);
			SafeSquare ssq = (SafeSquare) sq;

			if (ssq.getMineNeighborsCount() == 0) {
				helper.markVis(x, y);
				currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Reveal,
						new MoveResult(rules.revealEmpty(), SquareState.Revealed), ApplicationMechanism.Player));
				ArrayList<Pair> neighbours = helper.getNeighbours(x, y);
				for (Pair neighbour : neighbours) {
					if (gameGrid.getSquare(neighbour.getX(), neighbour.getY()).getState().equals(SquareState.Locked)
							&& !helper.isVis(neighbour.getX(), neighbour.getY()))
						floodFill(neighbour.getX(), neighbour.getY());

				}

			} else
				currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Reveal,
						new MoveResult(rules.revealDangerous(sq), SquareState.Revealed), ApplicationMechanism.Player));

		}

		public void reveal(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);
			if (sq instanceof MineSquare)
				revealMine(x, y);
			if (sq instanceof SafeSquare)
				revealSafe(x, y);
		}

		public void mark(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);

			if (sq instanceof MineSquare) {
				currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Mark,
						new MoveResult(rules.markMine(), SquareState.Marked), ApplicationMechanism.Player));
				return;
			}

			currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Mark,
					new MoveResult(rules.markSafe(), SquareState.Marked), ApplicationMechanism.Player));
		}

		public void unmark(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);

			if (sq instanceof MineSquare) {
				currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Unmark,
						new MoveResult(rules.unmarkMine(), SquareState.Locked), ApplicationMechanism.Player));
				return;
			}

			currentMoves.add(new PlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Unmark,
					new MoveResult(rules.unmarkSafe(), SquareState.Locked), ApplicationMechanism.Player));
		}

		public final ArrayList<PlayerMove> getCurrentMoves() {
			return currentMoves;
		}

	}

	public class GamePlayer {

		protected boolean vis[];

		public void replay() throws ForbiddenServesRequest {
			if (gameState.equals(GameState.RunningGame) || gameState.equals(GameState.NewGame))
				throw new ForbiddenServesRequest("Only ended games can be replayed");
			gameState = GameState.ReplayingGame;
			for (int i = 0; i < rules.putN(); i++)
				for (int j = 0; j < rules.putM(); j++)
					playerGrid.setSquare(new Square(i, j));
			currentPlayers.clear();
			currentPlayers.addAll(rules.determainPlayers());
			for (Player p : currentPlayers)
				p.reset();
			
			currentPlayer = currentPlayers.poll();
			currentPlayer.setState(PlayerState.Playing);
			if (vis == null)
				vis = new boolean[allMoves.size() + 5];
			for (int i = 0; i < allMoves.size(); i++)
				vis[i] = false;
		}

		public void doMove(int moveId) throws IllegalArgumentGameException {
			if(vis == null)
				throw new IllegalArgumentGameException("Replay method was not called");
			if(moveId < 0 || moveId >= allMoves.size())
				throw new IllegalArgumentGameException("Move id is not recognised");
			PlayerMove move = allMoves.get(moveId);
			if (move.getMechanism().equals(ApplicationMechanism.Flood))
				throw new IllegalArgumentGameException("All flood Moves will be rejected");
			if (vis[moveId])
				return;
			if (move.getType().equals(MoveType.Reveal)) {
				playerGrid.setSquare(move.getSq());
				move.getPlayer().getScore().calcScore(move);
				vis[moveId++] = true;
				if (move.getSq() instanceof SafeSquare) {
					SafeSquare safe = (SafeSquare) move.getSq();
					if (safe.getMineNeighborsCount() == 0) {
						while (moveId < allMoves.size()
								&& allMoves.get(moveId).getMechanism().equals(ApplicationMechanism.Flood)) {
							PlayerMove move1 = allMoves.get(moveId);
							move1.getPlayer().getScore().calcScore(move1);
							playerGrid.setSquare(move1.getSq());
							vis[moveId++] = true;
							if (rules.playerLose(move1))
								move1.getPlayer().setState(PlayerState.Loser);
							if(rules.endTurn(move1))
								determainNextPlayer();
								
						}
					}
				}
			} else {
				Square sq = playerGrid.getSquare(move.getSq().getX(), move.getSq().getY());
				if (sq.getState().equals(SquareState.Marked)) {
					sq.setColor(-1);
					sq.setState(SquareState.Locked);
				} else {
					sq.setState(SquareState.Marked);
					sq.setColor(move.getPlayer().getColor());
				}
				move.getPlayer().getScore().calcScore(move);
				playerGrid.setSquare(sq);
				vis[moveId++] = true;
			}
			
			if (rules.playerLose(move))
				move.getPlayer().setState(PlayerState.Loser);
			
			if(rules.endTurn(move))
				determainNextPlayer();
			
			if (moveId == allMoves.size() && rules.getCause().equals(EndGameCause.Allsafe)){ {
				int lockedMineCount = 0;
				for (int i = 0; i < mineSquares.size(); i++)
					if (mineSquares.get(i).getState().equals(SquareState.Locked))
						lockedMineCount++;
				for (Player player : currentPlayers)
					player.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
				currentPlayer.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
			}
				rules.determainWinner();
			}
		}
		
		public void end() {
			gameState = GameState.GameOver;
		}
	}

	protected GameState gameState;
	protected GameRules rules;
	protected Grid gameGrid, playerGrid;
	protected ArrayList<MineSquare> mineSquares;
	protected ArrayList<PlayerMove> allMoves;
	protected Queue<Player> currentPlayers;
	protected Player currentPlayer;
	protected int revealedCounter;
	protected boolean firstMove[];
	protected Applyer applyer;
	protected transient GamePlayer gamePlayer;
	protected Player winner;
	protected abstract boolean acceptMove(PlayerMove move) throws IllegalMoveException;

	public Game() {
		allMoves = new ArrayList<>();
		mineSquares = new ArrayList<>();
		currentPlayers = new LinkedList<Player>();
		gamePlayer = new GamePlayer();
	}

	public void initGame() {
		rules.reset();
		if (applyer == null)
			applyer = new Applyer();
		applyer.reset();
		for (int i = 0; i < rules.putN(); i++)
			for (int j = 0; j < rules.putM(); j++)
				playerGrid.setSquare(new Square(i, j));
		for (int i = 0; i < rules.determainPlayers().size(); i++)
			firstMove[i] = false;
		int mines = rules.mineCount();
		int cnt = 0;
		revealedCounter = 0;
		mineSquares.clear();
		for (int i = 0; i < gameGrid.getN(); i++)
			for (int j = 0; j < gameGrid.getM(); j++)
				if (rules.putMine(i, j)) {
					gameGrid.setSquare(new MineSquare(new Mine(cnt), i, j, SquareState.Locked));
					mineSquares.add((MineSquare) gameGrid.getSquare(i, j));
					cnt++;
				} else
					gameGrid.setSquare(new SafeSquare(i, j, SquareState.Locked));

		TraversingHelper helper = new TraversingHelper(gameGrid.getN(), gameGrid.getM());

		for (int i = 0; i < mines; i++) {
			Square sq = mineSquares.get(i);
			ArrayList<Pair> neighbours = helper.getNeighbours(sq.getX(), sq.getY());
			for (Pair neighbour : neighbours) {
				sq = gameGrid.getSquare(neighbour.getX(), neighbour.getY());
				if (sq instanceof SafeSquare) {
					SafeSquare ssq = (SafeSquare) sq;
					ssq.setMineNeighboursCount(ssq.getMineNeighborsCount() + 1);
					gameGrid.setSquare(ssq);
				}
			}
		}
		allMoves.clear();
		currentPlayers.clear();
		currentPlayers.addAll(rules.determainPlayers());
		cnt = 0;
		for (Player p : currentPlayers)
			p.reset();
		currentPlayer = currentPlayers.poll();
		currentPlayer.setState(PlayerState.Playing);
		gameState = GameState.NewGame;
	}

	public void loadGame() {
		if(gameState.equals(GameState.TerminatedGame))
			gameState = GameState.RunningGame;
		gamePlayer = new GamePlayer();
	}

	public void terminate(){
		gameState = GameState.TerminatedGame;
	}

	protected void fixGrid(int x, int y) {
		TraversingHelper helper = new TraversingHelper(gameGrid.getN(), gameGrid.getM());
		ArrayList<Pair> neighbours = helper.getNeighbours(x, y);
		for (Pair neighbour : neighbours) {
			Square sq = gameGrid.getSquare(neighbour.getX(), neighbour.getY());
			if (sq instanceof SafeSquare) {
				SafeSquare ssq = (SafeSquare) sq;
				ssq.setMineNeighboursCount(ssq.getMineNeighborsCount() - 1);
				gameGrid.setSquare(ssq);
			}

		}
		ArrayList<Square> safe = new ArrayList<>();
		for (int i = 0; i < gameGrid.getN(); i++)
			for (int j = 0; j < gameGrid.getM(); j++)
				if (gameGrid.getSquare(i, j) instanceof SafeSquare && i != x && j != y)
					safe.add(new Square(i, j));

		Random rand = new Random();
		if (safe.size() < 1)
			return;
		int idx = rand.nextInt(safe.size());
		int tox = safe.get(idx).getX(), toy = safe.get(idx).getY();
		neighbours = helper.getNeighbours(tox, toy);
		for (Pair neighbour : neighbours) {
			Square sq = gameGrid.getSquare(neighbour.getX(), neighbour.getY());
			if (sq instanceof SafeSquare) {
				SafeSquare ssq = (SafeSquare) sq;
				ssq.setMineNeighboursCount(ssq.getMineNeighborsCount() + 1);
				gameGrid.setSquare(ssq);
			}
		}

		MineSquare oldMine = (MineSquare) gameGrid.getSquare(x, y);
		MineSquare newMine = new MineSquare(oldMine);
		newMine.setX(tox);
		newMine.setY(toy);
		gameGrid.setSquare(newMine);
		SafeSquare newSafe = new SafeSquare(x, y, SquareState.Locked);
		neighbours = helper.getNeighbours(x, y);
		for (Pair neighbour : neighbours) {
			Square sq = gameGrid.getSquare(neighbour.getX(), neighbour.getY());
			if (sq instanceof MineSquare)
				newSafe.setMineNeighboursCount(newSafe.getMineNeighborsCount() + 1);
		}
		gameGrid.setSquare(newSafe);
		mineSquares.set(oldMine.getM().id, newMine);
	}

	protected void determainNextPlayer() {
		if (gameState.equals(GameState.GameOver))
			return;
		if (!currentPlayer.getState().equals(PlayerState.Loser)) {
			currentPlayer.setState(PlayerState.Waiting);
			currentPlayers.add(currentPlayer);
		}
		currentPlayer = currentPlayers.poll();
		if (currentPlayer != null)
			currentPlayer.setState(PlayerState.Playing);
	}

	protected void endGame() {
		if (rules.getCause().equals(EndGameCause.Allsafe)) {
			int lockedMineCount = 0;
			for (int i = 0; i < mineSquares.size(); i++)
				if (mineSquares.get(i).getState().equals(SquareState.Locked))
					lockedMineCount++;
			for (Player player : currentPlayers)
				player.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
			currentPlayer.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
		}
		gameState = GameState.GameOver;
		winner = rules.determainWinner();
	}

	public void applyPlayerMove(PlayerMove move) throws GameException {
		if(move == null)
			throw new IllegalArgumentGameException("Player move cant be null");
		try {
			if (!acceptMove(move))
				throw new IllegalMoveException();
		} catch (IllegalMoveException e) {
			throw e;
		}
		if (gameState.equals(GameState.GameOver) || gameState.equals(GameState.TerminatedGame))
			throw new IllegalMoveException("Game Over");
		gameState = GameState.RunningGame;
		int x, y;
		x = move.getSq().getX();
		y = move.getSq().getY();
		if (!firstMove[currentPlayer.getIdx()] && move.getType().equals(MoveType.Reveal)) {
			if (gameGrid.getSquare(x, y) instanceof MineSquare)
				fixGrid(x, y);
			firstMove[currentPlayer.getIdx()] = true;
		}
		applyer.reset();
		if (move.getType().equals(MoveType.Reveal))
			applyer.reveal(x, y);

		if (move.getType().equals(MoveType.Mark))
			if (gameGrid.getSquare(x, y).getState().equals(SquareState.Marked))
				applyer.unmark(x, y);
			else
				applyer.mark(x, y);

		ArrayList<PlayerMove> currentMoves = applyer.getCurrentMoves();
		for (PlayerMove mv : currentMoves) {
			Square sq = mv.getSq();
			sq.setState(mv.getRes().getNewSquareStates());
			sq.setColor(mv.getPlayer().getColor());

			if (mv.getType().equals(MoveType.Reveal)) {
				if (gameGrid.getSquare(mv.getSq().getX(), mv.getSq().getY()) instanceof MineSquare)
					playerGrid.setSquare(new MineSquare((MineSquare) sq));
				else {
					playerGrid.setSquare(new SafeSquare((SafeSquare) sq));
					revealedCounter++;
				}
			}

			if (mv.getType().equals(MoveType.Mark))
				playerGrid.setSquare(
						new Square(mv.getSq().getX(), mv.getSq().getY(), SquareState.Marked, currentPlayer.getColor()));
			if (mv.getType().equals(MoveType.Unmark))
				playerGrid.setSquare(new Square(mv.getSq().getX(), mv.getSq().getY(), SquareState.Locked, -1));

			currentPlayer.getScore().calcScore(mv);
		}

		allMoves.addAll(currentMoves);

		for (PlayerMove playerMove : currentMoves)
			if (rules.playerLose(playerMove)) {
				currentPlayer.setState(PlayerState.Loser);
				break;
			}

		if (rules.endTurn(move))
			determainNextPlayer();

		if (rules.endGame())
			endGame();
	}

	public final GameState getState() {
		return gameState;
	}

	public final ArrayList<PlayerMove> getAllMoves() {
		return allMoves;
	}

	public final ArrayList<Player> getPlayers() {
		return rules.determainPlayers();
	}

	public final ArrayList<MineSquare> getMineList() {
		return mineSquares;
	}

	public final Player getCurrentPlayer() {
		return currentPlayer;
	}

	public final Grid getGameGrid() {
		return gameGrid;
	}

	public final Grid getPlayerGrid() {
		return playerGrid;
	}

	public final GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public final Player getWinner() {
		return winner;
	}

	public final Queue<Player> getCurrentPlayers() {
		return currentPlayers;
	}

}
