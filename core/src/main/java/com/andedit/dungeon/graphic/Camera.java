package com.andedit.dungeon.graphic;

import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Plane.PlaneSide;

/** A {@link PerspectiveCamera} with extra stuff. */
public final class Camera extends PerspectiveCamera 
{
	/** A yaw of left and right. */
	public float yaw;
	public final Vector3 right = new Vector3();
	public final Vector3 upward = new Vector3();
	public final Vector3 down = new Vector3();
	
	{
		fieldOfView = 70;
	}
	
	/** Frustum point. */
	public boolean frust(float x, float y, float z) {
		final Plane[] planes = getPlanes();
		for (int i = 1; i < planes.length; i++) {
			PlaneSide result = planes[i].testPoint(x, y, z);
			if (result == PlaneSide.Back) return false;
		}
		return true;
	}
	
	/** Frustum sphere. */
	public boolean frust(float x, float y, float z, float radius) {
		final Plane[] planes = getPlanes();
		for (int i = 1; i < 6; i++) {
			final Plane plane = planes[i];
			final Vector3 normal = plane.normal;
			if (normal.dot(x, y, z) < (-radius - plane.d)) {
				return false;
			}
		}
		return true;
	}
	
	/** Frustum bounding box. */
	public boolean frust(float x, float y, float z, float w, float l, float h) {
		final Plane[] planes = getPlanes();
		final int s = planes.length;
		w *= 0.5f;
		l *= 0.5f;
		h *= 0.5f;
		x += w;
		y += l;
		z += h;

		for (int i = 1; i < s; i++) {
			final Plane plane = planes[i];
			final Vector3 normal = plane.normal;
			final float dist = normal.dot(x, y, z) + plane.d;
			
			final float radius = 
			w * Math.abs(normal.x) +
			l * Math.abs(normal.y) +
			h * Math.abs(normal.z);

			if (dist < radius && dist < -radius) {
				return false;
			}
		}
		return true;
	}
	
	public Plane[] getPlanes() {
		return frustum.planes;
	}
	
	public void updateRotation() {
		yaw = Util.modAngle(yaw);
		
		direction.x = MathUtils.cosDeg(yaw+90f);
		direction.y = MathUtils.sinDeg(yaw+90f);
		direction.z = 0;
		up.set(0, 0, 1);
		
		right.set(direction).crs(up);
		upward.set(right).add(up).scl(0.5f);
		down.set(right).sub(up).scl(0.5f);
	}
}
