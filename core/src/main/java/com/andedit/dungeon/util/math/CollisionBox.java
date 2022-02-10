package com.andedit.dungeon.util.math;

import static java.lang.Math.min;
import static java.lang.Math.max;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Pool;

public class CollisionBox {
	public static final Pool<CollisionBox> POOL = new Pool<CollisionBox>() {
		protected CollisionBox newObject() {
			return new CollisionBox();
		}
	};

	public float xMin, yMin;
	public float xMax, yMax;

	public void expand(float x, float y, float z, CollisionBox out) {
		out.set(min(xMin, xMin+x), min(yMin, yMin+z), max(xMax, xMax+x), max(yMax, yMax+z));
	}
	
	public void grow(float x, float y, float z, CollisionBox out) {
		out.set(xMin-x, yMin-z, xMax+x, yMax+z);
	}
	
	public CollisionBox set(CollisionBox box) {
		return set(box.xMin, box.yMin, box.xMax, box.yMax);
	}

	public CollisionBox move(float x, float y, float z) {
		return set(xMin+x, yMin+z, xMax+x, yMax+z);
	}

	public CollisionBox set(float xMin, float zMin, float xMax, float zMax) {
		this.xMin = xMin;
		this.yMin = zMin;
		this.xMax = xMax;
		this.yMax = zMax;
		return this;
	}

	public float collideX(CollisionBox box, float x) {
		if (box.yMax <= yMin || box.yMin >= yMax) {
			return x;
		}
		return box.xMax<=xMin ? min(xMin-box.xMax,x) : box.xMin>=xMax ? max(xMax-box.xMin,x) : x;
	}

	public float collideZ(CollisionBox box, float y) {
		if (box.xMax <= xMin || box.xMin >= xMax) {
			return y;
		}
		return box.yMax<=yMin ? min(yMin-box.yMax,y) : box.yMin>=yMax ? max(yMax-box.yMin,y) : y;
	}
	
	public boolean intersects(CollisionBox box) {
		return xMin < box.xMax && xMax > box.xMin && yMin < box.yMax && yMax > box.yMin;
	}
	
	@Override
	public CollisionBox clone() {
		return new CollisionBox().set(xMin, yMin, xMax, yMax);
	}
	
	public boolean intersect(final Ray ray) {
		final Vector3 direction = ray.direction;
		final Vector3 origin = ray.origin;
		final float divX = 1f / direction.x;
		final float divZ = 1f / direction.z;
		float t;
		
		float minx = (xMin - origin.x) * divX;
		float maxx = (xMax - origin.x) * divX;
		if (minx > maxx) {
			t = minx;
			minx = maxx;
			maxx = t;
		}

		float minz = (yMin - origin.z) * divZ;
		float maxz = (yMax - origin.z) * divZ;
		if (minz > maxz) {
			t = minz;
			minz = maxz;
			maxz = t;
		}

		final float min = max(minx, minz);
		final float max = min(maxx, maxz);

		return max >= 0f && max >= min;
	}
}
