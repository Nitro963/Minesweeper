package com.minesweeper.upgrade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.minesweeper.ApplicationMechanism;
import com.minesweeper.CustomGame;
import com.minesweeper.EndGameCause;
import com.minesweeper.ForbiddenServesRequest;
import com.minesweeper.GameException;
import com.minesweeper.GameState;
import com.minesweeper.Grid;
import com.minesweeper.IllegalArgumentGameException;
import com.minesweeper.MineSquare;
import com.minesweeper.MoveResult;
import com.minesweeper.MoveType;
import com.minesweeper.Player;
import com.minesweeper.PlayerMove;
import com.minesweeper.PlayerState;
import com.minesweeper.SafeSquare;
import com.minesweeper.Square;
import com.minesweeper.SquareState;
import com.util.Pair;
import com.util.TraversingHelper;

public class ShieldedNormalGame extends CustomGame {

	private static final long serialVersionUID = 6245422149909645928L;

	public class DefaultRulesAndShields extends DefaultRules {

		private static final long serialVersionUID = 6104074267012710582L;
		protected int revealFlyingShield;
		protected int isShielded[][];
		protected int shieldsCount;
		protected int flyingShieldsCount;
		protected int savedShieldsBouns;
		protected int timeLimit;

		public DefaultRulesAndShields(int n, int m, int minesCount, int shieldsCount, ArrayList<Player> players) {
			super(n, m, minesCount, players);
			revealFlyingShield = 1000;
			timeLimit = 10;
			isShielded = new int[n + 5][m + 5];
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					isShielded[i][j] = 0;
			this.flyingShieldsCount = shieldsCount / 2;
			this.shieldsCount = shieldsCount;
			savedShieldsBouns = 50;
			ArrayList<Square> indices = new ArrayList<>();
			for (int i = 0; i < n; i++)
				for (int j = 0; j < m; j++)
					if (!isMine[i][j])
						indices.add(new Square(i, j));
			Collections.shuffle(indices);
			for (int i = 0; i < shieldsCount - flyingShieldsCount; i++)
				isShielded[indices.get(i).getX()][indices.get(i).getY()] = 1;
			for (int i = shieldsCount - flyingShieldsCount; i < shieldsCount; i++)
				isShielded[indices.get(i).getX()][indices.get(i).getY()] = 2;
		}

		public int captureFlyingShield() {
			return revealFlyingShield;
		}

		public Shield putShield(int i, int j, int id) {
			if (isShielded[i][j] == 0)
				return null;
			if (isShielded[i][j] == 1)
				return new FixedShield(id, revealMine() * -1, (ShieldedSquare) gameGrid.getSquare(i, j));
			return new FlyingShield(id, revealMine() * -1, (ShieldedSquare) gameGrid.getSquare(i, j), gameGrid);
		}

		public int getsavedShieldsBouns() {
			return savedShieldsBouns;
		}

		public int getFlyingShieldsCount() {
			return flyingShieldsCount;
		}

		public final int getShieldsCount() {
			return shieldsCount;
		}

		public int getTimeLimit() {
			return timeLimit;
		}

		public final int shieldProtection() {
			return -1 * revealMine();
		}

		public boolean playerLose(PlayerMove playerMove) {
			if (shieldsCount == 0)
				return super.playerLose(playerMove);
			return playerMove.getPlayer().getScore().getScore() < 0;
		}

	}

	protected class ShieldedApplyer extends Applyer {

		private static final long serialVersionUID = 5567505771873930718L;

