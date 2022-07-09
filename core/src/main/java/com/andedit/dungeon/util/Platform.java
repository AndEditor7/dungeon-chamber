package com.andedit.dungeon.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;

public enum Platform {
	DESKTOP, MOBILE, BOTH;
	
	public static Platform toPlatform(ApplicationType type) {
		switch (type) {
			case Desktop: return DESKTOP;
			case Android:
			case iOS: return MOBILE;
			default: throw new IllegalArgumentException("unsuported platform.");
		}
	}
	
	public static Platform getPlatform() {
		return toPlatform(Gdx.app.getType());
	}
}
