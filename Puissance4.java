package Puissance4;

import java.util.Scanner;

public class Puissance4 {
	static final int GRID_HEIGHT = 8;
	static final int GRID_WIDTH = 7;
	static final char PLAYER1_MARK = 'X';
	static final char PLAYER2_MARK = 'O';
	
	public static void displayWelcomeMessage() {
		System.out.println("Bienvenue dans Puissance 4");
		
	}

	public static char[][] generateGrid() {
		char [][] grid = new char [GRID_HEIGHT][GRID_WIDTH];
		for (int i = 0; i<GRID_HEIGHT; i++) {
			for (int j = 0; j<GRID_WIDTH; j++) {
				grid[i][j] = ' ';
			}
		}
		return grid;
	}
	
	public static int changePlayer(int player) {
		if (player != 1) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public static void askColumn(char[][] grid, int player) {
		displayGrid(grid);
		String message = "C'est au jour "+player+" de jouer: (";
		if (player == 1) {
			message = message+PLAYER1_MARK;
		} else {
			message = message+PLAYER2_MARK;
		}
		System.out.println(message+")");
	}
	
	public static void displayGrid(char[][] grid) {
		System.out.println("_____________________________");
		for (int i = GRID_HEIGHT-1; i>=0; i--) {
			System.out.print('|');
			for (int j = 0; j<GRID_WIDTH; j++) {
				System.out.print(" "+grid[i][j]+" ");
				System.out.print('|');
			}
			System.out.println();
		}
		System.out.println("_____________________________");
		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
	}
	
	public static boolean checkValidColumn(char[][] grid, int column) {
		column = column -1;
		int maxHeight = GRID_HEIGHT -1;
		boolean valid = true;
		
		if (column < 0 || column > GRID_WIDTH) {
			valid = false;
			return valid;
		}
		if (grid[maxHeight][column] == PLAYER1_MARK || grid[maxHeight][column] == PLAYER2_MARK) {
			valid = false;
			return valid;
		}
		return valid;
	}
	
	public static char[][] addMark(char[][] grid, int column, int player) {
		column = column-1;
		for (int i = 0; i<GRID_HEIGHT; i++) {
			if (grid[i][column] == ' ') {
				if (player == 1) {
					grid[i][column] = PLAYER1_MARK;
				} else {
					grid[i][column] = PLAYER2_MARK;					
				}
				break;
			}
		}
		return grid;
	}
	
	public static int checkDirection(char[][] grid, int column, int row, String direction) {
		int quantity = 0;
		
		switch (direction){
		case "topLeft":	
			for (int i=column-1, j=row+1; i>=0 && j<GRID_HEIGHT; i--, j++) {
				if (grid[j][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "left":
			for (int i=column-1; i>=0; i--) {
				if (grid[row][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "bottomLeft":
			for (int i=column-1, j=row-1; i>=0 && j>=0; i--, j--) {
				if (grid[j][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "down":
			for (int i=row-1; i>=0; i--) {
				if (grid[i][column] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "bottomRight":
			for (int i=column+1, j=row-1; i<GRID_WIDTH && j>=0; i++, j--) {
				if (grid[j][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "right":
			for (int i=column+1; i<GRID_WIDTH; i++) {
				if (grid[row][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			
		case "topRight":
			for (int i=column+1, j=row+1; i<GRID_WIDTH && j<GRID_HEIGHT; i++, j++) {
				if (grid[j][i] == grid [row][column]) {
					quantity ++;
				} else {
					break;
				}
			}
			break;
			}
		return quantity;
	}
	
	public static boolean isWinningShot(char[][] grid, int column) {
		int lastColumn = column-1;
		int lastRow = 0;
		for (int i= GRID_HEIGHT-1; i>0; i--) {
			if (grid[i][lastColumn] != ' ') {
				lastRow = i;
				break;
			}
		}
		boolean win = false;
		
		int topLeft = checkDirection(grid, lastColumn, lastRow, "topLeft");
		int left = checkDirection(grid, lastColumn, lastRow, "left");
		int bottomLeft = checkDirection(grid, lastColumn, lastRow, "bottomLeft");
		int down = checkDirection(grid, lastColumn, lastRow, "down");
		int bottomRight = checkDirection(grid, lastColumn, lastRow, "bottomRight");
		int right = checkDirection(grid, lastColumn, lastRow, "right");		
		int topRight = checkDirection(grid, lastColumn, lastRow, "topRight");
		
		if (topLeft + bottomRight >= 3 || left + right >= 3 || topRight + bottomLeft >= 3 || down >= 3) {
			win = true;
		}
				
		return win;
	}
	
	public static void displayWinningMessage(int player) {
		System.out.println("Félicitations! Le joueur "+player+" a gagné!");
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);		
		int player = 0;
		char [][] grid = generateGrid();
		boolean win = false;
		
		displayWelcomeMessage();
		
		do {
			player = changePlayer(player);
			int column;
			boolean validColumn = false;
			do {
				askColumn(grid, player);
				column = Integer.parseInt(sc.nextLine());
				validColumn = checkValidColumn(grid, column);
			} while (!validColumn);
			grid = addMark(grid, column, player);
			win = isWinningShot(grid, column);
		}while (!win);
		displayGrid(grid);
		displayWinningMessage(player);
		
		sc.close();
	}

}
