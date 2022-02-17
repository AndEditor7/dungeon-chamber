package com.andedit.dungeon.graphic;

import com.andedit.dungeon.handle.ObjHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Light {
	// s = size
	public final float r, g, b, x, y, s, scl;
	
	public Light(ObjHandler obj) {
		this(obj.getPos(), obj.get("Color"), obj.get("Strength"), obj.get("Size"));
	}
	
	public Light(Vector2 pos, Color color, float scl, float size) {
		r = color.r;
		g = color.g;
		b = color.b;
		x = pos.x;
		y = pos.y;
		s = size;
		this.scl = scl;
	}
}
