package net.mgsx.dl3;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ObjectMap.Entry;

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
		
		int cnt = 0;
		for(Entry<String, String> entry : GameAssets.i.cards){
			if(cnt % 4 == 0){
				menu.row();
			}
			TextButton bt = GameAssets.i.textButton(entry.key);
			menu.add(bt).fill().pad(2).space(3);
			final String cardID = entry.value;
			bt.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					EnergyGame.i().setScreen(new EnergyGameScreen(cardID));
				}
			});
			cnt++;
		}
	}
}
