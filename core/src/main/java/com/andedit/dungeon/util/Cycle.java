package com.andedit.dungeon.util;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.utils.Array;

public class Cycle<T> {
	private final T[] array;
	private int idx;
	
	public Cycle(T[] array) {
		this.array = array;
	}
	
	public Cycle(Array<T> array) {
		this(Arrays.copyOf(array.items, array.size));
	}
	
	@SuppressWarnings("unchecked")
	public Cycle(List<T> list) {
		this((T[])list.toArray());
	}
	
	public int size() {
		return array.length;
	}
	
	public T get() {
		return get(idx %= size());
	}
	
	public T get(int idx) {
		return array[idx];
	}
	
	public T next() {
		return get(idx++ % size());
	}
	
	public T previous() {
		return get(idx-- % size());
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public boolean notEmpty() {
		return !isEmpty();
	}
}
