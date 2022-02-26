package com.andedit.dungeon.graphic.vertex;

import static com.badlogic.gdx.Gdx.gl;
import static com.andedit.dungeon.util.Util.BUFFER;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

public class VBO implements Vertex {
	
	public int glDraw;

	private int handle;
	private boolean isBound;
	private final VertContext context;

	public VBO(VertContext context, int glDraw) {
		this.glDraw = glDraw;
		this.context = context;

		handle = gl.glGenBuffer();
	}

	@Override
	public void setVertices(float[] array, int size, int offset) {
		BufferUtils.copy(array, BUFFER, size, offset);
		if (isBound) {
			gl.glBufferData(GL20.GL_ARRAY_BUFFER, BUFFER.remaining(), BUFFER, glDraw);
		} else {
			gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, handle);
			gl.glBufferData(GL20.GL_ARRAY_BUFFER, BUFFER.remaining(), BUFFER, glDraw);
			gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
		}
	}

	@Override
	public void bind() {
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, handle);
		context.setVertexAttributes(null);
		isBound = true;
	}

	@Override
	public void unbind() {
		context.unVertexAttributes();
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
		isBound = false;
	}
	
	@Override
	public void dispose() {
		gl.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
		gl.glDeleteBuffer(handle);
	}
}
