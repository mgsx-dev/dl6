package net.mgsx.dl3;

import com.badlogic.gdx.Game;

import net.mgsx.dl3.assets.GameAssets;

public class EnergyGame extends Game {
	public static final int WIDTH = 600;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	
	
	@Override
	public void create () {
		GameAssets.i.load();
		setScreen(new EnergyGameScreen());
	}
	
}
