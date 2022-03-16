package com.andedit.dungeon;

import static com.andedit.dungeon.Main.main;

import com.andedit.dungeon.input.control.Inputs;
import com.andedit.dungeon.util.AssetManager;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
			main.stage.setCrossTex(new TextureRegion(Assets.GUI, 119, 119, 9, 9));
			main.setMenu();
		}
	}
}
