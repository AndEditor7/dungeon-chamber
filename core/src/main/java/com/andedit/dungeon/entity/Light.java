package com.andedit.dungeon.entity;

import com.andedit.dungeon.ui.util.ObjHandler;
import com.badlogic.gdx.graphics.Color;

public class Light extends Entity {
	public final Color color;
	public final float size;
	
	public Light(ObjHandler obj) {
		super(obj);
		color = obj.get("Color");
		size = obj.get("Size");
	}
}
