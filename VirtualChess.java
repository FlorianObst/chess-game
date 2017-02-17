package VirtualChess;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.Handler;

/**
 * <dl>
 * 	<dt> Purpose:
 * 	<dd> Simple chess game
 * 
 * 	<dt> Description:
 * 	<dd> This program lets two users play simple chess game
 * </dl>.
 *
 * @author Florian Obst
 * @version: $Date: 2016/10/03 20:43:19 $
 */
public class VirtualChess{
	
	/** The logger. */
	private final static Logger theLogger = Logger.getLogger(VirtualChess.class.getName());
	
	// Define all chessmen
	public enum Chessmen {
		WHITE_KING,
		WHITE_QUEEN,
		WHITE_ROOK,
		WHITE_BISHOP,
		WHITE_KNIGHT,
		WHITE_PAWN,
		BLACK_KING,
		BLACK_QUEEN,
		BLACK_ROOK,
		BLACK_BISHOP,
		BLACK_KNIGHT,
		BLACK_PAWN,
		EMPTY;	
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main (String[] args) {
		
		Handler[] handlers = Logger.getLogger("").getHandlers();

		for (int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(Level.INFO);
		}
		
		// Set logger level
		theLogger.setLevel(Level.INFO);
		
		// Welcome
		System.out.println("Welcome to our chess game!\nPlease find a partner to play and enjoy the game\n");

		// First Logger message
		// LOGGER.info("Logger message 1");
		
		// Create board
		Chessmen [][] chessboard = new Chessmen [8][8]; 

		// Populate: EMPTY
		for (int row = 2; row < chessboard.length - 2 ; row++) {
			for (int col = 0; col < chessboard[row].length; col++){ 
				chessboard [row][col] = Chessmen.EMPTY;
			}		
		}

		// Populate: WHITE ARMEE
		chessboard [7][0] = Chessmen.WHITE_ROOK;
		chessboard [7][1] = Chessmen.WHITE_KNIGHT;
		chessboard [7][2] = Chessmen.WHITE_BISHOP;
		chessboard [7][3] = Chessmen.WHITE_QUEEN;
		chessboard [7][4] = Chessmen.WHITE_KING;
		chessboard [7][5] = Chessmen.WHITE_BISHOP;
		chessboard [7][6] = Chessmen.WHITE_KNIGHT;
		chessboard [7][7] = Chessmen.WHITE_ROOK;

		// Populate: BLACK ARMEE
		chessboard [0][0] = Chessmen.BLACK_ROOK;
		chessboard [0][1] = Chessmen.BLACK_KNIGHT;
		chessboard [0][2] = Chessmen.BLACK_BISHOP;
		chessboard [0][3] = Chessmen.BLACK_QUEEN;
		chessboard [0][4] = Chessmen.BLACK_KING;
		chessboard [0][5] = Chessmen.BLACK_BISHOP;
		chessboard [0][6] = Chessmen.BLACK_KNIGHT;
		chessboard [0][7] = Chessmen.BLACK_ROOK;

		// Populate: WHITE PAWN
		for (int col = 0; col < chessboard[6].length; col++) {
			chessboard [6][col] = Chessmen.WHITE_PAWN;
		}

		// Populate: BLACK PAWN
		for (int col = 0; col < chessboard[1].length; col++) {
			chessboard [1][col] = Chessmen.BLACK_PAWN;
		}

		// Create scanner
		Scanner scanner = new Scanner(System.in);
		String exit = "exit";

		while(true){
			// Call print function
			PrintBoard(chessboard);
			// Prompt user for move
			System.out.println("Enter next move in standard chess notation (e.g. e1 to e5).\nEnter EXIT to quit game!");
			// LOGGER.warning("Logger message 2");
			String nextMove = scanner.nextLine();
			// Exit loop & end game if user EXITs
			if(exit.equalsIgnoreCase(nextMove)) {
				System.out.println("Thank you for playing!");
				scanner.close();
				System.exit(0);
			} 
			move(chessboard, nextMove);
		}

	}

