package com.andedit.dungeon.level;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.util.TilePos;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;

public class Level {
	
	public final String id;
	public final int xSize, ySize;
	public final TileColors colors;
	
	final byte[][] tiles;
	
	private final TiledMap map;
	private final TiledMapTileLayer layer;
	
	private final Array<Entity> entities = new Array<>(false, 64);
	
	public Level(TiledMap map, TileColors colors) {
		this.map = map;
		this.colors = colors;
		layer = (TiledMapTileLayer)map.getLayers().get(0);
		id = map.getProperties().get("id", String.class);
		xSize = layer.getWidth();
		ySize = layer.getHeight();
		tiles = new byte[xSize][ySize];
		for (int x = 0; x < xSize; x++)
		for (int y = 0; y < ySize; y++) {
			Cell tile = layer.getCell(x, y);
			tiles[x][y] = tile == null ? 0 : (byte) (tile.getTile().getId()-1);
		}
	}
	
	public void update() {
		for (int i = 0; i < entities.size; i++) {
			Entity e = entities.get(i);
			e.update();
			e.move();
			if (e.isDead()) {
				entities.removeIndex(i--);
			}
		}
	}
	
	public Iterable<Entity> getEntities() {
		return entities.iterator();
	}
	
	public void addEntity(Entity entity) {
		entity.level = this;
		entities.add(entity);
	}

	public Tile getTile(TilePos pos) {
		return getTile(pos.x, pos.y);
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= xSize || y >= ySize) {
			return Tiles.FLOOR;
		}
		
		return Tiles.get(tiles[x][y]);
	}
	
	public void setTile(TilePos pos, Tile tile) {
		setTile(pos.x, pos.y, tile);
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (x < 0 || y < 0 || x >= xSize || y >= ySize) {
			return;
		}
		
		tiles[x][y] = (byte)tile.getId();
	}
}
