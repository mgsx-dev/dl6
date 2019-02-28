package net.mgsx.dl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;

public class BorderPOC {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = EnergyGame.WIDTH * EnergyGame.SCALE;
		config.height = EnergyGame.HEIGHT * EnergyGame.SCALE;
		new LwjglApplication(new ApplicationAdapter() {
			
			@Override
			public void create() {
				Pixmap pm = new Pixmap(Gdx.files.local("../assets/src/tileset.png"));
				Pixmap po = new Pixmap(pm.getWidth(), pm.getHeight(), Format.RGBA8888);
				po.setBlending(Blending.None);
				Color src = new Color();
				Color ne = new Color();
				for(int y=0 ; y<pm.getHeight() ; y++){
					for(int x=0 ; x<pm.getWidth() ; x++){
						Color.rgba8888ToColor(src, pm.getPixel(x, y));
						if(src.a == 0){
							int cnt = 0;
							if(x>0){
								Color.rgba8888ToColor(ne, pm.getPixel(x-1, y));
								if(ne.a > 0) cnt++;
							}
							if(y>0){
								Color.rgba8888ToColor(ne, pm.getPixel(x, y-1));
								if(ne.a > 0) cnt++;
							}
							if(x<pm.getWidth()-1){
								Color.rgba8888ToColor(ne, pm.getPixel(x+1, y));
								if(ne.a > 0) cnt++;
							}
							if(y<pm.getHeight()-1){
								Color.rgba8888ToColor(ne, pm.getPixel(x, y+1));
								if(ne.a > 0) cnt++;
							}
							if(cnt > 0) src.set(Color.BLACK);
						}
						po.drawPixel(x, y, Color.rgba8888(src));
					}
				}
				PixmapIO.writePNG(Gdx.files.local("../assets/src/tileset2.png"), po);
				Gdx.app.exit();
			}
			
		}, config);
	}
}
