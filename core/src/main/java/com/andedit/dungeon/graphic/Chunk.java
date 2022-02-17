package com.andedit.dungeon.graphic;

import static com.badlogic.gdx.Gdx.gl;
import static com.andedit.dungeon.Assets.CONTEXT;

import com.andedit.dungeon.Assets;
import com.andedit.dungeon.VertInfo;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.tile.model.Models;
import com.andedit.dungeon.util.TilePos;
import com.badlogic.gdx.graphics.GL20;

public class Chunk implements Vertex {
	public static final int SIZE = 16;
	private static final TilePos POS = new TilePos();
	
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
	
	public Chunk build(MeshBuilder consumer, Level level, byte[][] tiles) {
		int xPos = this.xPos * SIZE;
		int yPos = this.yPos * SIZE;
		for (int x = 0; x < SIZE; x++)
		for (int y = 0; y < SIZE; y++) {
			POS.set(xPos+x, yPos+y);
			int id = tiles[xPos+x][yPos+y];
			Models.get(id).build(consumer, level, POS, Tiles.get(id));
		}
		
		int size = consumer.getSize();
		float[] array = consumer.getArray();
		for (int i = 0; i < size; i += VertInfo.getFloatSize()) {
			array[i+4] = PathTrace.trace(level, array[i], array[i+1]);
		}
		
		consumer.build(this);
		return this;
	}
	
	@Override
	public void setVertices(float[] array, int size, int offset) {
		vertex.setVertices(array, size, 0);
		count = (size / CONTEXT.getAttrs().vertexSize) * 6;
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
