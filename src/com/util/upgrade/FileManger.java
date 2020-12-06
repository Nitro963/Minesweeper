package com.util.upgrade;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import com.minesweeper.upgrade.ShieldedNormalGame;

public class FileManger {
	private static ObjectOutputStream quickSaveStream, scoreBoardStream;
	private static File file;
	public static String savePath = Paths
			.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Save")
			.toString();
	public static String resourcesPath = Paths
			.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Resources")
			.toString();
	public static String replayPath = Paths
			.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Replays")
			.toString();
	public static ArrayList<GameReg> scoreBoardList;

	private static boolean map[] = new boolean[100005];
	
	private static boolean replayMap[] = new boolean[100005];
	
	public static void quickSave(ShieldedNormalGame game) throws FileNotFoundException, IOException {
		if (file == null) {
			file = File.createTempFile("prefix-", "-suffix");
			file.deleteOnExit();
		}
		if (quickSaveStream == null)
			quickSaveStream = new ObjectOutputStream(new FileOutputStream(file));
		quickSaveStream.flush();		
		quickSaveStream.writeObject(game);
	}

	public static ShieldedNormalGame quickLoad() throws FileNotFoundException, IOException, ClassNotFoundException {
		if (file == null)
			return null;
		ObjectInputStream quickLoadStream = new ObjectInputStream(new FileInputStream(file));
		try {
			return ((ShieldedNormalGame) quickLoadStream.readObject());
		} finally {
			quickLoadStream.close();
		}
	}

	private static void saveAs(GameReg gameReg, String filePath) throws IOException {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filePath + ".sav"));
			oos.writeObject(gameReg);
		} finally {
			if (oos != null)
				oos.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static void init() throws FileNotFoundException, IOException, ClassNotFoundException {
		Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Replays")
				.toFile().mkdirs();
		Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Save")
				.toFile().mkdir();
		Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath(), "MineSweeper Data", "Resources")
				.toFile().mkdir();
		File[] directoryListing = Paths.get(savePath).toFile().listFiles();
		for (File file : directoryListing) {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
			GameReg reg = (GameReg) stream.readObject();
			map[reg.getGameId()] = true;
			stream.close();
		}
		directoryListing = Paths.get(replayPath).toFile().listFiles();
		for (File file : directoryListing) {
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
			ReplayReg reg = (ReplayReg) stream.readObject();
			replayMap[reg.getReplayId()] = true;
			stream.close();
		}
		File f = Paths.get(resourcesPath, "ScoreBoard.dat").toFile();
		if (!f.exists())
			scoreBoardList = new ArrayList<>();
		else {
			ObjectInputStream scoreBoardStream = new ObjectInputStream(new FileInputStream(f));
			try {
				scoreBoardList = (ArrayList<GameReg>) scoreBoardStream.readObject();
			} catch (EOFException e) {
				//e.printStackTrace();
				scoreBoardList = new ArrayList<>();
			}
			scoreBoardStream.close();
		}
	}

	public static void save(ShieldedNormalGame game) throws IOException {
		for (int i = 0; i <= 100000; i++)
			if (!map[i]) {
				map[i] = true;
				saveAs(new GameReg(i, game), Paths.get(savePath, String.format("%06d", i)).toString());
				return;
			}
	}

	public static void saveAs(ShieldedNormalGame game ,String filePath) throws IOException {
		for (int i = 0; i <= 100000; i++)
			if (!map[i]) {
				map[i] = true;
				saveAs(new GameReg(i, game), filePath.substring(0, filePath.length() - 4));
				return;
			}
	}

	
	public static ShieldedNormalGame loadGame(String filePath) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		ShieldedNormalGame g = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			g = ((GameReg) ois.readObject()).getGame();
			return g;
		} finally {
			if (ois != null)
				ois.close();
		}
	}

	public static void addToScoreBoard(ShieldedNormalGame game) throws IOException {
		if(scoreBoardStream == null)
			scoreBoardStream = new ObjectOutputStream(new FileOutputStream(Paths.get(resourcesPath, "ScoreBoard.dat").toFile()));
		if (scoreBoardList.size() < 100) {
			scoreBoardList.add(new GameReg(scoreBoardList.size() + 1, game));
			scoreBoardStream.flush();
			scoreBoardStream.writeObject(scoreBoardList);

		} else {
			int mn = (int) 1e9;
			int idx = 0;
			for (int i = 0; i < scoreBoardList.size(); i++) {
				GameReg reg = scoreBoardList.get(i);
				if (reg.getGame().getWinner().getScore().getScore() < mn) {
					idx = i;
					mn = reg.getGame().getWinner().getScore().getScore();
				}
			}
			scoreBoardList.set(idx, new GameReg(idx + 1,game));
			scoreBoardStream.flush();
			scoreBoardStream.writeObject(scoreBoardStream);
		}

	}

	public static void saveReplay(ShieldedNormalGame game) throws FileNotFoundException, IOException {
		for (int i = 0; i <= 100000; i++)
			if (!replayMap[i]) {
				replayMap[i] = true;
				ObjectOutputStream replayStream = new ObjectOutputStream(new FileOutputStream(Paths.get(replayPath, String.format("%06d", i)).toString() + ".rep"));
				replayStream.writeObject(new ReplayReg(game, i));
				replayStream.close();
				return;
			}
	}

	public static ShieldedNormalGame loadReplay(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		ReplayReg reg = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filePath));
			reg = ((ReplayReg) ois.readObject());
			return reg.getGame();
		} finally {
			if (ois != null)
				ois.close();
		}
		
	}

}
