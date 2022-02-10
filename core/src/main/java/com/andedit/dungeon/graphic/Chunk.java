package com.andedit.dungeon.graphic;

import static com.badlogic.gdx.Gdx.gl;

import com.andedit.dungeon.graphic.vertex.Vertex;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;

public class Chunk implements Disposable {
	
	private final Vertex vertex = Vertex.newVbo(null, GL20.GL_STREAM_DRAW);
	private int count;
	
	public final int xPos, zPos;
	
	public Chunk(int xPos, int zPos) {
		this.xPos = xPos;
		this.zPos = zPos;
	}
	
	public void setVertices(FloatArray array) {
		vertex.setVertices(array.items, array.size, 0);
	}
	
	public void render() {
		if (count == 0) return;
		vertex.bind();
		gl.glDrawElements(GL20.GL_TRIANGLES, count, GL20.GL_UNSIGNED_SHORT, 0);
		vertex.unbind();
	}

	@Override
	public void dispose() {
		vertex.dispose();
	}
}
