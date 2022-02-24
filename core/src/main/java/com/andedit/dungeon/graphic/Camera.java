package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/** A {@link PerspectiveCamera} with extra stuff. */
public final class Camera extends PerspectiveCamera 
{
	private static final Quaternion quat = new Quaternion();
	
	/** A yaw of left and right. */
	public float yaw;
	public final Vector3 right = new Vector3();
	public final Vector3 upward = new Vector3();
	public final Vector3 down = new Vector3();
	
	{
		fieldOfView = 70;
	}
	
	public boolean frust(float x, float y, float z, float w, float l, float h) {
		final Plane[] planes = frustum.planes;
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
	
	public void updateRotation() {
		yaw = MathUtils.clamp(yaw%360f < 0f ? (yaw%360f)+360f : yaw%360f, 0f, 360f);
				
		//reset quaternion and then set its rotation.
		quat.setEulerAngles(0, 0, yaw);
		
		//set camera angle back to zero and rotate it.
		direction.set(0, 1, 0);
		up.set(0, 0, 1);
		rotate(quat);
		
		right.set(direction).crs(up);
		upward.set(right).add(up).scl(0.5f);
		down.set(right).sub(up).scl(0.5f);
	}
}
