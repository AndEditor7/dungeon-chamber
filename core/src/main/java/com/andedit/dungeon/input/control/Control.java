package com.andedit.dungeon.input.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Null;

/** Game's controller interface. */
public interface Control  {
	
	Vector2 getMove();
	/** Get look yaw delta. */
	float getLookYaw();
	
	@Null
	default InputProcessor getInput() {
		return null;
	};
	
	@Null
	default ControllerListener getController() {
		return null;
	};
	
	boolean isUse();
	
	/** Resets everything. */
	void reset();
	
	/** Resets just pressed input. */
	void clear();
}
