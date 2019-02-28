package net.mgsx.dl3.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import net.mgsx.dl3.assets.GameAssets;
import net.mgsx.dl3.events.PowerOnEvent;
import net.mgsx.dl3.events.ResignEvent;
import net.mgsx.dl3.model.Card;
import net.mgsx.dl3.model.ComponentType;
import net.mgsx.dl3.model.GameRules;

public class EnergyGameUI extends Table
{
	public ComponentType currentType;
	public Image currentImage;
	private Label labelMoney;
	private Card card;
	private Actor gameOverActor;
	private Label labelElectrons;
	
	public EnergyGameUI(Card card) {
		this.card = card;
		ButtonGroup btCompoGroup = new ButtonGroup();
		Table btList = new Table();
		
		final ImageButton btRemove = new ImageButton(GameAssets.i.getButtonRemoveUp(), GameAssets.i.getButtonRemoveDown(), GameAssets.i.getButtonRemoveDown());
		btList.add(btRemove);
		btRemove.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(btRemove.isChecked()) currentType = null;
				if(currentImage != null) currentImage.remove();
				currentImage = new Image(GameAssets.i.getButtonRemoveUp());
			}
		});
		btCompoGroup.add(btRemove);
		
		for(final ComponentType type : GameRules.getComponentTypes()){
			final ImageButton bt = createBuyButton(type.getButtonUp(), type.getButtonDown(), type.cost);
			btList.add(bt);
			btCompoGroup.add(bt);
			bt.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(bt.isChecked()) currentType = type;
					if(currentImage != null) currentImage.remove();
					currentImage = new Image(type.getButtonUp());
				}
			});
			btCompoGroup.add(bt);
		}
		
		Table stats = new Table();
		stats.defaults().space(4);
		
		TextButton btMenu = GameAssets.i.textButton("Resign");
		stats.add(btMenu);
		
		TextButton btPow = GameAssets.i.textButton("Powering");
		stats.add(btPow);
		
		labelMoney = new Label("", new Label.LabelStyle(GameAssets.i.font, Color.WHITE));
		stats.add(labelMoney);
		
		labelElectrons = new Label("", new Label.LabelStyle(GameAssets.i.font, Color.WHITE));
		stats.add(labelElectrons);
		
		add(stats).expandY().top();
		add().expand();
		add(btList).expandY().top();
		
		btMenu.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new ResignEvent());
			}
		});
		btPow.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				fire(new PowerOnEvent());
			}
		});
	}
	
	private ImageButton createBuyButton(Drawable up, Drawable down, int cost){
		final ImageButton bt = new ImageButton(up, down, down);
		bt.row();
		LabelStyle style = new Label.LabelStyle(GameAssets.i.font, Color.WHITE);
		bt.add(new Label(cost + "$", style));
		return bt;
	}
	
	@Override
	public void act(float delta) {
		if(currentImage != null && currentImage.getStage() == null){
			getStage().addActor(currentImage);
		}
		labelMoney.setText("Cash: " + card.money + "$");
		
		labelElectrons.setText("Electrons: " + card.power.electronsRemain);
		
		if(card.shortcut && gameOverActor == null){
			Table t = new Table();
			t.background(GameAssets.i.getButtonDown());
			t.pad(10);
			t.defaults().space(2).fill();
			t.add(GameAssets.i.createLabel("Shorted power supply")).row();
			t.add(GameAssets.i.createLabel("You fried your board")).row();
			t.add(GameAssets.i.createLabel("Game over")).row();
			TextButton btMenu = GameAssets.i.textButton("Well...");
			btMenu.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					fire(new ResignEvent());
				}
			});
			t.add(btMenu).row();
			t.pack();
			t.setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center);
			gameOverActor = t; 
			getStage().addActor(gameOverActor);
		}
		
		if(card.finished && gameOverActor == null){
			Table t = new Table();
			t.background(GameAssets.i.getButtonDown());
			t.pad(10);
			t.defaults().space(2).fill();
			t.add(GameAssets.i.createLabel("Congratulations!")).row();
			t.add(GameAssets.i.createLabel("Your board is safe")).row();
			t.add(GameAssets.i.createLabel("You did a great job")).row();
			TextButton btMenu = GameAssets.i.textButton("Cool!");
			btMenu.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					fire(new ResignEvent());
				}
			});
			t.add(btMenu).row();
			t.pack();
			t.setPosition(getStage().getWidth()/2, getStage().getHeight()/2, Align.center);
			gameOverActor = t; 
			getStage().addActor(gameOverActor);
		}
		
		super.act(delta);
	}

	public void setCursorPosition(Vector2 worldCursor) 
	{
		if(currentImage != null){
			currentImage.setPosition(worldCursor.x, worldCursor.y, Align.center | Align.bottom);
			currentImage.getColor().a = .5f;
			currentImage.setTouchable(Touchable.disabled);
		}
	}
}
