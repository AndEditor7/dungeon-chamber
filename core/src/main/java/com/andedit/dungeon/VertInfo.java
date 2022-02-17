package com.andedit.dungeon;

import com.badlogic.gdx.graphics.VertexAttributes;

public class VertInfo {
	private static int byteSize;
	private static int floatSize;
	
	public static int getByteSize() {
		return byteSize;
	}
	
	public static int getFloatSize() {
		return floatSize;
	}
	
	static void init(VertexAttributes attributes) {
		byteSize = attributes.vertexSize;
		floatSize = byteSize / Float.BYTES;
	}
}
