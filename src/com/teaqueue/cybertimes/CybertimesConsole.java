package com.teaqueue.cybertimes;

import java.util.ArrayList;

public class CybertimesConsole {
	static int maxLines = 10;
	ArrayList<String> strings;
	CybertimesConsoleLine currentLine;
	
	public CybertimesConsole() {
		strings = new ArrayList<String>();
		for (int i = 0; i < maxLines - 1; i++)
			strings.add("");
		currentLine = new CybertimesConsoleLine();
	}
	
	public void update() {
		currentLine.update();
		if (currentLine.isDone()) {
			strings.add(currentLine.toString());
			if (strings.size() >= maxLines)
				strings.remove(0);
			currentLine = new CybertimesConsoleLine();
		}
	}
	
	public String[] toStringArray() {
		String[] output = new String[maxLines];
		for (int i = 0; i < strings.size(); i++) {
			output[i] = strings.get(i);
		}
		output[maxLines - 1] = currentLine.toString();
		return output;
	}
}
