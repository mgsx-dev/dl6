package net.mgsx.dl3.assets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class GameAssets {
	public static final GameAssets i = new GameAssets();
	public TiledMap card1;
	public TiledMapTileSet tileset;
	
	public void load(){
		card1 = new TmxMapLoader().load("../assets/src/card1.tmx");
		tileset = card1.getTileSets().getTileSet(0);
	}

	public TextureRegion electron() {
		return tile(6);
	}

	private TextureRegion region(int id) {
		return tileset.getTile(id+1).getTextureRegion();
	}

	public TextureRegion tile(int id) {
		return region(id);
	}
}
