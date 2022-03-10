package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.handle.controller.Controller;
import com.andedit.dungeon.handle.controller.ControllerManager;
import com.andedit.dungeon.handle.controller.NothingController;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.level.Renderer;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.ui.GameUI;
import com.andedit.dungeon.ui.OptionUI;
import com.andedit.dungeon.ui.util.UI;
import com.andedit.dungeon.ui.util.UIManager;
import com.andedit.dungeon.util.RamTest;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TheGame extends ScreenAdapter {
	
	public final UIManager manager = new UIManager();
	public final Camera camera = new Camera();
	
	private final Level level;
	private final Renderer render = new Renderer();
	private final Player player = new Player(camera, this);
	private final ControllerManager controls = new ControllerManager();
	private boolean dispose;

	public final Array<RamTest> tests = new Array<>(512);
	
	public TheGame() {
		camera.viewportWidth  = FBO.WIDTH;
		camera.viewportHeight = FBO.HEIGHT;
		camera.near = 0.2f;
		camera.far = 10;
		
		manager.put(new GameUI(this));
		manager.put(new OptionUI(this));
		
		TileColors colors = new TileColors();
		colors.addColor(Tiles.FLOOR, 150, 150, 150);
		colors.addColor(Tiles.WALL,  150, 180, 215);
		level = new Level(Maps.get("test"), colors);
		player.setPos(level.getStarting() == null ? Vector2.Zero : level.getStarting());
		level.addEntity(player);
		render.setLevel(level);
	}
	
	@Override
	public void show() {
		manager.bind(main.stage);
		manager.setUI(GameUI.class);
		main.inputs.addProcessor(manager.input);
		//main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, Gdx.app::exit));
		controls.init(main.inputs);
		Util.setCatch(true);
	}
	
	private static final float STEP = 0.017f;
	private float time;
	
	@Override
	public void render(float delta) {
		controls.update();
		camera.yaw += getController().getLookYaw() * 0.5f;
		manager.update();

		/**
		if (Gdx.input.justTouched()) {
			tests.add(new RamTest());
		}
		tests.forEach(RamTest::update);
		*/
		
		time += delta;
		do {
			level.update();
			time -= STEP;
		} while (time >= STEP);
		time %= STEP;
		
		controls.clear();
		camera.updateRotation();
		camera.update();
		
		Util.glClear();
		render.render(camera);
	}
	
	@Override
	public void hide() {
		dispose();
		Gdx.input.setCursorCatched(false);
	}
	
	@Override
	public void dispose() {
		if (dispose) return;
		render.dispose();
		level.dispose();
		controls.dispose();
		dispose = true;
	}
	
	public Controller getController() {
		if (manager.isInputLock()) {
			return NothingController.INSTANCE;
		}
		return controls.current;
	}
	
	public void setUI(Class<? extends UI> type) {
		controls.reset();
		manager.setUI(type);
	}
}
