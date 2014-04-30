package com.teaqueue.cybertimes;

import java.io.IOException;

import android.graphics.Color;
import android.media.MediaRecorder;

public class ColorUtil {
	static MediaRecorder recorder;
	static int amplitude;

	public static void init() {
		/*
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile("/dev/null"); 
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.start();
		*/
	}
	
	public static void destroy() {
		/*
		recorder.stop();
		recorder.reset();
		recorder.release();
		recorder = null;
		*/
	}
	
	public static void update() {
		/*
		int newAmplitude = recorder.getMaxAmplitude();
		if (newAmplitude < amplitude)
			amplitude = (amplitude * 3 + newAmplitude) / 4;
		else
			amplitude = newAmplitude;
		*/
	}
	
	public static int rgb(int r, int g, int b) {
		return Color.rgb(r, g, b);
		/* volume color
		if (r == 0 && g == 0 && b == 0)
			return Color.BLACK;
		float mult = 1 + (amplitude / 10000f);
		int newR = Math.min(255, (int)Math.round(r * mult));
		int newG = Math.min(255, (int)Math.round(g * mult));
		int newB = Math.min(255, (int)Math.round(b * mult));
		return Color.rgb(newR, newG, newB);
		*/
	}
}
