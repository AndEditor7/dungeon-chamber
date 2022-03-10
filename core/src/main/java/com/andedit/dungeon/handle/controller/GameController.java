package com.andedit.dungeon.handle.controller;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;

public class GameController extends ControllerAdapter implements com.andedit.dungeon.handle.controller.Controller {
	
	private final Vector2 temp = new Vector2();
	private final Vector2 move = new Vector2();
	private final IntArray buttPressed = new IntArray(false, 20);
	private final IntArray justPressed = new IntArray(false, 20);
	private float yawDelta;

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		if (!buttPressed.contains(buttonIndex)) {
			buttPressed.add(buttonIndex);
		}
		if (!justPressed.contains(buttonIndex)) {
			justPressed.add(buttonIndex);
		}
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		buttPressed.removeValue(buttonIndex);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		ControllerMapping map = controller.getMapping();
		if (map.axisLeftX == axisIndex) {
			move.x = value;
		}
		if (map.axisLeftY == axisIndex) {
			move.y = -value;
		}
		if (map.axisRightX == axisIndex) {
			yawDelta = -value;
		}
		return false;
	}

	@Override
	public void disconnected(Controller controller) {
		reset();
	}

	@Override
	public Vector2 getMove() {
		if (move.isZero(0.15f)) {
			return temp.setZero();
		}
		temp.set(move).scl(1.2f).clamp(0.1f, 1);
		return temp;
	}

	@Override
	public float getLookYaw() {
		float delta = yawDelta;
		if (MathUtils.isZero(delta, 0.15f)) {
			yawDelta = 0;
			return 0;
		}
		delta = MathUtils.clamp(delta*1.4f, -1f, 1f);
		return delta * 5f;
	}

	@Override
	public ControllerListener getController() {
		return this;
	}

	@Override
	public void reset() {
		move.setZero();
		buttPressed.clear();
		clear();
	}

	@Override
	public void clear() {
		justPressed.clear();
	}

	@Override
	public boolean isUse() {
		return buttPressed.notEmpty() || !getMove().isZero() || getLookYaw() != 0;
	}
}
