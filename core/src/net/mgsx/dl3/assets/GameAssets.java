package net.mgsx.dl3.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameAssets {
	public static final GameAssets i = new GameAssets();
	public TiledMapTileSet tileset;
	public BitmapFont font;
	private Texture titleScreen;
	
	public void load(){
		TiledMap card1 = new TmxMapLoader().load("../assets/src/card1.tmx");
		tileset = card1.getTileSets().getTileSet(0);
		font = new BitmapFont();
		font.getData().scale(1f / 32f);
		titleScreen = new Texture(Gdx.files.local("../assets/src/title.png"));
	}

	public TextureRegion electron() {
		return tile(6);
	}

	private TextureRegion region(int id) {
		return getTile(id).getTextureRegion();
	}

	public TextureRegion tile(int id) {
		return region(id);
	}
	public TiledMapTile getTile(int id) {
		return tileset.getTile(id+1);
	}

	public Drawable getButtonRemoveUp() {
		return getButtonUp(38);
	}
	public Drawable getButtonRemoveDown() {
		return getButtonDown(38);
	}
	public Drawable getButtonUp(int id) {
		return new TextureRegionDrawable(tileset.getTile(id + 1).getTextureRegion());
	}
	public Drawable getButtonDown(int id) {
		TextureRegionDrawable drawable = (TextureRegionDrawable)getButtonUp(id);
		return drawable.tint(Color.GRAY);
	}

	public Label createLabel(String text) {
		LabelStyle style = new Label.LabelStyle(GameAssets.i.font, Color.WHITE);
		return new Label(text, style);
	}

	public Drawable getButtonUp() 
	{
		return new TextureRegionDrawable(tile(9));
	}
	public Drawable getButtonDown() {
		return new TextureRegionDrawable(tile(10));
	}

	public TiledMap card(String cardID) {
		return new TmxMapLoader().load("../assets/src/" + cardID + ".tmx");
	}

	public Drawable titleScreen() {
		return new TextureRegionDrawable(new TextureRegion(titleScreen));
	}

	public TextButton textButton(String text) {
		Drawable d = getButtonUp();
		TextButtonStyle style = new TextButton.TextButtonStyle(d, d, d, font);
		TextButton bt = new TextButton(text, style);
		return bt;
	}

}
