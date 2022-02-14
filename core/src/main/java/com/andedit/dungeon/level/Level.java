package com.andedit.dungeon.level;

import com.andedit.dungeon.entity.Entity;
import com.andedit.dungeon.entity.Player;
import com.andedit.dungeon.tile.Tile;
import com.andedit.dungeon.tile.TileColors;
import com.andedit.dungeon.tile.Tiles;
import com.andedit.dungeon.tile.entity.TileEntity;
import com.andedit.dungeon.util.TilePos;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Null;

public class Level {
	/** Tile size */
	public static final int SIZE = 16;
	
	public final String id;
	/** Width and height of map's size */
	public final int xSize, ySize;
	public final TileColors colors;
	
	@Null
	public Player player;
	
	/** Player starting position */
	Vector2 starting;
	
	final byte[][] tiles;
	private final Array<TileEntity> tileEntities = new Array<>(false, 128);
	private final Array<Entity> entities = new Array<>(false, 128);
	
	public Level(TiledMap map, TileColors colors) {
		this.colors = colors;
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
		id = map.getProperties().get("id", String.class);
		xSize = layer.getWidth();
		ySize = layer.getHeight();
		tiles = new byte[xSize][ySize];
		for (int x = 0; x < xSize; x++)
		for (int y = 0; y < ySize; y++) {
			Cell tile = layer.getCell(x, y);
			tiles[x][y] = tile == null ? 0 : (byte) (tile.getTile().getId()-1);
		}
		
		MapObjs.handle(this, map.getLayers().get(1).getObjects());
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
		for (int i = 0; i < tileEntities.size; i++) {
			TileEntity e = tileEntities.get(i);
			if (e.isValid()) {
				e.update();
			} else {
				tileEntities.removeIndex(i--);
			}
		}
	}
	
	public Iterable<Entity> getEntities() {
		return entities.iterator();
	}
	
	public Iterable<TileEntity> getTileEntities() {
		return tileEntities.iterator();
	}
	
	/** Get player starting position */
	@Null
	public Vector2 getStarting() {
		return starting;
	}
	
	/** Also moves entity's dimension/level */
	public void addEntity(Entity entity) {
		entity.setLevel(this);
		entities.add(entity);
	}
	
	public void addTileEntity() {
		
	}
	
	public void removeEntity(Entity entity) {
		entities.removeValue(entity, true);
	}

	public Tile getTile(TilePos pos) {
		return getTile(pos.x, pos.y);
	}
	
	@Null
	public TileEntity getTileEntity(TilePos pos) {
		for (TileEntity entity : tileEntities) {
			if (entity.match(pos)) return entity;
		}
		return null;
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
