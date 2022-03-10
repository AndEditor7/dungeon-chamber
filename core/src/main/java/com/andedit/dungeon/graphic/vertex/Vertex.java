package com.andedit.dungeon.graphic.vertex;

import java.nio.ByteBuffer;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;

public interface Vertex extends Disposable {
	void setVertices(float[] array, int size, int offset);
	void bind();
	void unbind();
	void setDraw(int glDraw);
	int getDraw();

	static Vertex newVbo(VertContext context, int draw) {
		return Util.isGL30() ? new VAO(context, draw) : new VBO(context, draw);
	}
	
	static Vertex newVa(VertContext context) {
		return newVa(context, Util.BUFFER);
	}
	
	static Vertex newVa(VertContext context, ByteBuffer buffer) {
		return Util.isGL30() ? new VAO(context, GL20.GL_DYNAMIC_DRAW) : new VA(context, buffer);
	}
}
