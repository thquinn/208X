package com.teaqueue.cybertimes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

public class Redline {
	public static Random random = new Random();
	public static HashMap<Point, Redline> map = new HashMap<Point, Redline>();
	public static enum Direction {FORWARD, UP, DOWN};
	ArrayList<Point> points;
	boolean left;
	Direction direction;
	
	public Redline(int x, int y, boolean left)
	{
		points = new ArrayList<Point>();
		points.add(new Point(x, y));
		this.left = left;
		direction = Direction.FORWARD;
	}
	
	public void update()
	{
		// Direction changes.
		if (direction == Direction.FORWARD && random.nextDouble() < .05)
			direction = random.nextBoolean() ? Direction.UP : Direction.DOWN;
		else if (random.nextDouble() < .2)
			direction = Direction.FORWARD;
		
		// Add next point.
		Point newest = points.get(points.size() - 1);
		Point newPoint;
		if (direction == Direction.FORWARD)
			newPoint = new Point(left ? newest.x - 1 : newest.x + 1, newest.y);
		else if (direction == Direction.UP)
			newPoint = new Point(newest.x, newest.y - 1);
		else // DOWN
			newPoint = new Point(newest.x, newest.y + 1);
		points.add(newPoint);
		map.put(newPoint, this);
		
		// Kill oldest if we're at max length.
		if (points.size() > 16)
		{
			if (map.get(points.get(0)) == this)
				map.remove(points.get(0));
			points.remove(0);
		}
	}
	
	public int color(int x, int y)
	{
		for (int i = 0; i < points.size() && i < 16; i++)
			if (points.get(i) == null)
				Log.i("debug", "Found a null point!");
		
		for (int i = 0; i < points.size(); i++)
		{
			final Point point = points.get(i);
			if (point.x == x && point.y == y)
			{
				int strength = i - points.size() + 17;
				return strength == 16 ? ColorUtil.rgb(255, 175, 175) : ColorUtil.rgb(strength * 16, 0, 0);
			}
		}
		return Color.BLACK;
	}
	
	public int lastX()
	{
		return points.get(0).x;
	}
}
