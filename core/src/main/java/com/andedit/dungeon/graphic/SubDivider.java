package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class SubDivider {
	private final MeshBuilder consumer;
	private final Vector3 v1 = new Vector3();
	private final Vector3 v2 = new Vector3();
	private final Vector3 v3 = new Vector3();
	private final Vector3 v4 = new Vector3();
	private final Vector2 r1 = new Vector2();
	private final Vector2 r2 = new Vector2();
	private final Vector2 r3 = new Vector2();
	private final Vector2 r4 = new Vector2();
	private final Vert[] verts = new Vert[9];
	
	public SubDivider(MeshBuilder consumer) {
		this.consumer = consumer;
		for (int i = 0; i < verts.length; i++) {
			verts[i] = new Vert();
		}
	}
	
	public void vert1(float x, float y, float z) {
		v1.set(x, y, z);
		r1.set(reg().getU2(), reg().getV2());
	}
	
	public void vert2(float x, float y, float z) {
		v2.set(x, y, z);
		r2.set(reg().getU2(), reg().getV());
	}
	
	public void vert3(float x, float y, float z) {
		v3.set(x, y, z);
		r3.set(reg().getU(), reg().getV());
	}
	
	public void vert4(float x, float y, float z) {
		v4.set(x, y, z);
		r4.set(reg().getU(), reg().getV2());
	}
	
	private TextureRegion reg() {
		return consumer.region;
	}
	
	// v3-----v2
	// |       |
	// |       |
	// v4-----v1
	// 
	// p0-----p1------p2
	// |       |       |
	// |       |       |
	// p3-----p4------p5
	// |       |       |
	// |       |       |
	// p6-----p7------p8
	public void build() {
		verts[0].set(v3.x, v3.y, v3.z, r3.x, r3.y);
		verts[1].set((v3.x+v2.x)/2f, (v3.y+v2.y)/2f, (v3.z+v2.z)/2f, (r3.x+r2.x)/2f, (r3.y+r2.y)/2f);
		verts[2].set(v2.x, v2.y, v2.z, r2.x, r2.y);
		verts[3].set((v3.x+v4.x)/2f, (v3.y+v4.y)/2f, (v3.z+v4.z)/2f, (r3.x+r4.x)/2f, (r3.y+r4.y)/2f);
		verts[4].set((v3.x+v1.x)/2f, (v3.y+v1.y)/2f, (v3.z+v1.z)/2f, (r3.x+r1.x)/2f, (r3.y+r1.y)/2f);
		verts[5].set((v1.x+v2.x)/2f, (v1.y+v2.y)/2f, (v1.z+v2.z)/2f, (r1.x+r2.x)/2f, (r1.y+r2.y)/2f);
		verts[6].set(v4.x, v4.y, v4.z, r4.x, r4.y);
		verts[7].set((v4.x+v1.x)/2f, (v4.y+v1.y)/2f, (v4.z+v1.z)/2f, (r4.x+r1.x)/2f, (r4.y+r1.y)/2f);
		verts[8].set(v1.x, v1.y, v1.z, r1.x, r1.y);
		
		consumer.vertex(verts[8]);
		consumer.vertex(verts[5]);
		consumer.vertex(verts[4]);
		consumer.vertex(verts[7]);
		
		consumer.vertex(verts[5]);
		consumer.vertex(verts[2]);
		consumer.vertex(verts[1]);
		consumer.vertex(verts[4]);
		
		consumer.vertex(verts[7]);
		consumer.vertex(verts[4]);
		consumer.vertex(verts[3]);
		consumer.vertex(verts[6]);
		
		consumer.vertex(verts[4]);
		consumer.vertex(verts[1]);
		consumer.vertex(verts[0]);
		consumer.vertex(verts[3]);
	}
	
	// v3-----v2
	// |       |
	// |       |
	// v4-----v1
	// 
	// p0-----p1------p2
	// |       |       |
	// |       |       |
	// |       |       |
	// |       |       |
	// |       |       |
	// p6-----p7------p8
	public void buildV() {
		verts[0].set(v3.x, v3.y, v3.z, r3.x, r3.y);
		verts[1].set((v3.x+v2.x)/2f, (v3.y+v2.y)/2f, (v3.z+v2.z)/2f, (r3.x+r2.x)/2f, (r3.y+r2.y)/2f);
		verts[2].set(v2.x, v2.y, v2.z, r2.x, r2.y);
		verts[6].set(v4.x, v4.y, v4.z, r4.x, r4.y);
		verts[7].set((v4.x+v1.x)/2f, (v4.y+v1.y)/2f, (v4.z+v1.z)/2f, (r4.x+r1.x)/2f, (r4.y+r1.y)/2f);
		verts[8].set(v1.x, v1.y, v1.z, r1.x, r1.y);
		
		consumer.vertex(verts[8]);
		consumer.vertex(verts[2]);
		consumer.vertex(verts[1]);
		consumer.vertex(verts[7]);
		
		consumer.vertex(verts[7]);
		consumer.vertex(verts[1]);
		consumer.vertex(verts[0]);
		consumer.vertex(verts[6]);
	}
	
	static class Vert {
		float x, y, z, u, v;
		void set(float x, float y, float z, float u, float v) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.u = u;
			this.v = v;
		}
	}
}
