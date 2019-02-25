package net.mgsx.dl3;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = EnergyGame.WIDTH * EnergyGame.SCALE;
		config.height = EnergyGame.HEIGHT * EnergyGame.SCALE;
		new LwjglApplication(new EnergyGame(), config);
	}
}
