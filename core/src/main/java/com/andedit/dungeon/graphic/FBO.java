package com.andedit.dungeon.graphic;

import static com.andedit.dungeon.Main.main;
import static com.badlogic.gdx.Gdx.gl;

import com.andedit.dungeon.graphic.FBO.ColorRes;
import com.andedit.dungeon.graphic.vertex.VertContext;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.andedit.dungeon.input.PixelPerfectViewport;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.GLFrameBuffer.FrameBufferBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FBO implements Disposable {
	public static final int SCALE = 1;
	public static final int WIDTH  = 320*SCALE; // 320
	public static final int HEIGHT = 240*SCALE; // 240
	
	public final Viewport view = new PixelPerfectViewport(WIDTH, HEIGHT);
	
	private FrameBuffer frame;
	private ColorRes colorRes = Util.isDesktop() ? ColorRes.RES8 : ColorRes.RES5;
	
	private final Vertex quad;
	private final ShaderProgram shader;
	private final TexBinder binder = new TexBinder();
	
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
		
		build();
		
		shader.bind();
		shader.setUniformf("size", WIDTH, HEIGHT);
		shader.setUniformi("tex", binder.unit);
		gl.glUseProgram(0);
		gl.glActiveTexture(GL20.GL_TEXTURE0);
		resize(Util.getW(), Util.getH());
	}
	
	public void setColorRes(ColorRes res) {
		if (colorRes == res) {
			return;
		}
		colorRes = res;
		build();
	}
	
	public void build() {
		if (frame != null) {
			frame.dispose();
		}
		
		FrameBufferBuilder builder = new FrameBufferBuilder(WIDTH, HEIGHT);
		builder.addColorTextureAttachment(GL20.GL_RGBA, GL20.GL_RGBA, colorRes.glType);
		builder.addBasicDepthRenderBuffer();
		frame = builder.build();
		binder.bind(frame.getColorBufferTexture());
		frame.getColorBufferTexture().unsafeSetFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		binder.deactive();
	}
	
	public void begin() {
		frame.begin();
		Util.glClear();
		
		if (colorRes.canDither())
		gl.glEnable(GL20.GL_DITHER);
	}
	
	public void end() {
		frame.end();
		
		if (colorRes.canDither())
		gl.glDisable(GL20.GL_DITHER);
	}
	
	public void draw() {
		QuadIndexBuffer.preBind();
		view.apply();
		shader.bind();
		shader.setUniformMatrix("mat", view.getCamera().combined);
		quad.bind();
		gl.glDrawElements(GL20.GL_TRIANGLES, 6, GL20.GL_UNSIGNED_SHORT, 0);
		quad.unbind();
		gl.glUseProgram(0);
	}
	
	public void resize(int width, int height) {
		view.update(width, height, true);
	}
	
	@Override
	public void dispose() {
		frame.dispose();
		shader.dispose();
		quad.dispose();
		binder.dispose();
	}
	
	public static enum ColorRes {
		RES4(GL20.GL_UNSIGNED_SHORT_4_4_4_4),
		RES5(GL20.GL_UNSIGNED_SHORT_5_5_5_1),
		RES8(GL20.GL_UNSIGNED_BYTE);
		
		private static final ColorRes[] VALUES = values();
		
		public final int glType;
		
		ColorRes(int glType) {
			this.glType = glType;
		}
		
		public boolean canDither() {
			return this != RES8;
		}
		
		public ColorRes toEnum(int ordinal) {
			return VALUES[ordinal];
		}
		
		public static ColorRes getColorRes(int res) {
			switch (res) {
			case 4: return ColorRes.RES4;
			case 5: return ColorRes.RES5;
			case 8: return ColorRes.RES8;
			default: return null;
			}
		}
	}
}
