package com.andedit.dungeon.ui.util;

@FunctionalInterface
public interface Alignment {
	int getAlign();
	default void setAlign(int align) {
	}
}
