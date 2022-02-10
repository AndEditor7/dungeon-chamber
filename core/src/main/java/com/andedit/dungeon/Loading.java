package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.handle.Inputs;
import com.andedit.dungeon.ui.util.AssetManager;
import com.badlogic.gdx.ScreenAdapter;

public class Loading extends ScreenAdapter {
	
	private final AssetManager asset;
	
	public Loading(AssetManager asset) {
		this.asset = asset;
		Assets.load(asset);
		Maps.load(asset);
	}
	
	@Override
	public void render(float delta) {
		if (asset.update(10)) {
			Assets.get(asset);
			Maps.get(asset);
			Inputs.clear();
			Statics.init();
			main.setMenu();
		}
	}
}