		protected void revealMine(int x, int y) {
			if (currentPlayer instanceof ProtectedPlayer) {
				ProtectedPlayer player = (ProtectedPlayer) currentPlayer;
				Shield s = player.useShield();
				currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, gameGrid.getSquare(x, y),
						MoveType.Reveal, new MoveResult(rules.revealMine() + (s != null ? s.getProtectionValue() : 0),
								SquareState.Revealed),
						ApplicationMechanism.Player, gameTime, s));
			} else
				currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, gameGrid.getSquare(x, y),
						MoveType.Reveal, new MoveResult(rules.revealMine(), SquareState.Revealed),
						ApplicationMechanism.Player, gameTime, null));
		}

		protected void revealSafe(int x, int y) {
			super.revealSafe(x, y);
			for (int i = 0; i < currentMoves.size(); i++)
				currentMoves.set(i, new TimedPlayerMove(currentMoves.get(i), gameTime, null));
			for (int i = 0; i < currentMoves.size(); i++) {
				TimedPlayerMove playerMove = (TimedPlayerMove) currentMoves.get(i);
				ShieldedSquare sq = (ShieldedSquare) playerMove.getSq();
				if (sq.isHasShield()) {
					Shield shield = shields.get(sq.getShieldId());
					if (currentPlayer instanceof ProtectedPlayer) {
						ProtectedPlayer protectedPlayer = (ProtectedPlayer) currentPlayer;
						if (shield instanceof FlyingShield && playerMove.getMechanism().equals(ApplicationMechanism.Player)) {
							protectedPlayer.addShield(new FixedShield(-1, shield.getProtectionValue(), shield.getSquare()));
							playerMove.setMoveResult(new MoveResult(playerMove.getRes().getScoreChange() + ((DefaultRulesAndShields)rules).captureFlyingShield(), SquareState.Revealed));
						}
						protectedPlayer.addShield(shield);
					}
					if (shield instanceof FlyingShield) {
						FlyingShield flyingShield = (FlyingShield) shield;
						flyingShield.setCaptured(true);
						currentFlyingShields--;
					}else
						currentFixedShields--;
				}
				currentMoves.set(i, playerMove);
			}
		}

		public void mark(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);

			if (sq instanceof MineSquare) {
				currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Mark,
						new MoveResult(rules.markMine(), SquareState.Marked), ApplicationMechanism.Player, gameTime,
						null));
				return;
			}

			currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Mark,
					new MoveResult(rules.markSafe(), SquareState.Marked), ApplicationMechanism.Player, gameTime, null));
		}

		public void unmark(int x, int y) {
			Square sq = gameGrid.getSquare(x, y);

			if (sq instanceof MineSquare) {
				currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Unmark,
						new MoveResult(rules.unmarkMine(), SquareState.Locked), ApplicationMechanism.Player, gameTime,
						null));
				return;
			}

			currentMoves.add(new TimedPlayerMove(allMoves.size(), currentPlayer, sq, MoveType.Unmark,
					new MoveResult(rules.unmarkSafe(), SquareState.Locked), ApplicationMechanism.Player, gameTime,
					null));
		}
	}

	protected class Clock extends TimerTask {

		@Override
		public void run() {
			gameTime += 100;
			currentplayerTime += 100;
			synchronized (ShieldedNormalGame.class) {
				ShieldedNormalGame.class.notify();
			}
			if (((DefaultRulesAndShields) rules).getTimeLimit() != 0)
				if (currentplayerTime == 1000L * getTimeLimit()) {
					determainNextPlayer();
					currentplayerTime = 0L;
					
				}

		}

	}

	public class ShieldedGamePlayer extends GamePlayer {
		protected long currentTime;
		protected long currentPlayerTime1;
		protected int cnt;
		protected Timer timer;
		protected HashMap<Long, PlayerMove> map;
		protected int increase;

		public ShieldedGamePlayer() {
			map = new HashMap<>();
			increase = 100;
		}

		protected class GamePlayerClock extends TimerTask {
			@Override
			public void run() {
				currentTime += increase;
				currentPlayerTime1 += increase;
				synchronized (GamePlayer.class) {
					GamePlayer.class.notify();
				}
				if (((DefaultRulesAndShields) rules).getTimeLimit() != 0)
					if (currentPlayerTime1 == 1000L * ((DefaultRulesAndShields) rules).getTimeLimit()) {
						currentplayerTime = currentPlayerTime1;
						determainNextPlayer();
						currentPlayerTime1 = 0;
						currentplayerTime = currentPlayerTime1;
						synchronized (gamePlayer) {
							gamePlayer.notifyAll();							
						}
						return;
					}
				if (currentTime >= gameTime) {
					try {
						doMove(map.get(gameTime).getId());
					} catch (IllegalArgumentGameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					end();
					return;
				}
				if (map.containsKey(currentTime))
					try {
						doMove(map.get(currentTime).getId());
					} catch (IllegalArgumentGameException e) {
						e.printStackTrace();
					}
			}
		}

		public void replay() throws ForbiddenServesRequest {
			super.replay();
			currentTime = 0;
			map.clear();
			for (PlayerMove playerMove : allMoves)
				if (playerMove.getMechanism().equals(ApplicationMechanism.Player))
					map.put(((TimedPlayerMove) playerMove).getMoveTime(), playerMove);
			timer = new Timer();
			timer.schedule(new GamePlayerClock(), 100, 100);
			cnt = ((DefaultRulesAndShields) rules).getShieldsCount();
		}

		public void doMove(int moveId) throws IllegalArgumentGameException {
			if (vis == null)
				throw new IllegalArgumentGameException("Replay method was not called");
			if (moveId < 0 || moveId > allMoves.size())
				throw new IllegalArgumentGameException("Move id is not recognised");
			currentPlayerTime1 = 0;
			PlayerMove move = allMoves.get(moveId);
			if (move.getMechanism().equals(ApplicationMechanism.Flood))
				throw new IllegalArgumentGameException("All flood Moves will be rejected");
			if (vis[moveId])
				return;
			if (move.getType().equals(MoveType.Reveal)) {
				playerGrid.setSquare(move.getSq());
				vis[moveId++] = true;
				if (move.getSq() instanceof ShieldedSquare) {
					ShieldedSquare safe = (ShieldedSquare) move.getSq();
					if (safe.isHasShield()) {
						cnt--;
						if (move.getPlayer() instanceof ProtectedPlayer) {
							ProtectedPlayer player = (ProtectedPlayer) move.getPlayer();
							Shield shield = shields.get(safe.getShieldId());
							player.addShield(shield);
							if (shield instanceof FlyingShield)
								player.addShield(new FixedShield(-1, shield.getProtectionValue(), shield.getSquare()));

						}
					}
					if (safe.getMineNeighborsCount() == 0) {
						while (moveId < allMoves.size()
								&& allMoves.get(moveId).getMechanism().equals(ApplicationMechanism.Flood)) {
							PlayerMove move1 = allMoves.get(moveId);
							move1.getPlayer().getScore().calcScore(move1);
							playerGrid.setSquare(move1.getSq());

							ShieldedSquare shieldedSquare = (ShieldedSquare) move1.getSq();
							if (shieldedSquare.isHasShield()) {
								cnt--;
								if (move1.getPlayer() instanceof ProtectedPlayer) {
									ProtectedPlayer player = (ProtectedPlayer) move1.getPlayer();
									player.addShield(shields.get(shieldedSquare.getShieldId()));
								}
							}

							vis[moveId++] = true;
							if (rules.playerLose(move1))
								move1.getPlayer().setState(PlayerState.Loser);
						}
					}
				} else if (move.getPlayer() instanceof ProtectedPlayer)
					((ProtectedPlayer) move.getPlayer()).removeShield(((TimedPlayerMove) move).getUsedShield());
			} else {
				Square sq = playerGrid.getSquare(move.getSq().getX(), move.getSq().getY());
				if (sq.getState().equals(SquareState.Marked)) {
					sq.setColor(-1);
					sq.setState(SquareState.Locked);
				} else {
					sq.setState(SquareState.Marked);
					sq.setColor(move.getPlayer().getColor());
				}
				playerGrid.setSquare(sq);
				vis[moveId++] = true;
			}
			move.getPlayer().getScore().calcScore(move);

			if (rules.playerLose(move))
				move.getPlayer().setState(PlayerState.Loser);

			if (rules.endTurn(move))
				determainNextPlayer();

			if (moveId == allMoves.size() && rules.getCause().equals(EndGameCause.Allsafe)) {
				int lockedMineCount = 0;
				for (int i = 0; i < mineSquares.size(); i++)
					if (mineSquares.get(i).getState().equals(SquareState.Locked))
						lockedMineCount++;
				for (Player player : currentPlayers) {
					player.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
					currentPlayer.getScore().setMineBouns(lockedMineCount * rules.lockedMineBouns());
					if (player instanceof ProtectedPlayer) {
						ProtectedPlayer p = (ProtectedPlayer) player;
						((ProtectedScore) p.getScore())
								.setShieldsBouns(((DefaultRulesAndShields)rules).getsavedShieldsBouns() * p.getShields().size());
					}
				}
				rules.determainWinner();
			}
			synchronized (this) {
				notify();
			}
		}

		public final void setIncrease(int increase) {
			this.increase = increase;
		}

		public final long getCurrentTime() {
			return currentTime;
		}

		public final long getCurrentPlayerTime() {
			return currentPlayerTime1;
		}

		public final Timer getTimer() {
			return timer;
		}
		
		public final int getCnt() {
			return cnt;
		}
	}

	protected ArrayList<Shield> shields;
	protected transient Timer shieldsMovementsTimer;
	protected transient Timer clock;
	protected Long gameTime, currentplayerTime;
	protected int currentFlyingShields, currentFixedShields;

	protected ShieldedNormalGame() {
		super();
	}

	public ShieldedNormalGame(int n, int m, int minesCount, int shieldsCount, ArrayList<Player> players) {
		super();
		rules = new DefaultRulesAndShields(n, m, minesCount, shieldsCount, players);
		gameGrid = new Grid(n, m);
		playerGrid = new Grid(n, m);
		firstMove = new boolean[players.size()];
		shields = new ArrayList<>();
		shieldsMovementsTimer = new Timer();
		clock = new Timer();
		gamePlayer = new ShieldedGamePlayer();
	}

	protected void scheduleShieldsMovement() {
		if (currentFlyingShields == 0)
			return;
		shieldsMovementsTimer = new Timer();
		ArrayList<FlyingShield> flyingShields = new ArrayList<>();
		for (Shield s : shields) {
			if (s instanceof FlyingShield) {
				int cnt = 0;
				FlyingShield fs = (FlyingShield) s;
				if (fs.isCaptured())
					continue;
				TraversingHelper helper = new TraversingHelper(gameGrid.getN(), gameGrid.getM());
				ArrayList<Pair> neighbours = helper.getNeighbours(fs.getSquare().getX(), fs.getSquare().getY());
				for (Pair neighbour : neighbours) {
					Square sq = gameGrid.getSquare(neighbour.getX(), neighbour.getY());
					if (sq instanceof ShieldedSquare) {
						ShieldedSquare shielded = (ShieldedSquare) sq;
						if (shielded.getState() != SquareState.Revealed && !shielded.isHasShield())
							cnt++;
					}
				}
				if (cnt != 0)
					flyingShields.add(fs);

			}
		}
		if (flyingShields.isEmpty())
			return;
		shieldsMovementsTimer.schedule(new MoveShieldsRandomly(flyingShields), 1000, 1000);
	}

	public void runClock() {
		clock = new Timer();
		clock.schedule(new Clock(), 100, 100);
	}

	protected void runTimers() {
		if(gameState.equals(GameState.TerminatedGame) || gameState.equals(GameState.GameOver))
			return;
		scheduleShieldsMovement();
		runClock();
	}

	protected void stopTimers() {
		shieldsMovementsTimer.cancel();
		clock.cancel();
	}

	@Override
	public void initGame() {
		if (applyer == null)
			applyer = new ShieldedApplyer();
		super.initGame();
		DefaultRulesAndShields myRules = (DefaultRulesAndShields) rules;
		int cnt = 0;
		shields.clear();
		currentFlyingShields = myRules.getFlyingShieldsCount();
		currentFixedShields = myRules.getShieldsCount() - myRules.getFlyingShieldsCount();
		for (int i = 0; i < rules.putN(); i++)
			for (int j = 0; j < rules.putM(); j++)
				if (!rules.putMine(i, j)) {
					SafeSquare safe = (SafeSquare) gameGrid.getSquare(i, j);
					ShieldedSquare shielded = new ShieldedSquare(safe);
					gameGrid.setSquare(shielded);
					Shield shield = myRules.putShield(i, j, cnt);
					if (shield != null) {
						shields.add(shield);
						shielded.setShieldId(cnt++);
					}
				}
		currentplayerTime = 0L;
		gameTime = 0L;
		runTimers();
	}

	public void loadGame() {
		if(gameState.equals(GameState.GameOver)) {
			gamePlayer = new ShieldedGamePlayer();
			return;
		}
		super.loadGame();
		currentplayerTime = 0L;
		shieldsMovementsTimer = new Timer();
		clock = new Timer();
		runTimers();
	}

	public void terminate(){
		super.terminate();
		stopTimers();
		synchronized (ShieldedNormalGame.class) {
			ShieldedNormalGame.class.notify();
		}
		synchronized (currentPlayer) {
			currentPlayer.notify();
		}
		for (Player player : currentPlayers) {
			synchronized (player) {
				player.notify();
			}
		}
	}

	synchronized public void determainNextPlayer() {
		if (currentplayerTime == 1000L * ((DefaultRulesAndShields) rules).getTimeLimit()
				&& ((DefaultRulesAndShields) rules).getTimeLimit() != 0) {
			currentPlayer.setState(com.minesweeper.upgrade.PlayerState.Timeout);
			currentPlayers.add(currentPlayer);
			synchronized (currentPlayer) {
				currentPlayer.notify();
			}
			currentPlayer = currentPlayers.poll();
			currentPlayer.setState(PlayerState.Playing);
			synchronized (currentPlayer) {
				currentPlayer.notify();
			}
			return;
		}
		super.determainNextPlayer();
		if (currentPlayer != null)
			synchronized (currentPlayer) {
				currentPlayer.notify();
			}
	}

	@Override
	protected void endGame() {
		gameState = GameState.GameOver;
		DefaultRulesAndShields myRules = (DefaultRulesAndShields) rules;
		synchronized (ShieldedNormalGame.class) {
			ShieldedNormalGame.class.notify();
		}
		if (rules.getCause() == EndGameCause.Allsafe)
			for (Player player : currentPlayers) {
				synchronized (player) {
					player.notify();
				}
				if (player instanceof ProtectedPlayer) {
					ProtectedPlayer p = (ProtectedPlayer) player;
					((ProtectedScore) p.getScore())
							.setShieldsBouns(myRules.getsavedShieldsBouns() * p.getShields().size());
				}
			}
		if (currentPlayer instanceof ProtectedPlayer) {
			ProtectedPlayer p = (ProtectedPlayer) currentPlayer;
			((ProtectedScore) p.getScore())
					.setShieldsBouns(myRules.getsavedShieldsBouns() * p.getShields().size());
		}
		stopTimers();
		super.endGame();
	}

	protected void fixGrid(int x, int y) {
		super.fixGrid(x, y);
		gameGrid.setSquare(new ShieldedSquare((SafeSquare) gameGrid.getSquare(x, y)));
	}

	@Override
	synchronized public void applyPlayerMove(PlayerMove move) throws GameException {
		stopTimers();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			super.applyPlayerMove(move);
		} catch (GameException e) {
				runTimers();
			throw e;
		}
		if (rules.endTurn(move))
			currentplayerTime = 0L;
		
		if (gameState != GameState.GameOver)
			runTimers();
	}

	public final ArrayList<Shield> getShields() {
		return shields;
	}

	public int getAvailableShields() {
		return currentFixedShields + currentFlyingShields;
	}

	public final Long getGameTime() {
		return gameTime;
	}

	public final Long getPlayerTime() {
		return currentplayerTime;
	}
	
	public final int getTimeLimit() {
		return ((DefaultRulesAndShields)rules).getTimeLimit();
	}
	
}
