package com.andedit.dungeon.graphic;

import static com.badlogic.gdx.Gdx.gl;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.badlogic.gdx.graphics.GL20;

public class Chunk implements Vertex {
	
	private final Vertex vertex = Vertex.newVbo(Assets.CONTEXT, GL20.GL_STREAM_DRAW);
	private int count;
	
	public final int xPos, yPos;
	
	public Chunk(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void render() {
		if (count == 0) return;
		vertex.bind();
		gl.glDrawElements(GL20.GL_TRIANGLES, count, GL20.GL_UNSIGNED_SHORT, 0);
		vertex.unbind();
	}
	
	@Override
	public void setVertices(float[] array, int size, int offset) {
		vertex.setVertices(array, size, offset);
	}

	@Override
	public void bind() {
		vertex.bind();
	}

	@Override
	public void unbind() {
		vertex.unbind();
	}

	@Override
	public void dispose() {
		vertex.dispose();
	}
}
