package com.andedit.dungeon.lwjgl3;

import org.lwjgl.opengl.GL11;

import com.andedit.dungeon.API;

public class DesktopAPI implements API {
	@Override
	public String getGlVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
}
