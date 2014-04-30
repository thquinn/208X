package com.teaqueue.cybertimes;

import android.graphics.Color;

public class Raindrop {
	public int top, bottom, age, decay;
	public boolean dying, isRed;
	
	public Raindrop(int start) {
		top = start;
		bottom = start;
		age = 0;
		dying = false;
	}
	
	public void update() {
		age++;
		if (!dying)
			bottom++;
		else
			decay++;
		if (length() > 16)
			top++;
	}
	
	public int length() {
		return bottom - top + 1;
	}
	public int color(int y) {
		if (y < top || y > bottom)
			return Color.BLACK;
		int strength = Math.max(0, y - bottom + 16 - decay);
		if (strength == 0)
			return Color.BLACK;
		if (y == bottom && isRed)
			return ColorUtil.rgb(strength * 16, 0, 0);
		if (strength == 16)
			return ColorUtil.rgb(175, 255, 175);
		return ColorUtil.rgb(0, strength * 16, 0);
	}
	public boolean isDead() {
		return dying && decay >= 16;
	}
}
