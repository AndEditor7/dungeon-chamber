package com.andedit.dungeon.graphic.vertex;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import com.badlogic.gdx.utils.BufferUtils;

public class VA implements Vertex {
	final VertContext context;
	final ByteBuffer buffer;
	
	public VA(VertContext context, ByteBuffer buffer) {
		this.context = context;
		this.buffer = buffer;
	}
	
	@Override
	public void setVertices(float[] array, int size, int offset) {
		BufferUtils.copy(array, buffer, size, offset);
	}

	@Override
	public void bind() {
		((Buffer)buffer).clear();
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