	/**
	 * This method prints out the graphics
	 *
	 * @param chessboard the chessboard
	 */
	public static void PrintBoard (Chessmen[][] chessboard) {
		// Create and print horizontal characters
		String[] alphaHorizontal = {"a", "b", "c", "d", "e", "f", "g", "h"};
		System.out.print("\t");
		for(int row = 0; row < alphaHorizontal.length; row++) {
			System.out.print(alphaHorizontal[row] + "\t");
		}
		System.out.println("\n");

		// Create variable for vertical number line
		int numberVertical = 8;
		for (int row = 0; row < 8; row++) {
			// Print vertical number
			System.out.print(numberVertical + ".\t");
			--numberVertical;

			// Print Unicode characters
			for (int col = 0; col < 8; col++) {
				switch (chessboard [row][col]){
				case BLACK_ROOK:
					System.out.print("\u265C\t");
					break;
				case BLACK_KNIGHT:
					System.out.print("\u265E\t");
					break;
				case BLACK_BISHOP:
					System.out.print("\u265D\t");
					break;
				case BLACK_QUEEN:
					System.out.print("\u265B\t");
					break;
				case BLACK_KING:
					System.out.print("\u265A\t");
					break;
				case BLACK_PAWN:
					System.out.print("\u265F\t");
					break;
				case WHITE_ROOK:
					System.out.print("\u2656\t");
					break;	
				case WHITE_KNIGHT:
					System.out.print("\u2658\t");
					break;
				case WHITE_BISHOP:
					System.out.print("\u2657\t");
					break;
				case WHITE_QUEEN:
					System.out.print("\u2655\t");
					break;
				case WHITE_KING:
					System.out.print("\u2654\t");
					break;
				case WHITE_PAWN:
					System.out.print("\u2659\t");
					break;
				case EMPTY:
					System.out.print("\t");
					break;
				}
			}	
			System.out.print("\n\n");
		}
	}

	
	/**
	 * Move.
	 *
	 * @param chessboard the chessboard
	 * @param move the move
	 */
	public static void move(Chessmen[][] chessboard, String move) {
		// Parse move string into components
		String[] components = move.split(" ");

		// Catch exception
		try {
			// OLD POSITION
			// sum of numberVertical and row in array always equals 8
			int rowOld = 8 - Character.getNumericValue(components[0].charAt(1));
			// 'a' from Unicode character returns 10; 'b' returns 11 etc
			// Hence, Unicode character always 10 larger than column in array
			int colOld = Character.getNumericValue(components[0].charAt(0)) - 10;

			// NEW POSITION
			int rowNew = 8 - Character.getNumericValue(components[2].charAt(1));
			int colNew = Character.getNumericValue(components[2].charAt(0)) - 10;

			if(isValid(chessboard, rowOld, colOld, rowNew, colNew)) {		
				// fill new and empty positions
				chessboard [rowNew][colNew] = chessboard [rowOld][colOld];
				chessboard [rowOld][colOld] = Chessmen.EMPTY;
			}
			else {
				System.out.println("Invalid move. Please enter move in form of e1 to e5");
				// System.err.println(“error message”);
				theLogger.severe("Invalid move.SEVERE");
				theLogger.warning("Invalid move.WARNING");
				theLogger.info("Invalid move.INFO");
				theLogger.log(Level.INFO, "Invalid move.INFO(T)");
				theLogger.config("Invalid Move. CONFIG");
				theLogger.fine("Invalid Move.FINE");
				theLogger.finer("Invalid Move.FINER");
				theLogger.finest("Invalid Move.FINEST");
			}
		}
		// Catch exception
		catch (ArrayIndexOutOfBoundsException exception) {
			
			System.out.println("No valid chess move! Try something like e1 to e5");
			theLogger.severe("Invalid move.SEVERE");
			theLogger.warning("Invalid move.WARNING");
			theLogger.info("Invalid move.INFO");
			theLogger.log(Level.INFO, "Invalid move.INFO(T)");
			theLogger.config("Invalid Move. CONFIG");
			theLogger.fine("Invalid Move.FINE");
			theLogger.finer("Invalid Move.FINER");
			theLogger.finest("Invalid Move.FINEST");
		}
	}

