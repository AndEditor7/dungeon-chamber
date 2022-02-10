package com.andedit.dungeon;

import com.andedit.dungeon.ui.util.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.ObjectMap;

public class Maps {
	private static final ObjectMap<String, TiledMap> MAPS = new ObjectMap<>();
	
	public static void load(AssetManager asset) {
		FileHandleResolver resolver = asset.getFileHandleResolver();
		asset.setLoader(TiledMap.class, new TmxMapLoader(resolver));
		
		load(asset, "test");
	}
	
	public static void get(AssetManager asset) {
		asset.getAll();
	}
	
	private static void load(AssetManager asset, String level) {
		asset.load("levels/" + level + ".tmx", TiledMap.class, t->MAPS.put(level, t));
	}
}
