package com.andedit.dungeon.level;

import java.util.function.BiConsumer;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;

class MapObjs {
	private static final ObjectMap<String, BiConsumer<Level, MapObject>> MAP = new ObjectMap<>();
	
	static {
		MAP.put("player", (level, obj) -> {
			level.starting = getPos(obj);
		});
	}
	
	static void handle(Level level, MapObjects objects) {
		for (MapObject obj : objects) {
			BiConsumer<Level, MapObject> consumer = MAP.get(obj.getName());
			if (consumer == null) {
				throw new RuntimeException("Object named \"" + obj.getName() + "\" was not found.");
			}
			consumer.accept(level, obj);
		}
	}
	
	private static Vector2 getPos(MapObject obj) {
		MapProperties props = obj.getProperties();
		float x = props.get("x", Float.class);
		float y = props.get("y", Float.class);
		return new Vector2(x/Level.SIZE, y/Level.SIZE);
	}
}
