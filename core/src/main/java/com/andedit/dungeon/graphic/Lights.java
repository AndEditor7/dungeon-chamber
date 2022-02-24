package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Lights {
	private static final int LENTGH = 100;
	private static final int AMOUNT = 3;
	
	private final ShaderProgram shader;
	private final int[] locs = new int[LENTGH * AMOUNT];
	private int lits;
	
	public Lights(ShaderProgram shader) {
		this.shader = shader;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < LENTGH; i++) {
			int idx = i * AMOUNT;
			sb.append("lits[").append(i).append("].pos");
			locs[idx] = shader.getUniformLocation(sb.toString());
			sb.setLength(0);
			
			sb.append("lits[").append(i).append("].color");
			locs[idx+1] = shader.getUniformLocation(sb.toString());
			sb.setLength(0);
			
			sb.append("lits[").append(i).append("].size");
			locs[idx+2] = shader.getUniformLocation(sb.toString());
			sb.setLength(0);
		}
	}
	
	public void add(Vector2 pos, Color color, float size) {
		add(pos, color, size, 0.5f);
	}
	
	public void add(Vector2 pos, Color color, float size, float z) {
		add(pos, color, size, z, 1.0f);
	}
	
	public void add(Vector2 pos, Color color, float size, float z, float power) {
		if (lits > LENTGH) {
			return;
		}
		
		int idx = lits++ * AMOUNT;
		shader.setUniformf(locs[idx], pos.x, pos.y, z);
		shader.setUniformf(locs[idx+1], color.r * power, color.g * power, color.b * power);
		shader.setUniformf(locs[idx+2], size);
	}
	
	public void flush() {
		shader.setUniformi("size", lits);
		lits = 0;
	}
}
