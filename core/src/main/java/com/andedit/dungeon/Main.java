package com.andedit.dungeon;

import com.andedit.dungeon.graphic.FastBatch;
import com.andedit.dungeon.graphic.QuadIndexBuffer;
import com.andedit.dungeon.handle.EscKey;
import com.andedit.dungeon.handle.Inputs;
import com.andedit.dungeon.util.Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Base {
	public static final Main main = new Main();
	
	public FastBatch batch;
	public AssetManager asset;
	public TheMenu menu;

	@Override
	public void create() {
		QuadIndexBuffer.init();
		stage = new Stage(view = new ScreenViewport(), batch = new FastBatch());
		Gdx.input.setInputProcessor(new InputMultiplexer(new EscKey(this::back), stage, inputs, Inputs.input));
		Assets.load(asset = new AssetManager(new InternalFileHandleResolver(), false));
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
	}
	
	private boolean exit;

	@Override
	public void render() {
		if (exit) {
			super.render();
			return;
		}
		
		if (asset.update(10)) {
			exit = true;
			Assets.get(asset);
			Inputs.clear();
			Statics.init();
			setScreen(menu = new TheMenu());
		}
		
		// loading screen
		
		Util.glClear();
	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		batch.dispose();
		asset.dispose();
		Statics.dispose();
		QuadIndexBuffer.dispose();
	}
}