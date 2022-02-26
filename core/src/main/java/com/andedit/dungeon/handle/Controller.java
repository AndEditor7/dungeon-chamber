package com.andedit.dungeon.handle;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/** Game's controller interface. */
public interface Controller  {
	
	Vector2 getMove();
	/** Get look yaw delta */
	float getLookYaw();
	
	InputProcessor getProcessor();
	
	void reset();
}
