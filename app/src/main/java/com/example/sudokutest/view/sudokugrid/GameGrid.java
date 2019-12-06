package com.example.sudokutest.view.sudokugrid;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.sudokutest.SudokuChecker;

public class GameGrid {
	private SudokuCell[][] sudoku = new SudokuCell[9][9];
	private Context context;
	
	public GameGrid(Context context) {
		this.context = context;
		for(int x=0 ; x<9 ; x++){
			for(int y=0 ; y<9 ; y++){
				sudoku[x][y] = new SudokuCell(context);
			}
		}
	}
	
	public void setGrid (int[][] grid){
		for(int x=0 ; x<9 ; x++){
			for(int y=0 ; y<9 ; y++){
				sudoku[x][y].setInitValue(grid[x][y]);
				if(grid[x][y] != 0){
					sudoku[x][y].setNotModifiable(); //no editable yang udah keisi
				} else {
					sudoku[x][y].setModifiable(); //editable yg belum keisi
				}
			}
		}
	}
	
	public SudokuCell[][] getGrid(){
		return sudoku;
	}
	
	public SudokuCell getItem(int x, int y){
		return sudoku[x][y];
	}
	
	public SudokuCell getItem(int position) {
		int x = position % 9;
		int y = position / 9;
		
		return sudoku[x][y];
	}
	
	public void setItem(int x , int y , int number){
//		Log.d("CEK", "CEK setItem "+number);
		sudoku[x][y].setValue(number);
	}

	public int[][] getSudokuValue() {
		int [][] sudGrid = new int[9][9];
		for(int x=0 ; x<9 ; x++) {
			for(int y=0 ; y<9 ; y++){
				sudGrid[x][y] = getItem(x,y).getValue();
//				Log.d("CEK", "CEK Sudoku Value "+sudGrid[x][y]);
			}
		}

		return sudGrid;
	}

	
	public boolean checkGame(){
		int [][] sudGrid = new int[9][9];
		sudGrid = getSudokuValue();
		
		if(SudokuChecker.getInstance().checkSudoku(sudGrid)){
			Toast.makeText(context, "You solved the sudoku.", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
}
