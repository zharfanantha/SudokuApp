package com.example.sudokutest.view.sudokugrid;

import android.content.Context;
import android.util.Log;
import android.view.View;

public class BaseSudokuCell extends View {

	
	private int value;
	private boolean modifiable = true;
	
	public BaseSudokuCell(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
	
	public void setNotModifiable(){
		modifiable = false;
	}
	public void setModifiable() {
		modifiable = true;
	}
	
	public void setInitValue(int value){
		this.value = value;
		invalidate();
	}
	
	public void setValue(int value){
//		Log.d("CEK", "CEK setValue "+modifiable);
		if(modifiable){
			this.value = value;
		}

		invalidate();
	}
	
	public int getValue(){
		return value;
	}
}
