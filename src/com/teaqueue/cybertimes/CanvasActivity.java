// TODO:
//	- Color blending
//	- No touch event on drag
//	- Proper blueline behavior with ArrayList<(Struct w/ Point and strength)>
//	- Line graph? Mic-powered bar graphs? Stock ticker?
//	- Controls to toggle features

package com.teaqueue.cybertimes;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CanvasActivity extends Activity {
	MatrixView matrixView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Hide title bar.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		
		super.onCreate(savedInstanceState);
		matrixView = new MatrixView(this);
		matrixView.setSystemUiVisibility(MatrixView.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		setContentView(matrixView);
		
		Thread t = new Thread() {
		  @Override
		  public void run() {
		    try {
		      while (!isInterrupted()) {
		        Thread.sleep(50);
		        runOnUiThread(new Runnable() {
		          @Override
		          public void run() {
		        	  ColorUtil.update();
		        	  matrixView.matrix.update();
		        	  matrixView.console.update();
		        	  matrixView.invalidate();
		          }
		        });
		      }
		    } catch (InterruptedException e) {
		    }
		  }
		};

		t.start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ColorUtil.init();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		ColorUtil.destroy();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	private class MatrixView extends View
	{
		final static int textSize = 40;
		final static int consoleTextSize = 25;
		TextPaint paint;
		public CybertimesMatrix matrix;
		public CybertimesConsole console;
		int width, height;
		
		public MatrixView(Context context) {
			super(context);
			paint = new TextPaint();
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			width = size.x;
			height = size.y;
			matrix = new CybertimesMatrix(width / textSize, height / textSize);
			paint.setAntiAlias(true);
			setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final int x = (int)Math.round(event.getX()) / textSize;
					final int y = (int)Math.round(event.getY()) / textSize;
					if (event.getAction() == MotionEvent.ACTION_DOWN)
						matrix.onTouch(x, y);
					else if (event.getAction() == MotionEvent.ACTION_MOVE)
						matrix.onMove(x, y);
					else if (event.getAction() == MotionEvent.ACTION_UP)
						matrix.onUp();
					return true;
				}
			});
			console = new CybertimesConsole();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			// Fill black.
			canvas.drawColor(Color.BLACK);
			
			// Draw matrix.
			paint.setTextSize(textSize);
			for (int x = 0; x < matrix.width; x++)
				for (int y = 0; y < matrix.height; y++)
				{
					int color = matrix.color(x, y);
					if (color != Color.BLACK)
					{
						paint.setColor(color);
						paint.setShadowLayer(textSize / 4, 0, 0, color);
						String string = Character.toString(matrix.charMatrix[x][y]);
						if (string.length() > 1)
							Log.i("debug", "LONG STRING: " + string);
						canvas.drawText(string, x * textSize, (y + 1) * textSize, paint);
					}
				}
			
			// Draw console.
			String[] consoleLines = console.toStringArray();
			paint.reset();
			paint.setTypeface(Typeface.MONOSPACE);
			paint.setColor(Color.WHITE);
			paint.setTextSize(consoleTextSize);
			for (int i = 0; i < consoleLines.length; i++) {
				int y = height - (consoleTextSize * consoleLines.length) + (consoleTextSize * i);
				canvas.drawText(consoleLines[i], 0, y, paint);
			}
		}
	}
}
