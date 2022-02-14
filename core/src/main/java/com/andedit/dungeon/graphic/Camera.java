package com.andedit.dungeon.graphic;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;

/** A {@link PerspectiveCamera} with yaw. */
public final class Camera extends PerspectiveCamera 
{
	private static final Quaternion quat = new Quaternion();
	
	/** A yaw of left and right. */
	public float yaw;
	
	{
		fieldOfView = 70;
	}
	
	public void updateRotation() {
		yaw = MathUtils.clamp(yaw%360f < 0f ? (yaw%360f)+360f : yaw%360f, 0f, 360f);
				
		//reset quaternion and then set its rotation.
		quat.setEulerAngles(0, 0, yaw);
		
		//set camera angle back to zero and rotate it.
		direction.set(0f, 1f, 0f);
		up.set(0f, 0f, 1f);
		rotate(quat);
	}
}
