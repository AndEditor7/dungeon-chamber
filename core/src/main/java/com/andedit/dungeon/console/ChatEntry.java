package com.andedit.dungeon.console;

import java.util.EnumMap;

import com.andedit.console.log.LogEntry;
import com.andedit.console.log.LogStatus;
import com.badlogic.gdx.graphics.Color;

public class ChatEntry {
	public static float length = 7;
	
	public final LogEntry log;
	
	private float time = length;
	
	public ChatEntry(LogEntry log) {
		this.log = log;
	}
	
	@Override
	public String toString() {
		return "> " + log;
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
		Color color = MAP.get(log.status).cpy();
		color.a = getTrans();
		return color;
	}
	
	private static EnumMap<LogStatus, Color> MAP = new EnumMap<>(LogStatus.class);
	static {
		MAP.put(LogStatus.DEFAULT, Color.WHITE);
		MAP.put(LogStatus.ERROR, new Color(220f / 255f, 0.2f, 0.2f, 1));
		MAP.put(LogStatus.SUCCESS, new Color(0.2f, 220f / 255f, 0.2f, 1));
	}
}
