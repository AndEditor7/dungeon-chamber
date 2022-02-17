package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Lights {
	private static final int LENTGH = 30;
	private static final int AMOUNT = 3;
	
	private final ShaderProgram shader;
	private final int[] locs = new int[LENTGH * AMOUNT];
	private int size;
	
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
	
	public void add(Vector2 pos, Vector3 color, float sizef) {
		if (size > LENTGH) {
			return;
		}
		
		int idx = size * AMOUNT;
		shader.setUniformf(locs[idx], pos);
		shader.setUniformf(locs[idx+1], color);
		shader.setUniformf(locs[idx+2], sizef);
		
		if (++size >= LENTGH) {
			size--;
		}
	}
	
	public void flush() {
		shader.setUniformi("size", size);
		size = 0;
	}
	
	/*
	public void bind(Iterable<Entity> entities) {
		int size = 0;
		sb.setLength(0);
		for (Entity entity : entities) {
			if (entity instanceof Light) {
				Light lit = (Light) entity;
				// lits[2].

				sb.append("lits[").append(size).append("].pos");
				shader.setUniformf(sb.toString(), lit.getPos());
				sb.setLength(0);
				
				Color color = lit.color;
				sb.append("lits[").append(size).append("].color");
				shader.setUniformf(sb.toString(), color.r, color.g, color.b);
				sb.setLength(0);
				
				sb.append("lits[").append(size).append("].size");
				shader.setUniformf(sb.toString(), lit.size);
				sb.setLength(0);
				
				if (++size >= 10) {
					size--;
					break; 
				}
			}
		}
		shader.setUniformi("size", size);
	} */
}
