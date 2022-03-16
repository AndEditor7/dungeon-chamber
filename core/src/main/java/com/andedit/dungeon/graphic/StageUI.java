package com.andedit.dungeon.graphic;

import static com.andedit.dungeon.Main.main;
import static com.badlogic.gdx.math.MathUtils.floor;

import com.andedit.dungeon.Main;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class StageUI extends Stage implements ControllerListener {
	
	private final Viewport view;
	private final GridPoint2 pos = new GridPoint2();
	private final Vector2 move = new Vector2();
	private final Vector2 temp = new Vector2();
	private final Sprite cross = new Sprite();
	private boolean isPress;
	private boolean isController;
	
	public StageUI(FBO frame) {
		super(newView(), new FastBatch());
		getViewport().update(FBO.WIDTH, FBO.HEIGHT, true);
		view = frame.view;
	}
	
	private static Viewport newView() {
		FixedViewport view = new FixedViewport();
		view.setUnitsPerPixel(1 / (float)FBO.SCALE);
		return view;
	}
	
	public void setCrossTex(TextureRegion region) {
		cross.setRegion(region);
		cross.setSize(region.getRegionWidth(), region.getRegionHeight());
		cross.setOriginCenter();
	}
	
	private void getTouchPos(int screenX, int screenY) {
		Vector3 origin = view.getPickRay(screenX, screenY).origin;
		pos.set(floor(origin.x), FBO.HEIGHT - floor(origin.y));
	}
	
	private void getCrossPos() {
		float x = (cross.getX()+cross.getOriginX()) * FBO.SCALE;
		float y = (cross.getY()+cross.getOriginY()) * FBO.SCALE;
		pos.set(floor(x), FBO.HEIGHT-floor(y));
	}
	
	public void setCrossPos(int x, int y) {
		cross.setOriginBasedPosition(x+0.2f, y+0.2f);
	}
	
	public void setController(boolean isController) {
		this.isController = isController;
		if (!main.isCatched()) {
			Gdx.input.setCursorCatched(isController);
		}
	}
	
	public boolean isController() {
		return isController;
	}
	
	private boolean cantClick() {
		return Util.isDesktop() && main.isCatched();
	}
	
	@Override
	public void act(float delta) {
		if (!move.isZero(0.15f) && !main.isCatched()) {
			temp.set(move).scl(1.2f).clamp(0.1f, 1).scl(delta * 170f);
			float xMove = MathUtils.clamp(cross.getX() + temp.x, -cross.getOriginX(), Main.WIDTH-cross.getOriginX());
			float yMove = MathUtils.clamp(cross.getY() + temp.y, -cross.getOriginY(), Main.HEIGHT-cross.getOriginY());
			cross.setPosition(xMove, yMove);
			getCrossPos();
			if (isPress) {
				super.touchDragged(pos.x, pos.y, 0);
			} else {
				super.mouseMoved(pos.x, pos.y);
			}
			setController(true);
		}
		super.act(delta);
	}
	
	@Override
	public void draw () {
		Camera camera = getViewport().getCamera();
		camera.update();

		if (!getRoot().isVisible()) return;

		Batch batch = getBatch();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		getRoot().draw(batch, 1);
		if (isController && !main.isCatched()) {
			if (cross.getTexture() != null)
				cross.draw(batch);
		}
		batch.end();
	}
	
	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		setController(true);
		if (main.isCatched()) return false;
		if (controller.getMapping().buttonA == buttonCode) {
			getCrossPos();
			isPress = true;
			return super.touchDown(pos.x, pos.y, 0, Buttons.LEFT);
		}
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		setController(true);
		if (main.isCatched()) return false;
		if (controller.getMapping().buttonA == buttonCode) {
			getCrossPos();
			isPress = false;
			return super.touchUp(pos.x, pos.y, 0, Buttons.LEFT);
		}
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		ControllerMapping map = controller.getMapping();
		if (map.axisLeftX == axisCode) {
			move.x = value;
		}
		if (map.axisLeftY == axisCode) {
			move.y = -value;
		}
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		setController(false);
		if (cantClick()) return false;
		getTouchPos(screenX, screenY);
		return super.touchDown(pos.x, pos.y, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		setController(false);
		if (cantClick()) return false;
		getTouchPos(screenX, screenY);
		return super.touchUp(pos.x, pos.y, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		setController(false);
		if (cantClick()) return false;
		getTouchPos(screenX, screenY);
		return super.touchDragged(pos.x, pos.y, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		setController(false);
		if (cantClick()) return false;
		getTouchPos(screenX, screenY);
		return super.mouseMoved(pos.x, pos.y);
	}

	@Override
	protected boolean isInsideViewport(int screenX, int screenY) {
		return screenX >= 0 && screenY >= 0 && screenX <= Main.WIDTH && screenY <= Main.HEIGHT;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getBatch().dispose();
	}

	@Override
	public void connected(Controller controller) {
	}

	@Override
	public void disconnected(Controller controller) {
		setController(false);
		isPress = false;
		move.setZero();
	}
}
