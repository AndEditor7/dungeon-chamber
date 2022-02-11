package com.andedit.dungeon.graphic;

import com.andedit.dungeon.graphic.vertex.Vertex;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.FloatArray;

// v3-----v2
// |       |
// |       |
// v4-----v1
public class MeshBuilder {
	private final FloatArray array = new FloatArray(512);
	private TextureRegion region;
	private float color;
	
	public void setRegion(TextureRegion region) {
		this.region = region;
	}
	
	public void setColor(Color color) {
		this.color = color.toFloatBits();
	}
	
	public void setColor(float r, float g, float b) {
		this.color = Color.toFloatBits(r, g, b, 1);
	}
	
	public void setColor(int r, int g, int b) {
		setColor(r/255f, g/255f, b/255f);
	}
	
	public void vert1(float x, float y, float z) {
		array.add(x, y, z);
		array.add(color, region.getU2(), region.getV2());
	}
	
	public void vert2(float x, float y, float z) {
		array.add(x, y, z);
		array.add(color, region.getU2(), region.getV());
	}
	
	public void vert3(float x, float y, float z) {
		array.add(x, y, z);
		array.add(color, region.getU(), region.getV());
	}
	
	public void vert4(float x, float y, float z) {
		array.add(x, y, z);
		array.add(color, region.getU(), region.getV2());
	}
	
	public void vertex(float x, float y, float z, float u, float v) {
		array.add(x, y, z);
		array.add(color, u, v);
	}
	
	public void vertex(float x, float y, float z, float c, float u, float v) {
		array.add(x, y, z);
		array.add(c, u, v);
	}
	
	public void build(Vertex vertex) {
		vertex.setVertices(array.items, array.size, 0);
	}
}
