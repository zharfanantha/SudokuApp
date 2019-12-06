package com.example.sudokutest;

import android.util.Log;

public class SudokuChecker {
	private static SudokuChecker instance;
	
	private SudokuChecker(){}
	
	public static SudokuChecker getInstance(){
		if (instance == null) {
			instance = new SudokuChecker();
		}
		return instance;
	}

	public void autoSolver(int[][] sudoku) {
		for(int y=0 ; y<9 ; y++){
			for(int x=0 ; x<9 ; x++) {
				if (sudoku[x][y] == 0) {
					Log.d("CEK", "CEK AutoSolver Posisi Kosong "+x+" "+y);
					for (int i=1 ; i<=9 ; i++) {
						if (fillBlankCheckComplete(sudoku, x, y, i)){
							GameEngine.getInstance().setSelectedPosition(x, y);
							GameEngine.getInstance().setNumber(i);
							Log.d("CEK", "CEK AutoSolver Sama nih "+i);
						}
					}
				}
			}
		}
	}

	private boolean fillBlankCheckComplete(int[][] sudoku, int posOfX, int posOfy, int number) {
		return !fillBlankCheckColumn(sudoku, posOfX, number) && !fillBlankCheckRow(sudoku, posOfy, number)
				&& !fillBlankCheckRegion(sudoku, posOfX, posOfy, number);
	}

	private boolean fillBlankCheckColumn(int[][] sudoku, int posOfX, int number) {
		for (int y=0 ;y<9 ; y++) {
			if (sudoku[posOfX][y] == number)
				return true;
		}

		return false;
	}

	private boolean fillBlankCheckRow(int[][] sudoku, int posOfY, int number) {
		for (int x=0 ;x<9 ; x++) {
			if (sudoku[x][posOfY] == number)
				return true;
		}

		return false;
	}

	private boolean fillBlankCheckRegion(int[][] sudoku, int posOfX, int posOfY, int number) {
		int r = posOfY - (posOfY%3);
		int c = posOfX - (posOfX%3);

		for (int i = c; i < r + 3; i++)
			for (int j = r; j < c + 3; j++)
				if (sudoku[i][j] == number)
					return true;

		return false;
	}

	public boolean checkSudoku(int[][] sudoku){
		return (checkHorizontal(sudoku) || checkVertical(sudoku) || checkRegions(sudoku));
	}

	private boolean checkHorizontal(int[][] sudoku) {
		for(int y = 0 ; y < 9 ; y++){
			for(int xPos=0 ; xPos<9 ; xPos++) {
				if(sudoku[xPos][y] == 0){
					return false;
				}
				for(int x = xPos + 1 ; x < 9 ; x++){
					if(sudoku[xPos][y] == sudoku[x][y] || sudoku[x][y] == 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean checkVertical(int[][] sudoku) {
		for(int x=0 ; x<9 ; x++) {
			for(int yPos=0 ; yPos<9 ; yPos++) {
				if(sudoku[x][yPos] == 0) {
					return false;
				}
				for (int y=yPos+1 ; y<9 ; y++) {
					if (sudoku[x][yPos] == sudoku[x][y] || sudoku[x][y] == 0) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean checkRegions(int[][] sudoku) {
		for (int xRegion=0; xRegion<3; xRegion++) {
			for (int yRegion=0; yRegion<3 ; yRegion++) {
				if (!checkRegion(sudoku, xRegion, yRegion)) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkRegion(int[][] sudoku , int xRegion , int yRegion) {
		for (int xPos=xRegion*3 ; xPos<xRegion*3+3 ; xPos++) {
			for (int yPos=yRegion*3 ; yPos<yRegion*3+3 ; yPos++) {
				for (int x=xPos ; x<xRegion*3+3 ; x++) {
					for (int y=yPos ; y<yRegion*3+3 ; y++) {
						if (((x!=xPos || y!=yPos) && sudoku[xPos][yPos] == sudoku[x][y]) || sudoku[x][y] == 0) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
