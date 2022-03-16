package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.graphic.Camera;
import com.andedit.dungeon.graphic.FBO;
import com.andedit.dungeon.input.control.Control;
import com.andedit.dungeon.input.control.ControlManager;
import com.andedit.dungeon.input.control.NothingControl;
import com.andedit.dungeon.level.Level;
import com.andedit.dungeon.level.Renderer;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.ui.GameUI;
import com.andedit.dungeon.ui.OptionUI;
import com.andedit.dungeon.ui.util.UI;
import com.andedit.dungeon.ui.util.UIManager;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;

public class TheGame extends ScreenAdapter {
	
	public final UIManager manager = new UIManager();
	public final Camera camera = new Camera();
	
	private final Level level;
	private final Renderer render = new Renderer();
	private final Player player = new Player(camera, this);
	private final ControlManager controls = new ControlManager();
	private boolean dispose;
	
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
		main.controls.addProcessor(manager.control);
		controls.init();
	}
	
	private static final float STEP = 0.017f;
	private float time;
	
	@Override
	public void render(float delta) {
		controls.update();
		camera.yaw += getController().getLookYaw() * 0.5f;
		manager.update();
		
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
	}
	
	@Override
	public void dispose() {
		if (dispose) return;
		render.dispose();
		level.dispose();
		controls.dispose();
		dispose = true;
	}
	
	public Control getController() {
		return main.inputLock ? NothingControl.INSTANCE : controls.current;
	}
	
	public <T extends UI> T setUI(Class<T> type) {
		controls.reset();
		return manager.setUI(type);
	}
}
