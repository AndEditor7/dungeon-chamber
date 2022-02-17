package com.andedit.dungeon.handle;

import com.andedit.dungeon.level.Level;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;

public class ObjHandler {
	public final Level level;
	public final MapObject obj;
	
	public ObjHandler(Level level, MapObject obj) {
		this.level = level;
		this.obj = obj;
	}
	
	public String getName() {
		return obj.getName();
	}
	
	public MapProperties getProps() {
		return obj.getProperties();
	}
	
	/** @param key property name
	 * @return true if and only if the property exists */
	public boolean containsKey (String key) {
		return getProps().containsKey(key);
	}

	/** @param key property name
	 * @return the value for that property if it exists, otherwise, null
	 * @throws ClassCastException if the object with the given key is not of type clazz */
	@SuppressWarnings("unchecked")
	public <T> T get (String key) {
		return (T) getProps().get(key);
	}

	/** Returns the object for the given key, casting it to clazz.
	 * @param key the key of the object
	 * @param clazz the class of the object
	 * @return the object or null if the object is not in the map
	 * @throws ClassCastException if the object with the given key is not of type clazz */
	public <T> T get (String key, Class<T> clazz) {
		return get(key);
	}
	
	public Vector2 getPos() {
		float x = get("x", Float.class) / Level.SIZE;
		float y = get("y", Float.class) / Level.SIZE;
		return new Vector2(x, y);
	}
}
