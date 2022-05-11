package com.andedit.dungeon.graphic;

import com.andedit.dungeon.graphic.SubDivider.Vert;
import com.andedit.dungeon.graphic.vertex.Vertex;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;

// v3-----v2
// |       |
// |       |
// v4-----v1
public class MeshBuilder {
	private final FloatArray array = new FloatArray(512);
	TextureRegion region;
	private float color;
	private float normal;
	public final SubDivider divider = new SubDivider(this);
	
	public void setRegion(TextureRegion region) {
		this.region = region;
	}
	
	public void setColor(float color) {
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color.toFloatBits();
	}
	
	public void setNormal(float normal) {
		this.normal = normal;
	}
	
	public void setNormal(float x, float y, float z) {
		this.normal = Util.packNorm(x, y, z);
	}
	
	public void vert1(float x, float y, float z) {
		array.add(x, y, z, color);
		array.add(normal, region.getU2(), region.getV2());
	}
	
	public void vert2(float x, float y, float z) {
		array.add(x, y, z, color);
		array.add(normal, region.getU2(), region.getV());
	}
	
	public void vert3(float x, float y, float z) {
		array.add(x, y, z, color);
		array.add(normal, region.getU(), region.getV());
	}
	
	public void vert4(float x, float y, float z) {
		array.add(x, y, z, color);
		array.add(normal, region.getU(), region.getV2());
	}
	
	void vertex(Vert vert) {
		vertex(vert.x, vert.y, vert.z, vert.u, vert.v);
	}
	
	public void vertex(float x, float y, float z, float u, float v) {
		array.add(x, y, z, color);
		array.add(normal, u, v);
	}
	
	public void vertex(float x, float y, float z, float c, float u, float v) {
		array.add(x, y, z);
		array.add(c, normal, u, v);
	}
	
	public void draw(Camera camera, float x, float y, float z) {
		Vector3 up = camera.upward;
		Vector3 dw = camera.down;
		Vector3 dir = camera.direction;
		normal = Util.packNorm(-dir.x, -dir.y, -dir.z);
		vert1(x+dw.x, y+dw.y, z+dw.z);
		vert2(x+up.x, y+up.y, z+up.z);
		vert3(x-dw.x, y-dw.y, z-dw.z);
		vert4(x-up.x, y-up.y, z-up.z);
	}
	
	public int getSize() {
		return array.size;
	}
	
	public float[] getArray() {
		return array.items;
	}
	
	public int build(Vertex vertex) {
		int size = array.size;
		vertex.setVertices(array.items, size, 0);
		array.clear();
		return size;
	}
}
