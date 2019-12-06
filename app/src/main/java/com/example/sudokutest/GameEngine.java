package com.example.sudokutest;

import com.example.sudokutest.view.sudokugrid.GameGrid;

import android.content.Context;
import android.util.Log;

public class GameEngine {
	private static GameEngine instance;
	
	private GameGrid grid = null;
	private boolean checkGameDone = false;

	private int[][] sudokuOutside;
	private int selectedPosX = -1, selectedPosY = -1;
	
	private GameEngine(){}
	
	public static GameEngine getInstance(){
		if (instance == null) {
			instance = new GameEngine();
		}
		return instance;
	}

	public void createEmptyGrid(Context context) {
		int[][] sudoku = new int[9][9];
		grid = new GameGrid(context);
		grid.setGrid(sudoku);
	}
	
	public void createGrid(){
		int[][] sudoku = SudokuGenerator.getInstance().generateGrid();
		sudoku = SudokuGenerator.getInstance().removeElements(sudoku);
		grid.setGrid(sudoku);
	}
	
	public GameGrid getGrid(){
		return grid;
	}

	public int[][] getSudokuOutside() {
		sudokuOutside = grid.getSudokuValue();
		return sudokuOutside;
	}

	public void setSelectedPosition(int x , int y){
		selectedPosX = x;
		selectedPosY = y;
	}
	
	public void setNumber(int number) {
		if(selectedPosX != -1 && selectedPosY != -1){
			grid.setItem(selectedPosX,selectedPosY,number);
		}
		checkGameDone = grid.checkGame();
	}

	public boolean isCheckGameDone() {
		return checkGameDone;
	}
}
