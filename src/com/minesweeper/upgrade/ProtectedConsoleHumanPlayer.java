package com.minesweeper.upgrade;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.minesweeper.Grid;
import com.minesweeper.IllegalSquareNameException;
import com.minesweeper.MoveType;
import com.minesweeper.PlayerMove;
import com.minesweeper.Square;
import com.util.TraversingHelper;

public class ProtectedConsoleHumanPlayer extends ProtectedHumanPlayer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1018872590413164102L;

	public ProtectedConsoleHumanPlayer(String name, int idx) {
		super(name, idx);
	}

	private class InputFormatException extends IOException {
		private static final long serialVersionUID = -2313796820907873578L;

		public InputFormatException() {
			super("Wrong Input Format");
		}
	}

	private void validate(String s) throws InputFormatException {
		if (s.length() < 2)
			throw new InputFormatException();
		int cnt = 0;
		int idx = -1;
		for (int i = 0; i < s.length(); i++)
			if (Character.isLetter(s.charAt(i))) {
				if (Character.isLowerCase(s.charAt(i)))
					throw new InputFormatException();
				cnt++;
				idx = i;
			} else if (!Character.isDigit(s.charAt(i)))
				new InputFormatException();
		if (cnt != 1)
			throw new InputFormatException();
		if (idx != 0)
			if (idx != s.length() - 1)
				throw new InputFormatException();
	}

	private void validate(int x) throws InputFormatException {
		if (x == 2 || x == 1)
			return;
		throw new InputFormatException();
	}

	@Override
	public PlayerMove getMove(Grid playerGrid) {
		int x, y;
		@SuppressWarnings("resource")
		Scanner cin = new Scanner(System.in);
		String s = null;
		TraversingHelper helper = new TraversingHelper(playerGrid.getN(), playerGrid.getM());
		for (;;) {
			try {
				x = 0;
				y = 0;
				do {
					System.out.println("Enter Square Coordinates Eg.2A\n<<case sensitive>>");
					s = cin.nextLine();
					try {
						validate(s);
						break;
					} catch (InputFormatException e) {
						System.out.println(e.getMessage());
					}
				} while (true);

				if (Character.isAlphabetic(s.charAt(0))) {
					y = s.charAt(0) - 'A';
					x = Integer.parseInt(s.substring(1, s.length())) - 1;
				} else {
					y = s.charAt(s.length() - 1) - 'A';
					x = Integer.parseInt(s.substring(0, s.length() - 1)) - 1;
				}
				if (!helper.isValid(x, y))
					throw new IllegalSquareNameException();
				break;
			} catch (IllegalSquareNameException e) {
				System.out.println(e.getMessage());
			}
		}

		Square sq = new Square(x, y);
		MoveType moveType = null;
		int choice = 0;
		for (;;) {
			try {
				System.out.println("Enter Move Type 1:Reveal ,2:Mark\\Unmark");
				choice = cin.nextInt();
				try {
					validate(choice);
					break;
				} catch (InputFormatException e) {
					System.out.println(e.getMessage());
				}
			} catch (InputMismatchException e) {
				cin.nextLine();
				System.out.println("Please input an integer");
			}
		}

		if (choice == 1)
			moveType = MoveType.Reveal;
		if (choice == 2)
			moveType = MoveType.Mark;
		return new PlayerMove(this, sq, moveType);

	}

}
