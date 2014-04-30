package com.teaqueue.cybertimes;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class Blueline {
	public static HashMap<Point, Blueline> map = new HashMap<Point, Blueline>();
	static enum Direction {HORIZONTAL, VERTICAL, UNKNOWN};
	static final int minPull = 4;
	ArrayList<Point> points;
	Direction direction;
	int boost;
	
	public Blueline(int x, int y) {
		points = new ArrayList<Point>();
		points.add(new Point(x, y));
		direction = Direction.UNKNOWN;
		boost = 8;
	}
	
	public void update() {
		if (boost > 0)
			boost--;
		
		// Light up new point.
		if (points.size() >= 16)
			map.put(points.get(15), this);
		
		// Remove oldest point.
		if (points.size() < 2)
			return;
		Point point = points.get(0);
		points.remove(0);
		if (map.get(point) == this)
			map.remove(point);
	}
	
	public boolean isDead() {
		return points.size() == 1;
	}
	
	public void onTouch(int x, int y) {
		Point newest = points.get(points.size() - 1);
		int dx = x - newest.x;
		int dy = y - newest.y;
		if (dx == 0 && dy == 0)
			return;
		if (direction == Direction.UNKNOWN)
			if (dx >= dy) {
				addPointsTo(dx, 0);
				direction = Direction.HORIZONTAL;
			} else {
				addPointsTo(0, dy);
				direction = Direction.VERTICAL;
			}
		else if (direction == Direction.HORIZONTAL) {
			addPointsTo(dx, 0);
			if (Math.abs(dy) >= minPull) {
				addPointsTo(0, dy);
				direction = Direction.VERTICAL;
			}
		} else { // direction == Direction.VERTICAL
			addPointsTo(0, dy);
			if (Math.abs(dx) >= minPull) {
				addPointsTo(dx, 0);
				direction = Direction.HORIZONTAL;
			}
		}
	}
	private void addPointsTo(int dx, int dy) {
		Point newest = points.get(points.size() - 1);
		if (dx < 0)
			for (int i = -1; i >= dx; i--)
				addPoint(newest.x + i, newest.y);
		else if (dx > 0)
			for (int i = 1; i <= dx; i++)
				addPoint(newest.x + i, newest.y);
		else if (dy < 0)
			for (int i = -1; i >= dy; i--)
				addPoint(newest.x, newest.y + i);
		else // dy > 0
			for (int i = 1; i <= dy; i++)
				addPoint(newest.x, newest.y + i);
	}
	private void addPoint(int x, int y) {
		Log.i("debug", "Adding point at " + x + ", " + y);
		final Point point = new Point(x, y);
		points.add(point);
	}
	
	public int color(int x, int y) {
		int maxStrengthIndex = Math.min(points.size() - 1, 15);
		for (int i = maxStrengthIndex; i >= 0; i--) {
			final Point point = points.get(i);
			if (point.x == x && point.y == y) {
				int strength = Math.min(16, i + 2 + boost);
				return strength == 16 ? ColorUtil.rgb(175, 175, 255) : ColorUtil.rgb(0, 0, 16 * strength);
			}
		}
		return Color.BLACK;
	}
}
