package net.mgsx.dl3;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.utils.PixelPerfectViewport;
import net.mgsx.dl3.utils.StageScreen;

public class TitleScreen extends StageScreen
{
	public TitleScreen() {
		super(new PixelPerfectViewport(EnergyGame.WIDTH, EnergyGame.HEIGHT));
		
		Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
		
		root.setBackground(GameAssets.i.titleScreen());
		
		Table menu = new Table();
		root.add(menu).expandX().center().padTop(172);
		
		for(int i=1 ; i<=2 ; i++){
			TextButton bt = GameAssets.i.textButton("Board " + i);
			menu.add(bt).fill().pad(2).space(3).row();
			final String cardID = "card" + i;
			bt.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					EnergyGame.i().setScreen(new EnergyGameScreen(cardID));
				}
			});
		}
	}
}
