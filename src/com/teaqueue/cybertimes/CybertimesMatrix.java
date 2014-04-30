package com.teaqueue.cybertimes;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.text.SpannableStringBuilder;
import android.util.Log;

public class CybertimesMatrix {
	// ０１２３４５６７８９ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ
	final static String charset = "ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂッツヅテデトドナニヌネノハバパヒビピフプヘベペホボポマミムメモャヤュユョヨラリルレロヮワヰヱヲンヴヵヶーヽヾぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞただちぢっつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろゎわゐゑをんゔゝゞ";
	final static double changePercent = 0.01;
	Random random;
	public int width, height;
	char[][] charMatrix;
	Raindrop[] raindrops;
	ArrayList<Redline> redlines;
	ArrayList<Blueline> bluelines;
	Blueline activeBlueline;
	
	public CybertimesMatrix(int width, int height) {
		random = new Random();
		this.width = width;
		this.height = height;
		charMatrix = new char[width][height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				charMatrix[x][y] = charset.charAt(random.nextInt(charset.length()));
		raindrops = new Raindrop[width];
		redlines = new ArrayList<Redline>();
		Redline.map.clear();
		bluelines = new ArrayList<Blueline>();
		Blueline.map.clear();
	}
	
	public void update() {
		// Update characters.
		for (int change = (int)Math.round(width * height * changePercent); change > 0; change--)
			charMatrix[random.nextInt(width)][random.nextInt(height)] = charset.charAt(random.nextInt(charset.length()));
		
		// Update raindrops.
		for (int x = 0; x < width; x++)
		{
			Raindrop raindrop = raindrops[x];
			if (raindrop == null) {
				if (random.nextDouble() < .05)
					raindrops[x] = new Raindrop(random.nextDouble() < .75 ? 0 : random.nextInt(height));
			}
			else
			{
				if (raindrop.bottom >= height || (raindrop.age > 8 && random.nextDouble() < .03))
					raindrop.dying = true;
				raindrop.update();
				if (raindrop.isDead())
					raindrops[x] = null;
			}
		}
		
		// Update redlines.
		for (int i = redlines.size() - 1; i >= 0; i--)
		{
			final Redline redline = redlines.get(i);
			if (redline.lastX() < 0 || redline.lastX() >= width) {
				for (Point point : redline.points)
					if (Redline.map.get(point) == redline)
						Redline.map.remove(point);
				redlines.remove(i);
			}
			redline.update();
		}
		
		// Update bluelines.
		if (activeBlueline != null)
			activeBlueline.update();
		for (int i = bluelines.size() - 1; i >= 0; i--)
		{
			final Blueline blueline = bluelines.get(i);
			if (blueline.isDead()) {
				blueline.points.clear();
				bluelines.remove(i);
			}
			blueline.update();
		}
	}
	
	public int color(int x, int y)
	{
		final Point point = new Point(x, y);
		final Redline redline = Redline.map.get(point);
		if (redline != null)
			return redline.color(x, y);
		final Blueline blueline = Blueline.map.get(point);
		if (blueline != null)
			return blueline.color(x, y);
		if (raindrops[x] == null)
			return Color.BLACK;
		return raindrops[x].color(y);
	}
	
	public void onTouch(int x, int y)
	{
		redlines.add(new Redline(x, y, x <= width / 2));
	}
	public void onMove(int x, int y)
	{
		if (activeBlueline != null)
			activeBlueline.onTouch(x, y);
		else
			activeBlueline = new Blueline(x, y);
	}
	public void onUp()
	{
		if (activeBlueline != null) {
			bluelines.add(activeBlueline);
			activeBlueline = null;
		}
	}
}
