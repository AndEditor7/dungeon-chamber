package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.handle.Controller;
import com.andedit.dungeon.handle.DesktopController;
import com.andedit.dungeon.handle.KeyListener;
import com.andedit.dungeon.handle.TouchController;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.level.Renderer;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.ui.GameUI;
import com.andedit.dungeon.ui.util.UIManager;
import com.andedit.dungeon.util.InputHolder;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;

public class TheGame extends ScreenAdapter {
	
	public final UIManager manager = new UIManager();
	public final Camera camera = new Camera();
	
	private final Level level;
	private final InputHolder holder = new InputHolder();
	private final Renderer render = new Renderer();
	private final Player player = new Player(camera, this);
	private Controller control;
	private boolean dispose;
	
	public TheGame() {
		camera.viewportWidth  = FBO.WIDTH;
		camera.viewportHeight = FBO.HEIGHT;
		camera.near = 0.15f;
		camera.far = 10;
		
		manager.put(new GameUI(this));
		
		TileColors colors = new TileColors();
		colors.addColor(Tiles.FLOOR, 150, 150, 150);
		colors.addColor(Tiles.WALL,  150, 180, 215);
		level = new Level(Maps.get("test"), colors);
		player.setPos(level.getStarting() == null ? Vector2.Zero : level.getStarting());
		level.addEntity(player);
		render.setLevel(level);
		
		control = Util.isDesktop() ? new DesktopController() : new TouchController();
		holder.set(control.getProcessor());
	}
	
	@Override
	public void show() {
		manager.bind(main.stage);
		manager.setUI(GameUI.class);
		
		main.inputs.addProcessor(new KeyListener(Keys.ESCAPE, Gdx.app::exit));
		main.inputs.addProcessor(holder);
		
		Gdx.input.setCursorCatched(true);
	}
	
	private static final float STEP = 0.017f;
	private float time;
	
	@Override
	public void render(float delta) {
		camera.yaw += control.getLookYaw() * 0.5f;
		manager.update();
		
		time += delta;
		do {
			level.update();
			time -= STEP;
		} while (time >= STEP);
		time %= STEP;
		
		camera.updateRotation();
		camera.update();
		
		Util.glClear();
		render.render(camera);
	}
	
	public Controller getController() {
		return control;
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
		dispose = true;
	}
}
