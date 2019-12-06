package com.example.sudokutest;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {
	private static SudokuGenerator instance;

	//variabel penampung angka yg tersedia
	private ArrayList<ArrayList<Integer>> availableNumber = new ArrayList<ArrayList<Integer>>();

	//variabel untuk ngerandom angka
	private Random rand = new Random();

	private static int COUNTED_ROW = 81;
	
	private SudokuGenerator(){}
	
	public static SudokuGenerator getInstance() {
		if(instance == null) {
			instance = new SudokuGenerator();
		}
		return instance;
	}
	
	public int[][] generateGrid() {
		int[][] sudoku = new int[9][9];
		
		int currentPos = 0;

		while(currentPos < COUNTED_ROW){
			if(currentPos == 0){
				clearGrid(sudoku);
			}
			
			if(availableNumber.get(currentPos).size() != 0){
				int i = rand.nextInt(availableNumber.get(currentPos).size());
				int number = availableNumber.get(currentPos).get(i);

				if(!checkConflict(sudoku, currentPos , number)) {
					int xPos = currentPos%9;
					int yPos = currentPos/9;

					sudoku[xPos][yPos] = number;
					availableNumber.get(currentPos).remove(i);
					currentPos++;
				} else {
					availableNumber.get(currentPos).remove(i);
				}
			} else {
				for(int i=1 ; i<=9 ; i++) {
					availableNumber.get(currentPos).add(i);
				}
				currentPos--;
			}
		}
		return sudoku;
	}
	
	public int[][] removeElements(int[][] sudoku) {
		int i = 0;
		int emptyField;
		int min = COUNTED_ROW/9;
		int max = COUNTED_ROW/4;
		emptyField = rand.nextInt((max-min) + 1) + min;

		while(i < emptyField) {
			int x = rand.nextInt(9);
			int y = rand.nextInt(9);
			
			if(sudoku[x][y] != 0){
				sudoku[x][y] = 0;
				i++;
			}
		}
		return sudoku;
	}
	
	private void clearGrid(int [][] sudoku) {
		availableNumber.clear();
		
		for(int y=0 ; y<9 ; y++) {
			for(int x=0 ; x<9 ; x++) {
				sudoku[x][y] = -1;
			}
		}
		
		for(int x=0 ; x<81 ; x++) {
			availableNumber.add(new ArrayList<Integer>());
			for(int i=1 ; i<=9 ; i++) {
				availableNumber.get(x).add(i);
			}
		}
	}
	
	private boolean checkConflict(int[][] sudoku, int currentPos, final int number){
		int xPos = currentPos%9;
		int yPos = currentPos/9;
		
		if(checkHorizontalConflict(sudoku, xPos, yPos, number)
				|| checkVerticalConflict(sudoku, xPos, yPos, number)
				|| checkRegionConflict(sudoku, xPos, yPos, number)){
			return true;
		}
		return false;
	}

	private boolean checkHorizontalConflict(final int[][] sudoku, final int xPos,
											 final int yPos, final int number) {
		for(int x=xPos-1 ; x>=0 ; x--) {
			if(number == sudoku[x][yPos]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkVerticalConflict(final int[][] sudoku, final int xPos,
										   final int yPos, final int number) {
		for(int y=yPos-1; y>=0 ; y--) {
			if(number == sudoku[xPos][y]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkRegionConflict(final int[][] sudoku, final int xPos,
										 final int yPos, final int number) {
		int xRegion = xPos/3;
		int yRegion = yPos/3;
		
		for (int x=xRegion*3 ; x<xRegion*3+3 ; x++ ) {
			for (int y=yRegion*3 ; y<yRegion*3+3 ; y++) {
				if ((x != xPos || y != yPos) && number == sudoku[x][y]) {
					return true;
				}
			}
		}
		
		return false;
	}
}
