package com.andedit.dungeon.console;

import java.util.EnumMap;

import com.badlogic.gdx.graphics.Color;
import com.strongjoshua.console.LogLevel;

public class LogEntry {
	public static float length = 7;
	
	public String log;
	public LogLevel level;
	public float time = length;
	
	public LogEntry(String log, LogLevel level) {
		this.log = log;
		this.level = level;
	}
	
	public void update(float delta) {
		time -= delta;
	}
	
	public boolean isOut() {
		return time < 0;
	}
	
	public float getTrans() {
		return Math.min(time, 1f);
	}
	
	public Color getColor() {
		Color color = MAP.get(level).cpy();
		color.a = getTrans();
		return color;
	}
	
	private static EnumMap<LogLevel, Color> MAP = new EnumMap<>(LogLevel.class);
	static {
		MAP.put(LogLevel.DEFAULT, Color.WHITE);
		MAP.put(LogLevel.ERROR, new Color(220f / 255f, 0.2f, 0.2f, 1));
		MAP.put(LogLevel.SUCCESS, new Color(0.2f, 220f / 255f, 0.2f, 1));
		MAP.put(LogLevel.COMMAND, Color.WHITE);
	}
}
