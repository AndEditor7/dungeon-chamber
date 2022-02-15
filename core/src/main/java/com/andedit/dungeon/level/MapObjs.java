package com.andedit.dungeon.level;

import java.util.function.BiConsumer;

import com.andedit.dungeon.entity.Goblin;
import com.andedit.dungeon.entity.Light;
import com.andedit.dungeon.ui.util.ObjHandler;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.utils.ObjectMap;

class MapObjs {
	private static final ObjectMap<String, BiConsumer<Level, ObjHandler>> MAP = new ObjectMap<>();
	
	static {
		MAP.put("Player", (level, obj) -> {
			level.starting = obj.getPos();
		});
		MAP.put("Goblin", (level, obj) -> {
			level.addEntity(new Goblin(obj));
		});
		MAP.put("Light", (level, obj) -> {
			level.addEntity(new Light(obj));
		});
	}
	
	static void handle(Level level, MapObjects objects) {
		for (MapObject obj : objects) {
			BiConsumer<Level, ObjHandler> consumer = MAP.get(obj.getName());
			if (consumer == null) {
				throw new RuntimeException("Object named \"" + obj.getName() + "\" was not found.");
			}
			consumer.accept(level, new ObjHandler(level, obj));
		}
	}
}
