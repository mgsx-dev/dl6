package net.mgsx.dl3.assets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameAssets {
	public static final GameAssets i = new GameAssets();
	public TiledMap card1;
	public TiledMapTileSet tileset;
	public BitmapFont font;
	
	public void load(){
		card1 = new TmxMapLoader().load("../assets/src/card2.tmx");
		tileset = card1.getTileSets().getTileSet(0);
		font = new BitmapFont();
		font.getData().scale(1f / 32f);
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

	public Drawable getButtonRemoveUp() {
		return getButtonUp(38);
	}
	public Drawable getButtonRemoveDown() {
		return getButtonDown(38);
	}
	public Drawable getButtonUp(int id) {
		TiledMapTileSet tileset = GameAssets.i.card1.getTileSets().getTileSet(0);
		return new TextureRegionDrawable(tileset.getTile(id + 1).getTextureRegion());
	}
	public Drawable getButtonDown(int id) {
		TextureRegionDrawable drawable = (TextureRegionDrawable)getButtonUp(id);
		return drawable.tint(Color.GRAY);
	}
}
