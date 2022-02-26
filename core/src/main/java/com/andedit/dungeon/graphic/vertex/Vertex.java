package com.andedit.dungeon.graphic.vertex;

import java.nio.ByteBuffer;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.utils.Disposable;

public interface Vertex extends Disposable {
	void setVertices(float[] array, int size, int offset);
	void bind();
	void unbind();

	static Vertex newVbo(VertContext context, int draw) {
		return new VBO(context, draw);
	}
	
	static Vertex newVa(VertContext context) {
		return newVa(context, Util.BUFFER);
	}
	
	static Vertex newVa(VertContext context, ByteBuffer buffer) {
		return new VA(context, buffer);
	}
}
