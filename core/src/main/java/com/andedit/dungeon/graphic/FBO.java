package com.andedit.dungeon.graphic;

import com.andedit.dungeon.graphic.vertex.VertContext;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.andedit.dungeon.util.PixelPerfectViewport;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FBO implements Disposable {
	public static final int WIDTH =  320*2; // 320
	public static final int HEIGHT = 240*2; // 240
	
	private final FrameBuffer frame = new FrameBuffer(Format.RGB888, WIDTH, HEIGHT, true);
	private final Vertex quad;
	private final ShaderProgram shader;
	private final Viewport view = new PixelPerfectViewport(WIDTH, HEIGHT);
	
	{
		shader = Util.newShader("shaders/frame");
		VertContext context = VertContext.of(shader, new VertexAttribute(Usage.Position, 2, "pos"));
		quad = Vertex.newVbo(context, GL20.GL_STATIC_DRAW);
		
		FloatArray array = new FloatArray(8);
		array.add(0, 0);
		array.add(0, 1);
		array.add(1, 1);
		array.add(1, 0);
		
		quad.setVertices(array.items, array.size, 0);
		
		frame.getColorBufferTexture().bind(1);
		frame.getColorBufferTexture().unsafeSetFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		shader.bind();
		shader.setUniformf("size", WIDTH, HEIGHT);
		shader.setUniformi("tex", 1);
		Gdx.gl.glUseProgram(0);
		Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
		
		resize(Util.getW(), Util.getH());
	}
	
	public void begin() {
		frame.begin();
		Util.glClear();
	}
	
	public void end() {
		frame.end();
	}
	
	public void draw() {
		QuadIndexBuffer.preBind();
		view.apply();
		shader.bind();
		shader.setUniformMatrix("mat", view.getCamera().combined);
		quad.bind();
		Gdx.gl.glDrawElements(GL20.GL_TRIANGLES, 6, GL20.GL_UNSIGNED_SHORT, 0);
		quad.unbind();
		Gdx.gl.glUseProgram(0);
	}
	
	public void resize(int width, int height) {
		view.update(width, height, true);
	}
	
	@Override
	public void dispose() {
		frame.dispose();
		shader.dispose();
		quad.dispose();
	}
}
