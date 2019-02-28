package net.mgsx.dl3.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
		
		this.pad(2);
		
		ButtonGroup btCompoGroup = new ButtonGroup();
		Table btList = new Table();
		btList.defaults().fill().space(2);
		
		final Button btRemove = new Button(GameAssets.i.skin, "toggle");
		btRemove.add(new Image(GameAssets.i.crossDrawable()));
		
		btList.add(btRemove);
		btRemove.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(btRemove.isChecked()) currentType = null;
				if(currentImage != null) currentImage.remove();
				currentImage = new Image(GameAssets.i.crossDrawable());
			}
		});
		btCompoGroup.add(btRemove);
		
		for(final ComponentType type : GameRules.getComponentTypes()){
			final Button bt = createBuyButton(type.drawable(), type.cost);
			btList.add(bt);
			btCompoGroup.add(bt);
			bt.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(bt.isChecked()) currentType = type;
					if(currentImage != null) currentImage.remove();
					currentImage = new Image(type.drawable());
				}
			});
			btCompoGroup.add(bt);
		}
		
		Table stats = new Table();
		stats.defaults().space(4).fill();
		
		TextButton btMenu = GameAssets.i.textButton("Resign");
		stats.add(btMenu);
		
		labelMoney = new Label("", GameAssets.i.skin);
		stats.add(labelMoney);

		stats.row();
		
		TextButton btPow = GameAssets.i.textButton("Powering");
		stats.add(btPow);

		labelElectrons = new Label("", GameAssets.i.skin);
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
	
	private Button createBuyButton(Drawable drawable, final int cost){
		final Button bt = new Button(GameAssets.i.skin, "toggle"){
			@Override
			public void act(float delta) {
				setDisabled(cost > card.money);
				super.act(delta);
			}
		};
		bt.add(new Image(drawable));
		bt.row();
		bt.add(new Label(cost + "", GameAssets.i.skin));
		return bt;
	}
	
	@Override
	public void act(float delta) {
		if(currentImage != null && currentImage.getStage() == null){
			getStage().addActor(currentImage);
		}
		
//		if(currentImage != null){
//			currentImage.setVisible(!(card.shortcut || card.finished));
//		}
		
		labelMoney.setText("Cash: " + card.money);
		
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
			if(currentType != null){
				currentImage.setPosition(worldCursor.x, worldCursor.y, Align.center | Align.bottom);
			}else{
				currentImage.setPosition(worldCursor.x, worldCursor.y, Align.center);
			}
			currentImage.getColor().a = .5f;
			currentImage.setTouchable(Touchable.disabled);
		}
	}

	public void setCursorVisible(boolean state) {
		if(currentImage != null){
			currentImage.setVisible(state);
		}
	}
}
