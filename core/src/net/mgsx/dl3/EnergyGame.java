package net.mgsx.dl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.mgsx.dl3.assets.GameAssets;

public class EnergyGame extends Game {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 350;
	public static final int SCALE = 2;
	
	public static final EnergyGame i(){
		return (EnergyGame)Gdx.app.getApplicationListener();
	}
	
	@Override
	public void create () {
		GameAssets.i.load();
		// setScreen(new EnergyGameScreen("card2"));
		setScreen(new TitleScreen());
	}
	
}
