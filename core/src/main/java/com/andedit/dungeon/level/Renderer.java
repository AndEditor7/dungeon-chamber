package com.andedit.dungeon.level;

import static com.andedit.dungeon.Assets.CONTEXT;
import static com.andedit.dungeon.Assets.SHADER;
import static com.andedit.dungeon.Assets.TEXS;
import static com.badlogic.gdx.Gdx.gl;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.Chunk;
import com.andedit.dungeon.graphic.Lights;
import com.andedit.dungeon.graphic.MeshBuilder;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.andedit.dungeon.tile.entity.TileEntity;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
	private static final float DST = 20 * 20;
	private final MeshBuilder consumer = new MeshBuilder();
	private final Array<Chunk> chunks = new Array<>(false, 64);
	private final Vertex mesh = Vertex.newVa(CONTEXT);
	private final Lights lits = new Lights(SHADER);
	private Texture texture;
	private Level level;
	
	public void setLevel(Level level) {
		this.level = level;
		if (chunks.notEmpty()) {
			chunks.forEach(Chunk::dispose);
			chunks.clear();
		}
		
		for (int x = 0; x < level.xSize / Chunk.SIZE; x++)
		for (int y = 0; y < level.ySize / Chunk.SIZE; y++) {
			chunks.add(new Chunk(x, y).build(consumer, level, level.tiles));
		}
		
		if (texture != null) {
			texture.dispose();
		}
		texture = new Texture(level.pixmap);
		texture.bind(2);
		texture	.unsafeSetFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		gl.glActiveTexture(GL20.GL_TEXTURE0);
	}
	
	public void render(Camera camera) {
		gl.glEnable(GL20.GL_CULL_FACE);
		QuadIndexBuffer.preBind();
		TEXS.bind();
		SHADER.bind();
		SHADER.setUniformMatrix("mat", camera.combined);
		SHADER.setUniformf("mapSize", level.xSize, level.ySize);
		SHADER.setUniformi("map", 2);
		
		lits.setCamera(camera);
		for (Entity entity : level.getEntities()) {
			entity.addLights(lits);
		}
		lits.flush();
		
		for (Chunk chunk : chunks) {
			chunk.render(camera);
		}
		for (TileEntity entity : level.getTileEntities()) {
			if (entity.dst(camera) < DST)
			entity.render(camera, consumer);
		}
		for (Entity entity : level.getEntities()) {
			if (entity.dst(camera) < DST)
			entity.render(camera, consumer);
		}
		
		mesh.bind();
		int size = consumer.build(mesh);
		gl.glDrawElements(GL20.GL_TRIANGLES, (size / CONTEXT.getAttrs().vertexSize) * 6, GL20.GL_UNSIGNED_SHORT, 0);
		mesh.unbind();
		
		gl.glUseProgram(0);
		gl.glDisable(GL20.GL_CULL_FACE);
	}

	@Override
	public void dispose() {
		chunks.forEach(Chunk::dispose);
		chunks.clear();
		mesh.dispose();
		texture.dispose();
	}
}
