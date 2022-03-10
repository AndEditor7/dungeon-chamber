package com.andedit.dungeon.graphic.vertex;

import static com.andedit.dungeon.util.Util.BUFFER;
import static com.badlogic.gdx.Gdx.gl;
import static com.badlogic.gdx.Gdx.gl30;

import java.nio.Buffer;
import java.nio.IntBuffer;

import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

/** Vertex Buffer Object with Vertex Array Object (VBO with VAO) */
public class VAO implements Vertex {
	private static final IntBuffer intBuf = BufferUtils.newIntBuffer(1);

	private int glDraw;
	private int handle, vao;
	private boolean isBound;
	
	VAO(VertContext context, int glDraw) {
		handle = gl30.glGenBuffer();
		this.glDraw = glDraw;
		
		((Buffer)intBuf).clear();
		gl30.glGenVertexArrays(1, intBuf);
		vao = intBuf.get();
		
		gl30.glBindVertexArray(vao);
		gl30.glBindBuffer(GL20.GL_ARRAY_BUFFER, handle);
		QuadIndexBuffer.bind();
		context.setVertexAttributes(null);
		gl30.glBindVertexArray(0);
	}
	
	@Override
	public void setVertices(float[] array, int size, int offset) {
		BufferUtils.copy(array, BUFFER, size, offset);
		if (!isBound) {
			gl30.glBindVertexArray(vao);
		}
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, handle);
		gl.glBufferData(GL20.GL_ARRAY_BUFFER, BUFFER.remaining(), BUFFER, glDraw);
		if (!isBound) {
			gl30.glBindVertexArray(0);
		}
	}

	@Override
	public void bind() {
		gl30.glBindVertexArray(vao);
		isBound = true;
	}

	@Override
	public void unbind() {
		gl30.glBindVertexArray(0);
		isBound = false;
	}
	
	@Override
	public void setDraw(int glDraw) {
		this.glDraw = glDraw;
	}

	@Override
	public int getDraw() {
		return glDraw;
	}

	@Override
	public void dispose() {
		((Buffer)intBuf).clear();
		intBuf.put(vao);
		((Buffer)intBuf).flip();
		gl30.glDeleteVertexArrays(1, intBuf);
		gl30.glDeleteBuffer(handle);
	}
}
