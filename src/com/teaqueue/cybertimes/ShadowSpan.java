package com.teaqueue.cybertimes;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class ShadowSpan extends CharacterStyle
{
    public float dx;
    public float dy;
    public float radius;
    public int color;
    
    public ShadowSpan(float radius, float dx, float dy, int color)
    {
        this.radius = radius;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
    }

    @Override
    public void updateDrawState (TextPaint tp)
    {
    	tp.setColor(color);
        tp.setShadowLayer(radius, dx, dy, color);
    }
}