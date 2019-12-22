package com.example.sudokutest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button easy, med, hard, solveMe;
	TextView timer;
	boolean isStarted = false;
	private long startTime = 0L;
	private Handler customHandler = new Handler();
	long timeInMilliseconds = 0L;
	long timeSwapBuff = 0L;
	long updatedTime = 0L;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		easy = findViewById(R.id.easyBtn);
		med = findViewById(R.id.mediumBtn);
		hard = findViewById(R.id.hardBtn);
		solveMe =  findViewById(R.id.solveMeBtn);
		timer = findViewById(R.id.timer);

		GameEngine.getInstance().createEmptyGrid(this);

		easy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame("easy");
			}
		});

		med.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame("med");
			}
		});

		hard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame("hard");
			}
		});

		solveMe.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!isStarted) {
					Toast.makeText(MainActivity.this,
							"Anda belum memulai permainan", Toast.LENGTH_SHORT).show();
				} else {
					SudokuChecker.getInstance().autoSolver(SudokuGenerator.getInstance().getPosisiX(),
																SudokuGenerator.getInstance().getPosisiY(),
																SudokuGenerator.getInstance().getValueXY());
				}
			}
		});
	}

	private void startGame(String level) {
		isStarted = true;
		GameEngine.getInstance().createGrid(level);
		startTime = SystemClock.uptimeMillis();
		customHandler.postDelayed(updateTimerThread, 0);
	}

	private Runnable updateTimerThread = new Runnable() {

		public void run() {
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;

			String jam="", menit="";
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			int hours = mins / 60;
			secs = secs % 60;

			if (hours == 0) {
				jam = "00";
			} else if (hours<10) {
				jam = "0"+hours;
			}
			if (mins == 0) {
				menit = "00";
			} else if (mins<10) {
				menit = "0"+mins;
			}

			timer.setText(""+jam+":"+menit+":"
					+ String.format("%02d", secs));
			customHandler.postDelayed(this, 0);
			if (GameEngine.getInstance().isCheckGameDone()) {
				timeSwapBuff += timeInMilliseconds;
				customHandler.removeCallbacks(updateTimerThread);
			}
		}

	};
}
