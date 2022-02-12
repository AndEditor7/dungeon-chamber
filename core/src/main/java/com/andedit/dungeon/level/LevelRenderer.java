package com.andedit.dungeon.level;

import static com.andedit.dungeon.Assets.SHADER;
import static com.andedit.dungeon.Assets.TILES;

import com.andedit.dungeon.graphic.Chunk;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class LevelRenderer implements Disposable {
	private final MeshBuilder consumer = new MeshBuilder();
	private final Array<Chunk> chunks = new Array<>(false, 64);
	private Level level;
	
	public void setLevel(Level level) {
		this.level = level;
		if (chunks.notEmpty()) {
			chunks.forEach(Chunk::dispose);
			chunks.clear();
		}
		
		System.out.println(level.xSize);
		
		for (int x = 0; x < level.xSize / Chunk.SIZE; x++)
		for (int y = 0; y < level.ySize / Chunk.SIZE; y++) {
			chunks.add(new Chunk(x, y).build(consumer, level, level.tiles));
		}
	}
	
	public void render(Camera camera) {
		QuadIndexBuffer.preBind();
		TILES.bind();
		SHADER.bind();
		SHADER.setUniformMatrix("mat", camera.combined);
		chunks.forEach(Chunk::render);
		Gdx.gl.glUseProgram(0);
		
		
	}

	@Override
	public void dispose() {
		chunks.forEach(Chunk::dispose);
		chunks.clear();
	}
}
