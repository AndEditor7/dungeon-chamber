package com.andedit.dungeon.graphic;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.entity.Light;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Lights {
	private static final int LENTGH = 10;
	private static final int SIZE = 6;
	
	private final ShaderProgram shader;
	private final StringBuilder sb = new StringBuilder();
	
	public Lights(ShaderProgram shader) {
		this.shader = shader;
	}
	
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
	}
}