	/**
	 * Checks if is valid.
	 *
	 * @param chessboard the chessboard
	 * @param oldI the old I
	 * @param oldJ the old J
	 * @param newI the new I
	 * @param newJ the new J
	 * @return the boolean
	 */
	public static Boolean isValid(Chessmen[][] chessboard, int oldI, int oldJ, int newI, int newJ) {
		// CHECK if any move has been made
		if((newI == oldI) && (newJ == oldJ)) {
			System.out.println("You are trying to stay on same field");
			return false;
		}

		// CHECK if target location is outside of the box
		if(newI > 7 || newI < 0 || newJ > 7 || newJ < 0) {
			System.out.println("The chosen destination is outside of the board!");
			return false;
		}

		// CHECK if target position is of same color
		// Define color of current piece. true = black
		boolean currentBlack = false;
		if(chessboard[oldI][oldJ] == Chessmen.BLACK_ROOK || chessboard[oldI][oldJ] == Chessmen.BLACK_KNIGHT || chessboard[oldI][oldJ] == Chessmen.BLACK_BISHOP || chessboard[oldI][oldJ] == Chessmen.BLACK_QUEEN || chessboard[oldI][oldJ] == Chessmen.BLACK_KING ||chessboard[oldI][oldJ] == Chessmen.BLACK_PAWN) {
			currentBlack = true;
		}
		// Boolean for target: true = black
		boolean targetBlack = false;
		if(chessboard[newI][newJ] == Chessmen.BLACK_ROOK || chessboard[newI][newJ] == Chessmen.BLACK_KNIGHT || chessboard[newI][newJ] == Chessmen.BLACK_BISHOP || chessboard[newI][newJ] == Chessmen.BLACK_QUEEN || chessboard[newI][newJ] == Chessmen.BLACK_KING ||chessboard[newI][newJ] == Chessmen.BLACK_PAWN) {
			targetBlack = true;
		}
		// Boolean for target: true = empty
		boolean targetEmpty = false;
		if(chessboard[newI][newJ] == Chessmen.EMPTY) {
			targetEmpty = true;
		}
		// Return false if current and target are same color	
		if((currentBlack && targetBlack) || (!currentBlack && !targetBlack && !targetEmpty)) {
			System.out.println("You cannot attack your own army!");
			return false;
		}

		// CHECK if move is allowed
		switch(chessboard [oldI][oldJ]) {
		case BLACK_ROOK:
			// only horizontal and vertical moves
			if((oldI == newI) || (oldJ == newJ))
				return true;
			else
				return false;
		case BLACK_KNIGHT:
			// only move in shape of L (2 steps plus 1 step)
			if((Math.abs(oldI - newI) == 1 && Math.abs(oldJ - newJ) == 2) || (Math.abs(oldI - newI) == 2 && Math.abs(oldJ - newJ) == 1))
				return true;
			else
				return false;
		case BLACK_BISHOP:
			// only diagonal moves
			if(Math.abs(oldI - newI) == Math.abs(oldJ - newJ))
				return true;
			else
				return false;
		case BLACK_QUEEN:
			// only horizontal, vertical and diagonal moves 
			if(oldI == newI || oldJ == newJ || Math.abs(oldI - newI) == Math.abs(oldJ - newJ))
				return true;
			else
				return false;
		case BLACK_KING:
			// booleans return true, if King moves more than 1 step
			if((Math.abs(oldI - newI) == Math.abs(oldJ - newJ) && Math.abs(oldI - newI) == 1) || (oldI == newI) && Math.abs(oldJ - newJ) == 1 || (oldJ == newJ) && Math.abs(oldI - newI) == 1) {
				return true;
			}
			/*
			boolean tooFarI = (Math.abs(oldI - newI) > 1);
			boolean tooFarJ = (Math.abs(oldJ - newJ) > 1);
			if(tooFarI || tooFarJ)
				return false;
			 */
			else
				return false; 
		case BLACK_PAWN:
			// moves 1 square, only during its first movement can move 2 squares
			// no backward movement
			// attacks diagonally by 1 square
			if(oldI == 1 && newI == (oldI + 2) && oldJ == newJ && chessboard[newI][newJ] == Chessmen.EMPTY) 
				return true;
			else if(newI == (oldI + 1) && oldJ == newJ && chessboard[newI][newJ] == Chessmen.EMPTY)
				return true;
			else if(newI == (oldI + 1) && Math.abs(oldJ - newJ) == 1 && chessboard[newI][newJ] != Chessmen.EMPTY)
				return true;
			else 
				return false;
		case WHITE_ROOK:
			if((oldI == newI) || (oldJ == newJ))
				return true;
			else
				return false;
		case WHITE_KNIGHT:
			if((Math.abs(oldI - newI) == 1 && Math.abs(oldJ - newJ) == 2) || (Math.abs(oldI - newI) == 2 && Math.abs(oldJ - newJ) == 1))
				return true;
			else
				return false;
		case WHITE_BISHOP:
			if(Math.abs(oldI - newI) == Math.abs(oldJ - newJ))
				return true;
			else
				return false;
		case WHITE_QUEEN:
			if(oldI == newI || oldJ == newJ || Math.abs(oldI - newI) == Math.abs(oldJ - newJ))
				return true;
			else
				return false;
		case WHITE_KING:
			if((Math.abs(oldI - newI) == Math.abs(oldJ - newJ) && Math.abs(oldI - newI) == 1) || (oldI == newI) && Math.abs(oldJ - newJ) == 1 || (oldJ == newJ) && Math.abs(oldI - newI) == 1) {
				return true;
			}
			/*
			boolean tooFarI = (Math.abs(oldI - newI) > 1);
			boolean tooFarJ = (Math.abs(oldJ - newJ) > 1);
			if(tooFarI || tooFarJ)
				return false;
			 */
			else
				return false;  
		case WHITE_PAWN:
			if(oldI == 6 && newI == (oldI - 2) && oldJ == newJ && chessboard[newI][newJ] == Chessmen.EMPTY) 
				return true;
			else if(newI == (oldI - 1) && oldJ == newJ && chessboard[newI][newJ] == Chessmen.EMPTY)
				return true;
			else if(newI == (oldI - 1) && Math.abs(oldJ - newJ) == 1 && chessboard[newI][newJ] != Chessmen.EMPTY)
				return true;
			else 
				return false;
		case EMPTY:
			break;
		}
		return false;
	}
}