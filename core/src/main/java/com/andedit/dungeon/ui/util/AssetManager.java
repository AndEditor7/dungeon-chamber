package com.andedit.dungeon.ui.util;

import java.util.function.Consumer;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.OrderedMap;

public class AssetManager extends com.badlogic.gdx.assets.AssetManager {
	private final OrderedMap<String, Consumer<?>> consumers = new OrderedMap<>();
	
	public AssetManager() {
		super(new InternalFileHandleResolver(), false);
	}
	
	public <T> void load(String fileName, Class<T> type, Consumer<T> consumer) {
		load(fileName, type);
		consumers.put(fileName, consumer);
	}
	
	public <T> void load(String fileName, Class<T> type, Consumer<T> consumer, AssetLoaderParameters<T> praram) {
		load(fileName, type, praram);
		consumers.put(fileName, consumer);
	}
	
	@Override
	public void clear() {
		super.clear();
		consumers.clear();
	}
	
	public void getAll() {
		for (Entry<String, Consumer<?>> entry : consumers) {
			entry.value.accept(get(entry.key));
		}
	}
}
