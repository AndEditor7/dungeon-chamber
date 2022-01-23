package com.andedit.dungeon.graphic.vertex;

import java.nio.ByteBuffer;

import com.badlogic.gdx.utils.BufferUtils;

public class Va20 implements Vertex {
	final VertContext context;
	final ByteBuffer buffer;
	
	public Va20(VertContext context, ByteBuffer buffer) {
		this.context = context;
		this.buffer = buffer;
	}
	
	@Override
	public void setVertices(float[] array, int size, int offset) {
		BufferUtils.copy(array, buffer, size, offset);
	}

	@Override
	public void bind() {
		buffer.clear();
		context.setVertexAttributes(buffer);
	}

	@Override
	public void unbind() {
		context.unVertexAttributes();
	}

	@Override
	public void dispose() {
	}
}
